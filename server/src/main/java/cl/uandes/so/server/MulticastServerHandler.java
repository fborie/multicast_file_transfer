package cl.uandes.so.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

/**
 * Created by fjborie on 08-12-15.
 */
public class MulticastServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        if(packet.content().readableBytes() < 4) {
            return;
        }
        ByteBuf data = packet.content().readBytes(packet.content().readableBytes());
        System.out.format("GOT %s", new String(data.array()));
    }
}
