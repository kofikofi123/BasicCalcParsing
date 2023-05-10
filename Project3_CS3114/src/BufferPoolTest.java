import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.SeekableByteChannel;

/**
 * Test case for the BufferPool class
 * @author erikchair
 * @version 10.24.2022
 */
public class BufferPoolTest extends student.TestCase {
    private BufferPool pool;
    
    /**
     * Sets up the test class
     */
    public void setUp() throws Exception {
        byte[] inputArray = {0, 1, 0, 32, 
                             0, 2, 0, 33, 
                             0, 3, 0, 33};
        
        
        
        RandomAccessFile file = 
            new RandomAccessFile(
                ByteFileGenerator.generateFromArray(inputArray), 
                "rw");
        SeekableByteChannel channel = file.getChannel();
        
        pool = new BufferPool(channel, 2);
    }


    /**
     * Test the constructor to ensure
     * the BufferPool is at inital state
     */
    public void testBufferPool() {
        assertEquals(0, pool.getCacheHit());
        assertEquals(0, pool.getCacheMiss());
    }


    /**
     * Tests the requestLine method to 
     * ensure when a line is requested,
     * if the line is in memory its a 
     * cache hit, cache miss otherwise and 
     * read into memory
     */
    public void testRequestLine() {
        IOException eCheck = null;
        Buffer buffer = null;
        
        try {
            buffer = pool.requestLine(1);
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        
        assertNull(eCheck);
        assertTrue(buffer.isValid());
        assertFalse(buffer.isDirty());
        assertEquals(2, buffer.getKey());
        assertEquals(33, buffer.getValue());
        
        Buffer otherBuffer = null;
        
        try {
            otherBuffer = pool.requestLine(1);
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(otherBuffer, buffer);
    }


    /**
     * Tests the getCacheHit and getCacheMiss
     * to ensure the correct values are calculated
     */
    public void testGetCacheHitandCacheMiss() {
        IOException eCheck = null;
        
        try {
            pool.requestLine(1);
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertEquals(0, pool.getCacheHit());
        assertEquals(1, pool.getCacheMiss());
        
        
        
        try {
            pool.requestLine(1);
            pool.requestLine(0);
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(1, pool.getCacheHit());
        assertEquals(2, pool.getCacheMiss());
        
        
        try {
            pool.requestLine(2);
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(1, pool.getCacheHit());
        assertEquals(3, pool.getCacheMiss());
        
    }
    
    /**
     * Tests the getDiskReads and getDiskWrites method
     * to ensure the correct number of reads and writes
     * are performed by the pool
     */
    public void testGetDiskReadsandDiskWrites() {
        IOException eCheck = null;
        
        Buffer buffer1 = null;
        Buffer buffer2 = null;
        try {
            pool.requestLine(1);
        }
        catch (IOException e)
        {
            eCheck = e;
        }
        
        
        assertNull(eCheck);
        assertEquals(1, pool.getDiskReads());
        assertEquals(0, pool.getDiskWrites());
        
        try {
            buffer1 = pool.requestLine(1);
            buffer2 = pool.requestLine(0);
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(2, pool.getDiskReads());
        assertEquals(0, pool.getDiskWrites());
        
        
        try {
            buffer1.swapBuffers(buffer2);
            pool.flush();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        
        assertNull(eCheck);
        assertEquals(2, pool.getDiskReads());
        assertEquals(2, pool.getDiskWrites());
    }
}
