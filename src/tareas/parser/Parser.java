package tareas.parser;

import tareas.common.Constants;
import tareas.common.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class is obviously a parser.
 */

//@author A0093934W
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
                    "[d-M[-yyyy] ]h[:mm]a",
                    "[d-M[-yy] ]h[:mm]a"
            };

            LocalDateTime now = LocalDateTime.now();

            formatters = new ArrayList<>();

            for (String pattern : patterns) {
                DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder()
                        .appendPattern(pattern)
                        .parseDefaulting(ChronoField.YEAR, now.getYear())
                        .parseDefaulting(ChronoField.MONTH_OF_YEAR, now.getMonthValue())
                        .parseDefaulting(ChronoField.DAY_OF_MONTH, now.getDayOfMonth())
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0);


                if (!(pattern.contains("H") || pattern.contains("h"))) {
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
        Log.i(TAG, "Parsing command: " + command.toString());

        CommandType type = command.getType();

        if (type == CommandType.UNKNOWN_COMMAND) {
            Log.e(TAG, Constants.LOG_SECOND_LEVEL + "Unknown command: " + command.getPrimaryKey());
            return new ParsingResult(ParsingStatus.UNKNOWN_COMMAND, command.getPrimaryKey());
        }

        if (type.isPrimaryArgumentPresent() && command.getPrimaryArgument().equals("")) {
            // if the primary argument is supposed to be present but missing
            Log.e(TAG, Constants.LOG_SECOND_LEVEL + "Missing primary argument");
            return new ParsingResult(ParsingStatus.MISSING_PRIMARY_ARGUMENT);

        } else if (!type.isPrimaryArgumentPresent() && !command.getPrimaryArgument().equals("")) {
            // if the primary argument is present but it shouldn't be there
            Log.e(TAG, Constants.LOG_SECOND_LEVEL + "Unexpected primary argument");
            return new ParsingResult(ParsingStatus.UNEXPECTED_PRIMARY_ARGUMENT, command.getPrimaryArgument());
        }

        if (type.isCombinationAllowed()) { // if combination is allowed, check whether the keywords are valid
            HashSet<String> valid_keywords = type.getOverloadKeywordList().get(0);

            for (String keyword : command.getSecondaryKeys()) {
                if (!valid_keywords.contains(keyword)) {
                    return new ParsingResult(ParsingStatus.UNKNOWN_KEYWORD, keyword);
                }
            }

            Log.i(TAG, Constants.LOG_SECOND_LEVEL + "Command is valid.");
            return new ParsingResult(ParsingStatus.SUCCESS);

        } else { // if not, check whether the command matches an overloading signature, i.e. the set of keywords matches
            for (HashSet<String> overload : type.getOverloadKeywordList()) {
                if (overload.equals(command.getSecondaryKeys())) {
                    Log.i(TAG, Constants.LOG_SECOND_LEVEL + "Command is valid.");
                    return new ParsingResult(ParsingStatus.SUCCESS);
                }
            }

            Log.e(TAG, Constants.LOG_SECOND_LEVEL + "Signature doesn't matched");
            return new ParsingResult(ParsingStatus.SIGNATURE_NOT_MATCHED);
        }
    }

    /**
     * Converts a String to a LocalDateTime.
     *
     * @param input the String input
     * @return a LocalDateTime if the input is a valid date/time, null otherwise
     */
    public static LocalDateTime getDateTimeFromString(String input) {
        Log.i(TAG, "Parsing date: " + input);

        input = input.toLowerCase();

        LocalDate localDateConstant = null;
        String dateConstantString = DateConstant.scanString(input);

        if (dateConstantString != null) {
            localDateConstant = DateConstant.fromString(dateConstantString).toLocalDate();
            input = input.replace(dateConstantString, "").trim(); // remove the date constant from input

            // if the string is empty after removing the date constant, i.e. no time info, return the date
            if (input.equals("")) {
                LocalDateTime result = LocalDateTime.of(localDateConstant, LocalTime.MIN);
                Log.i(TAG, String.format(Constants.LOG_SECOND_LEVEL + "Parsed successfully: " + result.toString()));
                return result;
            }
        }

        String lastError = "";

        for (DateTimeFormatter formatter : getDateTimeFormatters()) {
            try {
                LocalDateTime result = LocalDateTime.parse(input.toUpperCase(), formatter);

                if (localDateConstant != null) {
                    result = result
                            .withYear(localDateConstant.getYear())
                            .withMonth(localDateConstant.getMonthValue())
                            .withDayOfMonth(localDateConstant.getDayOfMonth());
                }

                Log.i(TAG, String.format(Constants.LOG_SECOND_LEVEL + "Parsed successfully: " + result.toString()));
                return result;

            } catch (DateTimeParseException e) {
                //Log.e(TAG, String.format("Parsing date '%s' failed. %s", input, e.getMessage()));
                lastError = e.getMessage();
            }
        }

        Log.e(TAG, Constants.LOG_SECOND_LEVEL + "Parsing failed.");
        Log.e(TAG, Constants.LOG_SECOND_LEVEL + lastError);
        return null;
    }

    /**
     * Converts a LocalDateTime to a String for display
     *
     * @param dateTime the LocalDateTime
     * @return a String representation of the dateTime
     */

    public static String getStringFromDateTime(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();

        StringBuilder builder = new StringBuilder();
        int dateDiff = Period.between(LocalDate.now(), date).getDays();

        if (dateDiff >= -1 && dateDiff <= 1) {
            builder.append(
                    DateConstant.fromTypeOffset(DateConstant.DateConstantType.RELATIVE_DATE, dateDiff)
                            .toString()
            );
        } else if (dateDiff > 1 && dateDiff <= 7) {
            builder.append(
                    DateConstant.fromTypeOffset(DateConstant.DateConstantType.DAY_OF_THE_WEEK, date.getDayOfWeek().getValue())
            );
        } else {
            builder.append(date.format(DateTimeFormatter.ofPattern("MMM dd")));
        }

        if (time.getHour() != 0 || time.getMinute() != 0) {
            builder.append(time.format(
                    DateTimeFormatter.ofPattern(time.getMinute() > 0 ? ", h:mma" : ", ha")
            ).toLowerCase());

        }

        return builder.toString();
    }

    /**
     * Generate commands for demo
     *
     * @return an ArrayList of String commands
     */
    public static ArrayList<String> generateCommands() {
        ArrayList<String> ret = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            ret.add("Finish homework " + i + " /by today");
            if (i % 5 == 0) {
                ret.add("/done 1");
            }

            if (i % 6 == 0) {
                ret.add("/prioritize 1");
            }
        }

        ret.add("Relax, because it’s friday #TGIF /by tomorrow");
        ret.add("Buy flowers /by 24-12 6pm");
        ret.add("Go to girlfriend’s house /by 24-12 8pm");
        ret.add("Have a wonderful date with girlfriend /by 24-12 9pm");

        for (int i = 0; i < 20; i++) {
            if (i % 4 == 0) {
                ret.add("Important work " + i + " /by yesterday");
            } else {
                ret.add("Important work " + i + " /by today");
            }

        }

        return ret;
    }

}
