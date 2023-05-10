/**
 * Test class for SkipNode
 * @author erikchair
 * @version 09.09.2022
 *
 */
public class SkipNodeTest extends student.TestCase {

    /**
     * Sets up the test class
     */
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    /**
     * Test case for the constructors
     */
    public void testSkipNodeConstructor() {
        SkipNode<String, String> t1 =
            new SkipNode<String, String>(0);
        SkipNode<String, String> t2 = 
            new SkipNode<String, String>(-1);
        
        Exception e = null;
        try {
            SkipNode<String, String> t3 =
                new SkipNode<String, String>(-2);
        }
        catch (Exception o) {
            e = o;
        }
        
        assertEquals(0, t1.getLevel());
        assertEquals(-1, t2.getLevel());
        assertNull(t2.getNode(0));
        assertNull(t1.getNode(0));
        assertNotNull(e);
    }

    /**
     * Test case for adjustNodes
     */
    public void testAdjustNodes() {
        SkipNode<String, String> test1 = 
            new SkipNode<String, String>(0);
        SkipNode<String, String> test2 = 
            new SkipNode<String, String>(4);
        assertEquals(0, test1.getLevel());
        test1.adjustNodes(5);
        assertEquals(5, test1.getLevel());
        
        Exception e = null;
        try {
            test2.adjustNodes(0);
        }
        catch (Exception o) {
            e = o;
        }
        
        assertNotNull(e);
    }


    /**
     * Test case for key function
     */
    public void testKey() {
        SkipNode<String, String> test1 = 
            new SkipNode<String, String>(
                "Key1", 
                "Value1", 
                0);
        SkipNode<String, String> test2 = 
            new SkipNode<String, String>(
                null, 
                "Value2", 
                4);
        
        assertEquals("Key1", test1.key());
        assertEquals(null, test2.key());
    }


    /**
     * Test case for element function
     */
    public void testElement() {
        SkipNode<String, String> test1 = 
            new SkipNode<String, String>(
                "Key1", 
                "Value1", 
                0);
        SkipNode<String, String> test2 = 
            new SkipNode<String, String>(
                "Key2", 
                null, 
                4);
        
        assertEquals("Value1", test1.element());
        assertEquals(null, test2.element());
    }


    /**
     * Test case for toString
     */
    public void testToString() {
        SkipNode<String, String> test1 = 
            new SkipNode<String, String>(
                "Key1", 
                "Value1", 
                0);
        SkipNode<String, String> test2 = 
            new SkipNode<String, String>(
                "Key2", 
                null, 
                4);
        SkipNode<String, String> test3 = 
            new SkipNode<String, String>(
                null, 
                "Value2", 
                4);
        SkipNode<String, String> test4 = 
            new SkipNode<String, String>(
                null, 
                null, 
                0);
        
        assertEquals("{Key1:Value1:0}", test1.toString());
        assertEquals("{Key2::4}", test2.toString());
        assertEquals("{:Value2:4}", test3.toString());
        assertEquals("{::0}", test4.toString());
    }


    /**
     * Test case for getLevel function
     */
    public void testGetLevel() {
        SkipNode<String, String> test1 = 
            new SkipNode<String, String>(
                "Key1", 
                "Value1", 
                0);
        
        assertEquals(0, test1.getLevel());
    }

    
    /**
     * Test case for getNode function
     */
    public void testGetNode() {
        SkipNode<String, String> test1 = new SkipNode<String, String>(
            "Key1", 
            "Value1", 
            1);
        SkipNode<String, String> test2 = new SkipNode<String, String>(
            "Key2", 
            "Value2", 
            0);
        
        test1.setNode(0, test2);
        
        assertEquals(test2, test1.getNode(0));
        assertNull(test2.getNode(2));
        assertNull(test2.getNode(-2));
    }


    /**
     * Test case for setNode function
     */
    public void testSetNode() {
        SkipNode<String, String> test1 = 
            new SkipNode<String, String>(
                "Key1", 
                "Value1", 
                1);
        SkipNode<String, String> test2 = 
            new SkipNode<String, String>(
                "Key2", 
                "Value2", 
                0);
        
        test1.setNode(0, test2);
        test1.setNode(2, test1);
        test1.setNode(-2, test1);
        assertEquals(test2, test1.getNode(0));
        assertNull(test1.getNode(2));
        assertNull(test1.getNode(-2));
    }

    /**
     * Test case for hasRecord function
     */
    public void testHasRecord() {
        SkipNode<String, String> test1 = 
            new SkipNode<String, String>(
            1);
        SkipNode<String, String> test2 = 
            new SkipNode<String, String>(
                "Key2", 
                "Value2", 
                0);

        assertFalse(test1.hasRecord());
        assertTrue(test2.hasRecord());
    }

}
