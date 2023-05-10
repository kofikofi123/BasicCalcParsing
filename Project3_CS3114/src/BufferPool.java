import java.io.Flushable;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;

/**
 * Buffer pool implementation
 * @author erikchair
 * @version 10.19.2022
 */
public class BufferPool implements Flushable {
    private LinkedList<Buffer> head;
    private int capacity;
    private int size;
    
    private int cacheHit;
    private int cacheMiss;
    
    private int diskRead;
    private int diskWrite;
    
    private SeekableByteChannel channel;
    
    /**
     * Constructor for the BufferPool class.
     * Takes in a channel for read/write data,
     * and the maximum capacity the pool can hold
     * @param inChannel The channel to perform I/O on
     * @param inCapacity The amount of cache rows to have
     */
    BufferPool(SeekableByteChannel inChannel, int inCapacity)  {
        head = null;
        capacity = inCapacity;
        size = 0;
        cacheHit = 0;
        cacheMiss = 0;
        diskRead = 0;
        diskWrite = 0;
        channel = inChannel;
    }
    
    
    /**
     * Gets a Buffer pointing to a line,
     * if the buffer is already loaded in the 
     * pool, its a cache hit, if it is not
     * it is a cache miss, and the line must
     * be read into memory.
     * @param line The line for the Buffer to point to
     * @return A valid Buffer object that points to the 
     *         specific line
     * @throws IOException If the channel is closed/invalid
     */
    public Buffer requestLine(long line) throws IOException {
        //Buffer buffer = findLine(line);
        LinkedList<Buffer> node = findLine(line);
        Buffer buffer = null;
        if (node != null) {
            cacheHit++;
            updateNode(node);
            buffer = node.data();
        }
        else {
            cacheMiss++;
            buffer = allocateLine(line);
        }
        
        return buffer;
    }
    
    /**
     * Gets the number of cache hits processed
     * by the pool
     * @return The number of cache hits
     */
    public int getCacheHit() {
        return cacheHit;
    }
    
    /**
     * Gets the number of cache misses processed
     * by the pool
     * @return THe number of cache misses
     */
    public int getCacheMiss() {
        return cacheMiss;
    }
    
    /**
     * Gets the number of reads from disk (or I/O sources)
     * by the pool
     * @return The number of reads done
     */
    public int getDiskReads() {
        return diskRead;
    }
    
    /**
     * Gets the number of writes from disk (or I/O sources)
     * by the pool
     * @return The number of writes done
     */
    public int getDiskWrites() {
        return diskWrite;
    }
    
    /**
     * Flushes any unwritten dirty buffers back to source
     */
    public void flush() throws IOException {
        LinkedList<Buffer> node = head;
        
        
        while (node != null) {
            Buffer buffer = node.data();
            
            if (buffer.isDirty()) {
                writeBuffer(buffer);
            }
            node = node.next();
        }
    }
    
    
    
    
    /**
     * Goes through the allocated buffers
     * to see if the line requested is loaded
     * @param line The line to check
     * @return A valid Buffer object pointing
     *         to the line, or null if none is found
     */
    protected LinkedList<Buffer> findLine(long line) {
        LinkedList<Buffer> node = head;
        
        while (node != null && 
               node.data().getIndex() != line) {
            node = node.next();
        }
        
        
        return node;
    }
    
    /**
     * Allocates a new Buffer that points to the new 
     * line. If the capcity is reached, evicts the least
     * used buffer
     * @param line The array entry to index
     * @return A valid Buffer object pointing to the line
     * @throws IOException If the channel is closed/invalid
     */
    protected Buffer allocateLine(long line) throws IOException {
        LinkedList<Buffer> node = null;
        Buffer buffer = null;
        if (size < capacity) {
            buffer =
                new Buffer(channel, line);
            
            node = new LinkedList<Buffer>(buffer, null, null);
            
            
            size++;
        }
        else {
            node = getTail();
            buffer = node.data();
            
            if (buffer.isDirty()) {
                writeBuffer(buffer);
            }

            buffer.setIndex(line);
        }
        
        updateNode(node);
        readBuffer(buffer);
        return buffer;
    }
    
    /**
     * Reads from disk into the buffer, and increments
     * the amount of disk reads performed by this buffer
     * @param buffer he buffer to read
     * @throws IOException
     */
    protected void readBuffer(Buffer buffer) throws IOException {
        buffer.readBuffer();
        diskRead++;
    }
    
    /**
     * Writes the buffer back to disk, and increments the amount
     * of disk writes performed by this buffer
     * @param buffer The buffer to write
     * @throws IOException If the channel is closed/invalid
     */
    protected void writeBuffer(Buffer buffer) throws IOException {
        buffer.writeBuffer();
        diskWrite++;
    }
    
    /**
     * Places the inputted node as head, shifting 
     * the rest of node around it if it already existed
     * @param node The node to put as  head
     */
    protected void updateNode(LinkedList<Buffer> node) {
        if (node.equals(head)) {
            return;
        }
        LinkedList<Buffer> prev = node.prev();
        LinkedList<Buffer> next = node.next();
        
        if (prev != null) {
            prev.setNext(next);
        }
        
        if (next != null) {
            next.setPrev(prev);
        }
        
        node.setPrev(null);
        
        if (head != null) {
            node.setNext(head);
            head.setPrev(node);
        }
        
            
        head = node;
    }
    
    /**
     * Gets the last node linked to head
     * @return The last node linked to head, or null
     *         if head is null
     */
    protected LinkedList<Buffer> getTail() {
        LinkedList<Buffer> node = head;
        while (node.hasNext()) {
            node = node.next();
        }
        
        return node;
    }
}
