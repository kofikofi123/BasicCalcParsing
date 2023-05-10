
/**
 * A leaf TreeNode class
 * @author erikchair
 * @version 10.20.2022
 * @param <T> The element type this class uses
 */
public class TreeLeafNode<T extends Comparable<T>> 
    extends TreeNode
    implements Comparable<TreeLeafNode<T>> {
    private T element;
    
    /**
     * Constructor for TreeLeafNode with an
     * element and a parent
     * @param newElement The element stored by the leaf
     * @param parent The parent used
     */
    TreeLeafNode(T newElement, TreeNode parent) {
        super(parent);
        element = newElement;
    }
    
    /**
     * Gets the element stored by the leaf
     * @return The element stored
     */
    public T getElement() {
        return element; 
    }
    
    /**
     * Creates a string representation of the LeafTreeNode object
     * Format: "Leaf Node: [element.toString()]"
     * @return A string representation of the object
     */
    @Override
    public String toString() {
        return "Leaf Node: [" + element.toString() + "]";
    }
    
    /**
     * Returns false to indicate this class is 
     * not an internal node class
     * @return False everytime
     */
    @Override
    public boolean isInternal() {
        return false;
    }
    
    /**
     * Returns false to indicate this class is
     * not a flyweight node class
     * @return False everytime
     */
    public boolean isFlyweight() {
        return false;
    }
    
    /**
     * Compares two TreeLeafNode objects by their
     * element
     * 
     * @param o The other TreeLeafNode<T> object
     * @return 0   if both leaf's elements are equal
     *         >0  if this leaf content is greater than the 
     *                other leaf content
     *         <0  if this leaf content is less than the
     *                other leaf content
     */
    @Override
    public int compareTo(TreeLeafNode<T> o) {
        return element.compareTo(o.element);
    }
}
