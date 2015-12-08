package cl.uandes.so.client;

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

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException, UnknownHostException, SocketException
    {

        if(args.length <=1){
            System.out.println("usage: java -jar target/client.jat <server_login_ip> <server_login_port>");
            System.exit(1);
        }

        String serverLoginIp = args[0];
        int serverLoginPort = Integer.parseInt(args[1]);

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
        Bootstrap cb = new Bootstrap();
        cb.channelFactory(new ChannelFactory<NioDatagramChannel>() {
                                            @Override
                                            public NioDatagramChannel newChannel() {
                                                    return new NioDatagramChannel(InternetProtocolFamily.IPv4);
                                            }
                                    });
        cb.handler(new MultiCastClientHandler());
        String ip_address = multicast_address.substring(0, multicast_address.indexOf(":"));
        System.out.println("Multicast IP: "+ip_address);
        
        int port = Integer.parseInt(multicast_address.substring(multicast_address.indexOf(":")+1, multicast_address.length()));
        System.out.println("Multicast Port: "+port);
        
        
        NetworkInterface nif = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
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
        
        
    }
    
    public static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
    
}
