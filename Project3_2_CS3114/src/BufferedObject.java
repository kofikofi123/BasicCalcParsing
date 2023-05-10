import java.nio.ByteBuffer;

/**
 * ADT for objects that interract with ByteBuffer
 * @author erikchair
 * @version 10.30.2022
 */
public interface BufferedObject {
    /**
     * Processes data from ByteBuffer
     * @param buffer The buffer to process from
     */
    public void processFrom(ByteBuffer buffer);
    /**
     * Process data to the ByteBuffer
     * @param buffer The buffer to process to
     */
    public void processTo(ByteBuffer buffer);
    
    /**
     * THe amount of bytes to chop up the buffer
     * @return The amount of byte between valid entries
     */
    public long stride();
}
