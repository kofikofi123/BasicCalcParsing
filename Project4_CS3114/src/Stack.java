
/**
 * Implementation of the StackADT
 * @author erikchair
 * @version 09.26.2022
 * @param <T> Type to use as element
 */
public class Stack<T> implements StackADT<T> {
    private LinkedList<T> top;
    private int size;
    
    /**
     * Default constructor for stack,
     * sets stack to default state
     */
    Stack() {
        top = null;
        size = 0;
    }
    
    /**
     * Pushes an element, <T>, to the stack
     * @param element The element to push
     * @return If element is null, return false
     *         return true otherwise
     */
    @Override
    public boolean push(T element) {
        if (element == null) {
            return false;
        }
        
        LinkedList<T> newTop = new LinkedList<T>(element, top);
        
        top = newTop;
        
        size++;
        
        return true;
    }

    /**
     * Pops an element, <T>, from the stack
     * @return Returns the element popped off the stack,
     *         or null if empty
     */
    @Override
    public T pop() {
        T element = null;
        
        if (top != null) {
            element = top.data();
            top = top.next();
            size--;
        }
        return element;
    }

    /**
     * Gets the element at the top of the stack
     * @return The element at the top, 
     *         or null if empty
     */
    @Override
    public T peek() {
        T element = null;
        
        if (top != null) {
            element = top.data();
        }
        
        return element;
    }

    /**
     * Checks whether the stack is empty
     * @return True if the stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * The number of elements in the stack
     * @return TThe number of elements in the stack
     */
    @Override
    public int size() {
        return size;
    }
    
    /**
     * Puts the stack into default state
     */
    @Override
    public void reset() {
        top = null;
        size = 0;
    }

}
