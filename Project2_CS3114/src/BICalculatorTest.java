
import java.text.ParseException;
import student.TestCase;

/**
 * Test case for BICalculator
 * @author erikchair
 * @version 09.28.2022
 */
public class BICalculatorTest extends TestCase {
    private BICalculator calculator = new BICalculator();
    
    /**
     * Setup method for test enviornment
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test parse method to ensure correct 
     * results are computed, and non parsable
     * statements are handled
     */
    public void testParse() {
        String statement1 = "0002 8 + 2 ^";
        String statement2 = "2 8 + 2";
        String statement3 = "02 8 + 2 /";
        String statement4 = "0000000002 8 + +";
        String statement5 = "";
        
        ParseException e1 = null;
        ParseException e2 = null;
        ParseException e3 = null;
        ParseException e4 = null;
        
        
        try {
            assertEquals("100", 
                calculator.parse(
                    BICalculator.tokenize(
                        statement1)).toString());
        }
        catch (ParseException e) {
            e1 = e;
        }
        
        assertNull(e1);
        
        
        try {
            calculator.parse(
                BICalculator.tokenize(
                    statement2));
        } 
        catch (ParseException e) {
            e1 = e;
        }
        
        try {
            calculator.parse(
                BICalculator.tokenize(
                    statement3));
        }
        catch (ParseException e) {
            e2 = e;
        }
        
        try {
            calculator.parse(
                BICalculator.tokenize(
                    statement4));
        }
        catch (ParseException e) {
            e3 = e;
        }
        
        assertNotNull(e1);
        assertEquals("Unable to parse", 
            e1.getMessage());
        
        assertNotNull(e2);
        assertEquals("Unsupported operation: /", 
            e2.getMessage());
        
        assertNotNull(e3);
        assertEquals("Not enough numbers specified", 
            e3.getMessage());
        
        
        try {
            String[] emptyArray = {};
            calculator.parse(emptyArray);
        }
        catch (ParseException e) {
            e = e;
        }
    }
    
    /**
     * Second part testing of parse method
     */
    public void testParse2() {
        String statement1 = "5555555";
        String statement2 = "0000005";
        String statement3 = "0000000";
        
        ParseException e1 = null;
        ParseException e2 = null;
        ParseException e3 = null;
        
        try {
            assertEquals("5555555", 
                         calculator.parse(
                             BICalculator.tokenize(
                                 statement1)).toString());
        }
        catch (ParseException e) {
            e1 = e;
        }
        
        
        try {
            assertEquals("5",
                        calculator.parse(
                            BICalculator.tokenize(
                                statement2)).toString());
        }
        catch (ParseException e) {
            e2 = e;
        }
        
        
        try {
            assertEquals("0",
                         calculator.parse(
                             BICalculator.tokenize(
                                 statement3)).toString());
        }
        catch (ParseException e) {
            e3 = e;
        }
        
        assertNull(e1);
        assertNull(e2);
        assertNull(e3);
    }
}
