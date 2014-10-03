package tareas.parser;

import tareas.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * A TareasCommand has a type and a map of secondary arguments.
 * <p/>
 * The arguments can be retrieved as a list of CommandArgument using getSecondaryArgumentList().
 * <p/>
 * Use fromString(String command) to convert a String to a TareasCommand.
 * Created on Sep 20, 2014.
 *
 * @author Kent
 */

public class TareasCommand {
    private static String PRIMARY_ARGUMENT_KEY = "!primary";

    public static class CommandArgument {
        private String mKey;
        private String mValue;

        public CommandArgument(String key, String argument) {
            this.mKey = key;
            this.mValue = argument;
        }

        public String getKey() {
            return mKey;
        }

        public String getValue() {
            return mValue;
        }
    }

    private CommandType mType;
    private HashMap<String, String> mSecondaryArguments;

    //region Constructors

    public TareasCommand(CommandType type) {
        this.mType = type;
        this.mSecondaryArguments = new HashMap<>();
    }

    private TareasCommand() {
        this.mSecondaryArguments = new HashMap<>();
    }

    //endregion

    //region Getters & Setters

    public CommandType getType() {
        return mType;
    }

    private void setType(CommandType type) {
        this.mType = type;
    }


    public boolean hasKey(String key) {
        return mSecondaryArguments.containsKey(key);
    }

    public String getArgument(String key) {
        return mSecondaryArguments.get(key);
    }

    public void putArgument(String key, String value) {
        mSecondaryArguments.put(key, value);
    }

    public void putArgument(CommandArgument argument) {
        putArgument(argument.getKey(), argument.getValue());
    }

    public String getPrimaryArgument() {
        return getArgument(PRIMARY_ARGUMENT_KEY);
    }

    /**
     * Retrieve the set of secondary keywords.
     *
     * @return
     */
    public HashSet<String> getSecondaryKeys() {
        HashSet<String> keys = new HashSet<>();
        keys.addAll(mSecondaryArguments.keySet());
        keys.remove(PRIMARY_ARGUMENT_KEY);
        return keys;
    }

    public ArrayList<CommandArgument> getSecondaryArgumentList() {
        ArrayList<CommandArgument> ret = new ArrayList<>();

        for (String key : mSecondaryArguments.keySet()) {
            if (key.equals(PRIMARY_ARGUMENT_KEY)) continue;

            String value = getArgument(key);
            ret.add(new CommandArgument(key, value));
        }

        return ret;
    }

    //endregion

    /**
     * Convert a String command into the respective TareasCommand.
     *
     * @param command the string command to be converted
     * @return the converted TareasCommand.
     */
    public static TareasCommand fromString(String command) {
        command = command.trim();

        // if the command doesn't start with the delimiter (i.e. it's a special command),
        // add the primary keyword back to normalize the command.
        if (!command.startsWith(Constants.COMMAND_DELIMITER)) {
            command = Constants.COMMAND_DELIMITER +
                    CommandType.getSpecialCommandType().getPrimaryKeyword() +
                    " " + command;
        }

        // split the command into pairs of keyword and value.
        String[] keywordsAndValues = command.split(Constants.COMMAND_DELIMITER);
        String primaryKeyword = null;

        TareasCommand ret = new TareasCommand();

        for (String kv : keywordsAndValues) {
            if (kv.length() == 0) continue;

            String[] kv_array = kv.split("\\s+", 2); // split into keyword and argument
            String key = kv_array[0];
            String value = kv_array.length == 2 ? kv_array[1].trim() : "";

            if (primaryKeyword == null) { // the first key-value pair is the primary argument.
                primaryKeyword = kv_array[0];
                ret.putArgument(PRIMARY_ARGUMENT_KEY, value);
            } else { //subsequent pairs are secondary arguments
                ret.putArgument(key, value);
            }
        }

        ret.setType(CommandType.fromPrimaryKeyword(primaryKeyword));
        return ret;
    }

    /**
     * Convert TareasCommand to a String for debugging purposes.
     *
     * @return a String representation of the TareasCommand.
     */
    @Override
    public String toString() {
        String ret = String.format("'%1$s' <%2$s>",
                this.getType(), this.getPrimaryArgument());

        for (CommandArgument ca : this.getSecondaryArgumentList()) {
            ret += String.format(", '%1$s' <%2$s>", ca.getKey(), ca.getValue());
        }

        return ret;
    }

}
