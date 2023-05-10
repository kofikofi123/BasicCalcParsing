import java.io.IOException;
import java.nio.channels.SeekableByteChannel;

/**
 * An ArrayList implementation that
 * caches multiple blocks of data
 * for array access
 * @author erikchair
 * @version 10.30.2022
 * @param <T> Element of ArrayList
 */
public class BufferPool<T extends BufferedObject & Comparable<T>> 
    implements ArrayList<T> {
    private LinkedList<Buffer> head;
    private int capacity;
    private int size;
    
    private int cacheHit;
    private int cacheMiss;
    
    private int diskRead;
    private int diskWrite;
    
    private int blockSize;
    
    private SeekableByteChannel channel;
    
    private Class<T> objectClass;
    
    /**
     * Constructor for BufferPool that relies on a
     * I/O channel, the amount of buffers to allocate
     * and default buffer block sizes
     * @param classObject the BufferedObject class to use
     * @param inChannel The I/O channel to use
     * @param inCapacity The amount of buffers to use
     */
    BufferPool(Class<T> classObject, 
               SeekableByteChannel inChannel, 
               int inCapacity) {
        this(classObject, inChannel, inCapacity, Buffer.BLOCK_SIZE);
    }
    
    /**
     * Constructor for BufferPool that relies on a
     * I/O channel, the amount of buffers to allocate,
     * and a specified buffer block size
     * @param classObject the BufferedObject class to use
     * @param inChannel The I/O channel to use
     * @param inCapacity The amount of buffers to use
     * @param sizeBlock The buffer block sizes
     */
    BufferPool(Class<T> classObject, 
               SeekableByteChannel inChannel, 
               int inCapacity, 
               int sizeBlock) {
        head = null;
        capacity = inCapacity;
        size = 0;
        cacheHit = 0;
        cacheMiss = 0;
        diskRead = 0;
        diskWrite = 0;
        blockSize = sizeBlock;
        channel = inChannel;
        objectClass = classObject;
    }
    
    /**
     * Gets an element from buffers at an index
     * @param position The index into a Buffer object
     * @return The element The element T at the index
     * @throws Exception Any array access error
     */
    @Override
    public T get(long position) 
        throws Exception {
        //Record record = new Record();
        T record = null;
        
        record = objectClass
            .getDeclaredConstructor()
            .newInstance();
        long truePosition = 
            position * record.stride();
        Buffer buffer = requestBuffer(truePosition);
        buffer.readEntry(record, position);
        return record;
    }
    
    /**
     * Sets an element to location index at a
     * Buffer object
     * @param position The index into the Buffer object
     * @param value The value to set
     * @throws Exception Any array access error
     */
    @Override
    public void set(long position, T value) throws IOException {
        long truePosition =
            position * value.stride();
        Buffer buffer = requestBuffer(truePosition);
        buffer.writeEntry(value, position);
    }
    
    /**
     * Writes all dirty Buffers to I/O
     * @throws IOException If the I/O channel is closed/invalid
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
     * Gets the number cache hits
     * @return The number of cache hits
     */
    public int getCacheHit() {
        return cacheHit;
    }
    
    /**
     * Gets the number cache reads
     * @return The number of cache read
     */
    public int getCacheMiss() {
        return cacheMiss;
    }
    
    /**
     * Gets the number of reads on I/O
     * channels
     * @return The number of reads on I/O
     */
    public int getDiskRead() {
        return diskRead;
    }
    
    /**
     * Gets the number of writes on I/O
     * channels
     * @return The number of writes on I/O
     */
    public int getDiskWrite() {
        return diskWrite;
    }
    
    /**
     * Gets an Buffer object that targets index 
     * within the region held by a Buffer
     * or allocates a new Buffer that has this
     * position in its region
     * @param position The position to use
     * @return A buffer that has the position
     *         in its region
     * @throws IOException If the I/O channel is closed/invalid
     */
    protected Buffer requestBuffer(long position) throws IOException {
        LinkedList<Buffer> nodeBuffer = findBuffer(position);
        Buffer buffer = null;
        if (nodeBuffer == null) {
            cacheMiss++;
            nodeBuffer = allocateBuffer(position);
        }
        else {
            cacheHit++;
            buffer = nodeBuffer.data();
        }
        buffer = nodeBuffer.data();
        updateNode(nodeBuffer);
        return buffer;
    }
    
    /**
     * Makes space for a new Buffer object, or takes
     * the least used node (the tail node). Updates the new node
     * to most recently used
     * @param position The buffer based on position
     * @return The node pointing to the buffer
     *         that controls the region with the
     *         position
     * @throws IOException If the I/O channel is closed/invalid
     */
    protected LinkedList<Buffer> allocateBuffer(long position) 
        throws IOException {
        LinkedList<Buffer> node = null;
        Buffer buffer = null;
        if (size < capacity) {
            buffer = new Buffer(channel, blockSize, position);
            
            node =
                new LinkedList<Buffer>(buffer, null, null);
            
            size++;
        }
        else {
            node = getTail();
            buffer = node.data();
            
            if (buffer.isDirty()) {
                writeBuffer(buffer);
            }
            buffer.reset(position);
        }
        
        readBuffer(buffer);
        
        return node;
    }
    
    /**
     * Gets the last node linked to head
     * @return The LinkedList node at the 
     *         end of the list
     */
    protected LinkedList<Buffer> getTail() {
        LinkedList<Buffer> node = head;
        
        while (node.hasNext()) {
            node = node.next();
        }
        
        
        return node;
    }
    
    
    /**
     * Reads the buffer from I/O and increments the
     * number of I/O reads
     * @param buffer The buffer to perform a I/O read
     * @throws IOException If the channel is closed/invalid
     */
    protected void readBuffer(Buffer buffer) throws IOException {
        diskRead++;
        buffer.readBuffer();
    }
    
    /**
     * Writes the buffer from I/O and increments the 
     * number of I/O writes
     * @param buffer The buffer to perform a I/O write
     * @throws IOException If the channel is closed/invalid
     */
    protected void writeBuffer(Buffer buffer) throws IOException {
        diskWrite++;
        buffer.writeBuffer();
    }
    
    /**
     * Makes a node the most recently used, and
     * shifts nodes down
     * @param node The node to make MRU
     */
    protected void updateNode(LinkedList<Buffer> node) {
        if (head == node) {
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
     * Find buffers that have the position in its
     * region
     * @param position The position to check
     * @return A node with the Buffer that has the region
     *         or null
     */
    protected LinkedList<Buffer> findBuffer(long position) {
        LinkedList<Buffer> node = head;
        
        while (node != null) {
            Buffer buffer = node.data();
            if (buffer.getIndex() == 
                Buffer.calculateBlockIndex(position, buffer.getBlockSize())) {
                break;
            }
            
            node = node.next();
        }
        
        return node;
    }
}
