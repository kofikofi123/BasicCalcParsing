import java.util.Iterator;
import java.util.Random;

import student.TestableRandom;

/**
 * Test case for SkipList
 * @author erikchair
 * @version 09.09.2022
 */
public class SkipListTest extends student.TestCase {
    
    private SkipList<String, String> list;
    private Random rng;
    private final int maxEntries = 10;
    
    /**
     * Sets up test env for class
     */
    public void setUp() throws Exception {
        super.setUp();
        list = new SkipList<String, String>();
        rng = new Random();
    }
    
    /**
     * Tests the find function of the SkipList
     */
    public void testFind() {
        assertEquals(0, list.find("Test1").size());

        int entries = (rng.nextInt(maxEntries) + 1);
        
        for (int i = 0; i < entries; i++) {
            list.insert("Test", "Apple" + i);
        }
        
        ListADT<String, String> list2 = 
            list.find("Test");
        
        Iterator<SkipNode<String, String>> it = 
            list2.iterator();
        assertEquals(entries, list2.size());
        it.next();
        
        for (int i = 0; i < entries; i++) {
            assertEquals("Apple" + i, 
                it.next().element().toString());
        }
        
        assertEquals(0, list.find("Test2").size());
        
    }
    
    /**
     * Tests the insert (key, value) version 
     * of the function for SkipList
     */
    public void testInsertKV() {
        TestableRandom.setNextBooleans(false, 
            true, false, true, true, false);
        list.insert("Test1", "Apple");
        list.insert("Test2", "Banana");
        list.insert("Test3", "Carrot");
        
        assertEquals("[ {::2} "
            + "{Test1:Apple:0} "
            + "{Test2:Banana:1} "
            + "{Test3:Carrot:2} ]", 
            list.toString());
        assertEquals(3, list.size());
    }

    /**
     * Tests the insert (KVPair) 
     * version of the function for SkipList
     */
    public void testInsertKVPairOfKV() {
        TestableRandom.setNextBooleans(
            false, 
            true, 
            false, 
            true, 
            true, 
            false);
        
        list.insert(
            new KVPair<String, String>("Test1", "Apple"));
        list.insert(
            new KVPair<String, String>("Test2", "Banana"));
        list.insert(
            new KVPair<String, String>("Test3", "Carrot"));
        
        assertEquals("[ {::2} "
            + "{Test1:Apple:0} "
            + "{Test2:Banana:1} "
            + "{Test3:Carrot:2} ]", 
            list.toString());
        assertEquals(3, list.size());
    }

    /**
     * Tests the remove by key value version of the function for SkipList
     */
    public void testRemove() {
        int entries = (rng.nextInt(maxEntries + 1) + 1);
        int entry = (rng.nextInt(entries));
        
        
        String expectedKey = "Test" + entry;
        String expectedValue = "Apple" + entry;
        
        
        assertNull(list.remove(expectedKey));
        
        populateList(entries, "Test", "Apple");
    
        assertEquals(entries, list.size());
        assertEquals(expectedValue, list.remove(expectedKey));
        assertEquals(entries - 1, list.size());
        
        assertEquals(0, list.find(expectedKey).size());
    }
    
    /**
     * Tests the remove by SkipNode 
     * object version of the function for SkipList
     */
    public void testRemoveKVSkipNode() {
        int entries = 
            (rng.nextInt(maxEntries + 1) + 1);
        int entry = (rng.nextInt(entries));
        
        
        String expectedKey = "Test" + entry;
        String expectedValue = "Apple" + entry;
        
        assertNull(list.remove(expectedKey));
        
        populateList(entries, "Test", "Apple");
        
        assertEquals(entries, list.size());
        assertEquals(expectedValue, list.remove(expectedKey));
        assertEquals(entries - 1, list.size());
        assertEquals(0, list.find(expectedKey).size());
    }

    /**
     * Tests the size function of the SkipList
     */
    public void testSize() {
        assertEquals(0, list.size());
        
        int entries = (rng.nextInt(11));
        
        populateList(entries, "Test", "Apple");
    
        
        assertEquals(entries, list.size());
    }
    
    /**
     * Helper funtion to populate arrays of KVPairs
     * @param entries Amount of entries
     * @param keyPrefix Prefix to use for Key
     * @param valuePrefix Prefix to use for Value
     */
    private void populateList(int entries, 
        String keyPrefix, 
        String valuePrefix) {
        for (int i = 0; i < entries; i++) {
            list.insert(
                new KVPair<String, String>(keyPrefix + i, 
                                           valuePrefix + i));
        }
    }
    

}
