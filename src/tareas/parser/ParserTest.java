package tareas.parser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Parser component.
 *
 * @author Kent
 */

public class ParserTest {

    private ParsingStatus tryParse(String input) {
        return Parser.checkCommandValidity(TareasCommand.fromString(input)).getStatus();
    }


    /**
     * Cases for the valid command partition.
     * These are all boundary cases since missing just one element (keyword or argument)
     * will render the command invalid.
     */
    @Test
    public void testCheckCommandValidity_ValidCommands() {
        String[] tests = {
                "buy ham",
                "meeting with 2103 group /from 22-09-2014 1200 /to 22-09-2014 2200",
                "complete user guide /by 22-09-2014 1500",
                "gym /recurring daily from 0800 to 1000",
                "/edit 1 /des buy watermelon /start 22-09-2014 1200 /end 22-09-2014 1300",
                "/edit 3 /deadline 11-12-2015",
                "/delete 3",
                "/undo",
                "/font Helvetica /size 12"
        };

        for (String test : tests) {
            assertEquals(ParsingStatus.SUCCESS, tryParse(test));
        }
    }

    /**
     * Boundary cases for invalid command partition.
     */
    @Test
    public void testCheckCommandValidity_InvalidCommands() {
        // unknown command: /what
        assertEquals(ParsingStatus.UNKNOWN_COMMAND, tryParse("/what hello world"));

        // signature not matched: missing /to
        assertEquals(ParsingStatus.SIGNATURE_NOT_MATCHED, tryParse("buy ham /from 22-09-2014"));

        // signature not matched: unexpected keyword /foo
        assertEquals(ParsingStatus.SIGNATURE_NOT_MATCHED, tryParse("/delete 3 /foo bar"));

        // missing primary argument for /edit (which task id to edit)
        assertEquals(ParsingStatus.MISSING_PRIMARY_ARGUMENT, tryParse("/edit /des new description"));

        // unexpected primary argument, /redo should not have any argument at all
        assertEquals(ParsingStatus.UNEXPECTED_PRIMARY_ARGUMENT, tryParse("/redo 123"));
    }
}