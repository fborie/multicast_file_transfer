package cl.uandes.so.server;

import cl.uandes.so.server.RequestChunkProtos.RequestChunk;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

/**
 * Created by fjborie on 08-12-15.
 */
public class MulticastServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private AppStatus appStatus;
    
    MulticastServerHandler(AppStatus appStatus) {
        this.appStatus = appStatus;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket msg) throws Exception {
        if(msg.content().readableBytes() < 5) {
            return;
        }
        msg.content().readByte(); //0xFF
        msg.content().readByte(); //0xFF
        msg.content().readByte(); //0xFF
        msg.content().readByte(); //0xFF
        // Payload type
        char a = (char)msg.content().readByte();
        
        short length;
        byte[] payload;
        byte[] shortlength;
        switch(a) {
            // File Announcement
            case 'B':
                if(!appStatus.isReceivingRequests) {
                    return;
                }
                byte[] a_length = {msg.content().readByte(), msg.content().readByte()};
                System.out.println();
                //System.out.println("[B] packet received - Length: " +  Utils.getShortFromLittleEndianRange(a_length));
                payload = msg.content().readBytes(msg.content().readableBytes()).array();
                RequestChunk r = RequestChunk.parseFrom(payload);
                //System.out.format("Client requesting fragment %d", r.getChunkNumber());
                // TODO: r.getChunkNumber() < maxfragments
                appStatus.requested_fragments[r.getChunkNumber()] = true;
                break;
        }
    }
}
