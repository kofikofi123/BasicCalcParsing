import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import student.TestCase;

/**
 * Test case for DNATreeSearchResult class
 * @author erikchair
 * @version 11.30.2022
 */
public class DNATreeSearchResultTest extends TestCase {
    private DNATreeSearchResult result;
    private ArrayList<TreeNode> visited;
    private ArrayList<TreeNode> nodeResult;
    /**
     * Sets up the test enviornment
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        
        TreeNode emptyNode = 
            TreeFlyWeightNode.getInstance();
        
        visited = 
            new ArrayList<TreeNode>(Arrays.asList(
                new TreeLeafNode<String>("A", emptyNode)));
        
        nodeResult =
            new ArrayList<TreeNode>(Arrays.asList(
                new TreeLeafNode<String>("B", emptyNode)));
        
        result = new DNATreeSearchResult(visited, nodeResult);
    }


    /**
     * Checks whether the correct arraylists are 
     * saved in the result
     */
    @Test
    public void testDNATreeSearchResult() {
        assertEquals(visited, result.getNodesSearched());
        assertEquals(nodeResult, result.getNodes());
    }

}
