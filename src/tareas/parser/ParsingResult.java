package tareas.parser;

/**
 * Represents a result from calling an API method in Parser.
 * <p>
 * Created on Oct 14, 2014.
 *
 * @author Kent
 */

public class ParsingResult {
    private ParsingStatus mStatus;
    private String mExtra;

    //@author A0093934W
    public ParsingStatus getStatus() {
        return mStatus;
    }

    //@author A0093934W
    public String getExtra() {
        return mExtra;
    }

    //@author A0093934W
    public ParsingResult(ParsingStatus status, String extra) {
        this.mStatus = status;
        this.mExtra = extra;
    }

    //@author A0093934W
    public ParsingResult(ParsingStatus status) {
        this.mStatus = status;
        this.mExtra = "";
    }

    //@author A0093934W
    public boolean isSuccessful() {
        return getStatus() == ParsingStatus.SUCCESS;
    }
}
