package tareas.common;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * [Description]
 * <p/>
 * Created on Oct 15, 2014.
 *
 * @author Kent
 */

public class Log {
    private final static Logger LOGGER = Logger.getLogger("TAREAS");

    public static void i(String tag, String msg) {
        if (Constants.LOGGING_ENABLED)
            LOGGER.logp(Level.INFO, tag, "", msg);
    }

    public static void e(String tag, String msg) {
        if (Constants.LOGGING_ENABLED)
            LOGGER.logp(Level.SEVERE, tag, "", msg);
    }

    public static void w(String tag, String msg) {
        if (Constants.LOGGING_ENABLED)
            LOGGER.logp(Level.WARNING, tag, "", msg);
    }
}
