import java.io.IOException;

/**
 * Test class for the HeapSort class
 * @author erikchair
 * @version 10.28.2022
 */
public class HeapSortTest extends student.TestCase {
    /**
     * Setup for test env
     */
    public void setUp() throws Exception {
        ByteFileGenerator.generate("Test1.bin", 4096 * 10);
    }

    /**
     * Tests the main method to ensure combination
     * of inputs are correctly handled, and the file
     * is correctly sorted
     */
    public void testMain() {
        String[] args1 = {};
        String[] args2 = {"Test1.bin", "20", "Test1Stats.txt"};
        
        
        //HeapSort.main(args1);
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
        catch (IOException e) {
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
