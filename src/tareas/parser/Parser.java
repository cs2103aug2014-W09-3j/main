package tareas.parser;

import java.util.HashSet;

/**
 * This class is obviously a parser.
 *
 * @author Kent
 */

public class Parser {

    public static void main(String[] args) {
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
            return new ParsingResult(ParsingStatus.UNKNOWN_COMMAND, command.getPrimaryKey());
        }

        if (type.isPrimaryArgumentPresent() && command.getPrimaryArgument().equals("")) {
            // if the primary argument is supposed to be present but missing
            return new ParsingResult(ParsingStatus.MISSING_PRIMARY_ARGUMENT);
        } else if (!type.isPrimaryArgumentPresent() && !command.getPrimaryArgument().equals("")) {
            // if the primary argument is present but it shouldn't be there
            return new ParsingResult(ParsingStatus.UNEXPECTED_PRIMARY_ARGUMENT, command.getPrimaryArgument());
        }

        if (type.isCombinationAllowed()) { // if combination is allowed, check whether the keywords are valid
            HashSet<String> valid_keywords = type.getOverloadKeywordList().get(0);

            for (String keyword : command.getSecondaryKeys()) {
                if (!valid_keywords.contains(keyword)) {
                    return new ParsingResult(ParsingStatus.UNKNOWN_KEYWORD, keyword);
                }
            }

            return new ParsingResult(ParsingStatus.SUCCESS);

        } else { // if not, check whether the command matches an overloading signature, i.e. the set of keywords matches
            for (HashSet<String> overload : type.getOverloadKeywordList()) {
                if (overload.equals(command.getSecondaryKeys())) {
                    return new ParsingResult(ParsingStatus.SUCCESS);
                }
            }

            return new ParsingResult(ParsingStatus.SIGNATURE_NOT_MATCHED);
        }
    }


}
