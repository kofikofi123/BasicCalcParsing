import java.io.IOException;

/**
 * MaxHeap impelmentation using a BufferPool interface
 * to a random access array-list structure
 * @author erikchair
 * @version 10.24.2022
 */
public class MaxHeap {
    private BufferPool arrayPool;
    private long heapCapacity;
    
    /**
     * Constructor that takes in BufferPool and capacity
     * argument for construction
     * @param poolArray The BufferPool object to interface with
     * @param capacityHeap THe amount of elements in the array
     * @throws IOException 
     */
    MaxHeap(BufferPool poolArray, long capacityHeap) throws IOException {
        arrayPool = poolArray;
        heapCapacity = capacityHeap;
        buildHeap();
    }
    
    /**
     * Gets the maximum value in the heap
     * @return The buffer associated with the 
     *         maxiumum key
     * @throws IOException if channel is closed/invalid
     */
    public Buffer removeMax() throws IOException {
        heapCapacity--;
        swap(0, heapCapacity);
        siftDown(0);
        return get(heapCapacity);
    }
    
    /**
     * Gets the Buffer at the specific position from
     * the buffer pool
     * @param pos The position to retrieve
     * @return A valid Buffer object at the speicifc
     *         position
     * @throws IOException If the channel is closed/invalid
     */
    public Buffer get(long pos) throws IOException {
        return arrayPool.requestLine(pos);
    }
    
    /**
     * Determines whether a position in the tree
     * is a leaf (no children)
     * @param pos The position to determine
     * @return True if a position is a leaf
     *         False otherwise
     */
    protected boolean isLeaf(long pos) {
        return (heapCapacity / 2) <= pos &&
               (pos < heapCapacity);
    }
    
    /**
     * Gets the parent of the current position
     * @param pos The position for calculation
     * @return The parent position of this position
     */
    protected long parent(long pos) {
        return (pos - 1) / 2;
    }
    
    /**
     * Gets the left child of the current position
     * @param pos The position for calculation
     * @return The left child position of this position
     */
    protected long leftChild(long pos) {
        return (pos * 2) + 1;
    }
    
    
    /**
     * Checks to see if a position is in 
     * the Heap array
     * @param pos The position the check
     * @return True if the position is within array bounds
     *         False otherwise
     */
    protected boolean inHeapArray(long pos) {
        return pos < heapCapacity;
    }
    
    
    /**
     * Moves an element at a specific position in the tree 
     * downwards along the tree if either of its children are greater
     * than it
     * @param pos The pos to sift
     * @throws IOException If the channel is closed/invalid
     */
    protected void siftDown(long pos) throws IOException {
        if (pos < 0 || !inHeapArray(pos)) {
            return;
        }
        while (!isLeaf(pos)) {
            long lChild = leftChild(pos);
            long rChild = lChild + 1;
            long swapChild = lChild;
            
            if (inHeapArray(rChild) && isGreaterThan(rChild, lChild)) {
                swapChild = rChild;
            }
            
            if (!isGreaterThan(swapChild, pos)) {
                return;
            }
            swap(swapChild, pos);
            pos = swapChild;
        }
    }
    
    /**
     * Builds the heap up to stable state
     * @throws IOException If the channel is closed/invalid
     */
    protected void buildHeap() throws IOException {
        for (long i = parent(heapCapacity - 1); i >= 0; i--) {
            siftDown(i);
        }
    }
    
    /**
     * Compares the keys from one buffer to another
     * to detemrine whether one is greater
     * @param posA The first position to consider
     * @param posB The second position to consider
     * @return True if the key at posA is greater than the 
     *              key at posB
     *         False otherwise
     * @throws IOException If the channel is closed/invalid
     */
    protected boolean isGreaterThan(long posA, long posB) throws IOException {
        return get(posA).compareTo(get(posB)) > 0;
    }
    
    /**
     * Swaps the places of two positions for Buffers
     * @param posA The Buffer object at posA
     * @param posB The Buffer object at posB
     * @throws IOException If channel is closed/invalid
     */
    protected void swap(long posA, long posB) throws IOException {
        get(posA).swapBuffers(get(posB));
    }
    
}
