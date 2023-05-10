
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import student.TestCase;

/**
 * @author erikchair
 * @version 09.28.2022
 */
public class BigNumArithmeticTest extends TestCase {

    /**
     * No additional setup needed
     */
    protected void setUp() throws Exception {
        super.setUp();
    }


    /**
     * Test method for main
     * @throws FileNotFoundException 
     * if output file doesn't exist
     */
    public void testMain() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/TestOutput.txt"));
        
        String output = "Invalid number of arguments\n";
        while (scanner.hasNext()) {
            output += scanner.nextLine() + "\n";
        }
        
        output = output.substring(0, output.length() - 1);
        scanner.close();
        
        String[] args1 = {};
        String[] args2 = {"src/TestFile.txt"};
        
        BigNumArithmetic.main(args1);
        BigNumArithmetic.main(args2);
        
        assertEquals(output, systemOut().getHistory());
        
    }

}
