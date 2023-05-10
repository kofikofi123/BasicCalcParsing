
import student.TestCase;

/**
 * Test case for Stack class
 * @author erikchair
 * @version 09.26.2022
 */
public class StackTest extends TestCase {
    private Stack<String> stack;
    
    /**
     * Setup method for test enviornment
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        stack = new Stack<String>();
    }


    /**
     * Test the constructor of the stack class
     * to ensure the stack is empty
     */
    public void testStack() {
        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
    }


    /**
     * Test the push method of the stack class
     * to ensure all valid pushes are handled
     */
    public void testPush() {
        assertFalse(stack.push(null));
        assertTrue(stack.isEmpty());
        
        assertTrue(stack.push("Test1"));
        assertTrue(stack.push("Test2"));
        
        assertEquals(2, stack.size());
        assertEquals("Test2", stack.peek());
    }


    /**
     * Test to ensure pop method of stack
     * class returns the correct values
     */
    public void testPop() {
        assertNull(stack.pop());
        assertEquals(0, stack.size());
        
        
        stack.push("Test1");
        stack.push("Test2");
        
        assertEquals("Test2", stack.pop());
        assertEquals("Test1", stack.pop());
        assertNull(stack.pop());
    }

    /**
     * Test to ensure peek method returns
     * correct top element
     */
    public void testPeek() {
        assertNull(stack.peek());
        
        stack.push("Test1");
        stack.push("Test2");
        
        assertEquals("Test2", stack.peek());
        assertEquals(2, stack.size());
    }


    /**
     * Test to ensure isEmpty method correctly
     * returns whether the stack is empty
     * or not
     */
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
        
        stack.push("Test");
        
        assertFalse(stack.isEmpty());
        
        stack.pop();
        
        assertTrue(stack.isEmpty());
    }

    
    /**
     * Test to ensure size method 
     * correctly returns the number of 
     * elements in the stack
     */
    public void testSize() {
        assertEquals(0, stack.size());
        
        stack.push("Test");
        
        assertEquals(1, stack.size());
        
        stack.pop();
        
        assertEquals(0, stack.size());
        
        stack.pop();
        
        assertEquals(0, stack.size());
    }
    
    /**
     * Test to ensure reset method
     * correctly returns the stack
     * to default state
     */
    public void testReset() {
        stack.push("Test1");
        stack.push("Test2");
        
        assertEquals("Test2", stack.peek());
        assertEquals(2, stack.size());
        
        
        stack.reset();
        
        assertNull(stack.peek());
        assertEquals(0, stack.size());
    }

}
