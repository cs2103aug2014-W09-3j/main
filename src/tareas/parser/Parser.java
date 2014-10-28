package tareas.parser;

import tareas.common.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.HashSet;

/**
 * This class is obviously a parser.
 *
 * @author Kent
 */

public class Parser {
    private static String TAG = "tareas/parser";

    private static DateTimeFormatter[] formatters;

    /**
     * This function returns an array of DateTimeFormatter of different patterns.
     *
     * @return an array of DateTimeFormatter
     */
    private static DateTimeFormatter[] getDateTimeFormatters() {
        if (formatters == null) {
            LocalDateTime now = LocalDateTime.now();

            formatters = new DateTimeFormatter[] {
                    // Date only. Time will be set to 00:00
                    new DateTimeFormatterBuilder()
                            .appendPattern("d-M[-yyyy]")
                            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                            .parseDefaulting(ChronoField.YEAR, now.getYear())
                            .toFormatter(),

                    // Full date & time, e.g. 24-12-2014 18:30. Date is optional
                    new DateTimeFormatterBuilder()
                            .appendPattern("[d-M[-yyyy] ]H:mm")
                            .parseDefaulting(ChronoField.DAY_OF_MONTH, now.getDayOfMonth())
                            .parseDefaulting(ChronoField.MONTH_OF_YEAR, now.getMonthValue())
                            .parseDefaulting(ChronoField.YEAR, now.getYear())
                            .toFormatter(),

                    // Full date & time (short year), e.g. 24-12-14 18:30. Date is optional
                    new DateTimeFormatterBuilder()
                            .appendPattern("[d-M[-yy] ]H:mm")
                            .parseDefaulting(ChronoField.DAY_OF_MONTH, now.getDayOfMonth())
                            .parseDefaulting(ChronoField.MONTH_OF_YEAR, now.getMonthValue())
                            .parseDefaulting(ChronoField.YEAR, now.getYear())
                            .toFormatter(),

                    // Full date & time (am/pm), e.g. 24-12-2014 9:45am. Date & minute are optional
                    new DateTimeFormatterBuilder()
                            .appendPattern("[d-M[-yyyy] ]K[:mm][ ]a")
                            .parseDefaulting(ChronoField.DAY_OF_MONTH, now.getDayOfMonth())
                            .parseDefaulting(ChronoField.MONTH_OF_YEAR, now.getMonthValue())
                            .parseDefaulting(ChronoField.YEAR, now.getYear())
                            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                            .toFormatter(),

                    // Full date & time (am/pm) & short year, e.g. 24-12-14 9:45am. Date & minute are optional
                    new DateTimeFormatterBuilder()
                            .appendPattern("[d-M[-yy] ]K[:mm][ ]a")
                            .parseDefaulting(ChronoField.DAY_OF_MONTH, now.getDayOfMonth())
                            .parseDefaulting(ChronoField.MONTH_OF_YEAR, now.getMonthValue())
                            .parseDefaulting(ChronoField.YEAR, now.getYear())
                            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                            .toFormatter(),

            };

        }

        return formatters;
    }

    /**
     * Check whether a command matches any recognizable overload.
     *
     * @param command The command whose signature is being checked
     * @return true if the command signature is valid.
     */
    public static ParsingResult checkCommandValidity(TareasCommand command) {
        CommandType type = command.getType();

        if (type == CommandType.UNKNOWN_COMMAND) {
            Log.i(TAG, "unknown command: " + command.getPrimaryKey());
            return new ParsingResult(ParsingStatus.UNKNOWN_COMMAND, command.getPrimaryKey());
        }

        if (type.isPrimaryArgumentPresent() && command.getPrimaryArgument().equals("")) {
            // if the primary argument is supposed to be present but missing
            Log.i(TAG, "missing primary argument");
            return new ParsingResult(ParsingStatus.MISSING_PRIMARY_ARGUMENT);

        } else if (!type.isPrimaryArgumentPresent() && !command.getPrimaryArgument().equals("")) {
            // if the primary argument is present but it shouldn't be there
            Log.i(TAG, "unexpected primary argument");
            return new ParsingResult(ParsingStatus.UNEXPECTED_PRIMARY_ARGUMENT, command.getPrimaryArgument());
        }

        if (type.isCombinationAllowed()) { // if combination is allowed, check whether the keywords are valid
            HashSet<String> valid_keywords = type.getOverloadKeywordList().get(0);

            for (String keyword : command.getSecondaryKeys()) {
                if (!valid_keywords.contains(keyword)) {
                    return new ParsingResult(ParsingStatus.UNKNOWN_KEYWORD, keyword);
                }
            }

            Log.i(TAG, "success: " + command);
            return new ParsingResult(ParsingStatus.SUCCESS);

        } else { // if not, check whether the command matches an overloading signature, i.e. the set of keywords matches
            for (HashSet<String> overload : type.getOverloadKeywordList()) {
                if (overload.equals(command.getSecondaryKeys())) {
                    Log.i(TAG, "success: " + command);
                    return new ParsingResult(ParsingStatus.SUCCESS);
                }
            }

            Log.i(TAG, "signature doesn't matched");
            return new ParsingResult(ParsingStatus.SIGNATURE_NOT_MATCHED);
        }
    }

/*    public static LocalDateTime getDateTimeFromString(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yy H:mm");
        return LocalDateTime.parse(input, formatter);
    }*/

    public static LocalDateTime getDateTimeFromString(String input) {

        for (DateTimeFormatter formatter : getDateTimeFormatters()) {
            try {
                return LocalDateTime.parse(input.toUpperCase(), formatter);
            } catch (DateTimeParseException e) {
                Log.e(TAG, String.format("Parsing date failed. %s", e.getMessage()));
            }
        }

        return null;
    }

    public static void main(String[] args) {

        System.out.println(getDateTimeFromString("24-12 9pm"));
    }
}
