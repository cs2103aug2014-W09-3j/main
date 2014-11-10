package tareas.parser;

import com.sun.xml.internal.ws.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;

//@author A0093934W

/**
 * An enum contains date related String constants.
 */

public enum DateConstant {
    YESTERDAY(DateConstantType.RELATIVE_DATE, -1, "yesterday"),
    TODAY(DateConstantType.RELATIVE_DATE, 0, "today"),
    TOMORROW(DateConstantType.RELATIVE_DATE, 1, "tomorrow"),
    DAY_AFTER_TOMORROW(DateConstantType.RELATIVE_DATE, 2, "day after tomorrow"),

    MONDAY(DateConstantType.DAY_OF_THE_WEEK, 1, "monday", "mon"),
    TUESDAY(DateConstantType.DAY_OF_THE_WEEK, 2, "tuesday", "tue"),
    WEDNESDAY(DateConstantType.DAY_OF_THE_WEEK, 3, "wednesday", "wed"),
    THURSDAY(DateConstantType.DAY_OF_THE_WEEK, 4, "thursday", "thurs", "thu"),
    FRIDAY(DateConstantType.DAY_OF_THE_WEEK, 5, "friday", "fri"),
    SATURDAY(DateConstantType.DAY_OF_THE_WEEK, 6, "saturday", "sat"),
    SUNDAY(DateConstantType.DAY_OF_THE_WEEK, 7, "sunday", "sun"),

    CHRISTMAS_EVE(DateConstantType.HOLIDAY, 12, 24, "christmas", "xmas");


    public enum DateConstantType {
        RELATIVE_DATE,
        DAY_OF_THE_WEEK,
        HOLIDAY
    }

    private DateConstantType mType;
    private int mOffset;
    private ArrayList<String> mValues;
    private LocalDate mPreValue;


    DateConstant(DateConstantType type, int offset, String... values) {
        mType = type;
        mOffset = offset;
        mPreValue = LocalDate.now();

        mValues = new ArrayList<>();

        Collections.addAll(mValues, values);

    }


    DateConstant(DateConstantType type, int month, int day, String... values) {
        mType = type;
        mPreValue = LocalDate.of(LocalDate.now().getYear(), month, day);
        mValues = new ArrayList<>();

        Collections.addAll(mValues, values);
    }


    public ArrayList<String> getValues() {
        return mValues;
    }


    public static String scanString(String input) {
        input = input.toLowerCase();

        for (DateConstant constant : DateConstant.values()) {
            for (String value : constant.getValues()) {
                if (input.contains(value)) {
                    return value;
                }
            }
        }

        return null;
    }


    public static DateConstant fromString(String input) {
        input = input.toLowerCase().trim();

        for (DateConstant constant : DateConstant.values()) {
            if (constant.getValues().contains(input)) {
                return constant;
            }
        }

        return null;
    }


    public static DateConstant fromTypeOffset(DateConstantType type, int offset) {
        for (DateConstant constant : DateConstant.values()) {
            if (constant.mType == type && constant.mOffset == offset) {
                return constant;
            }
        }

        return null;
    }


    public LocalDate toLocalDate() {
        switch (this.mType) {
            case RELATIVE_DATE:
                return LocalDate.now().plus(this.mOffset, ChronoUnit.DAYS);
            case DAY_OF_THE_WEEK:
                return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.of(mOffset)));
            case HOLIDAY:
                return mPreValue;
            default:
                return LocalDate.now();
        }
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(getValues().get(0));
    }
}
