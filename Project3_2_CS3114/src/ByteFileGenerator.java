
/**
 * Generator of the byte file
 * 
 * @author CS Staff
 * @version 08/07/2018
 */
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteFileGenerator {
    /**
     * Generates the byte file
     * 
     * @param numRecords
     *            number of records you want your byte file to have
     * @throws IOException
     */
    public static File generate(String filename, int numRecords) throws IOException {

        File file = new File(filename);
        DataOutputStream outs = new DataOutputStream(new FileOutputStream(file,
            false));
        for (int j = 0; j < numRecords; j++) {
            short key = (short)(Math.random() * Short.MAX_VALUE);
            short val = (short)(Math.random() * Short.MAX_VALUE);
            byte[] testB = new byte[4];
            for (int i = 0; i < 2; i++) {
                testB[i] = (byte)(key >> (8 - 8 * i));
                testB[i + 2] = (byte)(val >> (8 - 8 * i));
            }

            outs.write(testB);
        }
        outs.close();
        
        return file;
    }
    
    
    public static File generateFromArray(byte[] arr) throws IOException {
        File file = File.createTempFile("buf1", "rw");
        DataOutputStream outs = new DataOutputStream(
            new FileOutputStream(file));
        
        outs.write(arr);
        
        outs.close();
        
        return file;
    }
    
    
    
}
