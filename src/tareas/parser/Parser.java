package tareas.parser;

import tareas.common.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class is obviously a parser.
 *
 * @author Kent
 */

public class Parser {
    private static String TAG = "tareas/parser";

    private static ArrayList<DateTimeFormatter> formatters;

    /**
     * This function returns an array of DateTimeFormatter of different patterns.
     *
     * @return an array of DateTimeFormatter
     */
    private static ArrayList<DateTimeFormatter> getDateTimeFormatters() {
        if (formatters == null) {
            String[] patterns = new String[] {
                    "d-M[-yyyy]",
                    "d-M[-yy]",
                    "[d-M[-yyyy] ]H:mm",
                    "[d-M[-yy] ]H:mm",
                    "[d-M[-yyyy] ]K[:mm]a",
                    "[d-M[-yy] ]K[:mm]a"
            };

            LocalDateTime now = LocalDateTime.now();

            formatters = new ArrayList<>();

            for (String pattern : patterns) {
                DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder()
                        .appendPattern(pattern)
                        .parseCaseInsensitive()
                        .parseDefaulting(ChronoField.YEAR, now.getYear())
                        .parseDefaulting(ChronoField.MONTH_OF_YEAR, now.getMonthValue())
                        .parseDefaulting(ChronoField.DAY_OF_MONTH, now.getDayOfMonth())
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0);

                if (!(pattern.contains("H") || pattern.contains("K"))) {
                    builder.parseDefaulting(ChronoField.HOUR_OF_DAY, 0);
                }

                formatters.add(builder.toFormatter());
            }
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

    public static LocalDateTime getDateTimeFromString(String input) {
        input = input.toLowerCase();

        LocalDate localDateConstant = null;
        String dateConstantString = DateConstant.scanString(input);

        if (dateConstantString != null) {
            localDateConstant = DateConstant.fromString(dateConstantString).toLocalDate();
            input = input.replace(dateConstantString, "").trim(); // remove the date constant from input

            // if he string is empty after removing the date constant, i.e. no time info, return the date
            if (input.equals("")) return LocalDateTime.of(localDateConstant, LocalTime.MIN);
        }

        String lastError = "";

        for (DateTimeFormatter formatter : getDateTimeFormatters()) {
            try {
                LocalDateTime result = LocalDateTime.parse(input.toUpperCase(), formatter);

                if (localDateConstant != null) {
                    return result
                            .withYear(localDateConstant.getYear())
                            .withMonth(localDateConstant.getMonthValue())
                            .withDayOfMonth(localDateConstant.getDayOfMonth());
                } else {
                    return result;
                }

            } catch (DateTimeParseException e) {
                //Log.e(TAG, String.format("Parsing date '%s' failed. %s", input, e.getMessage()));
                lastError = e.getMessage();
            }
        }

        Log.e(TAG, String.format("Parsing date '%s' failed.\n%s", input, lastError));
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getDateTimeFromString("sunday 8:10pm"));
    }
}
