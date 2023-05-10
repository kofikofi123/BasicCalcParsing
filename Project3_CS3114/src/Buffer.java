import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

/**
 * Buffer class for BufferPool
 * 
 * @author erikchair
 * @version 10.19.2022
 */
public class Buffer implements Comparable<Buffer> {
    private long index;

    private SeekableByteChannel bufferChannel;
    private ByteBuffer byteBuffer;

    private short key;
    private short value;

    private boolean dirty;
    private boolean valid;

    /**
     * Default constructor that takes in a Channel
     * used to read and write data from at index 0
     * 
     * @param inChannel
     *            The channel to use
     * @throws IOException
     *             If inChannel is invalid
     */
    Buffer(SeekableByteChannel inChannel) throws NullPointerException {
        this(inChannel, 0);
    }


    /**
     * Constructor that takes in a Channel
     * used to read and write data from at
     * a specific position
     * 
     * @param inChannel
     *            The channel to use
     * @param position
     *            The dword position in
     *            the channel
     * @throws IOException
     *             if inChannel is invalid
     */
    Buffer(SeekableByteChannel inChannel, long position)
        throws NullPointerException {

        if (inChannel == null) {
            throw new NullPointerException("Channel must not be null");
        }
        index = position;
        bufferChannel = inChannel;
        byteBuffer = ByteBuffer.allocateDirect(4);

        key = 0;
        value = 0;

        dirty = false;
        valid = false;

    }


    /**
     * Resets the buffer to a default state
     * 
     * @throws IOException
     *             If channel is invalid
     */
    public void reset() {
        index = 0;
        key = 0;
        value = 0;

        dirty = false;
        valid = false;
    }


    /**
     * Reads the key, value pair from the
     * inputStream provided
     * 
     * @throws IOException
     *             If the inputStream is invalid,
     *             or I/O errors
     */
    public void readBuffer() throws IOException {
        if (!isChannelReady() || index < 0) {
            return;
        }

        updateBuffer();

        byteBuffer.position(0);
        bufferChannel.read(byteBuffer);

        key = byteBuffer.getShort(0);
        value = byteBuffer.getShort(2);

        valid = true;
    }


    /**
     * Writes the key, value pair to the
     * outputStream provided
     * 
     * @throws IOException
     *             If the outputStream is invalid
     *             or I/O errors
     */
    public void writeBuffer() throws IOException {
        if (!isChannelReady() || index < 0 || !isValid()) {
            return;
        }

        updateBuffer();

        byteBuffer.position(0);
        byteBuffer.putShort(0, key);
        byteBuffer.putShort(2, value);

        bufferChannel.write(byteBuffer);

        valid = true;
        dirty = false;
    }


    /**
     * Swaps the key, value pair from two buffers and
     * marks them as dirty
     * 
     * @param other
     *            The buffer to perform the swapping
     */
    public void swapBuffers(Buffer other) {
        short tempKey = key;
        short tempValue = value;

        key = other.key;
        value = other.value;

        other.key = tempKey;
        other.value = tempValue;

        dirty = true;
        other.dirty = true;
    }


    /**
     * Sets the buffet to a new index,
     * also causes the buffer the be invalidated
     * 
     * @param newIndex
     *            The new index in the file
     * @throws IOException
     *             If the channel is invalid
     */
    public void setIndex(long newIndex) {
        reset();
        index = newIndex;
    }


    /**
     * Checks whether the value has been updated
     * 
     * @return True if value has been update, false otherwise
     */
    public boolean isDirty() {
        return dirty;
    }


    /**
     * Checks whether the entry is a valid
     * read entry from the stream
     * 
     * @return True if the key, value pair was read in
     *         false otherwise
     */
    public boolean isValid() {
        return valid;
    }


    /**
     * Gets the current index in the input/output
     * stream the buffer is handling
     * 
     * @return The index in the streams
     */
    public long getIndex() {
        return index;
    }


    /**
     * Gets the key associated with the buffer
     * Should be called if the buffer is valid
     * 
     * @return The key read by the buffer
     */
    public short getKey() {
        return key;
    }


    /**
     * Gets the value associated with the buffer
     * Should be called if the buffer is valid
     * 
     * @return The value read by the buffer
     */
    public short getValue() {
        return value;
    }


    /**
     * Compares two buffer's keys
     * 
     * @param other
     *            The other Buffer to compare with
     * @return 0 if the keys are the same
     *         >0 if this Buffer's key is greater
     *         <0 if this Buffer's key is less
     */
    @Override
    public int compareTo(Buffer other) {
        return key - other.key;
    }


    public String toString() {
        return "[" + key + ": " + value + "]";
    }


    /**
     * Checks to see if bufferChannel is valid and open
     * 
     * @return True if the bufferChannel is not null and
     *         is open, false otherwise
     */
    protected boolean isChannelReady() {
        return (bufferChannel.isOpen());
    }


    /**
     * Updates the dword position of the channel
     * 
     * @throws IOException
     *             If channel is invalid
     */
    protected void updateBuffer() throws IOException {
        bufferChannel.position((long)index * 4);
    }

}
