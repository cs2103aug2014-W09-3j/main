package tareas.parser;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Parser component.
 */

public class ParserTest {

    //@author A0093934W
    private ParsingStatus tryParse(String input) {
        return Parser.checkCommandValidity(TareasCommand.fromString(input)).getStatus();
    }

    //@author A0093934W

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

    //@author A0093934W

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

    //@author A0093934W
    @Test
    public void testParseDateTime_ValidInput() {
        String[] tests = {
                "24-12-2014", // full date
                "24-12", // date without year
                "24-12-2014 9:30", // full date time
                "24-12-14 9:30", // full date time, short year
                "24-12-2014 10:15pm", // full date time, AM/PM
                "24-12-14 10am", // date with AM/PM hour
                "18:20", // time only
                "9am", // AM/PM time
                "today", // date constant
                "tomorrow 2pm", // date constant with time
                "tue 3am" // day of the week with time
        };

        LocalDateTime[] results = {
                LocalDateTime.parse("2014-12-24T00:00"),
                LocalDateTime.parse("2014-12-24T00:00"),
                LocalDateTime.parse("2014-12-24T09:30"),
                LocalDateTime.parse("2014-12-24T09:30"),
                LocalDateTime.parse("2014-12-24T22:15"),
                LocalDateTime.parse("2014-12-24T10:00"),
                LocalDateTime.of(LocalDate.now(), LocalTime.parse("18:20")),
                LocalDateTime.of(LocalDate.now(), LocalTime.parse("09:00")),
                LocalDateTime.of(LocalDate.now(), LocalTime.parse("00:00")),
                LocalDateTime.of(LocalDate.now().plus(1, ChronoUnit.DAYS), LocalTime.parse("14:00")),
                LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.of(2))), LocalTime.parse("03:00"))
        };

        for (int i = 0; i < tests.length; i++) {
            assertEquals(Parser.getDateTimeFromString(tests[i]), results[i]);
        }
    }
}