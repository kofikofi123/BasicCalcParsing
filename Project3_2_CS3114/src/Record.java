import java.nio.ByteBuffer;

/**
 * A BufferedObject Record class that 
 * takes a 2 byte key and a 2 byte value
 * @author erikchair
 * @version 10.30.2022
 */
public class Record
    implements Comparable<Record>, BufferedObject {
    private short key;
    private short value;
    
    /**
     * Default constructor sets key and value
     * to 0
     */
    Record() {
        key = 0;
        value = 0;
    }

    /**
     * Processes data from ByteBuffer
     * @param buffer The buffer to process from
     */
    @Override
    public void processFrom(ByteBuffer buffer) {
        key = buffer.getShort();
        value = buffer.getShort();
    }
    
    /**
     * Process data to the ByteBuffer
     * @param buffer The buffer to process to
     */
    @Override
    public void processTo(ByteBuffer buffer) {
        buffer.putShort(key);
        buffer.putShort(value);
    }
    
    /**
     * The amount of bytes to chop up the buffer
     * @return The amount of byte between valid entries
     */
    @Override
    public long stride() {
        return 4;
    }
    
    /**
     * Gets the key held by this Record
     * @return The 2 byte key held
     */
    public short getKey() {
        return key;
    }
    
    /**
     * Gets the value held by this Record
     * @return The 2 byte value held
     */
    public short getValue() {
        return value;
    }

    /**
     * Compares one's records keys to another
     * @param other The other Record key to compare to
     * @return >0 if this object's key is greater than the
     *            object's key
     *         0  if this object's key and the other's key is
     *            the same
     *         <0 if this object's key is less than the
     *            object's key
     */
    @Override
    public int compareTo(Record other) {
        return (key - other.key);
    }
    
    
    
    
}
