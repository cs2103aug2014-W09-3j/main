package tareas.gui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.Agenda;

import java.util.GregorianCalendar;

/**
 * Created by Her Lung on 27/10/2014.
 */
public class AgendaViewContoller {
    private Agenda agenda = new Agenda();

    public AgendaViewContoller(Agenda agenda) {
        this.agenda = agenda;
    }

    public void showAgendaView() {
        Agenda agenda = new Agenda();

        GregorianCalendar start = new GregorianCalendar();
        start.set(2014, 10, 27);

        GregorianCalendar end = new GregorianCalendar();
        end.set(2014, 10, 29);

        /*agenda.appointments().add(new Agenda.AppointmentImpl()
            .withStartTime(start).withEndTime(end));*/

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(agenda);

        Scene secondScene = new Scene(secondaryLayout, 800, 600);

        Stage secondStage = new Stage();
        secondStage.setTitle("Agenda View");
        secondStage.setScene(secondScene);

        secondStage.show();
    }
}
