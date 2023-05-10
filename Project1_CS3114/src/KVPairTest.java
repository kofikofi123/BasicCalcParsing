import student.TestCase;
/**
 * Test case for KVPair class
 * @author erikchair
 * @version 09.09.2022
 */
public class KVPairTest extends TestCase {

    /**
     * Sets up test class
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test case to check the compareTo KVPair version
     */
    public void testCompareToKVPairOfKE() {
        KVPair<String, String> pair = 
            new KVPair<String, String>("Test1", "Apple1");
        KVPair<String, String> pair2 = 
            new KVPair<String, String>("Test2", "Apple2");
        
        assertEquals(-1, pair.compareTo(pair2));
        assertEquals(1, pair2.compareTo(pair));
        assertEquals(0, pair.compareTo(pair));
    }

    /**
     * Test case to check the compareTo <K> version
     */
    public void testCompareToK() {
        KVPair<String, String> pair = 
            new KVPair<String, String>("Test1", "Apple1");
        KVPair<String, String> pair2 = 
            new KVPair<String, String>("Test2", "Apple2");
        
        assertEquals(-1, pair.compareTo(pair2.key()));
        assertEquals(1, pair2.compareTo(pair.key()));
        assertEquals(0, pair.compareTo(pair.key()));
    }

    /**
     * Test case to check if the key function is correct
     */
    public void testKey() {
        KVPair<String, String> pair = 
            new KVPair<String, String>("Test1", "Apple1");
        KVPair<String, String> pair2 = 
            new KVPair<String, String>("Test2", "Apple2");
        
        assertEquals("Test1", pair.key());
        assertEquals("Test2", pair2.key());
    }

    /**
     * Test case to check if the value function is correct
     */
    public void testValue() {
        KVPair<String, String> pair = 
            new KVPair<String, String>("Test1", "Apple1");
        KVPair<String, String> pair2 = 
            new KVPair<String, String>("Test2", "Apple2");
        
        assertEquals("Apple1", pair.value());
        assertEquals("Apple2", pair2.value());
    }

    /**
     * Test case to check if the toString function is correct
     */
    public void testToString() {
        KVPair<String, String> pair = 
            new KVPair<String, String>("Test1", "Apple1");
        KVPair<String, String> pair2 = 
            new KVPair<String, String>("Test2", "Apple2");
        
        assertEquals("Test1, Apple1", pair.toString());
        assertEquals("Test2, Apple2", pair2.toString());
    }

}
