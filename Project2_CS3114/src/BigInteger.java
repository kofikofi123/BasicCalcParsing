/**
 * Wrapper class for infinite integer precision
 * @author erikchair
 * @version 09.28.2022
 */
public class BigInteger {
    private LinkedList<Byte> digits;
    private int numDigits;
    /**
     * Default constructor; initial value is 0
     */
    BigInteger() {
        digits = null;
        numDigits = 0;
    }
    
    /**
     * Sets the number this object represents
     * @param number The number to use
     */
    BigInteger(int number) {
        setNumber(number);
    }
    
    /**
     * Sets the number this object represents
     * based on a string representation of the 
     * number
     * @param number The number in string form
     *               to use
     */
    BigInteger(String number) {
        setNumberByString(number);
    }
    
    /**
     * Constructor that clones from another
     * BigInteger object
     * @param other The BigInteger object
     *              to clone
     */
    BigInteger(BigInteger other) {
        digits = other.digits;
        numDigits = other.numDigits;
    }
    
    
    /**
     * Performs an add operation on two
     * BigInterger objects
     * @param other The other object to add 
     *              to this
     * @return this object
     */
    public BigInteger add(BigInteger other) {
        LinkedList<Byte> newDigits = 
            new LinkedList<Byte>();
        int d = Math.max(numDigits, other.numDigits);
        
        LinkedList<Byte> aList = digits;
        LinkedList<Byte> bList = other.digits;
        LinkedList<Byte> cList = newDigits;
        
        
        int counter = d;
        byte carry = 0;
        while (counter-- > 0) {
            byte a = 0;
            byte b = 0;
            
            if (aList != null) {
                a = aList.data().byteValue();
                aList = aList.next();
            }
            
            if (bList != null) {
                b = bList.data().byteValue();
                bList = bList.next();
            }
            
            byte sum = (byte)(carry + a + b);
            cList.setNext(
                new LinkedList<Byte>((byte)(sum % 10), null));
            cList = cList.next();
            carry = (byte)(sum / 10);
        }
        
        if (carry > 0) {
            d++;
            cList.setNext(
                new LinkedList<Byte>(carry, null));
        }
        
        digits = newDigits.next();
        numDigits = d;
       
        return this;
    }
    
    /**
     * Performs a multiply operation on two
     * BigInteger objects
     * @param other The BigInteger object to
     *              multiply with
     * @return this object
     */
    public BigInteger mul(BigInteger other) {
        
        LinkedList<Byte> aList = digits;
        LinkedList<Byte> bList = other.digits;
        
        
        byte carry = 0;
        byte currentDigit = 0;
        BigInteger num = new BigInteger(0);
        BigInteger partial = new BigInteger();
        while (bList != null) {
            byte b = bList.data().byteValue();
            
            partial.reset();
            partial.insertDigit((byte)0, currentDigit);
            
            while (aList != null) {
                byte a = aList.data().byteValue();
                
                byte prod = (byte)((a * b) + carry);
                
                partial.insertDigit((byte)(prod % 10), 1);
                carry = (byte)(prod / 10);
                
                aList = aList.next();
            }
            
            if (carry > 0)
                partial.insertDigit(carry, 1);
            
            num.add(partial);
            
            currentDigit++;
            aList = digits;
            bList = bList.next();
            carry = 0;
        }
        copy(num);
        
        return this;
    }
    
    /**
     * Calculates the signed 32-bit representation
     * of this object
     * @return The signed 32-bit value calculated
     */
    public int intValue() {
        int value = 0;
        int base = 1;
        
        
        LinkedList<Byte> node = digits;
        
        while (node != null) {
            value = value + 
                node.data().intValue() * base;
            
            base = base * 10;
            node = node.next();
        }
        
        
        return value;
    }
   
    /**
     * Calculates the exponential result of this
     * BigInteger as the base, to the power of
     * another BigInteger
     * @param other The BigInteger representing
     *              the exponent
     * @return This object
     */
    public BigInteger exp(BigInteger other) {
        int otherValue = other.intValue();
        
        BigInteger copied = new BigInteger(this);
        BigInteger collector = new BigInteger(1);
        
        
        int mod = otherValue % 2;
        int n = (otherValue - mod) / 2;
        
        // n = (int)((otherValue % 2) / 2) = 0
        // n = (int)(1 / 2) = 0
        
        if (mod == 1)
            collector.mul(copied);
        
        copied.mul(copied);
        while (n-- > 0) {
            collector.mul(copied);
        }
        
        copy(collector);
        
        
        
        return this;
    }
    /**
     * Creates a linked-list of each digit
     * in the number provided
     * @param number The number to use
     */
    public void setNumber(long number) {
        digits = null;
        int base = 1;
        
        if (number > 9)
            base = (int)Math.log10((double)number) + 1;
        
        numDigits = base;
        LinkedList<Byte> temp = null;
        while (base-- > 0) {
            byte digit = (byte)(number % 10);
            
            if (digits == null) {
                digits = 
                    new LinkedList<Byte>(digit, null);
                temp = digits;
            }
            else {
                temp.setNext(
                    new LinkedList<Byte>(digit, null));
                temp = temp.next();
            }
            
            number = number / 10;
            
        }
    }
    
    /**
     * Sets the objects number to a string
     * representation of the number
     * @param number The number in string form to
     *               parse
     */
    public void setNumberByString(String number) {
        digits = null;
        numDigits = number.length();
        
        
        for (int i = 0; i < numDigits; i++) {
            char c = number.charAt(i);
            
            digits = new LinkedList<Byte>((byte)(c - 0x30), digits);
        }
    }
    
    /**
     * Inserts digit(s) at the next 10's place
     * @param digit The digit to insert 
     * @param rep The number of times to insert it
     */
    public void insertDigit(byte digit, int rep) {
        LinkedList<Byte> node = digits;
        
        while (node != null && node.hasNext()) {
            node = node.next();
        }
        
        while (rep-- > 0) {
            LinkedList<Byte> newNode = 
                new LinkedList<Byte>(digit, null);
            
            if (node == null) {
                digits = newNode;
                node = newNode;
            }
            else {
                node.setNext(newNode);
                node = node.next();
            }
            
            numDigits++;
        }
    }
    
    
    /**
     * Copies another BigInteger structure
     * @param other The other BigInteger to copy
     */
    public void copy(BigInteger other) {
        digits = other.digits;
        numDigits = other.numDigits;
    }
    
    /**
     * Resets the big integer object to default state
     */
    public void reset() {
        digits = null;
        numDigits = 0;
    }
    
    /**
     * Trims away useless zeros from the LinkedList
     * @return this object
     */
    public BigInteger truncate() {
        if (numDigits < 2) return this;
        LinkedList<Byte> node = digits.next();
        LinkedList<Byte> prev = digits;
        LinkedList<Byte> save = null;
        
        while (node != null) {
            int val = node.data().intValue();
            
            if (val == 0 && save == null)
                save = prev;
            else if (val != 0)
                save = null;
            prev = node;
            node = node.next();
        }
        
        if (save != null)
            save.setNext(null); //garbage collection
        
        return this;
    }
    
    /**
     * Returns the number of digits this
     * number objects has
     * @return The number of digits
     */
    public int getNumDigits() {
        return numDigits;
    }
    
    
    /**
     * Returns the string representation of the 
     * number this object represents
     * 
     * @return A string of numbers representing
     *         the infinite precision number
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        LinkedList<Byte> node = digits;
        
        while (node != null) {
            builder.append(node.data().intValue());
            node = node.next();
        }
        
        return builder.reverse().toString();
    }
}
