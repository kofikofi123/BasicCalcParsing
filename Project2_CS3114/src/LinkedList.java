
/**
 * Implementation of the Linked Listt
 * @author erikchair
 * @version 09.26.2022
 *
 * @param <T> Type to use for element
 */
public class LinkedList<T> {
    private T element;
    private LinkedList<T> nextNode;
    
    /**
     * Default constructor for Linked List;
     * All variables set to null
     */
    LinkedList() {
        this(null, null);
    }
    
    /**
     * Constructor to set the element
     * and node after this
     * @param dataElement Element to use for this node
     * @param nodeNext The node after this one
     */
    LinkedList(T dataElement, LinkedList<T> nodeNext) {
        element = dataElement;
        nextNode = nodeNext;
    }
    
    /**
     * Gets the next element after this Linked List
     * @return The Linked List after this one, or null
     */
    public LinkedList<T> next() {
        return nextNode;
    }

    /**
     * Sets the next linked node
     * @param other The linked node to set to next
     * @return True if operation was successful,
     *         false otherwise
               
     */
    public boolean setNext(LinkedList<T> other) {
        nextNode = other;
        
        return true;
    }

    /**
     * Checks to see if the current node has
     * a next node
     * @return True if there is another node
     *              after this, false otherwise
     */
    public boolean hasNext() {
        return nextNode != null;
    }
    
    /**
     * Returns the data associated with the node
     * @return The data associated with this node
     */
    public T data() {
        return element;
    }

}
