/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.uandes.so.server;

/**
 *
 * @author rodrigo
 */
public class Fragment {
    public long start;
    public long end;
    private long id;
    public String checksum;
    
    public Fragment(long id, long start, long end) {
        this.id = id;
        this.start = start;
        this.end = end;
        
    }
    
    void setHash(String hash) {
        this.checksum = hash;
        
    }
    
    long getID(){
        return this.id;
    }
    
}
