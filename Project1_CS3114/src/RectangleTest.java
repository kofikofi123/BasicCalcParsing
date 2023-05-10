
/**
 * Test class for the Rectangle class
 * @author erikchair
 * @version 09.09.2022
 *
 */
public class RectangleTest extends student.TestCase {
    private Rectangle rectangle;
    
    /**
     * Setup function for the test suite
     */
    public void setUp() throws Exception {
        super.setUp();
        rectangle = new Rectangle("", 10, 5, 300, 20);
    }

    /**
     * Tests if the getPosition function returns correct results
     */
    public void testGetPosition() {
        int[] expectPos = {300, 20};
        int[] actualPos = rectangle.getPosition();
        
        assertEquals(expectPos[0], actualPos[0]);
        assertEquals(expectPos[1], actualPos[1]);
        assertEquals(2, actualPos.length);
    }

    /**
     * Tests if the getSize function returns the correct results
     */
    public void testGetSize() {
        int[] expectSize = {10, 5};
        int[] actualSize = rectangle.getSize();
        
        assertEquals(expectSize[0], actualSize[0]);
        assertEquals(expectSize[1], actualSize[1]);
        assertEquals(2, actualSize.length);
    }
    

    /**
     * Tests if the getMaxPosition function returns the correct results
     */
    public void testGetMaxPosition() {
        int[] expectedPos = {300 + 10, 20 + 5};
        int[] actualPos   = rectangle.getMaxPosition();
        
        assertEquals(expectedPos[0], actualPos[0]);
        assertEquals(expectedPos[1], actualPos[1]);
        assertEquals(2, actualPos.length);
    }
    /**
     * Tests if the equals function is able to correctly compare objects
     */
    public void testEqualsObject() {
        Object test1 = new Object();
        Object test2 = new Rectangle("", 5, 10, 20, 300);
        Object test3 = new Rectangle("", 10, 5, 300, 20);
        Object test4 = new Rectangle("", 10, 5, 20, 9);
        Object test5 = new Rectangle("", 10, 5, 300, 9);
        Object test6 = new Rectangle("", 10, 3, 300, 20);

        Object testNull = null;
        
        assertFalse(rectangle.equals(testNull));
        assertFalse(rectangle.equals(test1));
        assertFalse(rectangle.equals(test2));
        assertFalse(rectangle.equals(test4));
        assertFalse(rectangle.equals(test5));
        assertFalse(rectangle.equals(test6));
        assertTrue(rectangle.equals(rectangle));
        assertTrue(rectangle.equals(test3));
    }

}
