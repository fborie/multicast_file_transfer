/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.uandes.so.client;

import static cl.uandes.so.client.App.byteArray2Hex;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

/**
 *
 * @author rodrigo
 */
public class MultiCastClientHandler extends SimpleChannelInboundHandler<DatagramPacket>{
    
        @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        if(msg.content().readableBytes() < 6) {
            return;
        }
        msg.content().readByte();
        msg.content().readByte();
        msg.content().readByte();
        msg.content().readByte();
        
        // Payload type
        char a = (char)msg.content().readByte();
        byte[] ab = {(byte)a};
        short length;
        byte[] payload;
        switch(a) {
            case 'A':
                length = msg.content().readShort();
                System.out.println("A packet received - Length: " + length);
                payload = msg.content().readBytes(msg.content().readableBytes()).array();
                break;
            case 'B':
                length = msg.content().readShort();
                System.out.println("A packet received - Length: " + length);
                payload = msg.content().readBytes(msg.content().readableBytes()).array();
                break;
            case 'C':
                length = msg.content().readShort();
                System.out.println("A packet received - Length: " + length);
                payload = msg.content().readBytes(msg.content().readableBytes()).array();
                break;
            default:
                System.out.println(String.format("Unknown packet type received, got %c", a));
                System.out.println(byteArray2Hex(ab));

        }
        
        ByteBuf data = msg.content().readBytes(msg.content().readableBytes());
        
        System.out.println(new String(data.array()));
        
        

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
