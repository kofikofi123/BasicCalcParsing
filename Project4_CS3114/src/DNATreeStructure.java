import java.util.ArrayList;
/**
 * A tree structure that organizes DNA sequences
 * in a tree structure
 * @author erikchair
 * @version 11.26.2022
 */
public class DNATreeStructure {
    private TreeFlyWeightNode emptyNode;
    private TreeNode rootNode;
    
    /**
     * Default constructor with no rootnode. Initializes
     * the flyweight node
     */
    DNATreeStructure() {
        emptyNode = TreeFlyWeightNode.getInstance();
        rootNode = emptyNode;
    }
    
    /**
     * Inserts a new DNA sequence into the tree
     * @param sequence The DNA sequence to insert
     * @return  0 If the tree is empty, (first level)
     *            >0 When the tree is not empty (n-level)
     *            -1 If the sequence already exists
     */
    
    @SuppressWarnings("unchecked")
    public TreeLeafNode<String> insert(String sequence) {
        if (rootNode.isFlyweight()) {
            
            rootNode = new
                    TreeLeafNode<String>(sequence, emptyNode);
            return (TreeLeafNode<String>) rootNode;
        }
        
        
        TreeInternalNode parent = getDeepestParent(sequence);
        TreeLeafNode<String> prevNode = null;
        int index = 0;
        if (parent == null) {
            prevNode = (TreeLeafNode<String>) rootNode;
            if (prevNode
                    .getElement()
                    .compareTo(sequence) == 0) {
                return null;
            }
            parent = new TreeInternalNode(5, emptyNode);
            rootNode = parent;
        }
        else {
            index = 
                sequenceToIndex(
                    sequence.charAt(parent.getLevel()));
            
            TreeNode temp = parent.getChild(index);
            
            if (temp.isInternal()) {
                index = 4;
                parent = (TreeInternalNode) temp;
                temp = parent.getChild(index);
            }
            
            if (!temp.isFlyweight()) {
                TreeLeafNode<String> leaf =
                    (TreeLeafNode<String>) temp;
                
                if (leaf
                        .getElement()
                        .compareTo(sequence) == 0) {
                    return null;
                }
                
                prevNode = leaf;
            }
        }
        
        TreeLeafNode<String> node = 
            new TreeLeafNode<String>(sequence, emptyNode);
        
        if (prevNode == null) {
            parent.setChild(index, node);
        }
        else {
            createInternalPath(parent, prevNode, node);
        }
        
        return node;
        
    }
    
    /**
     * Removes a DNA sequence from the tree
     * @param sequence The sequence to remove
     * @return The removed sequenced represented as 
     *         a TreeLeafNode
     */
    @SuppressWarnings("unchecked")
    public TreeLeafNode<String> remove(String sequence) {
        DNATreeSearchResult result = findNode(sequence);
        
        ArrayList<TreeNode> nodes = result.getNodes();
        
        if (nodes.size() == 0) {
            return null;
        }
        TreeNode node = nodes.get(0);
        TreeLeafNode<String> returnNode = null;
        
        if (!node.isFlyweight()) {
            TreeNode parent = node.getParent();
            
            returnNode = (TreeLeafNode<String>) node;
            
            if (parent.isFlyweight()) {
                rootNode = emptyNode;
            }
            else {
                TreeInternalNode iParent =
                        (TreeInternalNode) parent;
                
                iParent.setChild(node.getChildIndex(),
                                 emptyNode);

                while (parent.isInternal() &&
                       refactorParent((TreeInternalNode) parent)) {
                    parent = parent.getParent();
                }
            }
            
            node.setParent(emptyNode);
        }
        
        return returnNode;
    }
    
    /**
     * Gets the tree structure in String form
     * @return A String representing the tree structure
     *         from root to its children, with idententations
     */
    public String dump() {
        return dump(null);
    }
    
    /**
     * Gets the tree structure in String form,
     * with a callback function for each node if 
     * needed
     * @param callback The callback function to use
     *                 for each node
     * @return A String representing the tree structure
     *         from root to its children, with idententations
     *         and addidtional data provided by callback
     */
    public String dump(DNATreeDumpCallback callback) {
        return dumpRecursive(rootNode, callback);
        
    }
    
    /**
     * Gets the general child index for a
     * specific character 
     * @param s The character to find an index for
     * @return An integer representing its index 
     *         in an internal node.
     *         a|A = 0
     *         c|C = 1
     *         g|G = 2
     *         t|T = 3
     */
    public static int sequenceToIndex(char s) {
        int result = 0;
        
        s = Character.toLowerCase(s);
        
        switch (s) {
            case 'a':
                result = 0;
                break;
            case 'c':
                result = 1;
                break;
            case 'g':
                result = 2;
                break;
            case 't':
                result = 3;
        }
        
        return result;
    }
    
    /**
     * Recursive dumping function that constructs a tree
     * structure at a point in the tree
     * @param node The current TreeNode is stringify
     * @param callback The callback function for each node
     * @return A string representation of the node with 
     *         indents for its children if its internal
     */
    private String dumpRecursive(TreeNode node, DNATreeDumpCallback callback) {
        String result = dumpNode(node, callback) + "\n";
        
        if (node.isInternal()) {
            TreeInternalNode iNode =
                    (TreeInternalNode) node;
            String space = doSpaceRep(iNode.getLevel() + 1);
            for (int i = 0; i < 5; i++) {
                TreeNode child = 
                        ((TreeInternalNode) node).getChild(i); 
                result = result + space + dumpRecursive(child, callback) + "\n";
            }
        }
        return result.substring(0, result.length() - 1);
    }
    
    /**
     * Gets the string representing the node
     * @param node The TreeNode object to use
     * @param callback The callback function to use
     * @return If the node is an emptyNode, returns E
     *         If the node is an internalNode, returns I
     *         If the node is a leaf node, returns the content
     *         If a non null callback function was provided
     *         it attaches additional information about the node
     *         if needed to previous returns.
     */
    @SuppressWarnings("unchecked")
    private String dumpNode(TreeNode node, DNATreeDumpCallback callback) {
        String result = null;
        if (node.isFlyweight()) {
            result = "E";
        }
        else if (node.isInternal()) {
            result = "I";
        }
        else {
            result = ((TreeLeafNode<String>) node).getElement();
        }
        
        if (callback != null) {
            String callbackString = callback.dumpCallback(node);
            if (callbackString != null &&
                callbackString.length() > 0) {
                result = result + " " + callbackString;
            }
        }
        
        return result;
    }
    
    /**
     * Searches for the node(s) starts the sequence. 
     * If a $ is appended to the end, then it
     * finds that one sequence
     * @param sequence The sequence to use
     * @return A DNATreeSearchResult object that
     *         has the amount of nodes searched, and
     *         the node(s) found
     */
    public DNATreeSearchResult search(String sequence) {
        int length = sequence.length();
        if (sequence.charAt(length - 1) == '$') {
            return findNode(sequence.substring(0, length - 1));
        }
        else {
            return findNodes(sequence);
        }
    }
    
    /**
     * Resets the tree with root node
     * being an empty node
     */
    public void reset() {
        rootNode = emptyNode;
    }
    
    /**
     * Finds a TreeNode with a specific sequence
     * @param sequence The sequence to use for search
     * @return A DNATreeSearchResult that holds
     *         the number of nodes visited
     *         and a node if found
     */
    @SuppressWarnings("unchecked")
    private DNATreeSearchResult findNode(String sequence) {
        TreeNode node = rootNode;
        int counter = 0;
        int length = sequence.length();
        
        
        ArrayList<TreeNode> visited = 
            new ArrayList<TreeNode>();
        TreeNode finalNode = emptyNode;
        
        while ((counter < length) && node.isInternal()) {
            visited.add(node);
            
            node = ((TreeInternalNode) node)
                    .getChild(
                            sequenceToIndex(
                                    sequence.charAt(counter)));
            counter = counter + 1;
        }
        
        visited.add(node);
        if (!node.isInternal() &&
            !node.isFlyweight()) {
            TreeLeafNode<String> leaf =
                (TreeLeafNode<String>) node;
            
            if (leaf.getElement().compareTo(sequence) == 0) {
                finalNode = node;
            }
        }
        else if (counter == length &&
            node.isInternal()) {
            TreeNode gTemp = 
                ((TreeInternalNode) node).getChild(4);
            
            visited.add(gTemp);
            if (!gTemp.isFlyweight()) {
                TreeLeafNode<String> temp =
                    (TreeLeafNode<String>) gTemp;
                finalNode = temp;
            }
        }
        
        ArrayList<TreeNode> list =
            new ArrayList<TreeNode>();
        if (!finalNode.isFlyweight()) {
            list.add(finalNode);
        }
        
        return new DNATreeSearchResult(visited, list);
    }
    
    /**
     * Finds a TreeNodes that starts with a sequence
     * @param sequence The sequence to use for search
     * @return A DNATreeSearchResult that holds
     *         the number of nodes visited
     *         and nodes if found
     */
    @SuppressWarnings("unchecked")
    private DNATreeSearchResult findNodes(String sequence) {
        int counter = 0;
        int length = sequence.length();
        
        
        ArrayList<TreeNode> visited =
            new ArrayList<TreeNode>();
        ArrayList<TreeNode> result =
            new ArrayList<TreeNode>();
        
        Stack<TreeNode> stack =
            new Stack<TreeNode>();
        TreeNode node = rootNode;
        
        
        //First step: Depth-search to parent tree
        while ((counter < length) && node.isInternal()) {
            visited.add(node);
            int index = sequenceToIndex(sequence.charAt(counter));
            
            node = ((TreeInternalNode) node).getChild(index);
            counter = counter + 1;
        }
        
        
        if (!node.isFlyweight()) {
            stack.push(node);
            
            while (!stack.isEmpty()) {
                TreeNode top = stack.pop();
                
                visited.add(top);
                if (top.isInternal()) {
                    TreeInternalNode iTop =
                        (TreeInternalNode) top;
                    
                    for (int i = 0; i < 5; i++) {
                        stack.push(iTop.getChild(i));
                    }
                }
                else if (!top.isFlyweight()) {
                    TreeLeafNode<String> leaf =
                        (TreeLeafNode<String>) top;
                    
                    if (leaf.getElement().startsWith(sequence)) {
                        result.add(top);
                    }
                }
            }
        }
        else {
            visited.add(node);
        }
        
        
        return new DNATreeSearchResult(visited, result);
        
    }
    
    /**
     * Creates a path of InternalNode at the parent level down
     * to fit a new node if needed
     * @param parent The parent to work with
     * @param originalChild The TreeLeafNode child originally
     *                      parented on the parent node
     * @param newChild The TreeLeafNode child to put into
     *                 parent
     */
    private void createInternalPath(TreeInternalNode parent, 
                                    TreeLeafNode<String> originalChild,
                                    TreeLeafNode<String> newChild) {
        
        ArrayList<TreeLeafNode<String>> list =
                new ArrayList<TreeLeafNode<String>>();
        list.add(originalChild);
        list.add(newChild);
        list.sort((a, b) -> a.compareTo(b));
        
        TreeLeafNode<String> lowerNode = list.get(0);
        TreeLeafNode<String> upperNode = list.get(1);
        
        String string1 = lowerNode.getElement();
        String string2 = upperNode.getElement();
        
        int counter = parent.getLevel();
        int length = string1.length();
        
        TreeInternalNode node = parent;
    
        
        while (counter < length) {
            char a = string1.charAt(counter);
            char b = string2.charAt(counter);
            
            if (a != b) {
                break;
            }
            else {
                int i = sequenceToIndex(a);
                TreeInternalNode newInternal =
                        new TreeInternalNode(5, node);
                node.setChild(i, newInternal);
                
                node = newInternal;
            }
            counter++;
        }
        
        if (counter == length) {
            node.setChild(4, lowerNode);
            
            node.setChild(sequenceToIndex(string2.charAt(counter)),
                          upperNode);
        }
        else {
            node.setChild(sequenceToIndex(string1.charAt(counter)),
                          lowerNode);
            node.setChild(sequenceToIndex(string2.charAt(counter)), 
                          upperNode);
        }
    }
    
    /**
     * Collapses a part of the tree that no longer needs
     * an internal node
     * @param parent The parent to collapse
     * @return True if it makes sense to collapse parent (children > 1)
     *         False otherwise
     */
    private boolean refactorParent(TreeInternalNode parent) {
        int children = parent.getNumChildren();
        
        
        if (children > 1) {
            return false;
        }
        TreeNode superParent = parent.getParent();
        TreeNode replacementNode = emptyNode;
        if (children == 1) {
            for (int i = 0; (i < 5) && (replacementNode.isFlyweight()); i++) {
                replacementNode = parent.getChild(i);
            }
        }
        
        if (replacementNode.isInternal()) {
            return false;
        }
        if (superParent.isInternal()) {
            TreeInternalNode iSuper =
                    (TreeInternalNode) superParent;
            iSuper.setChild(parent.getChildIndex(),
                            replacementNode);
        }
        else {
            rootNode = replacementNode;
            rootNode.resetLevel();
        }
        
        return true;
    }
    
    
    /**
     * Finds the deepest parent that matches the sequence
     * @param sequence The sequence to use
     * @return An Internal Node that is parent to this sequence
     *         or emptyNode (TreeFlyWeightNOde)
     */
    private TreeInternalNode getDeepestParent(String sequence) {
        TreeNode node = rootNode;
        TreeInternalNode parent = null;
        
        int length = sequence.length();
        int counter = 0;
        
        while ((!node.isFlyweight()) 
                && (node.isInternal()) 
                && (counter < length)) {
            int index = sequenceToIndex(sequence.charAt(counter));
            parent = (TreeInternalNode) node;
            node = parent.getChild(index);
            counter = counter + 1;
        }
        
        return parent;
    }
    
    /**
     * Creates a specific amount of 
     * column spaces for printing
     * @param q The number of columns
     * @return A whitespace string
     */
    private String doSpaceRep(int q) {
        String result = "";
        
        while (q-- > 0) {
            result = result + "  ";
        }
        
        return result;
    }
}
