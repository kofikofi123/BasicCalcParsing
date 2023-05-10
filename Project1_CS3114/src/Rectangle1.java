
//Virginia Tech Honor Code Pledge:
//
//As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal, nor will I accept the actions of those who
//do.
//-- erikchair
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * The main application class
 * @author erikchair
 * @version 09.09.2022
 */
public class Rectangle1 {
    
    /**
     * The main function that takes in 1 file argument
     * and outputs the result at System.out
     * @param args The arguments provided
     *             to the main function
     */
    public static void main(String[] args) {
        PrintStream output = System.out;
        RectangleParser parser = 
            new RectangleParser();
        RectangleDatabase database = 
            new RectangleDatabase();

        if (args.length != 1) {
            output.println("Invalid arguments: "
                + "Rectangle1 {commandfile}");
            System.exit(0);
        }
        
        try {
            parser.parseRectangleCommands(
                new FileInputStream(
                    new File(args[0])), 
                database, 
                System.out);
        } 
        catch (FileNotFoundException e) {
            e = e;
        }
        
    }

}
