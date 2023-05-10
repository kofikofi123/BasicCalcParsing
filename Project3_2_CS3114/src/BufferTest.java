import java.io.IOException;
import java.io.RandomAccessFile;
/**
 * Test file for Buffer class
 * @author erikchair
 * @version 10.30.2022
 *
 */
public class BufferTest extends student.TestCase {
    private Buffer buffer;
    private Buffer buffer2;
    private Buffer buffer3;
    
    private RandomAccessFile file;
    
    /**
     * Setup class for test envoirnment
     * @throws Exception Any exceptions occuring 
     *                   during setup
     */
    public void setUp() throws Exception {
        super.setUp();
        byte[] inputArray = {0, 1, 0, 32,
                             0, 2, 0, 33, 
                             0, 3, 0, 34,
                             0, 4, 0, 35};
        file = 
            new RandomAccessFile(
                ByteFileGenerator.generateFromArray(inputArray),
                "rw");
        
        
        
        buffer = new Buffer(file.getChannel(), 8);
        buffer2 = new Buffer(file.getChannel());
        buffer3 = new Buffer(file.getChannel(), 8, 1);
    }

    /**
     * Ensures that the construction of the
     * Buffer object is the correct default state 
     */
    public void testBufferSeekableByteChannel() {
        assertFalse(buffer2.isValid());
        assertFalse(buffer2.isDirty());
        assertEquals(0, buffer2.getIndex());
        assertEquals(Buffer.BLOCK_SIZE, buffer2.getBlockSize());
    }

    /**
     * Ensures that the construction of the
     * Buffer object is the correct default state 
     */
    public void testBufferSeekableByteChannelInt() {
        assertFalse(buffer.isValid());
        assertFalse(buffer.isDirty());
        assertEquals(0, buffer.getIndex());
        assertEquals(8, buffer.getBlockSize());
    }

    /**
     * Ensures that the construction of the
     * Buffer object is the correct default state 
     */
    public void testBufferSeekableByteChannelIntLong() {
        assertFalse(buffer3.isValid());
        assertFalse(buffer3.isDirty());
        assertEquals(0, buffer3.getIndex());
        assertEquals(8, buffer.getBlockSize());
    }

    /**
     * Ensures that reading from the Buffer from
     * I/O channel reads the correct block, 
     * and entries read are the correct data
     */
    public void testReadBufferAndReadEntry() {
        assertFalse(buffer.isValid());
        assertFalse(buffer.isDirty());
        
        IOException eCheck = null;
        
        try {
            buffer.readBuffer();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertTrue(buffer.isValid());
        assertFalse(buffer.isDirty());
        
        Record record = new Record();
        buffer.readEntry(record, 0);
        assertEquals(1, record.getKey());
        assertEquals(32, record.getValue());
        
        buffer.readEntry(record, 1);
        assertEquals(2, record.getKey());
        assertEquals(33, record.getValue());
    }


    /**
     * Ensures that write from the Buffer from
     * I/O channel writes the correct block back
     * and entries written to are correct in
     * the Buffer
     */
    public void testWriteBufferAndWriteEntry() {
        assertFalse(buffer.isValid());
        assertFalse(buffer.isDirty());
        
        IOException eCheck = null;
        
        try {
            buffer.readBuffer();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertTrue(buffer.isValid());
        assertFalse(buffer.isDirty());
        
        Record record = new Record();
        buffer.readEntry(record, 0);
        buffer.writeEntry(record, 1);
        
        assertTrue(buffer.isValid());
        assertTrue(buffer.isDirty());
        
        try {
            buffer.writeBuffer();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertTrue(buffer.isValid());
        assertFalse(buffer.isDirty());
        
        short readKey = 0;
        short readValue = 0;
        try {
            file.seek(4);
            readKey = file.readShort();
            readValue = file.readShort();
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(record.getKey(), readKey);
        assertEquals(record.getValue(), readValue);
        
    }
    
    /**
     * Ensures that when reseting the buffer, 
     * the state of the buffer is valid
     */
    public void testReset() {
        IOException eCheck = null;
        try {
            buffer.readBuffer();
            buffer.writeEntry(new Record(), 0);
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        
        
        assertTrue(buffer.isValid());
        assertTrue(buffer.isDirty());
        
        buffer.reset();
        
        assertFalse(buffer.isValid());
        assertFalse(buffer.isDirty());
        assertEquals(0, buffer.getIndex());
        
        buffer.reset(9);
        assertEquals(8, buffer.getIndex());
    }

}
