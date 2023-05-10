/**
 * Base class for tree nodes
 * @author erikchair
 * @version 11.20.2022
 */
public abstract class TreeNode {
    /**
     * The TreeNode object's parent
     * node
     */
    protected TreeNode parentNode;
    /**
     * The TreeNode object's index
     * in the parent's chldren array
     */
    protected int childIndex;
    /**
     * The TreeNode object's level
     * compared to the parent's 
     * level
     */
    protected int level;
    /**
     * Constructor for the TreeNode class
     * with a parent
     * @param parent The parent to use for 
     *               this structure
     */
    TreeNode(TreeNode parent) {
        childIndex = 0;
        setParent(parent);
    }
    
    /**
     * Gets the parent associated with 
     * this TreeNode object
     * @return The parent of this node
     */
    public TreeNode getParent() {
        return parentNode;
    }
    
    /**
     * Sets the parent for this TreeNode object
     * @param parent The parent to use for this node
     */
    public void setParent(TreeNode parent) {
        if (!parent.isInternal() && !parent.isFlyweight()) {
            return;
        }
        parentNode = parent;
        
        if (!parentNode.isFlyweight()) {
            level = parentNode.level + 1;
        }
    }
    
    /**
     * Sets the child index based in
     * the parent structure
     * @param index The index to set
     */
    protected void setChildIndex(int index) {
        childIndex = index;
    }
    
    /**
     * Returns the current level of the node
     * in a parent structure
     * @return The level of the node
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Returns the current index of the node
     * in a parent structure
     * @return The child index of the node
     */
    public int getChildIndex() {
        return childIndex;
    }
    
    /**
     * Resets the level
     */
    public void resetLevel() {
        level = 0;
    }
    
    /**
     * Checks if the TreeNode is an internal node 
     * (A node with children)
     * @return True if the node is an internal node
     *            False otherwise
     */
    abstract public boolean isInternal();
    
    /**
     * Checks if the TreeNode is a flyweight node
     * (A node with no children)
     * @return True if the node is an internal node
     *            False otherwise
     */
    abstract public boolean isFlyweight();
}
