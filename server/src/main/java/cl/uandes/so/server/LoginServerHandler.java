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
        String expected_payload = "ffffffffffffffff416765744164647265737326506f72740a";
        //System.err.println(packet);
        //System.out.println(packet.toString());
        
        ByteBuf data = packet.content().readBytes(packet.content().readableBytes());
        System.out.println(byteArray2Hex(data.array()));
        if(byteArray2Hex(data.array()).equals(expected_payload)) {
            System.out.println("Got login, sending multicast group address");
            ctx.write(new DatagramPacket(
                    Unpooled.copiedBuffer(multicastgroup, CharsetUtil.UTF_8), packet.sender()));
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
