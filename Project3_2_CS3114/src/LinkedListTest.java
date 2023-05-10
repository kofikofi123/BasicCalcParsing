
import student.TestCase;

/**
 * Test case for Linked List class
 * @author erikchair
 * @version 10.23.2022
 */
public class LinkedListTest extends TestCase {
    private LinkedList<String> node;
    /**
     * Setup method for test enviornment
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        node = new LinkedList<String>("Main", null, null);
    }


    /**
     * Test case for default linked list constructor 
     */
    public void testLinkedList() {
        LinkedList<String> otherNode = 
            new LinkedList<String>();
        
        
        assertNull(otherNode.next());
        assertNull(otherNode.prev());
        assertNull(otherNode.data());
    }


    /**
     * Test case for linked list with
     * element and next node specifiers
     */
    public void testLinkedListTLinkedListOfT() {
        assertEquals("Main", node.data());
        assertNull(node.next());
        assertNull(node.prev());
    }


    /**
     * Test case for next method to
     * ensure next node is correctly 
     * returned
     */
    public void testNext() {
        assertNull(node.next());
        node.setNext(new LinkedList<String>("Other", null, null));
        
        LinkedList<String> other = node.next();
        assertNotNull(other);
        assertEquals("Other", other.data());
    }

    /**
     * Test case for prev method to
     * ensure prev node is correctly 
     * returned
     */
    public void testPrev() {
        assertNull(node.prev());
        node.setPrev(new LinkedList<String>("Other", null, null));
        
        LinkedList<String> other = node.prev();
        assertNotNull(other);
        assertEquals("Other", other.data());
    }

    /**
     * Test case for setPrev method to
     * ensure the correct node is 
     * set for the next
     */
    public void testSetNext() {
        node.setNext(new LinkedList<String>("Other", null, null));
        
        LinkedList<String> other = node.next();
        
        assertNotNull(other);
        assertEquals("Other", other.data());
        
    }
    
    /**
     * Test case for setPrev method to
     * ensure the correct node is 
     * set for the prev
     */
    public void testSetPrev() {
        node.setPrev(new LinkedList<String>("Other", null, null));
        
        LinkedList<String> other = node.prev();
        
        assertNotNull(other);
        assertEquals("Other", other.data());
        
    }

    /**
     * Test case for data method to 
     * ensure the data related to the
     * node is returned
     */
    public void testData() {
        assertEquals("Main", node.data());
        
        LinkedList<String> other = 
            new LinkedList<String>();
        
        assertNull(other.data());
    }
    
    /**
     * Test case for hasPrev to ensure
     * that the correct result is 
     * returned
     */
    public void testHasPrev() {
        assertFalse(node.hasPrev());
        node.setPrev(new LinkedList<String>());
        assertTrue(node.hasPrev());
    }

    /**
     * Test case for hasNext to ensure
     * that the correct result is 
     * returned
     */
    public void testHasNext() {
        assertFalse(node.hasNext());
        node.setNext(new LinkedList<String>());
        assertTrue(node.hasNext());
    }
}
