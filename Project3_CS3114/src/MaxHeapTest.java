import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Test class for the MaxHeap class
 * @author erikchair
 * @version 10.27.2022
 */
public class MaxHeapTest extends student.TestCase {
    private MaxHeap heap;
    
    /**
     * Sets up the test enviornment
     */
    public void setUp() throws Exception {
        byte[] inputArray = {0, 5, 0,  32,
                             0, 20, 0, 33,
                             0, 10, 0, 34,
                             0, 50, 0, 35,
                             0, 45, 0, 36};
        int size = inputArray.length >> 2;
        RandomAccessFile file = 
            new RandomAccessFile(
                ByteFileGenerator.generateFromArray(inputArray),
                "rw");
        BufferPool pool = new BufferPool(file.getChannel(), 2);
        heap = new MaxHeap(pool, size);
    }

    /**
     * Tests the removeMax method to ensure the highest
     * value in the array is always removed.
     */
    public void testRemoveMax() {
        IOException eCheck = null;
        Buffer buffer = null;
        Buffer buffer2 = null;
        try {
            buffer = heap.removeMax();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        
        assertNull(eCheck);
        assertTrue(buffer.isValid());
        assertEquals(50, buffer.getKey());
        assertEquals(35, buffer.getValue());
        
        
        try {

            buffer2 = heap.removeMax();
        }
        catch (IOException e) {
            eCheck = e;
        }
        assertNull(eCheck);
        assertTrue(buffer2.isValid());
        assertEquals(45, buffer2.getKey());
        assertEquals(36, buffer2.getValue());
    }

    /**
     * Tests the get method to ensure the correct
     * entry in the array in file is retrieved
     */
    public void testGet() {
        IOException eCheck = null;
        Buffer buffer = null;
        
        try {
            buffer = heap.get(0);
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        
        assertNull(eCheck);
        assertTrue(buffer.isValid());
        assertEquals(50, buffer.getKey());
        assertEquals(35, buffer.getValue());
        
    }

}
