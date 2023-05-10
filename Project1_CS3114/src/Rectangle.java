/**
 * A simple rectangle class representation
 * @author erikchair
 * @version 09.09.2022
 */
public class Rectangle {
    private String name;
    private int width;
    private int height;
    private int x;
    private int y;
    
    /**
     * Initializes a rectangle at (0, 0) with 0 with and 0 height
     */
    Rectangle() {
        this("", 0, 0, 0, 0);
    }
    
    /**
     * Initializes a rectangle at (x, y) with a width and a height
     * @param name The name of the rectangle
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     * @param x The x position of the rectangle
     * @param y The y position of the rectangle
     */
    Rectangle(String name, int width, int height, int x, int y) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gets the position of the rectangle
     * @return Returns an array with the x position and y position
     */
    public int[] getPosition() {
        int[] array = {x, y};
        return array;
    }
    
    /**
     * Gets the size of the rectangle
     * @return Returns an array with the width and height
     */
    public int[] getSize() {
        int[] array = {width, height};
        return array;
    }
    
    
    /**
     * Gets the max position of the rectangle where
     * x' = x + w
     * y' = y + h
     * @return Returns an array with the max 
     */
    public int[] getMaxPosition() {
        int[] array = {x + width, y + height};
        return array;
    }
    
    /**
     * Checks to see if an object is the same or similar
     * @return Whether the object is the same or similar
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (this == other) return true;
        
        boolean result = false;
        if (other instanceof Rectangle) {
            Rectangle rectOther = (Rectangle)other;
            
            result = ((this.x == rectOther.x) && 
                      (this.y == rectOther.y) && 
                      (this.width == rectOther.width) &&
                      (this.height == rectOther.height));
        }
        
        return result;
    }
    
    /**
     * Returns a String representation of the Rectangle
     * (x, y, width, height)
     * @return The string representation of the Rectangle class
     */
    @Override
    public String toString() {
        String result = "(";
        if (name != null) {
            result = result + name + ", ";
        }
        return result + x + ", " + y + ", " + width + ", " + height + ")";
    }
    
}
