/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.uandes.so.client;

import static cl.uandes.so.client.App.byteArray2Hex;
import cl.uandes.so.server.FileTransferProtos.FileFragment;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import static cl.uandes.so.client.Utils.*;

/**
 *
 * @author rodrigo
 */
public class MultiCastClientHandler extends SimpleChannelInboundHandler<DatagramPacket>{
    private AppStatus appStatus;
    public MultiCastClientHandler(AppStatus appStatus) {
        this.appStatus = appStatus;
    }
        @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        if(msg.content().readableBytes() < 6) {
            return;
        }
        msg.content().readByte(); //0xFF
        msg.content().readByte(); //0xFF
        msg.content().readByte(); //0xFF
        msg.content().readByte(); //0xFF
        
        // Payload type
        char a = (char)msg.content().readByte();
        byte[] ab = {(byte)a};
        short length;
        byte[] payload;
        byte[] shortlength;
        switch(a) {
            // File Announcement
            case 'A':
                if(appStatus.hasFileAnnouncement) {
                    // We already know about the file we are going to receive
                    break;
                }
                byte[] a_length = {msg.content().readByte(), msg.content().readByte()};
                System.out.println();
                System.out.println("[A] packet received - Length: " +  Utils.getShortFromLittleEndianRange(a_length));
                payload = msg.content().readBytes(msg.content().readableBytes()).array();
                // TODO: Deserializar payload con protobuf
                FileAnnouncementProtos.FileAnnouncement fa = FileAnnouncementProtos.FileAnnouncement.parseFrom(payload);
                System.out.println("===============================================");
                System.out.println("Announcement Packet Received");
                System.out.println(" >cheksum: "+fa.getChecksum());
                System.out.println(" >fileName: "+fa.getFileName());
                System.out.println(" >fileSize: "+fa.getFileSize());
                System.out.println(" >chunksTotal: "+fa.getChunksTotal());
                System.out.println("===============================================");
                appStatus.fa = fa;
                appStatus.filename = fa.getFileName();
                appStatus.filesize = fa.getFileSize();
                // Prepare byte array to store chunks
                appStatus.filecontent = new byte[fa.getFileSize()];
                // Bool array for received chunks
                appStatus.receivedChunks = new boolean[fa.getChunksTotal()];
                for(int i = 0; i < fa.getChunksTotal(); i++) {
                    appStatus.receivedChunks[i] = false;
                }
                appStatus.hasFileAnnouncement = true;
                
                break;
            case 'B':
                //System.err.println("Ignoring B Packet...");
                break;
            case 'C':              
                if(!appStatus.hasFileAnnouncement) {
                    // We don't know what we are receiving
                    return;
                }
                // Reset Timer
                appStatus.timeSinceLastResponse = 0;
                byte[] c_length = {msg.content().readByte(), msg.content().readByte()};
                //System.out.println("[C] packet received - Length: " + Utils.getShortFromLittleEndianRange(c_length));
                payload = msg.content().readBytes(msg.content().readableBytes()).array();
                FileFragment f = FileFragment.parseFrom(payload);
                if(appStatus.receivedChunks[f.getId()] == true) {
                    // We already received this chunk
                    return;
                }
                // Verify Chunk integrity
                if(SHAsum(f.getData().toByteArray()).equals(f.getChecksum())) {
                    //System.out.printf("Got fragment %d, checksum OK", f.getId());
                    appStatus.receivedChunks[f.getId()] = true;
                    for(long i = f.getStart(); i < f.getEnd()+1; i++) {
                        int chunkindex = (int)(i - f.getStart());
                        appStatus.filecontent[(int)i] = f.getData().toByteArray()[chunkindex];
                    }
                    appStatus.incrementReceivedChunks();
                }
                
                break;
            default:
                System.out.println(String.format("Unknown packet type received, got %c", a));
                System.out.println(byteArray2Hex(ab));

        }
        
        //ByteBuf data = msg.content().readBytes(msg.content().readableBytes());
        
        //System.out.println(new String(data.array()));
        
        

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
