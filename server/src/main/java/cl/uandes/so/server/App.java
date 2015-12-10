package cl.uandes.so.server;


import java.io.File;
import java.io.IOException;
//import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

// Protobuf
// protoc -I=main/java/cl/uandes/so/server/ --java_out main/java/ main/java/cl/uandes/so/server/FileTransfer.proto
import cl.uandes.so.server.FileTransferProtos.FileFragment;
import cl.uandes.so.server.FileTransferProtos.FileFragmentOrBuilder;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.ByteString;

// Netty
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ChannelFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.apache.commons.lang3.ArrayUtils;

//import java.nio.file.Paths;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        if(args.length <= 1) {
            System.out.println("usage: java -jar server.jar <filename> <IP_iface> <login_server_port> <MSS>");
            System.exit(1);
        }
        
        String ipIface = args[1];
        int loginPort = Integer.parseInt(args[2]);
        File f = new File(args[0]);
        final int MAX_SEGMENT_SIZE = Integer.parseInt(args[3]);
        if(!f.exists() || !f.canRead()) {
            System.out.println(String.format("File %s not found or access denied.", args[0]));
            System.exit(1);
        }
        Long size = f.length();
        System.out.println( "Abriendo archivo "  + args[0] + " con " + size.toString() + " bytes.");
        
        // Calcular numero de fragmentos necesarios para enviar archivos
        Long fragments_required = 1L;
        while(MAX_SEGMENT_SIZE*(fragments_required) < size) {
            fragments_required += 1;
        }
        
        
        System.out.println(String.format("Se requieren %d fragmentos para %d bytes", fragments_required, size));
        
        // Volcar archivo completo a arreglo de bytes
        byte[] filecontent = Files.readAllBytes(f.toPath());
        
        // Crear lista de fragmentos
        ArrayList fragments = new ArrayList();
        int i = 0;
        long bytecount = 0;
        long remaining_bytes = size;
        while(i < fragments_required) {
            Fragment chunk;
            if(remaining_bytes > MAX_SEGMENT_SIZE-1) {
               // Full Fragment
                chunk = new Fragment(i, bytecount, bytecount+MAX_SEGMENT_SIZE-1);
                bytecount += MAX_SEGMENT_SIZE;
                remaining_bytes -= MAX_SEGMENT_SIZE;
            } else {
                // Debiera ser el último fragmento incompleto
                // System.out.println("Fragmento incompleto");
                chunk = new Fragment(i, bytecount, bytecount+remaining_bytes-1);
                bytecount += remaining_bytes;
                remaining_bytes = 0;
            }
            fragments.add(chunk);
            i += 1;
        }
        
        // Calcular hash de fragmentos
        for(Object item : fragments) {
            Fragment chunk = (Fragment)item;
            String checksum = "";
            try {
            checksum = Utils.SHAsum(Arrays.copyOfRange(filecontent, (int)chunk.start, (int)chunk.end+1));
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Couldn't find SHA1 algorithm...");
                System.exit(1);
            }
            
            chunk.setHash(checksum);
            System.out.println(String.format("Fragment %d: %d-%d (Length: %d) (Checksum: %s)", chunk.getID(), chunk.start, chunk.end, (chunk.end-chunk.start)+1, chunk.checksum));
            
        }
        
        /*
        // Prueba de generación de mensaje Protobuf con fragmento
        // Resultado: El largo del mensaje con un fragmento completo (1400) es de 1446 bytes
        Fragment first = (Fragment)fragments.get(0);
        FileFragment ff = FileFragment.newBuilder()
                .setId((int)first.getID())
                .setChecksum(first.checksum)
                .setData(ByteString.copyFrom(filecontent, (int)first.start, (int)first.end))
                .build();
        System.out.println(String.format("Debug: Largo de primer fragmento: %d", ff.toByteArray().length));
        */
        
        System.out.println(String.format("The file has been fragmented successfully.", filecontent.length));
        
        // TODO: Netty
        System.out.println("Starting socket...");
        String multicast_address = "239.1.2.3:1111";
        
        LoginServer ls = new LoginServer(loginPort, multicast_address);
        
        try {
         ls.run();
        } catch(Exception e) {
            System.out.println(e.toString());
        }


        System.out.println("File Announcement");
        byte[] filename = args[0].getBytes();
        byte[] fileSize = Longs.toByteArray(f.length());
        byte[] chunksTotal = Ints.toByteArray(fragments.size());
        byte[] aux = ArrayUtils.addAll(filename,fileSize);
        byte[] concatBytes = ArrayUtils.addAll(aux,chunksTotal);
        // Checksum del archivo
        String checksum;
        try {
            checksum = Utils.SHAsum(filecontent);
            final FileAnnouncementChunk fac = new FileAnnouncementChunk(checksum,f.getName(),f.length(), fragments.size());

            EventLoopGroup group = new NioEventLoopGroup();
            EventLoopGroup group2 = new NioEventLoopGroup();
            Bootstrap cb = new Bootstrap();
            Bootstrap sb = new Bootstrap();
            
            sb.handler(new SimpleChannelInboundHandler<Object>() {
                @Override
                protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                    // Nothing will be sent.
                }
            });

            NetworkInterface nif = NetworkInterface.getByInetAddress(InetAddress.getByName(ipIface)); // get eth0 o current iface that has assigned your local ipv4
            sb.option(ChannelOption.IP_MULTICAST_IF, nif);
            sb.option(ChannelOption.SO_REUSEADDR, true);
            sb.channel(NioDatagramChannel.class);
            sb.localAddress(27005);
            sb.group(group2);
            final Channel sc = sb.bind().sync().channel();
            
            cb.channelFactory(new ChannelFactory<NioDatagramChannel>() {
                @Override
                public NioDatagramChannel newChannel() {
                    return new NioDatagramChannel(InternetProtocolFamily.IPv4);
                }
            });
            
            AppStatus appStatus = new AppStatus();
            appStatus.requested_fragments = new boolean[fragments.size()];
            for(i = 0; i < fragments.size(); i++) {
                appStatus.requested_fragments[i] = false;
            }
            
            cb.handler(new MulticastServerHandler(appStatus));
            final String ip_address = multicast_address.substring(0, multicast_address.indexOf(":"));
            final int port = Integer.parseInt(multicast_address.substring(multicast_address.indexOf(":")+1, multicast_address.length()));


           
            cb.localAddress(port);
            cb.option(ChannelOption.SO_BROADCAST, true)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.IP_MULTICAST_LOOP_DISABLED, false)
                    .option(ChannelOption.SO_RCVBUF, 2048)
                    //.option(ChannelOption.IP_MULTICAST_TTL, 255)
                    .option(ChannelOption.IP_MULTICAST_IF, nif);
            cb.group(group);
//        cb.channel(NioDatagramChannel.class);
            final DatagramChannel cc = (DatagramChannel) cb.bind().sync().channel();
            cc.joinGroup(new InetSocketAddress(ip_address, port), nif).sync();
            final byte[] header = {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x41};
            
            
            Thread announcer = new Thread(new Runnable() {
                public void run() {
                    for(int i=0;i<4;i++){
                        byte[] message = Utils.generateFileAnnouncementMessage(fac).toByteArray();
                        short length = (short)message.length;
                        byte[] bytelength = {(byte)(length & 0xff), (byte)((length >> 8) & 0xff)}; 
                        ByteBuf pckt = Unpooled.copiedBuffer(header, bytelength, message );
                        DatagramPacket a = new DatagramPacket(pckt, new InetSocketAddress(ip_address, port));
                        sc.writeAndFlush(a);
                        System.out.println();
                        System.out.println("File Announcement Sent... Length: "+ length);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            announcer.start();
            Thread.sleep(1000);
            
            
            // 'C' Packet Header 
            final byte[] filefragment_header = {(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0x43};
            // Start sending chunks
            for(i = 0; i < fragments.size(); i++) {
                FileFragment payload = Utils.generateFileFragment((Fragment)fragments.get(i), filecontent);
                short length = (short)payload.toByteArray().length;
                byte[] bytelength = {(byte)(length & 0xff), (byte)((length >> 8) & 0xff)}; 
                ByteBuf pckt = Unpooled.copiedBuffer(filefragment_header, bytelength, payload.toByteArray() );
                        DatagramPacket a = new DatagramPacket(pckt, new InetSocketAddress(ip_address, port));
                        sc.writeAndFlush(a);
                Thread.sleep(3);
                //sc.writeAndFlush(a);
                //Thread.sleep(5);
                //System.out.println("Sent Fragment " + i);
            }
            appStatus.isReceivingRequests = true;
            Thread.sleep(3000);
            int no_request_rounds = 0;
            System.out.println("Looking for lost fragments...");
            while(true) {
                boolean gotRequests = false;
                for(i = 0; i < appStatus.requested_fragments.length; i++) {
                    if(appStatus.requested_fragments[i]) {
                        no_request_rounds  = 0;
                        gotRequests = true;
                        FileFragment payload = Utils.generateFileFragment((Fragment)fragments.get(i), filecontent);
                        short length = (short)payload.toByteArray().length;
                        byte[] bytelength = {(byte)(length & 0xff), (byte)((length >> 8) & 0xff)}; 
                        ByteBuf pckt = Unpooled.copiedBuffer(filefragment_header, bytelength, payload.toByteArray() );
                        DatagramPacket a = new DatagramPacket(pckt, new InetSocketAddress(ip_address, port));
                        sc.writeAndFlush(a);
                        //System.out.println("Sent requested fragment " + i);
                        appStatus.requested_fragments[i] = false;
                        Thread.sleep(1);
                    }
                }
                if(!gotRequests) {
                    no_request_rounds += 1;
                }
                if(no_request_rounds > 40) {
                    System.out.println("It seems that all clients received the file.");
                    group.shutdownGracefully();
                    group2.shutdownGracefully();
                    System.exit(0);
                }
                Thread.sleep(250);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Finish");
        
    }

}
