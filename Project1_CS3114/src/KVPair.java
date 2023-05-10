/**
 * Class to store a key value pair
 * @author Kofi Ofosu-Tuffour erikchair
 * @version 09.09.2022
 *
 * @param <K> The key type to use
 * @param <E> The value type to use
 */
public class KVPair<K extends Comparable<K>, E> 
        implements Comparable<KVPair<K, E>> {
    private K theKey;
    private E theVal;

    /**
     * Initialize the object with a key and a value
     * @param k The key to store
     * @param v The value to store
     */
    KVPair(K k, E v) {
        theKey = k;
        theVal = v;
    }

    /**
     * Returns a compared difference between two KVPair (based on key)
     * @param it The KVPair to compare to
     * @return 0 is when keys are the same
     *         >0 is when this key is greater than the other
     *         <0 is when this key is less than the other
     */
    public int compareTo(KVPair<K, E> it) {
        return theKey.compareTo(it.key());
    }

    /**
     * Returns a compared difference between two K keys
     * @param it The Key to compare to
     * @return 0 is when keys are the same
     *         >0 is when this key is greater than the other
     *         <0 is when this key is less than the other
     */
    public int compareTo(K it) {
        return theKey.compareTo(it);
    }
    
    /**
     * Retrieves the key stored
     * @return The key stored
     */
    public K key() {
        return theKey;
    }
    
    /**
     * Retrieves to value stored
     * @return The value stored
     */
    public E value() {
        return theVal;
    }
    
    /**
     * Returns a string representation of the KVPair
     * @return A string where the format is:
     *            key, value
     */
    public String toString() {
        return theKey.toString() + ", " + theVal.toString();
    }
}
/* *** ODSAendTag: KVPair *** */