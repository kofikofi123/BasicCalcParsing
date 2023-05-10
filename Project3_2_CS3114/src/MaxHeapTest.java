import java.io.RandomAccessFile;

/**
 * Test class for the MaxHeap class
 * @author erikchair
 * @version 11.07.2022
 */
public class MaxHeapTest extends student.TestCase {
    private MaxHeap<Record> heap;
    
    /**
     * Sets up the test enviornment
     */
    public void setUp() throws Exception {
        byte[] inputArray = {0, 5, 0,  32,
                             0, 20, 0, 33,
                             0, 10, 0, 34,
                             0, 50, 0, 35,
                             0, 45, 0, 36,
                             0, 1,  0 , 1};
        int size = inputArray.length >> 2;
        RandomAccessFile file = 
            new RandomAccessFile(
                ByteFileGenerator.generateFromArray(inputArray),
                "rw");
        BufferPool<Record> pool =
            new BufferPool<Record>(Record.class, 
                                   file.getChannel(), 
                                   2, 
                                   8);
        heap = new MaxHeap<Record>(pool, size);
    }

    /**
     * Tests the removeMax method to ensure the highest
     * value in the array is always removed.
     */
    public void testRemoveMax() {
        Exception eCheck = null;
        Record record = null;
        Record record2 = null;
        try {
            record = heap.removeMax();
        }
        catch (Exception e) {
            eCheck = e;
        }
        
        
        assertNull(eCheck);
        assertEquals(50, record.getKey());
        assertEquals(35, record.getValue());
        
        
        try {

            record2 = heap.removeMax();
        }
        catch (Exception e) {
            eCheck = e;
        }
        assertNull(eCheck);
        assertEquals(45, record2.getKey());
        assertEquals(36, record2.getValue());
    }


}
