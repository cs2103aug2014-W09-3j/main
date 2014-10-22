package tareas.parser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Parser component.
 *
 * @author Kent
 */

public class ParserTest {

    @Test
    public void testCheckCommandValidity_ValidCommands() throws Exception {
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
            // convert string test to TareasCommand
            TareasCommand command = TareasCommand.fromString(test);

            // get the parsing result
            ParsingResult result = Parser.checkCommandValidity(command);

            assertEquals(true, result.isSuccessful());
        }
    }
}