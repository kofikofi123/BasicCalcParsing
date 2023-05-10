import java.util.ArrayList;

/**
 * Class for storing DNATree search 
 * results
 * @author erikchair
 * @version 11.30.2022
 *
 */
public class DNATreeSearchResult {
    private ArrayList<TreeNode> visited;
    private ArrayList<TreeNode> results;
    
    /**
     * Constructor that takes in an ArrayList object
     * of TreeNodes visited, and an ArrayList object of
     * TreeNodes that fit the description of the search
     * @param inVisit The list of TreeNode's visited
     * @param inResults The list of TreeNode's that
     *                  matched the search description
     */
    DNATreeSearchResult(ArrayList<TreeNode> inVisit, 
                        ArrayList<TreeNode> inResults) {
        visited = inVisit;
        results = inResults;
    }
    
    
    /**
     * Gets the visited TreeNodes
     * @return ArrayList object of visited TreeNode
     */
    public ArrayList<TreeNode> getNodesSearched() {
        return visited;
    }
    
    /**
     * Gets the list of TreeNodes that fit the search
     * @return ArrayList object of TreeNode's that
     *         fit the description
     */
    public ArrayList<TreeNode> getNodes() {
        return results;
    }
}
