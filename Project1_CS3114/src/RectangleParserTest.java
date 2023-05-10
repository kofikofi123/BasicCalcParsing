import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

import student.TestCase;
import student.TestableRandom;
/**
 * A test class for RectangleParser
 * @author erikchair
 * @version 09.09.2022
 *
 */
public class RectangleParserTest extends TestCase {
    private RectangleParser parser;
    private RectangleDatabase database;
    
    /**
     * Sets up the test class
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        parser = new RectangleParser();
        database = new RectangleDatabase();
    }
    
    /**
     * Tests all commands for the parser
     */
    public void testParseRectangleCommands() {
        String inputTests = 
            "intersections\n" +
            "dump\n" +
            "insert Test1 -1 0 100 100\n" +
            "insert Test2 0 0 0 0\n" +
            "insert Test3 0 0 100 100\n" +
            "dump\n" +
            "insert Test4 50 50 100 100\n" +
            "insert Test5 0 90 10 100\n" +
            "insert Test6 150 150 100 100\n" +
            "insert Test7 200 200 200 200\n" +
            "insert Test8 50 50 50 50\n" +
            "insert Test4 1000 1000 2 2\n" +
            "remove Test9\n" +
            "remove Test8\n" +
            "remove 0 0 0 0\n" +
            "remove 200 200 200 230\n" +
            "remove 200 200 200 200\n" +
            "regionsearch 0 0 0 100\n" +
            "regionsearch 500 500 10 10\n" +
            "regionsearch 0 0 100 100\n" +
            "intersections\n" +
            "search Test10\n" +
            "search Test4";
        
        String expectedResults =
            "Intersections pairs:\n" +
            "SkipList dump:\n" +
            "Node has depth 1, Value (null)\n" +
            "SkipList size is: 0\n" +
            "Rectangle rejected: " +
            "(Test1, -1, 0, 100, 100)\n" +
            "Rectangle rejected: " +
            "(Test2, 0, 0, 0, 0)\n" +
            "Rectangle inserted: " +
            "(Test3, 0, 0, 100, 100)\n" +
            "SkipList dump:\n" +
            "Node has depth 1, Value " +
            "(null)\n" +
            "Node has depth 1, Value " +
            "(Test3, 0, 0, 100, 100)\n" +
            "SkipList size is: 1\n" +
            "Rectangle inserted: " +
            "(Test4, 50, 50, 100, 100)\n" +
            "Rectangle inserted: " +
            "(Test5, 0, 90, 10, 100)\n" +
            "Rectangle inserted: " +
            "(Test6, 150, 150, 100, 100)\n" +
            "Rectangle inserted: " +
            "(Test7, 200, 200, 200, 200)\n" +
            "Rectangle inserted: " +
            "(Test8, 50, 50, 50, 50)\n" +
            "Rectangle inserted: " +
            "(Test4, 1000, 1000, 2, 2)\n" +
            "Rectangle not removed: " +
            "(Test9)\n" +
            "Rectangle removed: " +
            "(Test8, 50, 50, 50, 50)\n" +
            "Rectangle rejected: " +
            "(0, 0, 0, 0)\n" +
            "Rectangle not removed: " +
            "(200, 200, 200, 230)\n" +
            "Rectangle removed: " +
            "(Test7, 200, 200, 200, 200)\n" +
            "Rectangle rejected: " +
            "(0, 0, 0, 100)\n" +
            "Rectangles intersecting region " +
            "(500, 500, 10, 10):\n" +
            "Rectangles intersecting region " +
            "(0, 0, 100, 100):\n" +
            "(Test3, 0, 0, 100, 100)\n" + 
            "(Test4, 50, 50, 100, 100)\n" +
            "(Test5, 0, 90, 10, 100)\n" +
            "Intersections pairs:\n" +
            "(Test3, 0, 0, 100, 100 | " +
            "Test4, 50, 50, 100, 100)\n" +
            "(Test3, 0, 0, 100, 100 | " +
            "Test5, 0, 90, 10, 100)\n" +
            "(Test4, 50, 50, 100, 100 | Test3" +
            ", 0, 0, 100, 100)\n(Test5, 0, " +
            "90, 10, 100 | " +
            "Test3, 0, 0, 100, 100)\n" +
            "Rectangle not found: (Test10)\n" +
            "Rectangles found:\n" +
            "(Test4, 50, 50, 100, 100)\n" +
            "(Test4, 1000, 1000, 2, 2)\n" +
            "";
        
        
        TestableRandom.setNextBooleans(
            false, 
            false, 
            false, 
            false, 
            false, 
            false, 
            false, 
            false, 
            false);      

        try {
            parser.parseRectangleCommands(
                new ByteArrayInputStream(inputTests.getBytes()), 
                   database, systemOut());
        } 
        catch (FileNotFoundException e) {
            e = e;
        }
        assertEquals(expectedResults, systemOut().getHistory());
    }
    

    
}
