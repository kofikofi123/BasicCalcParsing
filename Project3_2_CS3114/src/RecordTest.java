import static org.junit.Assert.*;
import java.nio.ByteBuffer;

/**
 * Test class for Record class
 * @author erikchair
 * @version 10.30.2022
 *
 */
public class RecordTest extends student.TestCase {
    private byte[] inputArray;
    private Record record;
    private ByteBuffer buffer;
    
    /**
     * Sets up the test enviornment
     */
    public void setUp() throws Exception {
        super.setUp();
        byte[] temp = {0, 1, 0, 30, 
                       0, 2, 0, 31,
                       0, 3, 0, 32,
                       0, 4, 0, 33};
        
        inputArray = temp;
        
        record = new Record();
        buffer = ByteBuffer.wrap(inputArray);
    }

    
    /**
     * Tests to ensure the defualt 
     * Record object has a key and value 
     * of 0
     */
    public void testRecord() {
        assertEquals(0, record.getKey());
        assertEquals(0, record.getValue());
    }


    /**
     * Tests to ensure processing from the 
     * ByteBuffer at a position gets
     * the correct key and value
     */
    public void testProcessFrom() {
        buffer.position((int)(1 * record.stride()));
        record.processFrom(buffer);
        assertEquals(2, record.getKey());
        assertEquals(31, record.getValue());
    }


    /**
     * Tests to ensure processing to the
     * ByteBuffer at a position
     * writes the correct key and value
     */
    public void testProcessTo() {
        buffer.position((int)(3 * record.stride()));
        record.processFrom(buffer);
        buffer.position(0);
        record.processTo(buffer);
        
        byte[] expected = {0, 4, 0, 33,
                           0, 2, 0, 31,
                           0, 3, 0, 32,
                           0, 4, 0, 33};
        
        assertArrayEquals(inputArray, expected);
    }


    /**
     * Ensures the stride for record 
     * is 4 bytes
     */
    public void testStride() {
        assertEquals(4, record.stride());
    }

    /**
     * Ensures the key is the correct
     * key from different situations
     */
    public void testGetKey() {
        assertEquals(0, record.getKey());
        buffer.position((int)(1 * record.stride()));
        record.processFrom(buffer);
        assertEquals(2, record.getKey());
    }

    /**
     * Ensures the value is the correct
     * value from different situations
     */
    public void testGetValue() {
        assertEquals(0, record.getValue());
        buffer.position((int)(1 * record.stride()));
        record.processFrom(buffer);
        assertEquals(31, record.getValue());
    }


    /**
     * Ensures that comparing two record 
     * objects returns the correct values
     */
    public void testCompareTo() {
        Record record2 = new Record();
        buffer.position((int)(3 * record.stride()));
        record.processFrom(buffer);
        buffer.position((int)(2 * record2.stride()));
        record2.processFrom(buffer);
        assertEquals(1, record.compareTo(record2));
        assertEquals(-1, record2.compareTo(record));
    }

}
