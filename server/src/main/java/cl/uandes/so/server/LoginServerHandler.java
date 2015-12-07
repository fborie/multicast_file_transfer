/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.uandes.so.server;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import static cl.uandes.so.server.App.byteArray2Hex;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author rodrigo
 */
class LoginServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private String multicastgroup;
    public LoginServerHandler(String multicastgroup) {
        this.multicastgroup = multicastgroup;
    }
    
    
    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        //if(packet.content().readableBytes() < 24) {
        //    return;
        //}
        // TEST Cliente: echo -e "\xff\xff\xff\xff\xff\xff\xff\xff\x41\x67\x65\x74\x41\x64\x64\x72\x65\x73\x73\x26\x50\x6f\x72\x74" | nc -4 -w 1 -u 127.0.0.1 9990
        String expected_payload = "ffffffffffffffff416765744164647265737326506f7274";
        //System.err.println(packet);
        //System.out.println(packet.toString());
        
        byte[] payload = {(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0x41, (byte)0x67, (byte)0x65, (byte)0x74, (byte)0x41, (byte)0x64, (byte)0x64, (byte)0x72, (byte)0x65, (byte)0x73, (byte)0x73, (byte)0x26, (byte)0x50, (byte)0x6f, (byte)0x72, (byte)0x74 };

        
        ByteBuf data = packet.content().readBytes(packet.content().readableBytes());
        System.out.println(data.array().length);
        
        byte[] header = {(byte)0xff, (byte)0x52};
        
        
        System.out.println(byteArray2Hex(data.array()));
        if(byteArray2Hex(data.array()).equals(byteArray2Hex(payload) )) {
            System.out.println("Got login, sending multicast group address (header+multicastgroup");
            ctx.write(new DatagramPacket(
                    Unpooled.copiedBuffer(ArrayUtils.addAll(header, multicastgroup.getBytes())), packet.sender()));
        } 

        
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        // We don't close the channel because we can keep serving requests.
    }

}
