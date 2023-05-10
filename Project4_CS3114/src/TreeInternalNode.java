import java.util.Arrays;

/**
 * An internal TreeNode class
 * @author erikchair
 * @version 10.20.2022
 */
public class TreeInternalNode extends TreeNode {
    private TreeNode[] children;
    private int currentNumChildren;
    /**
     * Default constructor for a
     * TreeInternalNode with no parent
     * and no children
     */
    TreeInternalNode() {
        this(1, TreeFlyWeightNode.getInstance());
    }
    
    /**
     * Constructor for TreeInternalNode with
     * no parent and a specific number
     * of children
     * @param numChildren The number of children     
     *                       for this node
     */
    TreeInternalNode(int numChildren) {
        this(numChildren, TreeFlyWeightNode.getInstance());
    }
    
    /**
     * Constructor for TreeInternalNode with
     * a parent and children
     * @param numChildren The number of children
     *                       for this node
     * @param parent The parent node for this node
     */
    TreeInternalNode(int numChildren, TreeNode parent) {
        super(parent);
        children = new TreeNode[numChildren];
        currentNumChildren = 0;
        
        TreeFlyWeightNode empty = TreeFlyWeightNode.getInstance();
        Arrays.fill(children, empty);
    }
    
    
    /**
     * Gets the TreeNode child at a position
     * @param child The child index to get
     * @return A TreeNode child of this object
     */
    public TreeNode getChild(int child) {
        return children[child];
    }
    
    /**
     * Sets the TreeNode child at a position
     * @param child The child index to set
     * @param node The TreeNode child to use
     */ 
    public void setChild(int child, TreeNode node) {
        if (node == null) {
            node = TreeFlyWeightNode.getInstance();
        }
        
        children[child] = node;
        
        node.setParent(this);
        node.setChildIndex(child);


        updateCurrentNumChildren();
    }
    
    /**
     * Calculates and caches the current number
     * of children in the internal node
     */
    private void updateCurrentNumChildren() {
        int count = 0;
        int length = children.length;
        for (int i = 0; i < length; i++) {
            if (!children[i].isFlyweight()) {
                count++;
            }
        }
        currentNumChildren = count;
    }
    
    /**
     * Gets the current number of children 
     * @return THe number of children
     */
    public int getNumChildren() {
        return currentNumChildren;
    }
    /**
     * Returns true to indicate this class is 
     * an internal
     * @return True everytime
     */
    @Override
    public boolean isInternal() {
        return true;
    }
    
    /**
     * Returns false to indicate this class
     * if not a flyweight node
     * @return false everytime
     */
    @Override
    public boolean isFlyweight() {
        return false;
    }
    
    /**
     * Creates a string representation of the LeafInternalNode object
     * Format: "Internal Node: [Children: children.size()]"
     * @return A string representation of the object
     */
    @Override
    public String toString() {
        return "Internal Node: [Children: " + children.length + "]";
    }
}
