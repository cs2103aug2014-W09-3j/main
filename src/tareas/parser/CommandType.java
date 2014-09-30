package tareas.parser;

import java.util.ArrayList;

/**
 * [Description]
 * <p/>
 * Created on Sep 20, 2014.
 *
 * @author Kent
 */

public enum CommandType {
    ADD_COMMAND("add", 1),
    DISPLAY_COMMAND("display", 0),
    DELETE_COMMAND("delete", 1),
    CLEAR_COMMAND("clear", 0),
    SAVE_COMMAND("save", 0),
    HELP_COMMAND("help", 0),
    EXIT_COMMAND("exit", 0),
    SORT_COMMAND("sort", 1),
    SEARCH_COMMAND("search", 1),
    UNKNOWN_COMMAND("", 0);

    private final String mCommand;
    private final int mArgumentCount;

    CommandType(String command, int argumentCount) {
        this.mCommand = command;
        this.mArgumentCount = argumentCount;
    }

    /**
     * Retrieve the keyword for the command.
     *
     * @return the keyword for the command.
     */
    public String getCommand() {
        return mCommand;
    }

    /**
     * Retrieve the number of arguments for this command.
     *
     * @return the number of arguments for this command.
     */
    public int getArgumentCount() {
        return mArgumentCount;
    }

    public boolean equals(String command) {
        return command.toLowerCase().equals(getCommand());
    }

    public static CommandType fromKeyword(String keyword) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.equals(keyword)) {
                return commandType;
            }
        }

        return CommandType.UNKNOWN_COMMAND;
    }

    public static ArrayList<String> getAllCommands() {
        ArrayList<String> ret = new ArrayList<>();
        for (CommandType commandType : CommandType.values()) {
            if (commandType == UNKNOWN_COMMAND) continue;
            ret.add(commandType.getCommand());
        }

        return ret;
    }

}
