import org.junit.Before;
import org.junit.Test;

import student.TestCase;

/**
 * Test case for DNATreeStructure
 * @author erikchair
 * @version 11.30.20
 */
public class DNATreeStructureTest extends TestCase {
    private DNATreeStructure tree;
    private String commonExpected1;
    private String commonExpected2;
    private String commonExpected3;
    
    /**
     * Sets up the test enviornment
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        tree = new DNATreeStructure();
        
        
        commonExpected1 = "ACGT";
        commonExpected2 = "I\n"
                + "  I\n"
                + "    AAAA\n"
                + "    ACGT\n"
                + "    E\n"
                + "    E\n"
                + "    E\n"
                + "  E\n"
                + "  E\n"
                + "  E\n"
                + "  E";
        commonExpected3 = "I\n"
                + "  I\n"
                + "    I\n"
                + "      AAAA\n"
                + "      E\n"
                + "      E\n"
                + "      E\n"
                + "      AA\n"
                + "    ACGT\n"
                + "    E\n"
                + "    E\n"
                + "    E\n"
                + "  E\n"
                + "  E\n"
                + "  E\n"
                + "  E";
    }

    /**
     * Ensures that the construction of the DNATree
     * is correct
     */
    @Test
    public void testDNATree() {
        assertEquals("E", tree.dump());
    }

    /**
     * Ensures that insert operations to the 
     * tree is correct
     */
    @Test
    public void testInsert() {
        assertEquals("E", tree.dump());
        TreeLeafNode<String> node = 
                tree.insert("ACGT");
        
        assertNotNull(node);
        assertEquals("ACGT", node.getElement());
        assertEquals(0, node.getLevel());
        assertEquals(commonExpected1, tree.dump());
        
        
        node = tree.insert("AAAA");
        
        assertNotNull(node);
        assertEquals("AAAA", node.getElement());
        assertEquals(2, node.getLevel());
        assertEquals(commonExpected2, tree.dump());
        
        
        node = tree.insert("AA");
        
        assertNotNull(node);
        assertEquals("AA", node.getElement());
        assertEquals(3, node.getLevel());
        assertEquals(commonExpected3, tree.dump());
        
        
        node = tree.insert("AAAA");
        
        assertNull(node);
    }

    /**
     * Ensures that remove operations to the
     * tree is correct
     */
    public void testRemove() {
        tree.insert("ACGT");
        tree.insert("AAAA");
        tree.insert("AA");
        
        assertEquals(commonExpected3, tree.dump());
        
        TreeLeafNode<String> node = 
                tree.remove("AA");
        
        assertNotNull(node);
        assertEquals("AA", node.getElement());
        assertEquals(3, node.getLevel());
        assertEquals(commonExpected2, tree.dump());
        
        node = tree.remove("AGGT");
        assertNull(node);
        assertEquals(commonExpected2, tree.dump());
        
        node = tree.remove("AAAA");
        
        assertNotNull(node);
        assertEquals("AAAA", node.getElement());
        assertEquals(2, node.getLevel());
        assertEquals(commonExpected1, tree.dump());
        
        node = tree.remove("ACGT");
        
        assertNotNull(node);
        assertEquals("ACGT", node.getElement());
        assertEquals(0, node.getLevel());
        assertEquals("E", tree.dump());
    }
    
    /**
     * Ensures that search operation to the
     * tree (both forms) are correct
     */
    public void testSearch() {
        tree.insert("ACGT");
        tree.insert("AAAA");
        tree.insert("AA");
        tree.insert("AAACCCCGGTGAAAACGTA");
        tree.insert("ACTT");
        
        
        DNATreeSearchResult result =
            tree.search("AA");
        
        assertEquals(13, result.getNodesSearched().size());
        assertEquals(3, result.getNodes().size());
        
        result = tree.search("AA$");
        
        assertEquals(4, result.getNodesSearched().size());
        assertEquals(1, result.getNodes().size());
        
        
        result = tree.search("ACCT$");
        assertEquals(4, result.getNodesSearched().size());
        assertEquals(0, result.getNodes().size());
    }
    
    /**
     * Ensures that resetting the tree
     * is correct
     */
    public void testReset() {
        tree.insert("ACGT");
        tree.insert("AAAA");
        tree.insert("AA");
        
        assertEquals(commonExpected3, tree.dump());
        
        tree.reset();
        
        assertEquals("E", tree.dump());
    }
}
