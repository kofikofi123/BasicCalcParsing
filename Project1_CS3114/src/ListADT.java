import java.util.Iterator;

/**
 * The Abstract Data 
 * Type used for lists in this project
 * @author erikchair
 * @version 09.09.2022
 *
 * @param <K> The key type used in the list
 * @param <V> The value type used in the list
 */
public interface ListADT
    <K extends Comparable<K>, V> 
        extends Iterable<SkipNode<K, V>> {
    
    /**
     * ADT method for finding an element with a key
     * @param key The key to use
     * @return A list of found elements
     */
    public ListADT<K, V> find(K key);
    
    /**
     * Insertion of a value 
     * with a key associated to it
     * @param key The key to use
     * @param value The value to use
     * @return Whether the insert function
     *         worked
     */
    public boolean insert(K key, 
                          V value);
    
    /**
     * Remove an element by its key
     * @param key The key to use
     * @return The V value removed
     */
    public V remove(K key);
   
    /**
     * Gets the number of elements contained 
     * in the list
     * @return The number of elements
     */
    public int size();
}
