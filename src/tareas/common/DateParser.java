package tareas.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    /**
     * This method converts a LocalDateTime to a readable format.
     * If it is today's or tomorrow's date, the words "Today" and "Tomorrow"
     * will be returned.
     * @param dateTime
     * @return String
     */
    public static String getDateTime(LocalDateTime dateTime) {
        LocalDate today = LocalDate.now();
        LocalDate tmr = LocalDate.now().plus(1, ChronoUnit.DAYS);
        if (dateTime.toLocalDate().isEqual(today)) {
            return "Today " + dateTime.getHour() + ":" +
                    dateTime.getMinute();
        } if (dateTime.toLocalDate().isEqual(tmr)) {
            return "Tomorrow " + dateTime.getHour() + ":" +
                    dateTime.getMinute();
        } else {
            return dateTime.getDayOfMonth() + "-" + dateTime.getMonthValue() + "-" +
                    dateTime.getYear() + " " + dateTime.getHour() + ":" +
                    dateTime.getMinute();
        }
    }
}
