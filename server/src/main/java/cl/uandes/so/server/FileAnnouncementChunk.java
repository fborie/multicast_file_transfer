package cl.uandes.so.server;

/**
 * Created by fjborie on 08-12-15.
 */
public class FileAnnouncementChunk {

    private String checksum;
    private String fileName;
    private Long fileSize;
    private int chunksTotal;

    public FileAnnouncementChunk(String checksum, String fileName, Long fileSize, int chunksTotal){
        this.checksum = checksum;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.chunksTotal = chunksTotal;
    }

    public String GetChecksum(){
        return checksum;
    }

    public String GetFileName(){
        return fileName;
    }

    public Long GetFileSize(){
        return fileSize;
    }

    public int GetChunksTotal(){
        return chunksTotal;
    }
}
