package tareas.parser;

import tareas.common.Constants;
import tareas.common.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

/**
 * This class is obviously a parser.
 *
 * @author Kent
 */

public class Parser {
    private static String TAG = "tareas/parser";

    public static void main1(String[] args) {
        Constants.LOGGING_ENABLED = false;

        String[] tests = {
                //"buy ham",
                //"meeting with 2103 group -from 22/09/2014 1200 -to 22/09/2014 2200",
                //"complete user guide  -by 22/09/2014 1500",
                //"gym -recurring daily from 0800 to 1000",
                //"-edit 1 -des buy watermelon -start 22/09/2014 1200 -end 22/09/2014 1300",
                //"-edit 3 -deadline 11/12/2015",
                //"-delete 3",
                //"-undo",
                //"-font Helvetica -size 12",
                //"",
                "-what hello world", // unknown command
                "buy ham -from 22/09/2014", // missing -to
                "-edit 1 -dedline daily", // deadline spelled wrongly
                "-edit -des something", // missing primary argument
                "-delete", // same as above
                "-redo xxx", // unexpected primary argument
                "-delete 3 -random" // unexpected argument 'random'

        };

        for (String test : tests) {
            if (test.equals("")) {
                System.out.println();
                continue;
            }

            // convert string test to TareasCommand
            TareasCommand command = TareasCommand.fromString(test);

            // get the parsing result
            ParsingResult result = checkCommandValidity(command);

            if (result.isSuccessful()) {
                System.out.println("VALID COMMAND: " + command);
            } else {
                // use result.getStatus to get the error, result.getExtra will contain extra info (if any)
                // associated with the error
                System.out.println(String.format("%s: %s\n%s\n", result.getStatus(), test, result.getExtra()));
            }
        }

        Constants.LOGGING_ENABLED = true;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yy H:mm");
        return LocalDateTime.parse(input, formatter);
    }

    /*public static LocalDateTime getDateTimeFromString(String input) {
        LocalDateTime now = LocalDateTime.now();


        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                //.appendPattern("d-M-yy K[:mm]a")
                //.appendPattern("d-M-yyyy K[:mm]a")


                .parseDefaulting(ChronoField.DAY_OF_MONTH, now.getDayOfMonth())
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, now.getMonthValue())
                .parseDefaulting(ChronoField.YEAR, now.getYear())

                .append(DateTimeFormatter.ofPattern("[d-M-yy ]K[:mm]a"))
                        //append(DateTimeFormatter.ofPattern("d-M-yyyy K[:mm]a"))
//                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
//                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 30)


                .toFormatter();


        return LocalDateTime.parse(input.toUpperCase(), formatter);
    }*/

    public static void main(String[] args) {
        try {
            System.out.println(getDateTimeFromString("14-03-17 4:10pm"));
            //System.out.println(LocalTime.parse("4AM", DateTimeFormatter.ofPattern("Ha")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
