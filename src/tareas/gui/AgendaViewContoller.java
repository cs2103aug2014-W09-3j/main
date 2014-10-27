package tareas.gui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.Agenda;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Her Lung on 27/10/2014.
 */
public class AgendaViewContoller {
    private Agenda agenda = new Agenda();

    public AgendaViewContoller(Agenda agenda) {
        this.agenda = agenda;
    }

    static private Calendar getFirstDayOfWeekCalendar(Locale locale, Calendar c)
    {
        // result
        int lFirstDayOfWeek = Calendar.getInstance(locale).getFirstDayOfWeek();
        int lCurrentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int lDelta = 0;
        if (lFirstDayOfWeek <= lCurrentDayOfWeek)
        {
            lDelta = -lCurrentDayOfWeek + lFirstDayOfWeek;
        }
        else
        {
            lDelta = -lCurrentDayOfWeek - (7-lFirstDayOfWeek);
        }
        c = ((Calendar)c.clone());
        c.add(Calendar.DATE, lDelta);
        return c;
    }

    public void showAgendaView() {
        GregorianCalendar start = new GregorianCalendar();
        start.set(2014, 10, 27, 10, 30);
        GregorianCalendar end = new GregorianCalendar();
        end.set(2014, 10, 27, 11, 30);

        this.agenda.appointments().add(
                new Agenda.AppointmentImpl()
                        .withStartTime(start)
                        .withEndTime(end));

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(agenda);

        Scene secondScene = new Scene(secondaryLayout, 800, 600);

        Stage secondStage = new Stage();
        secondStage.setTitle("Agenda View");
        secondStage.setScene(agenda.getScene());

        secondStage.show();

    }
}
