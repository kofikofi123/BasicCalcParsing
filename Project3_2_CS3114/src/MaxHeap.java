/**
 * MaxHeap implementation over an 
 * array of short values
 * @author erikchair
 * @version 10.30.2022
 * @param <T> The element of ArrayList
 */
public class MaxHeap<T extends Comparable<T>> {
    private ArrayList<T> list;
    private long capacity;
    
    /**
     * Creates a max heap with
     * the given array
     * @param inList The list to work with
     * @param inCapacity Size of the list
     * @throws Exception any exception dealing with
     *                   array access
     */
    MaxHeap(ArrayList<T> inList, long inCapacity) throws Exception {
        list = inList;
        capacity = inCapacity;
        buildHeap();
    }
    
    /**
     * Removes the maximum value in the array
     * @return The maximum value in the array
     * @throws Exception any exception dealing with
     *                   array access
     */
    public T removeMax() throws Exception {
        capacity--;
        swap(0, capacity);
        siftDown(0);
        return get(capacity);
    }
    
    /**
     * Sets a certain value at a position
     * in the list
     * @param pos The index to set the new
     *            value
     * @param value The value to set
     * @throws Exception any exception dealing with
     *                   array access
     */
    protected void set(long pos, T value) throws Exception {
        list.set(pos, value);
    }
    
    /**
     * Gets the value at a position
     * @param pos The index of the value
     * @return The value at the position
     * @throws Exception any exception dealing with
     *                   array access
     */
    protected T get(long pos) throws Exception {
        return list.get(pos);
    }
    
    /**
     * Swaps values at 2 positions
     * @param posA The first value to swap with the second
     * @param posB The second value to swap with the first
     * @throws Exception any exception dealing with
     *                   array access
     */
    protected void swap(long posA, long posB) throws Exception {
        T temp = get(posA);
        set(posA, get(posB));
        set(posB, temp);
    }
    
    /**
     * Builds the heap into a stable state
     * @throws Exception any exception dealing with
     *                   array access
     */
    protected void buildHeap() throws Exception {
        for (long i = parent(capacity - 1); i >= 0; i--) {
            siftDown(i);
        }
    }
    
    /**
     * Shifts the value at the current position down the 
     * tree if it's child value is greater than it
     * @param pos The position to shift down
     * @throws Exception any exception dealing with
     *                   array access 
     */
    protected void siftDown(long pos) throws Exception {
        if (pos < 0 || !inArray(pos)) {
            return;
        }
        
        while (!isLeaf(pos)) {
            long childLeft = leftChild(pos);
            long childRight = childLeft + 1;
            long swapPos = childLeft;
            
            
            if (inArray(childRight) && 
                isGreaterThan(childRight, childLeft)) {
                swapPos = childRight;
            }
            
            if (!isGreaterThan(swapPos, pos)) {
                return;
            }
            swap(pos, swapPos);
            pos = swapPos;
        }
    }
    
    /**
     * Checks to see if the current 
     * position would be a leaf
     * @param pos The position to check
     * @return True if the position is a leaf 
     *              node position
     *         False otherwise
     */
    protected boolean isLeaf(long pos) {
        return ((capacity / 2) <= pos) &&
               (pos < capacity);
    }
    
    /**
     * Calculates the left child position
     * @param pos The parent position
     * @return The calculated left position
     */
    protected long leftChild(long pos) {
        return (pos * 2) + 1;
    }
    
    /**
     * Calculates the parent position
     * @param pos The child position
     * @return The calculated parent position
     */
    protected long parent(long pos) {
        return (pos - 1) / 2;
    }
    
    /**
     * Checks to see if the pos is in
     * array bounds
     * @param pos The position to check
     * @return True if the position is in 
     *              array bounds
     *         False otherwise
     */
    protected boolean inArray(long pos) {
        return pos < capacity;
    }
    
    /**
     * Checks to see if one position's value
     * is greater than another position's value
     * @param posA First position to compare
     * @param posB Second position to compare
     * @return True if the value at posA is greater
     *              than the value at posB
     *         False otherwise
     * @throws Exception any exception dealing with
     *                   array access
     */
    protected boolean isGreaterThan(long posA, long posB) throws Exception {
        return (get(posA).compareTo(get(posB))) > 0;
    }
    
    
}
