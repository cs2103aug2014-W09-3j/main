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
                "buy ham",
                "meeting with 2103 group -from 22/09/2014 1200 -to 22/09/2014 2200",
                "complete user guide  -by 22/09/2014 1500",
                "gym -recurring daily from 0800 to 1000",
                "-edit 1 -des buy watermelon -start 22/09/2014 1200 -end 22/09/2014 1300",
                "-edit 3 -deadline 11/12/2015",
                "-delete 3",
                "-undo",
                "-font Helvetica -size 12",
                "",
                "-what hello world", // unknown command
                "buy ham -from 22/09/2014", // missing -to
                "workout -recurrin daily", // recurring spelled wrongly
                "-edit -des something", // missing primary argument
                "-delete", // same as above
                "-delete 3 -random" // unexpected argument 'random'

        };

        for (String test : tests) {
            if (test.equals("")) {
                System.out.println();
                continue;
            }

            TareasCommand command = TareasCommand.fromString(test);
            String valid = isCommandSignatureValid(command) ? "Valid " : "Invalid ";
            System.out.println(valid + command);
        }
    }

    /**
     * Check whether a command matches any recognizable overload.
     *
     * @param command The command whose signature is being checked
     * @return true if the command signature is valid.
     */
    public static boolean isCommandSignatureValid(TareasCommand command) {
        CommandType type = command.getType();

        if (type == CommandType.UNKNOWN_COMMAND) return false;

        // return false if the primary argument is supposed to be present but missing or vice versa.
        if (type.isPrimaryArgumentPresent() ^ command.getPrimaryArgument().length() > 0) return false;

        if (type.isCombinationAllowed()) { // if combination is allowed, check whether the keywords are valid
            HashSet<String> valid_keywords = type.getOverloadKeywordList().get(0);

            for (String keyword : command.getSecondaryKeys()) {
                if (!valid_keywords.contains(keyword)) {
                    return false;
                }
            }

            return true;

        } else { // if not, check whether the command matches an overloading signature, i.e. the set of keywords matches
            for (HashSet<String> overload : type.getOverloadKeywordList()) {
                if (overload.equals(command.getSecondaryKeys())) {
                    return true;
                }
            }

            return false;
        }
    }


}
