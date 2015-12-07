package cl.uandes.so.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;
import java.util.Formatter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioDatagramChannel.class)
             //.option(ChannelOption.SO_BROADCAST, true)
             .handler(new LoginClientHandler());

            Channel ch = b.bind(0).sync().channel();
            
            byte[] payload = {(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0x41, (byte)0x67, (byte)0x65, (byte)0x74, (byte)0x41, (byte)0x64, (byte)0x64, (byte)0x72, (byte)0x65, (byte)0x73, (byte)0x73, (byte)0x26, (byte)0x50, (byte)0x6f, (byte)0x72, (byte)0x74 };
            
            // Broadcast the QOTM request to port 8080.
            ch.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer( payload),
                    new InetSocketAddress("127.0.0.1", 9988))).sync();

            // QuoteOfTheMomentClientHandler will close the DatagramChannel when a
            // response is received.  If the channel is not closed within 5 seconds,
            // print an error message and quit.
            if (!ch.closeFuture().await(5000)) {
                System.err.println("Login request timed out.");
            }
        } finally {
            group.shutdownGracefully();
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
