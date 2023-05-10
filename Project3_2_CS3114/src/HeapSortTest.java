import java.util.Random;
import student.TestCase;
import student.TestableRandom;

/**
 * Main entry point for buffered file sorting
 * @author erikchair
 * @version 11.07.2022
 */
public class HeapSortTest extends TestCase {
    private Random random;
    /**
     * Set up for test enviornment
     */
    public void setUp() throws Exception {
        random = new TestableRandom();
        ByteFileGenerator
            .generate("Test1.bin", 
                      Buffer.BLOCK_SIZE * (random.nextInt(20) + 1));
        
    }

    /**
     * Tests the main method to ensure combination
     * of inputs are correctly handled, and the file
     * is correctly sorted
     */
    public void testMain() {
        String[] args1 = {};
        String[] args2 = {"Test1.bin", 
                          Integer.toString((random.nextInt(20) + 1)), 
                          "Test1Stats.txt"};
        
        
        Exception eCheck = null;
        
        try {
            assertFalse(CheckFile.checkFile("Test1.bin"));
        }
        catch (Exception e) {
            eCheck = e;
        }
        
        
        try {
            HeapSort.main(args1);
        }
        catch (Exception e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals("", systemOut().getHistory());
        
        try {
            HeapSort.main(args2);
        }
        catch (Exception e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        
        
        try {
            assertTrue(CheckFile.checkFile("Test1.bin"));
        }
        catch (Exception e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
    }

}
