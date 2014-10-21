package tareas.parser;

/**
 * [Description]
 * <p/>
 * Created on Oct 15, 2014.
 *
 * @author Kent
 */

public enum ParsingStatus {
    SUCCESS,
    UNKNOWN_COMMAND,
    MISSING_PRIMARY_ARGUMENT,
    UNEXPECTED_PRIMARY_ARGUMENT,
    UNKNOWN_KEYWORD,
    SIGNATURE_NOT_MATCHED
}
