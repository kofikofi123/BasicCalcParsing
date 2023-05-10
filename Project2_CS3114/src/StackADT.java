
/**
 * 
 * @author erikchair
 * @version 09.26.2022
 * 
 * ADT for stack data structure
 * 
 * @param <T> Element type the stackADT will use
 */
public interface StackADT<T> {
    /**
     * Pushes an element, <T>, to the stack
     * @param element The element to push
     * @return Whether the operation was successful
     */
    boolean push(T element);
    
    /**
     * Pops an element, <T>, from the stack
     * @return Returns the element popped off the stack
     */
    T pop();
    
    
    /**
     * Gets the element at the top of the stack
     * @return The element at the top, or null
     */
    T peek();
    
    /**
     * Checks whether the stack is empty
     * @return True if the stack is empty, false otherwise
     */
    boolean isEmpty();
    
    
    /**
     * The number of elements in the stack
     * @return TThe number of elements in the stack
     */
    int size();
    
    /**
     * Puts the stack into default state
     */
    void reset();
}
