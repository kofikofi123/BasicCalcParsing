
import java.io.IOException;
import java.io.RandomAccessFile;
import student.TestCase;

/**
 * Test case for BufferPool class
 * @author erikchair
 * @version 10.30.2022
 */
public class BufferPoolTest extends TestCase {
    
    private BufferPool<Record> bufferPool;
    private RandomAccessFile file;
    
    /**
     * Setup case for BufferPool test enviornment
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        byte[] inputArray = {0, 1, 0, 32,
                             0, 2, 0, 33, 
                             0, 3, 0, 34,
                             0, 4, 0, 35};
        file = 
            new RandomAccessFile(
                ByteFileGenerator.generateFromArray(inputArray),
                "rw");
        
        bufferPool = 
            new BufferPool<Record>(Record.class, 
                                   file.getChannel(), 
                                   2, 
                                   8);
    }

    /**
     * Ensures that the get method retrieves
     * the correct data 
     */
    public void testGet() {
        Exception eCheck = null;
        Record record = null;
        try {
            record = bufferPool.get(1);
        }
        catch (Exception e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(2, record.getKey());
        assertEquals(33, record.getValue());
    }

    /**
     * Ensures that the set method puts
     * the correct entries into the Buffer,
     * and the data is written to I/O
     */
    public void testSet() {
        Exception eCheck = null;
        Record record = null;
        try {
            record = bufferPool.get(1);
            bufferPool.set(3, record);
            bufferPool.flush();
            file.seek(12);
        }
        catch (Exception e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        try {
            assertEquals(2, file.readShort());
            assertEquals(33, file.readShort());
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
    }

    /**
     * Ensures that cacheHit and cacheMiss is
     * being recorded correctly
     */
    public void testGetCacheHitAndCacheMiss() {
        Exception eCheck = null;
        
        try {
            bufferPool.get(0);
            bufferPool.get(1);
        }
        catch (Exception e) {
            eCheck = e;
        }
        
        
        assertNull(eCheck);
        assertEquals(1, bufferPool.getCacheHit());
        assertEquals(1, bufferPool.getCacheMiss());
        
    }


    /**
     * Ensures that data read from I/O is
     * tracked properly
     */
    public void testGetDiskRead() {
        Exception eCheck = null;
        
        try {
            Record record1 = bufferPool.get(0);
            bufferPool.get(1);
            bufferPool.set(2, record1);
            bufferPool.flush();
        }
        catch (Exception e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(2, bufferPool.getDiskRead());
        assertEquals(1, bufferPool.getDiskWrite());
    }


}
