/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.uandes.so.client;

import cl.uandes.so.client.FileAnnouncementProtos.FileAnnouncement;

/**
 *
 * @author rodrigo
 */
public class AppStatus {
    public boolean hasFileAnnouncement = false;
    public byte[] filecontent;
    public boolean[] receivedChunks;
    public int receivedChunksCount = 0;
    public FileAnnouncement fa;
    public int timeSinceLastResponse = 0;
    public String filename;
    public long filesize;
    
    public AppStatus() {
        
        
    }
    
    public synchronized void incrementReceivedChunks() {
        this.receivedChunksCount += 1;
    }

    public synchronized int getReceivedChunksCount() {
        return this.receivedChunksCount;
    }
}
