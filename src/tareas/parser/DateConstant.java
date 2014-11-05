package tareas.parser;

import com.sun.xml.internal.ws.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;

/**
 * An enum contains date related String constants.
 * <p>
 * Created on Oct 29, 2014.
 */

public enum DateConstant {
    YESTERDAY(DateConstantType.RELATIVE_DATE, -1, "yesterday"),
    TODAY(DateConstantType.RELATIVE_DATE, 0, "today"),
    TOMORROW(DateConstantType.RELATIVE_DATE, 1, "tomorrow"),
    DAY_AFTER_TOMORROW(DateConstantType.RELATIVE_DATE, 2, "day after tomorrow"),

    MONDAY(DateConstantType.DAY_OF_THE_WEEK, 1, "mon", "monday"),
    TUESDAY(DateConstantType.DAY_OF_THE_WEEK, 2, "tue", "tuesday"),
    WEDNESDAY(DateConstantType.DAY_OF_THE_WEEK, 3, "wed", "wednesday"),
    THURSDAY(DateConstantType.DAY_OF_THE_WEEK, 4, "thu", "thursday", "thurs"),
    FRIDAY(DateConstantType.DAY_OF_THE_WEEK, 5, "fri", "friday"),
    SATURDAY(DateConstantType.DAY_OF_THE_WEEK, 6, "sat", "saturday"),
    SUNDAY(DateConstantType.DAY_OF_THE_WEEK, 7, "sun", "sunday");

    //@author A0093934W
    public enum DateConstantType {
        RELATIVE_DATE,
        DAY_OF_THE_WEEK
    }

    private DateConstantType mType;
    private int mOffset;
    private ArrayList<String> mValues;

    //@author A0093934W
    DateConstant(DateConstantType type, int offset, String... values) {
        mType = type;
        mOffset = offset;

        mValues = new ArrayList<>();

        Collections.addAll(mValues, values);

    }

    //@author A0093934W
    public ArrayList<String> getValues() {
        return mValues;
    }

    //@author A0093934W
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

    //@author A0093934W
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

    //@author A0093934W
    public LocalDate toLocalDate() {
        if (this.mType == DateConstantType.RELATIVE_DATE) {
            return LocalDate.now().plus(this.mOffset, ChronoUnit.DAYS);
        } else { // if (this.mType == DateConstantType.DAY_OF_THE_WEEK) {
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.of(mOffset)));
        }
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(getValues().get(0));
    }
}
