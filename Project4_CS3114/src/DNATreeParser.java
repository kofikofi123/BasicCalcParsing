import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for parsing commands
 * for a DNATree
 * @author erikchair
 * @version 12.01.2022
 */
public class DNATreeParser {
    private Writer output;
    private DNATreeStructure tree;
    
    /**
     * Initalizes the DNATreeParser object
     * with an OutputStream object 
     * and a newly created tree
     * @param inStream The output stream to use
     */
    public DNATreeParser(Writer inStream) {
        this(inStream,
             new DNATreeStructure());
    }
    
    /**
     * Initalizes the DNATreeParser object
     * with an OutputStream object
     * and a DNATreeStructure object to use
     * @param inStream The output stream to use
     * @param inTree The tree to perform inputs
     *               on
     */
    public DNATreeParser(Writer inStream,
                         DNATreeStructure inTree) {
        output = inStream;
        tree = inTree;
    }
    
    
    /**
     * Parses series of commands from a
     * stream
     * @param inStream The stream to read from
     * @throws IOException If an I/O exception occurs
     */
    public void parse(Reader inStream) throws IOException {
        tree.reset();
        try (Scanner scanner = new Scanner(inStream)) {
            while (scanner.hasNextLine()) {
                String[] command = scanner
                    .nextLine()
                    .trim().
                    split("\\s+");
                
                if (command.length > 0) {
                    processCommand(command);
                }
            }
        }
        
        output.flush();
    }
    
    /**
     * Processess the commands to the appropiate 
     * action
     * @param command The command array to process
     * @throws IOException If an I/O exception occurs
     */
    private void processCommand(String[] command) 
        throws IOException {
        String prefix = command[0].toLowerCase();
        String result = "";
        switch (prefix) {
            case "insert":
                result = insert(command);
                break;
            case "remove":
                result = remove(command);
                break;
            case "search":
                result = search(command);
                break;
            case "print":
                result = print(command);
                break;
        }
        
        output.write(result, 0, result.length());
    }
    
    private String insert(String[] command) {
        String sequence = command[1];
        String result;
        TreeNode node = tree.insert(sequence);
        
        if (node == null) {
            result = "sequence " 
                + sequence 
                + " already exists";
        }
        else {
            result = "sequence "
                + sequence
                + " inserted at level "
                + node.getLevel();
        }
        
        return result + "\n";
    }
    
    private String remove(String[] command) {
        String sequence = command[1];
        String result;
        TreeNode node = tree.remove(command[1]);
        
        if (node == null) {
            result = "sequence " 
                + sequence 
                + " does not exist";
        }
        else {
            result = "sequence " 
                + sequence
                + " removed";
        }
        
        return result + "\n";
    }
    
    @SuppressWarnings("unchecked")
    private String search(String[] command) {
        DNATreeSearchResult searchResult =
            tree.search(command[1]);
        String result = "# of nodes visited: "
            + searchResult
                .getNodesSearched()
                .size()
            + "\n";
        
        ArrayList<TreeNode> list =
            searchResult.getNodes();
        
        if (list.size() == 0) {
            result = result + "no sequence found\n";
        }
        else {
            int length = list.size();
            for (int i = length - 1; i >= 0; i--) {
                TreeNode child = 
                    list.get(i);
                
                if (!child.isFlyweight()) {
                    TreeLeafNode<String> node =
                        (TreeLeafNode<String>) 
                            child;
                    result =
                        result + "sequence: "
                            + node.getElement() + '\n';
                }
            }
        }
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    private String print(String[] command) {
        String result = "tree dump:\n";
        if (command.length == 1) {
            result = result + tree.dump();
        }
        else {
            String type = command[1];
            
            if (type.compareTo("lengths") == 0) {
                result = result +
                    tree.dump((node) -> {
                        String r = null;
                        if (!node.isInternal() &&
                            !node.isFlyweight()) {
                            r = Integer.toString(
                                ((TreeLeafNode<String>)
                                    node).getElement().length());
                        }
                        return r;
                    } );
            }
            else if (type.compareTo("stats") == 0) {
                result = result +
                    tree.dump((node) -> {
                        String r = null;
                        if (!node.isInternal() &&
                            !node.isFlyweight()) {
                            String str =
                                ((TreeLeafNode<String>)
                                    node).getElement();
                            double a =
                                (double) occuranceHelper(str, 'A')
                                    * 100.0;
                            double c =
                                (double) occuranceHelper(str, 'C')
                                    * 100.0;
                            double g =
                                (double) occuranceHelper(str, 'G')
                                    * 100.0;
                            double t = 
                                (double) occuranceHelper(str, 'T')
                                    * 100.0;
                            
                            
                            double len = str.length();
                            
                            r = String.format("A:%.2f "
                                + "C:%.2f "
                                + "G:%.2f "
                                + "T:%.2f",
                                a / len,
                                c / len,
                                g / len,
                                t / len);
                        }
                        
                        return r;
                    } );
            }
        }
        
        return result + '\n';
    }
    
    /**
     * Gets the number of times a character
     * is repeated in a sequence
     * @param sequence The sequence to search
     * @param c The character to count
     * @return The number of repeat characters
     *         in the sequence
     */
    private int occuranceHelper(String sequence, char c) {
        int count = 0;
        int length = sequence.length();
        for (int i = 0; i < length; i++) { 
            if (sequence.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Gets the current DNATreeStructure
     * being used by the parser
     * @return The tree being used
     */
    public DNATreeStructure getTree() {
        return tree;
    }
}
