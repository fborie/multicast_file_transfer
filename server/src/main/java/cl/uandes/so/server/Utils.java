package cl.uandes.so.server;

import cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement;
import cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncementOrBuilder;
import com.google.common.primitives.Ints;
import com.google.protobuf.ByteString;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by fjborie on 08-12-15.
 */
public class Utils {

    public static short getShortFromLittleEndianRange(byte[] range){
        return (short)((range[1] << 8) + (range[0] & 0xff));
    }
    public static FileAnnouncementProtos.FileAnnouncement generateFileAnnouncementMessage(FileAnnouncementChunk fileAnnouncementChunk){

        FileAnnouncement fa = FileAnnouncement.newBuilder()
                .setChecksum(fileAnnouncementChunk.GetChecksum())
                .setFileName(fileAnnouncementChunk.GetFileName())
                .setFileSize(Ints.checkedCast(fileAnnouncementChunk.GetFileSize())  )
                .setChunksTotal(fileAnnouncementChunk.GetChunksTotal())
                .build();
        return fa;
    }

    public static String SHAsum(byte[] convertme) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return byteArray2Hex(md.digest(convertme));
    }

    public static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    /**
     * Función que genera un mensaje FileFragment de protobuf a partir de un fragmento y archivo dado.
     * @param f Fragmento del cual se quiere generar el mensaje protobuf FileFragment
     * @param filecontent Byte Array del archivo al que pertenece el fragmento indicado
     * @return
     */
    public static FileTransferProtos.FileFragment generateFileFragment(Fragment f, byte[] filecontent) {
        //System.out.printf("FileContent[%d] - Generating Fragment %d-%d\n", filecontent.length, (int)f.start, (int)f.end);
        FileTransferProtos.FileFragment ff = FileTransferProtos.FileFragment.newBuilder()
                .setId((int)f.getID())
                .setChecksum(f.checksum)
                .setData(ByteString.copyFrom(filecontent, (int)f.start, (int)f.end+1-(int)f.start))
                .setStart(f.start)
                .setEnd(f.end)
                .build();
        return ff;
    }
}
