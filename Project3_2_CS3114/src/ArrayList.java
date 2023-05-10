
/**
 * ADT for Array like objects
 * @author erikchair
 * @version 10.30.2022
 * @param <T> The element type of the Array
 */
public interface ArrayList<T> {
    /**
     * Gets an element from the ArrayList object
     * at an index
     * @param index The index into the ArrayList object
     * @return The element The element T at the index
     * @throws Exception Any array access error
     */
    T get(long index) throws Exception;
    
    /**
     * Sets an element to location index at
     * ArrayList object
     * @param index The index into the ArrayList object
     * @param value The value to set
     * @throws Exception Any array access error
     */
    void set(long index, T value) throws Exception;
}