import java.text.ParseException;

/**
 * Parser for BigIntegerArithmatic 
 * @author erikchair
 * @version 09.27.2022
 */
public class BICalculator {
    private Stack<BigInteger> executionStack;
    
    /**
     * Default constructor for BICalculator
     */
    BICalculator() {
        executionStack = new Stack<BigInteger>();
    }
    
    
    /**
     * Parses and computes the postfix 
     * statement provided
     * @param tokens The array of math statement
     * @return The computed value represented as
     *         a BigInteger object
     */
    public BigInteger parse(String[] tokens)
        throws ParseException
    {
        executionStack.reset();
        
        for (int i = 0; i < tokens.length; i++) {
            String item = tokens[i];
            
            char c = item.charAt(0);
            if (Character.isDigit(c)) {
                executionStack.push(
                    new BigInteger(
                        truncateNumber(item)));
                        
            }
            else if (executionStack.size() < 2) {
                throw new ParseException(
                    "Not enough numbers specified",
                    -1);
            }
            else {
                BigInteger b = executionStack.pop();
                BigInteger a = executionStack.pop();
                
                switch (c) {
                    case '+': {
                        executionStack.push(
                            a.add(b).truncate());
                        break;
                    }
                    case '*': {
                        executionStack.push(
                            a.mul(b).truncate());
                        break;
                    }
                    case '^': {
                        executionStack.push(
                            a.exp(b).truncate());
                        break;
                    }
                    default: {
                        throw new ParseException(
                            "Unsupported operation: " + c,
                            -1);
                    }
                }
            }
        }
        
        if (executionStack.size() > 1) {
            throw new ParseException(
                "Unable to parse",
                -1);
        }
        return executionStack.pop();
    }
    
    /**
     * Trims white spaces off the edges 
     * of the string, and splits it up
     * between whitespaces
     * @param statement The string to perform
     * @return An array of tokens split by whitespaces
     */
    static public String[] tokenize(String statement) {
        return statement.trim().split("\\s+");
    }
    
    /**
     * Trims all useless leading 0's 
     * from numbers in string form
     * @param number The number in string form
     *               to truncate
     * @return Same number numerically without any
     *         leading zeros
     */
    private String truncateNumber(String number) {
        int index = 0;
        int length = number.length() - 1;
        
        //000 0
        while (index < length && number.charAt(index) == '0')
            index++;
        
        if (index <= length)
            number = number.substring(index);
        return number;
    }
}
