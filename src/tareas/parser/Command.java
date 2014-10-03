package tareas.parser;

import java.util.HashMap;

/**
 * A class representing a command.
 * <p/>
 * Created on Sep 20, 2014.
 *
 * @author Kent
 */

public class Command {
    private CommandType type;
    private HashMap<String, String> arguments;

    public Command(CommandType type) {
        this.type = type;
        this.arguments = new HashMap<>();
    }

    public CommandType getType() {
        return type;
    }

    public String putArgument(String key, String value) {
        arguments.put(key, value);
    }

    public String getArgument(String key) {
        return arguments.get(key);
    }

    public String

}
