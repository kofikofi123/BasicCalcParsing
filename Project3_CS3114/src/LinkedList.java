
/**
 * Implementation of the Linked Listt
 * @author erikchair
 * @version 10.23.2022
 *
 * @param <T> Type to use for element
 */
public class LinkedList<T> {
    private T element;
    private LinkedList<T> nextNode;
    private LinkedList<T> prevNode;
    
    /**
     * Default constructor for Linked List;
     * All variables set to null
     */
    LinkedList() {
        this(null, null, null);
    }
    
    /**
     * Constructor to set the element
     * and node after this
     * @param dataElement Element to use for this node
     * @param nodeNext The node after this one
     * @param nodePrev The node before this one
     */
    LinkedList(T dataElement, 
               LinkedList<T> nodeNext,
               LinkedList<T> nodePrev) {
        element = dataElement;
        nextNode = nodeNext;
        prevNode = nodePrev;
        
    }
    
    /**
     * Gets the next element after this Linked List
     * @return The Linked List after this one, or null
     */
    public LinkedList<T> next() {
        return nextNode;
    }
    
    /**
     * Gets the next element before this Linked List
     * @return The Linked List before this one, or null
     */
    public LinkedList<T> prev() {
        return prevNode;
    }

    /**
     * Sets the next linked node
     * @param other The linked node to set to next     
     */
    public void setNext(LinkedList<T> other) {
        nextNode = other;
    }
    
    /**
     * Sets the prev linked node
     * @param other The linked node to set to prev  
     */
    public void setPrev(LinkedList<T> other) {
        prevNode = other;
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
     * Checks to see if the current node has
     * a prev node
     * @return True if there is another node
     *              after this, false otherwise
     */
    public boolean hasPrev() {
        return prevNode != null;
    }
    
    /**
     * Returns the data associated with the node
     * @return The data associated with this node
     */
    public T data() {
        return element;
    }

}
