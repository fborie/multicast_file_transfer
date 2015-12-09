package cl.uandes.so.client;

import static cl.uandes.so.client.Utils.SHAsum;
import cl.uandes.so.server.RequestChunkProtos.RequestChunk;
import cl.uandes.so.server.RequestChunkProtos.RequestChunkOrBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ChannelFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Formatter;
import com.google.common.io.Files;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException, UnknownHostException, SocketException, IOException, NoSuchAlgorithmException
    {

        if(args.length <=2){
            System.out.println("usage: java -jar target/client.jat <IP_iface> <server_login_ip> <server_login_port>");
            System.exit(1);
        }

        String ipIface = args[0];
        String serverLoginIp = args[1];
        int serverLoginPort = Integer.parseInt(args[2]);

        StringBuilder multicast_address = new StringBuilder();
        System.out.println("Trying to connect to server...");
        
        int maxtries = 3;
        int tries = 1;
        while (tries <= maxtries) {
            System.out.format("== Try #%d ==", tries);
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(group)
                 .channel(NioDatagramChannel.class)
                 //.option(ChannelOption.SO_BROADCAST, true)
                 .handler(new LoginClientHandler(multicast_address));

                Channel ch = b.bind(0).sync().channel();

                byte[] payload = {(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0x41, (byte)0x67, (byte)0x65, (byte)0x74, (byte)0x41, (byte)0x64, (byte)0x64, (byte)0x72, (byte)0x65, (byte)0x73, (byte)0x73, (byte)0x26, (byte)0x50, (byte)0x6f, (byte)0x72, (byte)0x74 };

                // Broadcast the QOTM request to port 8080.
                ch.writeAndFlush(new DatagramPacket(
                        Unpooled.copiedBuffer( payload),
                        new InetSocketAddress(serverLoginIp, serverLoginPort))).sync();

                // QuoteOfTheMomentClientHandler will close the DatagramChannel when a
                // response is received.  If the channel is not closed within 5 seconds,
                // print an error message and quit.
                if (!ch.closeFuture().await(2000)) {
                    System.err.println("Login request timed out.");
                    tries += 1;
                } else {
                    group.shutdownGracefully();
                    break;
                }
            } finally {
                group.shutdownGracefully();
            }
        }
        if(tries == maxtries+1) {
            System.out.format("Gave up after %d tries", maxtries);
            System.exit(1);
        }
        
        
        System.out.println("Got Multicast Address: " + multicast_address);
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
        NetworkInterface nif = NetworkInterface.getByInetAddress(InetAddress.getByName(ipIface)); //Get eth0 or current iface by the ip given in the args[0]
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
        cb.handler(new MultiCastClientHandler(appStatus));
        String ip_address = multicast_address.substring(0, multicast_address.indexOf(":"));
        System.out.println("Multicast IP: "+ip_address);
        
        int port = Integer.parseInt(multicast_address.substring(multicast_address.indexOf(":")+1, multicast_address.length()));
        System.out.println("Multicast Port: "+port);
        
        
        cb.localAddress(port);
        cb.option(ChannelOption.SO_BROADCAST, true)
                            .option(ChannelOption.SO_REUSEADDR, true)
                            .option(ChannelOption.IP_MULTICAST_LOOP_DISABLED, false)
                            .option(ChannelOption.SO_RCVBUF, 2048)
                            .option(ChannelOption.IP_MULTICAST_TTL, 255)
                            .option(ChannelOption.IP_MULTICAST_IF, nif);
        cb.group(group);
//        cb.channel(NioDatagramChannel.class);
        DatagramChannel cc = (DatagramChannel) cb.bind().sync().channel();
        cc.joinGroup(new InetSocketAddress(ip_address, port), nif).sync();
        // 'B' Packet
        final byte[] requestchunk_header = {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x42};
        while(true) {
            // Search for remaining chunks
            if(appStatus.hasFileAnnouncement) {
                System.out.printf("Received %d/%d chunks, time since last data packet: %d\n", appStatus.receivedChunksCount, appStatus.receivedChunks.length, appStatus.timeSinceLastResponse);
            }
            
            if(appStatus.receivedChunks != null && appStatus.getReceivedChunksCount() == appStatus.receivedChunks.length ) {
                // File Transfer is over
                // Verify Integrity
                String receivedfilechecksum = SHAsum(appStatus.filecontent);
                if(!receivedfilechecksum.equals(appStatus.fa.getChecksum())) {
                    System.out.format("Corrupted file transfer, got checksum %s, expected %s", receivedfilechecksum, appStatus.fa.getChecksum() );
                    System.exit(1);
                }
                Files.write(appStatus.filecontent, new File(appStatus.filename));
                System.out.printf("Successfully written %d bytes to file %s\n", appStatus.filesize, appStatus.filename);
                group.shutdownGracefully();
                group2.shutdownGracefully();
                System.exit(0);
            }
            
            if(appStatus.hasFileAnnouncement && appStatus.timeSinceLastResponse > 1 && appStatus.timeSinceLastResponse < 5) {
                for(int i = 0; i < appStatus.receivedChunks.length; i++) {
                    if(!appStatus.receivedChunks[i]) {
                        // Request Chunk
                        RequestChunk c = RequestChunk.newBuilder().setChunkNumber(i).build();
                        short length = (short)c.toByteArray().length;
                        byte[] bytelength = {(byte)(length & 0xff), (byte)((length >> 8) & 0xff)}; 
                        System.out.println("Sending chunk request packet...");
                        sc.writeAndFlush(new DatagramPacket(
                        Unpooled.copiedBuffer( requestchunk_header, bytelength, c.toByteArray()),
                        new InetSocketAddress(ip_address, port))).sync();
                        Thread.sleep(1);
                        //if(appStatus.timeSinceLastResponse == 0) break; // racing
                    }
                }
            }
            //System.out.println("Time since last response: " + appStatus.timeSinceLastResponse+1);
            appStatus.timeSinceLastResponse += 1;
            Thread.sleep(1000);
        }
        
    }
    
    public static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
    
}
