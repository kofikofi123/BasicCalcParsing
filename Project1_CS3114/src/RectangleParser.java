import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Main parser for Rectangle1 
 * @author erikchair
 * @version 09.09.2022
 */
public class RectangleParser {
    /**
     * The main function for parsing a file of rectangular commands
     * @param inStream The stream to read from
     * @param database 
     * @param outStream The stream to output results to
     * @throws FileNotFoundException If file is not found, this is thrown
     */
    public void parseRectangleCommands(InputStream inStream, 
            RectangleDatabase database, PrintStream outStream) 
                throws FileNotFoundException {
        Scanner scanner = new Scanner(inStream);
        while (scanner.hasNextLine()) {
            String[] command = parseCommand(scanner.nextLine());
            String commandName = command[0];
            
            switch (commandName) {
                case "insert": {
                    performInsert(command, database, outStream);
                    break;
                }
                case "dump": {
                    performDump(database, outStream);
                    break;
                }
                case "remove": {
                    performRemove(command, database, outStream);
                    break;
                }
                case "regionsearch": {
                    performRegionSearch(command, database, outStream);
                    break;
                }
                case "intersections": {
                    performIntersection(database, outStream);
                    break;
                }
                case "search": {
                    performSearch(command, database, outStream);
                    break;
                }
            }
        }
        scanner.close();
    }
    
    /**
     * Parses the insert command
     * @param command The command array used
     * @database The rectangle database to insert rectangles into
     * @param outStream The stream to output results to
     */
    private void performInsert(String[] command, 
                               RectangleDatabase database, 
                               PrintStream outStream) {
        String name = command[1];
        int x       = Integer.parseInt(command[2]);
        int y       = Integer.parseInt(command[3]);
        int width   = Integer.parseInt(command[4]);
        int height  = Integer.parseInt(command[5]);
        
        Rectangle rectangle = new Rectangle(name, 
                                            width, 
                                            height, 
                                            x, 
                                            y);
        
        if (database.insert(name, rectangle)) {
            outStream.print("Rectangle inserted: ");
        } 
        else {
            outStream.print("Rectangle rejected: ");
        }
        
        outStream.println(rectangle.toString());
    }
    
    /**
     * Parses the dump command
     * @param database The rectangle database to work with
     * @param outStream The stream to output results to
     */
    private void performDump(RectangleDatabase database, 
                             PrintStream outStream) {
        outStream.println(database.dump());
    }
    
    /**
     * Parses the remove command (both remove by name or remove by coords)
     * @param command The command array used
     * @param database The rectangle database to remove rectangles from
     * @param outStream The stream to output results
     */
    private void performRemove(String[] command, 
                               RectangleDatabase database, 
                               PrintStream outStream) {
        String test = command[1];
        if (Character.isDigit(test.charAt(0)) || 
                              (test.charAt(0) == '-' && 
                              Character.isDigit(test.charAt(1)))) {
            int x = Integer.parseInt(command[1]);
            int y = Integer.parseInt(command[2]);
            Rectangle rectangle = new Rectangle(null, 
                                                Integer.parseInt(command[3]), 
                                                Integer.parseInt(command[4]), 
                                                x, 
                                                y);
            Rectangle other = database.removeByCords(rectangle);
            
            if (!database.checkRectangle(rectangle))
                outStream.print("Rectangle rejected: ");
            else if (other == null)
                outStream.print("Rectangle not removed: ");
            else 
                outStream.print("Rectangle removed: ");
            
            Rectangle toUse = (other == null ? rectangle : other);
            outStream.println(toUse.toString());
        } 
        else {
            String name = command[1];
            
            Rectangle rectangle = database.removeByName(name);
            if (rectangle != null) {
                outStream.println("Rectangle removed: " + 
                                   rectangle.toString());
            } 
            else {
                outStream.println("Rectangle not removed: (" + 
                                   name + 
                                   ")");
            }
        }
    }
    
    /**
     * Parses the regionsearch command
     * @param command The command array used
     * @param database The rectangle database to check for 
     *        rectangles intersection with a specified region
     * @param outStream The stream to output results
     */
    private void performRegionSearch(String[] command, 
                                     RectangleDatabase database, 
                                     PrintStream outStream) {
        int x = Integer.parseInt(command[1]);
        int y = Integer.parseInt(command[2]);
        Rectangle region = new Rectangle(null, 
                                         Integer.parseInt(command[3]), 
                                         Integer.parseInt(command[4]), 
                                         x, y);
        
        ListADT<Integer, Rectangle> rectangles = database.regionSearch(region);
        
        if (rectangles != null) {
            Iterator<SkipNode<Integer, Rectangle>> it = rectangles.iterator();
            it.next();
            
            outStream.println("Rectangles intersecting region " + 
                               region.toString() + ":");
            while ((it != null) && it.hasNext()) {
                outStream.println(it.next().element().toString());
            }
        } 
        else {
            outStream.println("Rectangle rejected: " + region.toString());
        }
    }

    /**
     * Parses the intersection command
     * @param database The rectangle database to 
     *        check for intersection with its Rectangles
     * @param outStream the stream to output results
     */
    private void performIntersection(RectangleDatabase database, 
                                     PrintStream outStream) {
        
        outStream.println("Intersections pairs:");
        ListADT<Integer, Rectangle[]> list = database.intersections();
        
        if (list != null && list.size() > 0) {
            Iterator<SkipNode<Integer, Rectangle[]>> it = list.iterator();
            it.next();
            
            while ((it != null) && it.hasNext()) {
                Rectangle[] r = (it.next()).element();
                
                String r1 = r[0].toString();
                String r2 = r[1].toString();
                outStream.println(r1.substring(0, r1.length() - 1) + 
                                  " | " + r2.substring(1));
            }
        }
    }
    
    /**
     * Parses the search command 
     * @param command The command array used
     * @param database The rectangle database to search Rectangles 
     *                 with a specific name
     * @param outStream The stream to output results
     */
    private void performSearch(String[] command, 
                               RectangleDatabase database, 
                               PrintStream outStream) {
        String name = command[1];
        ListADT<String, Rectangle> list = database.search(name);
        
        if (list.size() > 0) {
            outStream.println("Rectangles found:");
            Iterator<SkipNode<String, Rectangle>> it = list.iterator();
            it.next();
            
            while ((it != null) && it.hasNext()) {
                outStream.println(it.next().element().toString());
            }
        }
        else {
            outStream.println("Rectangle not found: (" + name + ")");
        }
    }
    
    /**
     * Splits the content from whitespaces
     * @param command The command string
     * @return An array of sliced command
     */
    private String[] parseCommand(String command) {
        return command.trim().split("\\s+");
    }
}
