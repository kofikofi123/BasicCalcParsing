import java.util.Iterator;
/**
 * A class to store Rectangles
 * @author erikchair
 * @version 09.09.2022
 *
 */
public class RectangleDatabase {
    private ListADT<String, Rectangle> list;
    private StringBuilder builder;
    
    private final int[] min  = {0, 0};
    private final int[] max = {1025, 1025};
    
    /**
     * Initializes the list and the String Builder needed
     */
    RectangleDatabase() {
        list = new SkipList<String, Rectangle>(); 
        //change container for project2
        builder = new StringBuilder();
    }
    
    /**
     * Inserts a Rectangle with a name, or fails 
     * if the rectangle given fails checkRectangle step
     * @param name The name to associate with the rectangle
     * @param rect The rectangle to add
     * @return If the rectangle was valid, return true. 
     * False otherwise
     */
    public boolean insert(String name, Rectangle rect) {
        boolean result = checkRectangle(rect) && 
                         (name != null) &&
                         Character.isLetter(
                             name.charAt(0));
        
        if (result)
                result = list.insert(name, rect);
        
        return result;
    }
    

    /**
     * Searches for a Rectangle with a given name 
     * @param name The name of the rectangle to find
     * @return Returns a Rectangle if one exists in the list 
     *         with the name given, null otherwise
     */
    public ListADT<String, Rectangle> search(String name) {
        return list.find(name);
    }
    
    /**
     * Dumps all rectangles into a string representaiton
     * @return Returns a string representation of all 
     * Rectangles in the list
     */
    public String dump() {
        builder.setLength(0);
        builder.append("SkipList dump:\n");
        for (SkipNode<String, Rectangle> node : list) {
            builder.append("Node has depth " 
                + (node.getLevel() + 1) + ", Value ");
            if (node.hasRecord()) {
                builder.append(node.element().toString());
            }
            else {
                builder.append("(null)");
            }
            builder.append('\n');
        }
        return builder.append("SkipList size is: ")
                      .append(list.size())
                      .toString();
    }
    
    
    /**
     * Removes a Rectangle with the given name from the list
     * @param name The name of the rectangle to remove
     * @return The rectangle that was removed, or null if 
     * no rectangle was found
     */
    public Rectangle removeByName(String name) {
        return list.remove(name);
    }
    
    /**
     * Removes a Rectangle that is similar or same to the Rectangle given
     * @param rect The rectangle to remove from the list
     * @return The rectangle remove (the actual one) otherwise null
     */
    public Rectangle removeByCords(Rectangle rect) {
        SkipList<String, Rectangle> sList = 
            (SkipList<String, Rectangle>)list;
        Iterator<SkipNode<String, Rectangle>> it = sList.iterator();
        
        if (!checkRectangle(rect)) 
            return null;
        
        while (it.hasNext()) {
            SkipNode<String, Rectangle> node = it.next();
            
            if (node.hasRecord() && 
                node.element().equals(rect)) {
                return sList.remove(node);
            }
        }
        
        
        
        return null;
    }
    
    /**
     * Returns a list of Rectangles that are within a 
     * Region specified by a Rectangle
     * @param region The rectangle region to use
     * @return Returns the list of rectangles intersection with 
     *         the region, null otherwise if the region is not 
     *         correctly formatted (width and height must be > 0)
     */
    public ListADT<Integer, Rectangle> regionSearch(Rectangle region) {
        ListADT<Integer, Rectangle> results = null;
        int[] size = region.getSize();
        int index = 0;
        if (size[0] > 0 && size[1] > 0) {
            results = new SkipList<Integer, Rectangle>();
            
            for (SkipNode<String, Rectangle> node : list) {
                if (node.hasRecord() && 
                    checkIntersection(region, node.element())) {
                    results.insert(index++, node.element());
                }
            }
        }
        
        return results;
    }
    
    /**
     * Returns all the rectangle intersections found in the list
     * @return A list of Rectangles that intersect
     */
    public ListADT<Integer, Rectangle[]> intersections() {
        ListADT<Integer, Rectangle[]> results = new 
            SkipList<Integer, Rectangle[]>();
        Iterator<SkipNode<String, Rectangle>> it1 = 
            list.iterator();
        it1.next();
        
        Rectangle[] pair = new Rectangle[2];
        int finalIndex = 0;
        while (it1.hasNext()) {
            SkipNode<String, Rectangle> n1 = it1.next();
            
            Iterator<SkipNode<String, Rectangle>> it2 = 
                list.iterator();
            
            it2.next();
            
            pair[0] = n1.element();
            
            while (it2.hasNext()) {
                SkipNode<String, Rectangle> n2 = it2.next();
                
                pair[1] = n2.element();
                if (!n1.equals(n2) && 
                    checkIntersection(pair[0], pair[1])) {
                    results.insert(finalIndex++, pair.clone());
                }
            }
        }
        
        
        return results;
    }
    
    /**
     * Returns the string representation of this database 
     * (which returns the stringified list)
     * @return The toString() of list
     */
    @Override
    public String toString() {
        return list.toString();
    }
    
    /**
     * Checks to see if a Rectangle intersects another Rectangle
     * Note: Assumes Rectangles are axis-aligned
     * @param rect1 One of the rectangles to check intersections with
     * @param rect2 One of the rectangles to check intersections with
     * @return True if rect1 intersects rect2, false otherwise
     */
    private boolean checkIntersection(Rectangle rect1, Rectangle rect2) {
        int[] pos1 = rect1.getPosition();
        int[] pos2 = rect2.getPosition();
        
        int[] max1 = rect1.getMaxPosition();
        int[] max2 = rect2.getMaxPosition();
        return pos1[0] < max2[0] &&
               max1[0] > pos2[0] && 
               pos1[1] < max2[1] &&
               max1[1] > pos2[1];
    }
    
    /**
     * Checks to see if the given Rectangle satisifies:
     * 
     * 0 <= x < MAX_POS (1024)
     * 0 <= y < MAX_POS (1024)
     * width > 0
     * height > 0
     * @param rect The Rectangle to check
     * @return True if the Rectangle satisifies the above conditions,
     *  false otherwise
     */
    public boolean checkRectangle(Rectangle rect) {
        int[] pos = rect.getPosition();
        int[] size  = rect.getSize();
        
        int[] maxPos = rect.getMaxPosition();
        
        return (pos[0] >= min[0] && pos[1] >= min[1]) && 
               (maxPos[0] < max[0] && maxPos[1] < max[1]) &&
               (size[0] > 0 && size[1] > 0);
    }
}
