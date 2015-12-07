package cl.uandes.so.server;


import java.io.File;
import java.io.IOException;
//import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

// Protobuf
// protoc -I=main/java/cl/uandes/so/server/ --java_out main/java/ main/java/cl/uandes/so/server/FileTransfer.proto
import cl.uandes.so.server.FileTransferProtos.FileFragment;
import cl.uandes.so.server.FileTransferProtos.FileFragmentOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.ByteString;

// Netty
import io.netty.buffer.ByteBuf;

//import java.nio.file.Paths;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        if(args.length <= 0) {
            System.out.println("usage: java -jar server.jar <filename>");
            System.exit(1);
        }
        File f = new File(args[0]);
        if(!f.exists() || !f.canRead()) {
            System.out.println(String.format("File %s not found or access denied.", args[0]));
            System.exit(1);
        }
        Long size = f.length();
        System.out.println( "Abriendo archivo "  + args[0] + " con " + size.toString() + " bytes.");
        
        // Calcular numero de fragmentos necesarios para enviar archivos
        Long fragments_required = 1L;
        while(1400*(fragments_required) < size) {
            fragments_required += 1;
        }
        
        
        System.out.println(String.format("Se requieren %d fragmentos para %d bytes", fragments_required, size));
        
        // Volcar archivo completo a arreglo de bytes
        byte[] filecontent = Files.readAllBytes(f.toPath());
        
        // Crear lista de fragmentos
        ArrayList fragments = new ArrayList();
        int i = 0;
        long bytecount = 0;
        long remaining_bytes = size;
        while(i < fragments_required) {
            Fragment chunk;
            if(remaining_bytes > 1399) {
               // Full Fragment
                chunk = new Fragment(i, bytecount, bytecount+1399);
                bytecount += 1400;
                remaining_bytes -= 1400;
            } else {
                // Debiera ser el último fragmento incompleto
                // System.out.println("Fragmento incompleto");
                chunk = new Fragment(i, bytecount, bytecount+remaining_bytes-1);
                bytecount += remaining_bytes;
                remaining_bytes = 0;
            }
            fragments.add(chunk);
            i += 1;
        }
        
        // Calcular hash de fragmentos
        for(Object item : fragments) {
            Fragment chunk = (Fragment)item;
            String checksum = "";
            try {
            checksum = SHAsum(Arrays.copyOfRange(filecontent, (int)chunk.start, (int)chunk.end+1));
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Couldn't find SHA1 algorithm...");
                System.exit(1);
            }
            
            chunk.setHash(checksum);
            System.out.println(String.format("Fragment %d: %d-%d (Length: %d) (Checksum: %s)", chunk.getID(), chunk.start, chunk.end, (chunk.end-chunk.start)+1, chunk.checksum));
            
        }
        
        /*
        // Prueba de generación de mensaje Protobuf con fragmento
        // Resultado: El largo del mensaje con un fragmento completo (1400) es de 1446 bytes
        Fragment first = (Fragment)fragments.get(0);
        FileFragment ff = FileFragment.newBuilder()
                .setId((int)first.getID())
                .setChecksum(first.checksum)
                .setData(ByteString.copyFrom(filecontent, (int)first.start, (int)first.end))
                .build();
        System.out.println(String.format("Debug: Largo de primer fragmento: %d", ff.toByteArray().length));
        */
        
        System.out.println(String.format("The file has been fragmented successfully.", filecontent.length));
        
        // TODO: Netty
        System.out.println("Starting socket...");
        
        LoginServer ls = new LoginServer(9988, "1.1.1.1:1111");
        
        try {
         ls.run();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
        System.out.println("Finish");
        
    }
    public static String SHAsum(byte[] convertme) throws NoSuchAlgorithmException{
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
    public static FileFragment generateFileFragment(Fragment f, byte[] filecontent) {
        FileFragment ff = FileFragment.newBuilder()
                .setId((int)f.getID())
                .setChecksum(f.checksum)
                .setData(ByteString.copyFrom(filecontent, (int)f.start, (int)f.end))
                .build();
        return ff;
    }
}
