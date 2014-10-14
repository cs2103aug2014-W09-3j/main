package tareas.parser;

/**
 * Represents a result from calling an API method in Parser.
 * <p/>
 * Created on Oct 14, 2014.
 *
 * @author Kent
 */

public class ParsingResult {
    private ParsingStatus mStatus;
    private String mExtra;

    public ParsingStatus getStatus() {
        return mStatus;
    }

    public String getExtra() {
        return mExtra;
    }

    public ParsingResult(ParsingStatus status, String extra) {
        this.mStatus = status;
        this.mExtra = extra;
    }

    public ParsingResult(ParsingStatus status) {
        this.mStatus = status;
        this.mExtra = "";
    }

    public boolean isSuccessful() {
        return getStatus() == ParsingStatus.SUCCESS;
    }
}
