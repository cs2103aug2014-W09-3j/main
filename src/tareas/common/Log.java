package tareas.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * A class used to log messages with customized format.
 * <p/>
 * Created on Oct 15, 2014.
 *
 * @author Kent
 */

public class Log {
    private static Logger LOGGER;
    private final static SimpleDateFormat TareasLogDateFormat = new SimpleDateFormat("dd-MM hh:mm:ss.S");

    private static Logger getLogger() {
        if (LOGGER == null) {
            LOGGER = Logger.getLogger(Log.class.getName());

            LOGGER.setUseParentHandlers(false);

            LOGGER.addHandler(new TareasConsoleInfoHandler());
            LOGGER.addHandler(new TareasConsoleErrorHandler());

            try {
                FileHandler fh = new FileHandler("tareas.log", true);
                LOGGER.addHandler(fh);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Cannot open log file.");
            }

        }

        return LOGGER;
    }

    public static void i(String tag, String msg) {
        if (Constants.LOGGING_ENABLED)
            getLogger().logp(Level.INFO, tag, "", msg);
    }

    public static void e(String tag, String msg) {
        if (Constants.LOGGING_ENABLED)
            getLogger().logp(Level.SEVERE, tag, "", msg);
    }

    public static void w(String tag, String msg) {
        if (Constants.LOGGING_ENABLED)
            getLogger().logp(Level.WARNING, tag, "", msg);
    }

    private static class TareasLogFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            return String.format("%s: %s/%s: %s\n",
                    TareasLogDateFormat.format(new Date(record.getMillis())),
                    record.getLevel(), record.getSourceClassName(), record.getMessage());

        }
    }

    private static class TareasConsoleInfoHandler extends StreamHandler {

        public TareasConsoleInfoHandler() {
            super(System.out, new TareasLogFormatter());
            this.setFilter(record -> record.getLevel() != Level.SEVERE);
        }

        @Override
        public void publish(LogRecord record) {
            super.publish(record);
            flush();
        }

        @Override
        public void close() {
            flush();
        }
    }

    private static class TareasConsoleErrorHandler extends StreamHandler {

        public TareasConsoleErrorHandler() {
            super(System.err, new TareasLogFormatter());
            this.setLevel(Level.SEVERE);
        }

        @Override
        public void publish(LogRecord record) {
            super.publish(record);
            flush();
        }

        @Override
        public void close() {
            flush();
        }
    }
}
