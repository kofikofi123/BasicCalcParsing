import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.SeekableByteChannel;
import student.TestCase;

/**
 * Test case for Buffer class
 * @author erikchair
 * @version 10.19.2022
 */
public class BufferTest extends TestCase {
    private Buffer buffer1;
    private Buffer buffer2;
    
    private RandomAccessFile file;
    /**
     * Setting up test enviornment
     */
    protected void setUp() throws Exception {
        super.setUp();
        byte[] inputArray = {0, 1, 0, 32, 0, 2, 0, 33};
        
        file = 
            new RandomAccessFile(
                ByteFileGenerator.generateFromArray(inputArray), 
                "rw");
        
        SeekableByteChannel channel = file.getChannel();
        
        
        buffer1 = new Buffer(channel);
        buffer2 = new Buffer(channel, 1);
    }
    
    /**
     * Tests to ensure that the Buffer(input, output) constructor
     * makes the correct Buffer object
     */
    public void testBufferInputStreamOutputStream() {
        assertEquals(0, buffer1.getIndex());
        assertFalse(buffer1.isValid());
        assertFalse(buffer1.isDirty());
        assertEquals(0, buffer1.getKey());
        assertEquals(0, buffer1.getValue());
        
        NullPointerException eCheck = null;
        
        try {
            new Buffer(null);
        }
        catch (NullPointerException e) {
            eCheck = e;
        }
        
        assertNotNull(eCheck);
        assertEquals("Channel must not be null", eCheck.getMessage());
    }

    
    /**
     * Tests the ensure that the Buffer(input, output, index) constructor
     * makes the correct Buffer object at a specific index
     */
    public void testBufferInputStreamOutputStreamInt() {
        assertEquals(1, buffer2.getIndex());
        assertFalse(buffer2.isValid());
        assertFalse(buffer2.isDirty());
        assertEquals(0, buffer2.getKey());
        assertEquals(0, buffer2.getValue());
        
        NullPointerException eCheck = null;
        
        try {
            new Buffer(null, 1);
        }
        catch (NullPointerException e) {
            eCheck = e;
        }
        
        assertNotNull(eCheck);
        assertEquals("Channel must not be null", eCheck.getMessage());
    }

    /**
     * Tests the reset method to ensure buffers reset to
     * default state
     */
    public void testReset() {
        
        buffer2.reset();
        assertEquals(0, buffer2.getIndex());
        assertFalse(buffer2.isValid());
        assertFalse(buffer2.isDirty());
        assertEquals(0, buffer2.getKey());
        assertEquals(0, buffer2.getValue());
    }


    /**
     * Tests the readBuffer method to ensure when
     * the buffer is in a valid state, it reads
     * the correct values
     */
    public void testReadBuffer() {
        IOException eCheck = null;
        try {
            buffer1.readBuffer();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        
        assertTrue(buffer1.isValid());
        assertEquals(1, buffer1.getKey());
        assertEquals(32, buffer1.getValue());
        
    }


    /**
     * Tests the writeBuffer method to ensure
     * when the buffer is in a valid state, it 
     * writes the correct values
     */
    public void testWriteBuffer() {
        IOException eCheck = null;
        
        try {
            buffer1.setIndex(-1);
            buffer1.writeBuffer();
            
            file.seek(0);
            assertEquals(1, file.readShort());
            assertEquals(32, file.readShort());
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        
        
        
        try {
            buffer1.setIndex(0);
            
            file.seek(0);
            assertEquals(1, file.readShort());
            assertEquals(32, file.readShort());
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        
        assertNull(eCheck);
        
        
        try {
            buffer1.readBuffer();
            buffer2.readBuffer();
            
            buffer1.swapBuffers(buffer2);
            buffer1.writeBuffer();
            
            file.seek(0);
            assertEquals(2, file.readShort());
            assertEquals(33, file.readShort());
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
    }


    /**
     * Tests the swapBuffers method to ensure
     * when buffers are swapped, both buffers
     * become dirty and the key, value pairs are
     * swapped, without affecting the index
     */
    public void testSwapBuffers() {
        IOException eCheck = null;
        try {
            buffer1.readBuffer();
            buffer2.readBuffer();
            
            buffer1.swapBuffers(buffer2);
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(0, buffer1.getIndex());
        assertEquals(2, buffer1.getKey());
        assertEquals(33, buffer1.getValue());
        
        
        assertEquals(1, buffer2.getIndex());
        assertEquals(1, buffer2.getKey());
        assertEquals(32, buffer2.getValue());
    }


    /**
     * Tests the isDirty method to ensure
     * that when the buffer key, value pair
     * has been updated, it is set to dirty.
     * If the buffer has written it is no longer 
     * dirty
     */
    public void testIsDirty() {
        IOException eCheck = null;
        try {
            buffer1.readBuffer();
            buffer2.readBuffer();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        
        assertFalse(buffer1.isDirty());
        assertFalse(buffer2.isDirty());
        
        buffer1.swapBuffers(buffer2);
        
        assertTrue(buffer1.isDirty());
        assertTrue(buffer2.isDirty());
        
        try {
            buffer1.writeBuffer();
            buffer2.writeBuffer();
        }
        catch (IOException e) {
            eCheck = e;
        }
       
        assertNull(eCheck);
        assertFalse(buffer1.isDirty());
        assertFalse(buffer2.isDirty());
    }

    
    /**
     * Tests the isValid method to ensure
     * that when buffers has not read 
     * to the input stream, that it is invalid
     * and valid when it has done a read
     */
    public void testIsValid() {
        assertFalse(buffer1.isValid());
        
        IOException eCheck = null;
        
        try {
            buffer1.readBuffer();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertTrue(buffer1.isValid());
        
        
        buffer1.reset();
        
        assertFalse(buffer1.isValid());
    }
    
    /**
     * Tests the getIndex method to ensure that
     * the correct index is returned for every buffer
     */
    public void testGetIndex() {
        assertEquals(0, buffer1.getIndex());
        assertEquals(1, buffer2.getIndex());
        
        buffer1.swapBuffers(buffer2);
        
        assertEquals(0, buffer1.getIndex());
        assertEquals(1, buffer2.getIndex());
    }
    
    /**
     * Tests the setIndex method to esnure
     * the new index set to the buffer
     * invalidates the buffer
     */
    public void testSetIndex() {
        IOException eCheck = null;
        
        try {
            buffer1.readBuffer();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(0, buffer1.getIndex());
        assertTrue(buffer1.isValid());
        
        buffer1.setIndex(1);
        
        assertFalse(buffer1.isValid());
    }
    
    /**
     * Tests the getKey method to ensure
     * the key read from stream is the 
     * correct key
     */
    public void testGetKey() {
        IOException eCheck = null;
        
        try {
            buffer1.readBuffer();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(1, buffer1.getKey());
    }
    
    /**
     * Tests the getValue method to ensure
     * the value read from stream is the 
     * correct value
     */
    public void testGetValue() {
        IOException eCheck = null;
        
        try {
            buffer1.readBuffer();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(32, buffer1.getValue());
    }
    
    /**
     * Test the compareTo method to ensure 
     * when comparing two buffer
     * the key with the highest value
     * is correctly chosen
     */
    public void testCompareTo() {
        IOException eCheck = null;
        
        try {
            buffer1.readBuffer();
            buffer2.readBuffer();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        
        assertEquals(0, buffer1.compareTo(buffer1));
        assertEquals(-1, buffer1.compareTo(buffer2));
        assertEquals(1, buffer2.compareTo(buffer1));
    }
}
