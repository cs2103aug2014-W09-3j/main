package tareas.common;

import java.time.LocalDateTime;

/**
 * Created by Her Lung on 28/10/2014.
 * This class provides static methods to parse LocalDateTime
 * into a consistent date time layout throughout Tareas.
 */
public class DateParser {
    public static String getDate(LocalDateTime dateTime) {
        return dateTime.getDayOfMonth() + "-" + dateTime.getMonthValue() + "-" +
                dateTime.getYear();
    }

    public static String getDateTime(LocalDateTime dateTime) {
        return dateTime.getDayOfMonth() + "-" + dateTime.getMonthValue() + "-" +
                dateTime.getYear() + " " + dateTime.getHour() + ":" +
                dateTime.getMinute();
    }
}
