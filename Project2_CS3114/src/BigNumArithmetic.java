// On my honor:
 // - I have not used source code obtained from another student,
 //   or any other unauthorized source, either modified or
 //   unmodified.
 //
 // - All source code and documentation used in my program is
 //   either my original work, or was derived by me from the
 //   source code published in the textbook for this course.
 //
 // - I have not discussed coding details about this project
 //   with anyone other than my partner (in the case of a joint
 //   submission), instructor, ACM/UPE tutors or the TAs assigned
 //   to this course. I understand that I may discuss the concepts
 //   of this program with other students, and that another student
 //   may help me debug my program so long as neither of us writes
 //   anything during the discussion or modifies any computer file
 //   during the discussion. I have violated neither the spirit nor
 //   letter of this restriction.

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Main class for execution
 * @author erikchair
 * @version 09.28.2022
 */
public class BigNumArithmetic {

    /**
     * Main method of program
     * @param args The argumnets fed into the
     *             the program, expects a filename
     * @throws FileNotFoundException If file specified
               is not found
     */
    public static void main(String[] args) 
        throws FileNotFoundException {
        BICalculator calculator = new BICalculator();
        PrintStream out = System.out;
        
        if (args.length != 1) {
            out.println("Invalid number of arguments");
            return;
        }
        
        Scanner scanner = new Scanner(new File(args[0]));
        StringBuilder builder = new StringBuilder();
        
        while (scanner.hasNext()) {
            String statement = scanner.nextLine();
            
            String[] parsedStatement = 
                BICalculator.tokenize(statement);
            
            if (statement.trim().equals(""))
                continue;
            builder.append(
                String.join(" ", parsedStatement));
            
            builder.append(" = ");
            
            try {
                BigInteger result = 
                    calculator.parse(parsedStatement);
                
                
                builder.append(result.toString());
            }
            catch (ParseException e) {
                e = e;
            }
            
            
          
            if (scanner.hasNext())
                builder.append('\n');
        }
        
        scanner.close();
        
        System.out.print(builder.toString());
        
    }
}
