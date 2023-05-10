import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Main class for DNATree parsing 
 * from file.
 * @author erikchair
 * @version 12.01.2022
 *
 */
public class DNAtree {
    /**
     * Main functions for the class
     * Expects 1 argument (filename)
     * @param args Arguments fed to main class
     * @throws IOException If I/O errors exists
     */
    public static void main(String[] args)
        throws IOException {
        if (args.length < 1) {
            return;
        }
        
        String filename = args[0];
        FileReader reader =
            new FileReader(filename);
        Writer writer = 
            new StringWriter();
      
        
        DNATreeParser parser =
            new DNATreeParser(writer);
        
        parser.parse(reader);
        
        String output = writer.toString();
        System.out.print(output.substring(0, output.length() - 1));
    }
}
