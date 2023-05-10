
/**
 * Implementation of the FlyWeight node
 * @author erikchair
 * @version 11.20.2022
 */
public final class TreeFlyWeightNode extends TreeNode {
    private static TreeFlyWeightNode node;
    /**
     * Default constructor for flyweight node
     * @param parent The parent for the flyweight node
     */
    private TreeFlyWeightNode() {
        super(null);
    }
    
    /**
     * Returns the singleton flyweight node used
     * @return A TreeFlyWeightNode singleton object
     */
    public static TreeFlyWeightNode getInstance() {
        if (node == null) {
            node = new TreeFlyWeightNode();
        }
        
        return node;
    }
    
    /**
     * Ensures that a flyweight node has no 
     * change in parent node
     * @param newParent The new parent to use
     */
    @Override
    public void setParent(TreeNode newParent) {
        newParent = null;
    }
    /**
     * Returns false to indicate this is not
     * an internal node
     * @return False everytime
     */
    @Override
    public boolean isInternal() {
        return false;
    }
    
    /**
     * Returns true to indicate this is
     * a flyweight node
     * @return True everytime
     */
    @Override
    public boolean isFlyweight() {
        return true;
    }
}
