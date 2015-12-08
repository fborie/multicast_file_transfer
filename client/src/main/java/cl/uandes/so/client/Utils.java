package cl.uandes.so.client;

/**
 * Created by fjborie on 08-12-15.
 */
 public class Utils {
    public static short getShortFromLittleEndianRange(byte[] range){
        return (short)((range[1] << 8) + (range[0] & 0xff));
    }

}
