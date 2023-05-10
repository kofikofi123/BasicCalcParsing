import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Random;
import student.TestableRandom;

/**
 * The SkipList implementation of the ListADT with a 
 * SkipNode head
 * @author erikchair
 * @version 09.09.2022
 *
 * @param <K> Key type to be used for SkipNode
 * @param <V> Value type to be used for SkipNode
 */
public class SkipList<K extends Comparable<K>, V> 
        implements ListADT<K, V> {
    private SkipNode<K, V> head;
    private Random rnd;
    private int size;
    
    /**
     * Initialize the head at level 0, 
     * the random generator needed, 
     * and the size at 0
     */
    SkipList() {
        this.head = new SkipNode<K, V>(0);
        this.rnd = new TestableRandom();
        this.size = 0;
    }
    
    /**
     * Searches for the SkipNode with the Key value K
     * @param k The key to use for the search
     * @return Returns the SkipNode associated with 
     *         the key, or null if no SkipNode is found
     */
    @Override
    public ListADT<K, V> find(K k) {
        SkipNode<K, V> node = head;
        Comparable<K> key = k;
        
        int maxLevel = node.getLevel();
        
        for (int i = maxLevel; i >= 0; i--) {
            while (node.getNode(i) != null && 
                   key.compareTo(node.getNode(i).key()) > 0)
                node = node.getNode(i);
        }
        
        
        ListADT<K, V> result = new SkipList<K, V>();
        SkipNode<K, V> finalNode = node.getNode(0);
        while ((finalNode != null) && 
               (key.compareTo(finalNode.key()) == 0)) {
            result.insert(k, finalNode.element());
            finalNode = finalNode.getNode(0);
        }
        
        return result;
    }

    /**
     * Inserts a new SkipNode with a key and a value
     * @param key the Key to be associated with the node
     * @param value the Value to be associated with the node
     * @return The result of the insert KVpair version of the function
     */
    @Override
    public boolean insert(K key, V value) {
        return insert(new KVPair<K, V>(key, value));
    }
    
    /**
     * Inserts a new SKipNode with a KVPair associated to it
     * @param pair The Key-Value pair to be used for the node
     * @return true, all insertion will be accepted 
     * for this implementation
     */
    @SuppressWarnings("unchecked")
    public boolean insert(KVPair<K, V> pair) {
        int level = head.getLevel();
        int newLevel = randomLevel();
        
        Comparable<K> key = pair.key();
        
        if (newLevel > level) {
            adjustHead(newLevel);
            level = newLevel;
        }
        
        SkipNode<K, V>[] updateList = (SkipNode[]) 
            Array.newInstance(head.getClass(), level + 1);
        SkipNode<K, V> x = head;
        for (int i = level; i >= 0; i--) {
            while ((x.getNode(i) != null) && 
                (key.compareTo(x.getNode(i).key()) > 0)) {
                x = x.getNode(i);
            }
            updateList[i] = x;
        }
        
        x = new SkipNode<K, V>(pair, newLevel);
        
        for (int i = 0; i <= newLevel; i++) {
            x.setNode(i, updateList[i].getNode(i));
            updateList[i].setNode(i, x);
        }
        
        size++;
        return true;
    }
    
    /**
     * Removes a SkipNode with the key K
     * @param k The SkipNode with the key K to be removed from the list
     * @return Whether the function was successful 
     *         of finding and removing the node
     */
    @Override
    @SuppressWarnings("unchecked")
    public V remove(K k) {
        int level = head.getLevel();
        Comparable<K> key = k;
        
        SkipNode<K, V>[] updateList = 
            (SkipNode[]) Array.newInstance(head.getClass(), level + 1);
        SkipNode<K, V> x = head;
        for (int i = level; i >= 0; i--) {
            while ((x.getNode(i) != null) && 
                   (key.compareTo(x.getNode(i).key()) > 0))
                x = x.getNode(i);
            updateList[i] = x;
        }
        
        V value = null;
        x = x.getNode(0);
        if ((x != null) && 
            (key.compareTo(x.key()) == 0)) {
            for (int i = 0; i <= level; i++) {
                SkipNode<K, V> node = updateList[i];
                SkipNode<K, V> forward = node.getNode(i);
                if (forward != null && 
                    key.compareTo(forward.key()) == 0) {
                    node.setNode(i, x.getNode(i));
                }
            }
            value = x.element();
            size--;
        }
        
        return value;
    }
    
    /**
     * Removes a SkipNode that is 
     * same as the SkipNode specified
     * @param deleteNode the node to 
     *        be removed from the list
     * @return Whether the function was 
     *         successful of finding 
     *         and removing the node
     */
    @SuppressWarnings("unchecked")
    public V remove(SkipNode<K, V> deleteNode) {
        SkipNode<K, V> node = head;
        int level = node.getLevel();
        
        SkipNode<K, V>[] update = (SkipNode[]) 
            Array.newInstance(head.getClass(), level + 1);
        
        for (int i = level; i >= 0; i--) {
            while (node.getNode(i) != null && 
                   (node.getNode(i) != deleteNode)) {
                node = node.getNode(i);
            }
            update[i] = node;
            node = head;
        }
        

        SkipNode<K, V> checkNode = update[0].getNode(0);
        V value = null;
        if (checkNode != null && 
            checkNode == deleteNode) {
            for (int i = 0; i <= level; i++) {
                SkipNode<K, V> oNode = 
                    update[i];
                SkipNode<K, V> forward = 
                    oNode.getNode(i);
                if (forward != null && 
                    forward == deleteNode) {
                    oNode.setNode(i, checkNode.getNode(i));
                }
            }
            value = checkNode.element();
            size--;
        }
        
        
        return value;
    }

    /**
     * Returns the number of SkipNodes in the list
     * @return The size variable that keeps the number of nodes
     */
    @Override
    public int size() {
        return size;
    }
    
    /**
     * Returns a String representation of the SkipList:
     * [ {::HeadLevel} {Key0:Value0:Level} ... {KeyN:ValueN:Level} ]
     * @return The string representation of the list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("[ ");
        
        SkipNode<K, V> node = head;
        
        while (node != null) {
            sb.append(node.toString());
            sb.append(' ');
            node = node.getNode(0);
        }
        
        sb.append(']');
        
        return sb.toString();
    }
    
    /**
     * Adjusts the level of the head node
     * @param newLevel The new level the head must be
     */
    protected void adjustHead(int newLevel) {
        head.adjustNodes(newLevel);
    }
    
    /**
     * Returns a random level
     * @return A random level based on geometric distribution
     */
    protected int randomLevel() {
        int lev;
        for (lev = 0; rnd.nextBoolean(); lev++) {
            lev = lev;
        }
        return lev;
    }

    
    /**
     * Returns a custom iterator to be used to walk-through all node
     * in the list (at level 0)
     * @return A newly created iterator at the head position
     */
    @Override
    public Iterator<SkipNode<K, V>> iterator() {
        return createIterator(head);
    }

    private Iterator<SkipNode<K, V>> createIterator(
        SkipNode<K, V> node) {
        return new Iterator<SkipNode<K, V>>() {
            private SkipNode<K, V> currentNode = node;
            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public SkipNode<K, V> next() {
                SkipNode<K, V> current = currentNode;
                currentNode = currentNode.getNode(0);
                return current;
            }
            
        };
    }

}
