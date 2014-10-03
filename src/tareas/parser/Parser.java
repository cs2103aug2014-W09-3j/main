package tareas.parser;

/**
 * This class manipulates text in a text file, allowing a user to:

 * @author Kent
 */

public class Parser {

    /**
     * Extract the keyword and arguments from a String command.
     *
     * @param command The command to be analyzed.
     * @return The command as a TextBuddyCommand object
     */
    public static Command analyzeCommand(String command) {
        // Normalize command
        command = command.trim();

        // Split command into two parts, keyword and arguments
        String[] keywordWithArguments = command.split(" ", 2);

        String keyword = keywordWithArguments[0];
        CommandType type = CommandType.fromKeyword(keyword);

        String[] arguments = new String[0];

        // if the command is known and there are arguments after the keyword
        if (type != CommandType.UNKNOWN_COMMAND && keywordWithArguments.length > 1) {
            arguments = keywordWithArguments[1].split(" ", type.getArgumentCount());  // split them into separate strings
        }

        return new Command(type, arguments);
    }

    /**
     * Execute a command.
     *
     * @param command The command, as a TextBuddyCommand
     * @return Output of command execution
     */
    public String executeCommand(Command command) {
        CommandType type = command.getType();
        String[] arguments = command.getArguments();

        // if number of arguments found is different from expectation, it's an error
        if (arguments.length != type.getArgumentCount()) {
            return String.format("", type.getCommand());
        }

            switch (type) {


            default:
                return "";
        }
    }

//endregion

    public static void main(String[] args) {

    }

}
