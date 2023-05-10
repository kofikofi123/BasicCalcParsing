import java.util.Iterator;
import java.util.Random;
import student.TestableRandom;

/**
 * Test class for RectangleDatabase
 * @author erikchair
 * @version 09.09.2022
 *
 */
public class RectangleDatabaseTest extends student.TestCase {

    private RectangleDatabase database;
    
    /**
     * Sets up the test class
     */
    public void setUp() throws Exception {
        super.setUp();
        database = new RectangleDatabase();
        new Random();
    }

    /**
     * Test to make sure insertion 
     * of Rectangles follow the correct
     * behavior specified in assignment
     */
    public void testInsert() {
        Rectangle rectFailByPos1 = 
            new Rectangle("Test1", 100, 100, -1, 2);
        Rectangle rectFailByPos2 = 
            new Rectangle("Test2", 100, 100, 3000, 3000);
        Rectangle rectFailBySize = 
            new Rectangle("Test3", 0, 0, 0, 0);
        Rectangle rectFailByMix  = 
            new Rectangle("Test4", 30, 30, 1020, 1020);
        Rectangle rectPass          = 
            new Rectangle("Test5", 30, 30, 0, 0);

        assertFalse(database.insert("1", rectPass));
        assertFalse(database.insert("Test1", rectFailByPos1));
        assertFalse(database.insert("Test2", rectFailByPos2));
        assertFalse(database.insert("Test3", rectFailBySize));
        assertFalse(database.insert("Test4", rectFailByMix));
        assertTrue(database.insert("Test5",  rectPass));
    }
    
    /**
     * Test to make sure the dump function returns the correct string
     * format for rectangles it contains
     */
    public void testDump() {
        Rectangle rectFail = new Rectangle("Test1", 100, 100, -1, 0);
        Rectangle pass1    = new Rectangle("Test2", 100, 100, 0, 0);
        Rectangle pass2       = new Rectangle("Test3", 200, 100, 300, 300);
        
        String expectedDump1 = "SkipList dump:\n" + 
                               "Node has depth 1, Value (null)\n" + 
                               "SkipList size is: 0";
        
        String expectedDump2 = 
            "SkipList dump:\n" + 
            "Node has depth 2, Value (null)\n" +
            "Node has depth 1, Value (Test2, 0, 0, 100, 100)\n" +
            "Node has depth 2, Value (Test3, 300, 300, 200, 100)\n" +
            "SkipList size is: 2";
        
        assertEquals(expectedDump1, database.dump());
        
        TestableRandom.setNextBooleans(false, true, false);
        database.insert("Test1", rectFail);
        database.insert("Test2", pass1);
        database.insert("Test3", pass2);
        
        assertEquals(expectedDump2, database.dump());
    }
    
    /**
     * Test to make sure the remove by name function correctly removes 
     * the rectangle associated by a given name
     */
    public void testRemoveByName() {
        Rectangle r1 = new Rectangle("Test1", 100, 100, 0, 0);
        Rectangle r2 = new Rectangle("Test2", 200, 200, 100, 100);
        
        String expected1 = 
            "[ {::0} {Test1:(Test1, 0, 0, 100, 100):0} " + 
            "{Test2:(Test2, 100, 100, 200, 200):0} " + 
            "{Test2:(Test2, 100, 100, 200, 200):0} ]";
        String expected2 = "[ {::0} {Test1:(Test1, 0, 0, 100, 100):0} "
            + "{Test2:(Test2, 100, 100, 200, 200):0} ]";
        
        assertNull(database.removeByName("Test1"));
        
        
        TestableRandom.setNextBooleans(false, false, false, false);
        
        database.insert("Test1", r1);
        database.insert("Test1", r2);
        database.insert("Test2", r2);
        database.insert("Test2", r2);
        
        assertEquals(r2, database.removeByName("Test1"));
        
        assertEquals(expected1, database.toString());
        
        assertEquals(r2, database.removeByName("Test2"));
        
        assertEquals(expected2, database.toString());
    }

    /**
     * Test to make sure the remove by cords function correctly removes
     * the rectangle with same or similar Rectangle provided
     */
    public void testRemoveByCords() {
        Rectangle r1 = new Rectangle("Test1", 100, 100, 0, 0);
        Rectangle r2 = new Rectangle("Test2", 200, 200, 100, 100);
        
        String expected1 = "[ {::0} "
            + "{Test1:(Test2, 100, 100, 200, 200):0} "
            + "{Test2:(Test1, 0, 0, 100, 100):0} "
            + "{Test2:(Test2, 100, 100, 200, 200):0} ]";
        String expected2 = "[ {::0} "
            + "{Test2:(Test1, 0, 0, 100, 100):0} "
            + "{Test2:(Test2, 100, 100, 200, 200):0} ]";
        
        assertNull(database.removeByCords(r1));
        assertNull(database.removeByCords(
            new Rectangle(null, 0, 100, 0, 0)));
        assertNull(database.removeByCords(
            new Rectangle(null, 100, 0, 0, 0)));
        assertNull(database.removeByCords(
            new Rectangle(null, 100, 100, -1, 0)));
        assertNull(database.removeByCords(
            new Rectangle(null, 100, 100, 0, -1)));
        
        TestableRandom.setNextBooleans(false, false, false, false);
        
        database.insert("Test1", r1);
        database.insert("Test1", r2);
        database.insert("Test2", r2);
        database.insert("Test2", r1);
        
        assertEquals(r1, database.removeByCords(r1));
        
        assertEquals(expected1, database.toString());
        
        assertEquals(r2, database.removeByCords(r2));
        
        assertEquals(expected2, database.toString());
    }
    
    /**
     * Checks to make sure the correct rectangles are returned when
     * performing a region search
     * (https://developer.mozilla.org/en-US/docs/Games/Techniques/2D_collision_detection)
     */
    public void testRegionSearch() {
        Rectangle regionFail = 
            new Rectangle("Region1", 5000, 0, -900, 5);
        Rectangle region1      = 
            new Rectangle("Region2", 100, 100, 0, 0);
        Rectangle rect1      = 
            new Rectangle("Test1", 10, 10, 0, 0);
        Rectangle rect2      = 
            new Rectangle("Test2", 100, 310, 400, 0);
        Rectangle rect3      = 
            new Rectangle("Test3", 100, 100, 300, 300);
        
        
        database.insert("Test1", rect1);
        database.insert("Test2", rect2);
        database.insert("Test3", rect3);
        
        ListADT<Integer, Rectangle> result1 = 
            database.regionSearch(regionFail);
        ListADT<Integer, Rectangle> result2 = 
            database.regionSearch(region1);
        
        assertNull(result1);
        
        assertEquals(1, result2.size());
        Iterator<SkipNode<Integer, Rectangle>> it = 
            result2.iterator();
        it.next();
        assertEquals(rect1, it.next().element());
        
    }
    
    /**
     * Checks to make sure the checkRectangle 
     * correctly distinguise between
     * correct and incorrect rectangles
     */
    public void testCheckRectangle() {
        Rectangle rectangleFail1 = 
            new Rectangle("", 0, 100, 100, 100);
        Rectangle rectangleFail2 = 
            new Rectangle("", 100, 0, 100, 100);
        Rectangle rectangleFail3 = 
            new Rectangle("", 100, 100, -1, 100);
        Rectangle rectangleFail4 = 
            new Rectangle("", 100, 100, 100, -1);
        Rectangle rectanglePass = 
            new Rectangle("", 100, 100, 100, 100);
        
        assertFalse(database.checkRectangle(rectangleFail1));
        assertFalse(database.checkRectangle(rectangleFail2));
        assertFalse(database.checkRectangle(rectangleFail3));
        assertFalse(database.checkRectangle(rectangleFail4));
        assertTrue(database.checkRectangle(rectanglePass));
    }
    
    /**
     * Checks the intersections function
     */
    public void testIntersections() {
        Rectangle rectangle1 = 
            new Rectangle("Test1", 100, 100, 0, 0);
        Rectangle rectangle2 = 
            new Rectangle("Test2", 50, 50, 0, 0);
        Rectangle rectangle3 = 
            new Rectangle("Test3", 100, 100, 400, 400);
        
        database.insert("Test1", rectangle1);
        database.insert("Test2", rectangle2);
        database.insert("Test3", rectangle3);
        
        
        ListADT<Integer, Rectangle[]> list = 
            database.intersections();
        
        assertEquals(2, list.size());
        
        Iterator<SkipNode<Integer, Rectangle[]>> it = 
            list.iterator();
        it.next();
        Rectangle[] result = it.next().element();
        assertNotNull(result);
        assertEquals(rectangle1, result[0]);
        assertEquals(rectangle2, result[1]);
        
        result = it.next().element();
        assertEquals(rectangle2, result[0]);
        assertEquals(rectangle1, result[1]);
    }

}
