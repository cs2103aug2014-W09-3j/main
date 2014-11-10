package tareas.parser;

/**
 * A typical format of a Tareas command:
 *      -command_keyword [argument_for_command] -keyword1 [argument_for_keyword1] -keyword2 [argument_for_keyword2] ...
 * <p/>
 * [argument_for_command] is the primary argument.
 * "-keyword1 [argument_for_keyword1] -keyword2 [argument_for_keyword2] ..." are secondary arguments and their keywords.
 * <p/>
 * <p/>
 * The 1st constructor (String, boolean, String[]...) will initialize:
 *      1. String       : The primary keyword, which if present is always the first word of the command.
 *      2. boolean      : Whether a combination of the secondary keywords can be used together (e.g. edit command).
 *      3. String[]...  : The signatures of the command's possible overloads, each includes the keywords required.
 *                        (String[]... is a varargs, i.e. an array contains an arbitrary number of String[])
 * <p/>
 * The 2nd constructor (String, String[]...) is similar to the previous one, except isCombinationAllowed is set to false
 * by default.
 * <p/>
 * The 3rd constructor (String, int) assumes that the command has no secondary arguments, and if the int value is 0, the
 * command does not expect a primary argument either. For example, the -undo command has no argument at all.
 *
 * int rather than boolean is chosen for the second parameter's type to differentiate this constructor from the 1st one,
 * in which String[]... can sometimes be omitted, which creates confusion.
 *
 * The primary keyword of one pre-determined special command type can be ignored, e.g. the "add" command. This command
 * type can be retrieved by calling getSpecialCommandType().
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

//@author A0093934W

/**
 * An enum storing command types and their overloads.
 * <p>
 */

public enum CommandType {

    //region Command Definitions


    // the add command has 5 overloads, one of which does not require secondary arguments
    ADD_COMMAND(
            "add",
            new String[] {}, // support for floating tasks
            new String[] {"tag"},
            new String[] {"from", "to"},
            new String[] {"by"},
            new String[] {"recurring"}
    ),


    // the edit command accepts a combination of keywords
    EDIT_COMMAND(
            "edit",
            true,
            new String[] {"des", "start", "end", "deadline"}
    ),


    // the delete command requires one primary argument and accepts no secondary argument.
    DELETE_COMMAND("delete"),


    SEARCH_COMMAND("search"),


    DETAILED_COMMAND("detailed"),


    TAG_COMMAND(
            "tag",
            new String[] {"with"}
    ),


    DONE_COMMAND("done"),


    // the undo command does not accept any argument
    UNDO_COMMAND("undo", 0),


    // the redo command does not accept any argument
    REDO_COMMAND("redo", 0),


    POSTPONE_COMMAND(
            "postpone",
            new String[] {"to"},
            new String[] {"by"}
    ),

    VIEW_COMMAND("view"),

    PRIORITIZE_COMMAND("prioritize"),

    REMIND_COMMAND(
            "remind",
            new String[] {"on"}
    ),

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

    private final String mPrimaryKeyword;
    private final ArrayList<HashSet<String>> mKeywords;
    private final boolean mCombinationAllowed;
    private final boolean mPrimaryArgumentPresent;

    //region Constructors


    CommandType(String primaryKeyword, boolean combinationAllowed, String[]... keywords) {
        this.mPrimaryKeyword = primaryKeyword;
        this.mCombinationAllowed = combinationAllowed;
        this.mPrimaryArgumentPresent = true;

        this.mKeywords = new ArrayList<>();

        if (keywords.length == 0) {
            this.mKeywords.add(new HashSet<>());
        } else {
            if (mCombinationAllowed) { // if allowed, merge all the keywords into one set
                HashSet<String> temp = new HashSet<>();
                for (String[] overload : keywords) {
                    temp.addAll(Arrays.asList(overload));
                }
                this.mKeywords.add(temp);

            } else { // if not, keep the keywords in separate sets for respective overloads
                for (String[] overload : keywords) {
                    this.mKeywords.add(new HashSet<>(Arrays.asList(overload)));
                }
            }
        }
    }


    CommandType(String primaryKeyword, String[]... keywords) {
        this(primaryKeyword, false, keywords);
    }


    CommandType(String primaryKeyword, int commandHasArgument) {
        this.mPrimaryKeyword = primaryKeyword;
        this.mCombinationAllowed = false;
        this.mPrimaryArgumentPresent = commandHasArgument > 0;

        // no secondary keyword if this constructor is used.
        this.mKeywords = new ArrayList<>();
        this.mKeywords.add(new HashSet<>());
    }

    //endregion

    //region Getters & Setters

    
    public String getPrimaryKeyword() {
        return mPrimaryKeyword;
    }


    public ArrayList<HashSet<String>> getOverloadKeywordList() {
        return mKeywords;
    }

    
    public boolean isPrimaryArgumentPresent() {
        return mPrimaryArgumentPresent;
    }

    
    public boolean isCombinationAllowed() {
        return mCombinationAllowed;
    }

    //endregion

    //region Others


    public boolean equals(String keyword) {
        return keyword.toLowerCase().equals(getPrimaryKeyword());
    }


    public static CommandType getSpecialCommandType() {
        return ADD_COMMAND;
    }


    public static CommandType fromPrimaryKeyword(String keyword) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.equals(keyword)) {
                return commandType;
            }
        }

        return CommandType.UNKNOWN_COMMAND;
    }

    //endregion

}