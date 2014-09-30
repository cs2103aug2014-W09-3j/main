package tareas.parser;

/**
 * A class representing a command.
 * <p/>
 * Created on Sep 20, 2014.
 *
 * @author Kent
 */

public class TextBuddyCommand {
    private CommandType type;
    private String[] args;

    public TextBuddyCommand(CommandType type, String[] args) {
        this.type = type;
        this.args = args;
    }

    public CommandType getType() {
        return type;
    }

    public String[] getArguments() {
        return args;
    }

    /**
     * Extract the keyword and arguments from a String command.
     *
     * @param command The command to be analyzed.
     * @return The command as a TextBuddyCommand object
     */
    public static TextBuddyCommand analyzeCommand(String command) {
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

        return new TextBuddyCommand(type, arguments);
    }

}
