import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import student.TestCase;

/**
 * Test suite for DNATreeParser
 * @author erikchair
 * @version 12.07.2022
 */
public class DNATreeParserTest extends TestCase {
    private StringWriter output;
    private DNATreeParser parser;
    /**
     * Setsup the test enviornment
     * @throws Exception Incase of any exception
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        output = new StringWriter();
        parser = new DNATreeParser(output);
    }


    /**
     * Ensures the parsers inital conditions are
     * correct
     */
    @Test
    public void testDNATreeParserWriterDNATreeStructure() {
        assertEquals("E", parser.getTree().dump());
    }


    /**
     * Ensures that the parser can
     * parse block of commands
     */
    @Test
    public void testParse() {
        String input = "insert AAAA\n"
            + "insert TAG\n"
            + "insert AAAA\n"
            + "insert AA\n"
            + "insert AAAA\n"
            + "print\n"
            + "remove AA\n"
            + "search AA\n"
            + "search GG\n"
            + "search AAAA$\n"
            + "print\n"
            + "print lengths\n"
            + "print stats";
        
        String expect = "sequence AAAA inserted at level 0\n"
            + "sequence TAG inserted at level 1\n"
            + "sequence AAAA already exists\n"
            + "sequence AA inserted at level 3\n"
            + "sequence AAAA already exists\n"
            + "tree dump:\n"
            + "I\n"
            + "  I\n"
            + "    I\n"
            + "      AAAA\n"
            + "      E\n"
            + "      E\n"
            + "      E\n"
            + "      AA\n"
            + "    E\n"
            + "    E\n"
            + "    E\n"
            + "    E\n"
            + "  E\n"
            + "  E\n"
            + "  TAG\n"
            + "  E\n"
            + "sequence AA removed\n"
            + "# of nodes visited: 2\n"
            + "sequence: AAAA\n"
            + "# of nodes visited: 2\n"
            + "no sequence found\n"
            + "# of nodes visited: 2\n"
            + "sequence: AAAA\n"
            + "tree dump:\n"
            + "I\n"
            + "  AAAA\n"
            + "  E\n"
            + "  E\n"
            + "  TAG\n"
            + "  E\n"
            + "tree dump:\n"
            + "I\n"
            + "  AAAA 4\n"
            + "  E\n"
            + "  E\n"
            + "  TAG 3\n"
            + "  E\n"
            + "tree dump:\n"
            + "I\n"
            + "  AAAA A:100.00 C:0.00 G:0.00 T:0.00\n"
            + "  E\n"
            + "  E\n"
            + "  TAG A:33.33 C:0.00 G:33.33 T:33.33\n"
            + "  E\n";
        
        IOException eCheck = null;
        try {
            parser.parse(new StringReader(input));
        }
        catch (IOException e) {
            eCheck = e;
        }
        
        assertNull(eCheck);
        assertEquals(expect, output.toString());
    }

}
