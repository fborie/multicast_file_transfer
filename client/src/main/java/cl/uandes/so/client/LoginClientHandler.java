/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.uandes.so.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import static cl.uandes.so.client.App.byteArray2Hex;
import io.netty.buffer.ByteBuf;
/**
 *
 * @author rodrigo
 */
class LoginClientHandler  extends SimpleChannelInboundHandler<DatagramPacket> {
    private StringBuilder address;
    public LoginClientHandler(StringBuilder address) {
        this.address = address;
    }
    
    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        if(msg.content().readableBytes() < 3) {
            return;
        }
        byte[] address = null;
        String ip = "";
        byte[] test = {msg.content().readByte()};
        System.out.println(byteArray2Hex(test));
        int readbytes = 1;
        if(msg.content().readByte() == (byte)0x52 ) {
            System.out.println("Byte 0x52 found, readablebytes: " + msg.content().readableBytes());
            ByteBuf data = msg.content().readBytes(msg.content().readableBytes());
            ip += new String(data.array());
            //System.out.println(byteArray2Hex(data.array()));
            
            
            this.address.append(ip);
            ctx.close();
        }
        
        

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
}
