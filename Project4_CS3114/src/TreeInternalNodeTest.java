import org.junit.Before;
import org.junit.Test;

import student.TestCase;

/**
 * Test case for TreeInternalNode
 * @author erikchair
 * @version 11.22.2022
 */
public class TreeInternalNodeTest extends TestCase {
    private TreeNode rootNode;
    private TreeInternalNode internalNode;
    /**
     * Sets up the test environment
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        
        rootNode = new TreeInternalNode(1);
        internalNode = new TreeInternalNode(2, rootNode);
        
        
        internalNode.setChild(0, 
            new TreeLeafNode<String>(
                "Child0", 
                internalNode));
        internalNode.setChild(1, 
            new TreeLeafNode<String>(
                "Child1", 
                internalNode));
    }
    
    /**
     * Ensures that this is an internal node
     * and is not a flyweight node
     */
    @Test
    public void testIsInternal() {
        assertTrue(internalNode.isInternal());
        assertFalse(internalNode.isFlyweight());
    }
    
    /**
     * Ensures that the internal node is 
     * constructed correctly
     */
    @Test
    public void testTreeInternalNode() {
        assertEquals("Internal Node: [Children: 2]", 
            internalNode.toString());
        assertEquals(rootNode, internalNode.getParent());
    }

    /**
     * Ensures that the correct child
     * is return from the internal node
     */
    @Test
    public void testGetChild() {
        TreeNode node = internalNode.getChild(1);
        
        assertFalse(node.isInternal());
        
        @SuppressWarnings("unchecked")
        TreeLeafNode<String> leafNode = 
            (TreeLeafNode<String>) node;
        
        assertEquals("Leaf Node: [Child1]", 
            leafNode.toString());
    }

    /**
     * Ensures that the new child
     * is correctly added to the internal
     * node
     */
    @Test
    public void testSetChild() {
        internalNode.setChild(0, 
            new TreeInternalNode(2, internalNode));
        
        TreeInternalNode node = 
            (TreeInternalNode) internalNode.getChild(0);
        
        assertEquals("Internal Node: [Children: 2]", 
            node.toString());
    }
    
    
    /**
     * Tests to ensure that the 
     * number of children in the node is
     * consistent
     */
    public void testGetNumChildren() {
        assertEquals(2, internalNode.getNumChildren());
        internalNode.setChild(0,  null);
        assertEquals(1, internalNode.getNumChildren());
    }

}
