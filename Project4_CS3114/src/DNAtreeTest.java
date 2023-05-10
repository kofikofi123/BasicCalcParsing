import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import student.TestCase;

/**
 * Test class for DNATree
 * @author erikchair
 * @version 12.01.2022
 *
 */
public class DNAtreeTest extends TestCase {
    private String output;
    /**
     * Sets up the test enviornment
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        
        output = "";
        
        
        Scanner scanner =
            new Scanner(
                new File("src/TestOutput.txt"));
        
        while (scanner.hasNextLine()) {
            output = output + scanner.nextLine() + '\n';
        }
        
        output = output.substring(0, output.length() - 1);
    }

    /**
     * Tests if given a file, the 
     * main function can correctly operate
     * the tree
     */
    
    @Test
    public void testMain() {
        String[] args1 = {};
        String[] args2 = {"src/TestInput.txt"};
        
        IOException eCheck = null;
        try {
            DNAtree.main(args1);
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals("", systemOut().getHistory());
        
        try {
            DNAtree.main(args2);
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(output, systemOut().getHistory());
    }
    
    
    
}
