import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

/**
 * Buffer implementatiton based on
 * set number of bytes per block
 * @author erikchair
 * @version 10.30.2022
 */
public class Buffer {
    private ByteBuffer internalBuffer;
    private SeekableByteChannel channel;
    private long index;
    private int blockSize;
    
    
    private boolean valid;
    private boolean dirty;
    
    /**
     * The default block size to be read and write
     * from I/O
     */
    public final static int BLOCK_SIZE = 4096;
    
    
    /**
     * Constructor for the Buffer object that 
     * takes a channel, with default block size
     * at index 0
     * @param inChannel The channel to perform I/O
     */
    Buffer(SeekableByteChannel inChannel) {
        this(inChannel, BLOCK_SIZE, 0);
    }
    /**
     * Constructor for tthe Buffer object that
     * takes a channel, with a specified block size
     * and positions it at index 0
     * 
     * @param inChannel The channel to perform I/O
     * @param inBlockSize The block size used
     */
    Buffer(SeekableByteChannel inChannel, int inBlockSize) {
        this(inChannel, inBlockSize, 0);
    }
    
    /**
     * Constructor for the Buffer object that 
     * takes a channel and an block based index
     * @param inChannel The channel to perform I/O
     * @param inBlockSize The block size used
     * @param inIndex The block based index to use in power of 2
     */
    Buffer(SeekableByteChannel inChannel, int inBlockSize, long inIndex) {
        channel = inChannel;
        internalBuffer = ByteBuffer.allocateDirect(inBlockSize);
        index = Buffer.calculateBlockIndex(inIndex, inBlockSize);
        blockSize = inBlockSize;
        
        valid = false;
        dirty = false;
    }
    
    /**
     * Reads a whole block buffer to disk
     * Makes the buffer non-dirty and valid
     * @throws IOException The channel is closed/invalid
     */
    public void readBuffer() throws IOException {
        if (!isChannelReady()) {
            return;
        }
        internalBuffer.clear();
        channel.position(index);
        
        
        channel.read(internalBuffer);
        
        valid = true;
        dirty = false;
    }
    
    /**
     * Writes the whole block buffer to disk
     * Makes the buffer non-dirty
     * @throws IOException The channel is closed/invalid
     */
    public void writeBuffer() throws IOException {
        if (!isChannelReady()) {
            return;
        }
        internalBuffer.clear();
        channel.position(index);
        
        
        channel.write(internalBuffer);
        
        dirty = false;
    }
    
    /**
     * Read data from a location to a BufferedObject
     * @param object The object to read to
     * @param position The position to read from
     */
    public void readEntry(BufferedObject object, long position) {
        if (!valid) {
            return;
        }
        long entry = 
            Buffer.calculateEntryIndex(position, blockSize);
        long stride = object.stride();
        
        
        
        internalBuffer.position(
            (int)((entry * stride) % blockSize));
        object.processFrom(internalBuffer);
    }
    
    /**
     * Write a BufferedObject to a location
     * @param object The object to write data form
     * @param position The position to write to
     */
    public void writeEntry(BufferedObject object, long position) {
        if (!valid) {
            return;
        }
        long entry = 
            Buffer.calculateEntryIndex(position, blockSize);
        long stride = object.stride();
        
        internalBuffer.position(
            (int)((entry * stride) % blockSize));
        object.processTo(internalBuffer);
        
        dirty = true;
    }
    
    /**
     * Returns whether the buffer is dirty
     * The buffer is dirty if it performs a write
     * not in the disk
     * @return True if the buffer is dirty
     *         False otherwise
     */
    public boolean isDirty() {
        return dirty;
    }
    
    /**
     * Calculates the block based index based on position 
     * and block size
     * @param position The position to index
     * @param sizeBlock The block size used
     * @return The part of the number important to block index
     */
    public static long calculateBlockIndex(long position, int sizeBlock) {
        return (position & ~(sizeBlock - 1));
    }
    
    /**
     * Calculates the block based entry index based on
     * position and block size
     * @param position The position to index
     * @param sizeBlock TThe block sized used
     * @return TThe part of the number important to entry index
     */
    public static long calculateEntryIndex(long position, int sizeBlock) {
        return (position & (sizeBlock - 1));
    }
    
    /**
     * Returns whether the buffer is valid.
     * The buffer is valid if it performed a read
     * and has valid data
     * @return True if the buffer is valid
     *         False otherwise
     */
    public boolean isValid() {
        return valid;
    }
    
    /**
     * Gets the current index set
     * @return The index used for I/O
     */
    public long getIndex() {
        return index;
    }
    
    /**
     * Resets the buffer to a default valid state
     * at position 0
     */
    public void reset() {
        reset(0);
    }
    
    /**
     * Gets the block size the Buffer object uses
     * @return The number of bytes per block
     */
    public int getBlockSize() {
        return blockSize;
    }
    
    /**
     * Resets the buffer to a default valid state
     * at a specific block-based index
     * @param inIndex Resets the buffer to a valid state, 
     *                and sets the index
     */
    public void reset(long inIndex) {
        internalBuffer.clear();
        index = Buffer.calculateBlockIndex(inIndex, blockSize);
        valid = false;
        dirty = false;
    }
    
    /**
     * Checks if he channel is open
     * @return True if the channel is open
     *         False otherwise
     */
    protected boolean isChannelReady() {
        return channel.isOpen();
    }
}
