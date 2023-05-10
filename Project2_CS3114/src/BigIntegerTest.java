
import student.TestCase;

/**
 * Test case for BigInterger class
 * @author erikchair
 * @version 09.26.2022
 */
public class BigIntegerTest extends TestCase {
    private BigInteger number;
    /**
     * Setup method for test enviornment
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        number = new BigInteger(0);
    }
    
    /**
     * Tests the default constructor
     */
    public void testBigInteger() {
        assertEquals("0", number.toString());
        assertEquals(1, number.getNumDigits());
    }

    /**
     * Tests the constructor if 
     * the correct number is registered
     */
    public void testBigIntegerInt() {
        BigInteger other = new BigInteger(32);
        
        assertEquals("32", other.toString());
        assertEquals(2, other.getNumDigits());
    }
    
    /**
     * Tests the add method to
     * ensure numbers added by two
     * BigInteger objects are correct
     */
    public void testAdd() {
        BigInteger n = new BigInteger(100 - 64);
        number.setNumber(32);
        
        assertEquals("64", 
            number.add(number).toString());
        
        assertEquals("100", number.add(n).toString());
        
        
        n.setNumber(9);

        assertEquals("18", n.add(n).toString());
        
        
        n.setNumber(99);
        
        assertEquals("198", n.add(n).toString());
    }
    
    /**
     * Tests the mul method to
     * ensure numbers multiplied 
     * by two BigInteger object
     * are correct
     */
    public void testMul() {
        BigInteger a = new BigInteger(1);
        BigInteger b = new BigInteger(9);
        
        assertEquals("9", a.mul(b).toString());
        
        a.setNumber(0);
        
        assertEquals("0", a.mul(b).toString());
        
        a.setNumber(32);
        b.setNumber(32);
        
        assertEquals("1024", a.mul(b).toString());
    }
    
    /**
     * Tests the exp method to ensure
     * the correct results for exponenets
     * are computed
     */
    public void testExp() {
        BigInteger x = new BigInteger(2);
        BigInteger n = new BigInteger(0);
        
        assertEquals("1", x.exp(n).toString());
        
        x.setNumber(2);
        n.setNumber(1);
        
        assertEquals("2", x.exp(n).toString());
        
        x.setNumber(2);
        n.setNumber(4);
        
        assertEquals("16", x.exp(n).toString());
        
        x.setNumber(2);
        n.setNumber(9);
        
        assertEquals("512", x.exp(n).toString());
        
    }

    
    /**
     * Test the setNumber method 
     * to ensure all the digits
     * represented are present
     */
    public void testSetNumber() {
        number.setNumber(100);
        
        assertEquals("100", number.toString());
        assertEquals(3, number.getNumDigits());
        
        number.setNumber(1);
        
        assertEquals("1", number.toString());
        assertEquals(1, number.getNumDigits());
    }
    
    /**
     * Tests the setNumberByString method
     * to ensure the correct number representation
     * of the string'd number is constructed
     */
    public void testSetNumberByString() {
        number.setNumberByString("2000");
        
        assertEquals("2000", number.toString());
        assertEquals(4, number.getNumDigits());
    }

    /**
     * Test the getNumDigits method
     * to ensure the number of digits specified
     * is returned
     */
    public void testGetNumDigits() {
        assertEquals(1, number.getNumDigits());
        
        BigInteger other = new BigInteger(40000);
        
        assertEquals(5, other.getNumDigits());
    }

    /**
     * Test the toString method to ensure
     * the correct string representing the
     * number provided is returned
     */
    public void testToString() {
        assertEquals("0", number.toString());
    }
    
    
    /**
     * Test the reset method to ensure
     * that the object correctly returns
     * to default state
     */
    public void testReset() {
        number.reset();
        assertEquals(0, number.getNumDigits());
        assertEquals("", number.toString());
    }

    /**
     * Test the insertDigit method to ensure
     * the correct amount of digits are inserted
     * in many scenerios
     */
    public void testInsertDigit() {
        number.setNumber(3);
        
        assertEquals("3", number.toString());
        
        number.insertDigit((byte)2, 1);
        
        assertEquals("23", number.toString());
        
        number.insertDigit((byte)1, 3);
        
        assertEquals("11123", number.toString());
        
        BigInteger other = new BigInteger();
        
        other.insertDigit((byte)1, 1);
        
        assertEquals("1", other.toString());
        
        other.reset();
        
        other.insertDigit((byte)1, 5);
        
        assertEquals("11111", other.toString());
    }
    
    /**
     * Tests the intValue method to ensure
     * the correct number is returned
     */
    public void testIntValue() {
        assertEquals(0, number.intValue());
        
        number.setNumber(1024);
        
        assertEquals(1024, number.intValue());
        
        BigInteger other = new BigInteger();
        
        assertEquals(0, other.intValue());
    }
    
    /**
     * Tests the truncate method to ensure
     * the BigInteger object correctly trims
     * offs useless 0's
     */
    public void testTruncate() {
        number.setNumberByString("0");
        
        assertEquals("0", number.truncate().toString());
        
        number.setNumberByString("00000000");
        
        assertEquals("0", number.truncate().toString());
        
        number.setNumberByString("000000123");
        
        assertEquals("123", number.truncate().toString());
        
        number.setNumberByString("3123000000031231");
        
        assertEquals("3123000000031231", number.truncate().toString());
        
        number.reset();
        
        assertEquals("", number.truncate().toString());
    }
}
