/**
 * Implementation of the skipnode from openDSA. A forward linked-node list
 * with possibility of multiple levels of forward references
 * 
 * @author erikchair
 * @version 09.09.2022
 *
 * @param <K> Type to be used as the key
 * @param <V> Type to be used as the value
 */

public class SkipNode<K extends Comparable<K>, V> {
    private int levels;
    private SkipNode<K, V>[] nodes;
    private KVPair<K, V> record;
    
    
    /**
     * Initialize he SkipNode with level + 1 forward node references
     * @param l The amount of depth the node will have
     */
    @SuppressWarnings("unchecked")
    SkipNode(int l) {
        this.nodes = new SkipNode[l + 1];
        this.levels = l;
        this.record = null;
        for (int i = 0; i < nodes.length; i++) {
            this.nodes[i] = null;
        }
    }
    
    /**
     * Initialize the SkipNode with level + 1 forward references, a key, 
     * and a value to also be associated with the node
     * @param key THe key to use to identify the node
     * @param value The content of the SkipNode
     * @param l The amount of depth the node will have
     */
    SkipNode(K key, V value, int l) {
        this(new KVPair<K, V>(key, value), l);
    }
    
    /**
     * Initialize the SkipNode with level + 1 forward references, 
     * and a KVPair that is associated with the node
     * @param pair A structure that contains a key and value pair to be used
     * @param l The amount of depth the node will have
     */
    SkipNode(KVPair<K, V> pair, int l) {
        this(l);
        this.record = pair;
        this.levels = l;
    }
    
    /**
     * Adjusts the depth of the SkipNode with the same references
     * saved
     * @param l The new deptth the skip node will be
     */
    @SuppressWarnings("unchecked")
    public void adjustNodes(int l) {
        SkipNode<K, V>[] temp = new SkipNode[l + 1];
        
        int i;
        for (i = 0; i < nodes.length; i++)
            temp[i] = nodes[i];
        
        for (; i < temp.length; i++)
            temp[i] = null;
        

        this.nodes = temp;
        this.levels = l;
    }
    
    /**
     * Returns the key associated with the SkipNode
     * @return The key associated to this node, or null if no key exists
     */
    public K key() {
        K result = null;
        if (record != null)
            result = record.key();
        
        return result;
    }
    
    /**
     * Returns the value associated with the SkipNode
     * @return The value associated with the SkipNode, or null if no 
     * value exists
     */
    public V element() {
        V result = null;
        
        if (record != null)
            result = record.value();
        
        return result;
    }
    
    
    /**
     * Returns a string with the format {Key:Value:Depth}
     * @return Returns the string representation of the SkipNode
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append('{');
        
        K k = key();
        V v = element();
        
        if (k != null)
            sb.append(k.toString());
        
        sb.append(':');
        
        if (v != null)
            sb.append(record.value().toString());
        
        sb.append(':');
        sb.append(levels);
        sb.append('}');
        
        return sb.toString();
    }
    
    /**
     * Returns the depth of the node
     * @return Return the depth (saved in the level variable)
     */
    public int getLevel() {
        return levels;
    }
    
    /**
     * Getter function for the forward referenced node saved,
     * Will only work if i levels exist
     * @param i The index into the forward reference list
     * @return The forward node at level i, or null
     */
    public SkipNode<K, V> getNode(int i) {
        SkipNode<K, V> result = null;
        
        if (i >= 0 && i <= levels)
            result = nodes[i];
        
        return result;
    }
    
    /**
     * Setter function for the forward referenced node list,
     * Will only work if i levels exist
     * @param i The level to set the new node
     * @param node The new node to be saved at level i
     */
    public void setNode(int i, SkipNode<K, V> node) {
        
        if (i >= 0 && i <= levels)
            nodes[i] = node;
    }
    
    /**
     * Checks to see if there is a record associated to the node
     * @return Returns whether the KVPair record is null
     */
    public boolean hasRecord() {
        return record != null;
    }
};
