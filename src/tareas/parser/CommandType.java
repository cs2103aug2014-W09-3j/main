package tareas.parser;

/**
 * A typical format of a Tareas command:
 * -command_name [argument_for_command] -keyword1 [argument_for_keyword1] -keyword2 [argument_for_keyword2] ...
 * <p/>
 * [argument_for_command] is the primary argument.
 * "-keyword1 [argument_for_keyword1] -keyword2 [argument_for_keyword2] ..." are secondary arguments and their keywords.
 * <p/>
 * The 1st constructor (String, boolean, String[]...) will initialize:
 * 1. String       : The command's name
 * 2. boolean      : Whether a combination of the keywords can be used together (e.g. edit command)
 * 3. String[]...  : The signatures of the command's possible overloads, each includes the keywords required.
 * (String[]... is a varargs, i.e. an array contains an arbitrary number of String[])
 * <p/>
 * The 2nd constructor (String, String[]...) is similar to the previous one, except isCombinationAllowed is set to false
 * by default.
 * <p/>
 * The 3rd constructor (String, boolean) assumes that the command has no secondary arguments, and the boolean value
 * decides whether the primary command is present or not. For example, the -undo command has no argument at all.
 *
 * @author Kent
 */

/**
 * An enum storing command types and their overloads.
 * <p/>
 */

public enum CommandType {


    //region Command Definitions

    ADD_COMMAND(
            "", // no need to type "-add" at the beginning
            new String[] {"from", "to"},
            new String[] {"by"},
            new String[] {"recurring"}
    ),

    EDIT_COMMAND(
            "edit",
            true,
            new String[] {"des", "start", "end", "deadline"}
    ),

    DELETE_COMMAND("delete"),

    SEARCH_COMMAND("search"),

    DONE_COMMAND("done"),

    UNDO_COMMAND("undo", false),

    REDO_COMMAND("redo", false),

    POSTPONE_COMMAND(
            "postpone",
            new String[] {"to"},
            new String[] {"by"}
    ),

    VIEW_COMMAND("view"),

    PRIORITIZE_COMMAND("prioritize"),

    CATEGORIZE_COMMAND(
            "categorize",
            new String[] {"to"}
    ),

    REMIND_COMMAND(
            "remind",
            new String[] {"on"}
    ),

    BACKUP_COMMAND("backup", false),

    MUTE_COMMAND(
            "mute",
            new String[] {"to"}
    ),

    FONT_COMMAND(
            "font",
            new String[] {},
            new String[] {"size"}
    ),

    COLOR_COMMAND(
            "color",
            new String[] {"with"}
    ),

    UNKNOWN_COMMAND(null);

    //endregion

    private final String mCommand;
    private final String[][] mKeywords;
    private final boolean mCombinationAllowed;
    private final boolean mPrimaryArgumentPresent;

    CommandType(String command, boolean combinationAllowed, String[]... keywords) {
        this.mCommand = command;
        this.mCombinationAllowed = combinationAllowed;
        this.mKeywords = keywords;
        this.mPrimaryArgumentPresent = true;
    }

    CommandType(String command, String[]... keywords) {
        this(command, false, keywords);
    }

    CommandType(String command, boolean commandHasArgument) {
        this.mCommand = command;
        this.mCombinationAllowed = false;
        this.mKeywords = null;
        this.mPrimaryArgumentPresent = commandHasArgument;
    }

    public String getCommand() {
        return mCommand;
    }

    public String[][] getOverloadKeywords() {
        return mKeywords;
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

    public boolean isPrimaryArgumentPresent() {
        return mPrimaryArgumentPresent;
    }

    public boolean isCombinationAllowed() {
        return mCombinationAllowed;
    }


}
