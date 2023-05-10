import org.junit.Before;
import org.junit.Test;
import student.TestCase;

/**
 * Test class for TreeLeafNode
 * @author erikchair
 * @version 11.22.2022
 */
public class TreeLeafNodeTest extends TestCase {
    private TreeNode parentNode;
    private TreeLeafNode<String> leafNode;
    
    /**
     * Sets up the test environment
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        parentNode = new TreeInternalNode(1);
        leafNode = new TreeLeafNode<String>("Test", parentNode);
    }

    /**
     * Ensures that the leafNode is not an internal node
     * and not a flyweight node
     */
    @Test
    public void testIsInternal() {
        assertFalse(leafNode.isInternal());
        assertFalse(leafNode.isFlyweight());
    }

    /**
     * Checks the TreeLeafNode object to ensure
     * that it is constructed correctlky
     */
    @Test
    public void testTreeLeafNode() {
        assertEquals("Leaf Node: [Test]", leafNode.toString());
        assertEquals(parentNode, leafNode.getParent());
    }

    /**
     * Checks the method to ensure that
     * the correct value is returned
     */
    @Test
    public void testGetElement() {
        assertEquals("Test", leafNode.getElement());
    }

}
