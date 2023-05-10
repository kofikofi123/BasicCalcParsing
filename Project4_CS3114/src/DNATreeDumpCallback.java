
/**
 * Callback class for DNATree 
 * dump function
 * @author erikchair
 * @version 12.07.2022
 *
 */
public interface DNATreeDumpCallback {
    /**
     * Callback function for DNATree dump
     * @param node The current TreeNode object
     *             processing
     * @return A string depending on need
     */
    public String dumpCallback(TreeNode node);
}
