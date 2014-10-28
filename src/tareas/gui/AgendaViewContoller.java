package tareas.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        GregorianCalendar start = new GregorianCalendar();
        start.set(2014, 10, 27, 10, 30);
        GregorianCalendar end = new GregorianCalendar();
        end.set(2014, 10, 27, 11, 30);

        this.agenda.appointments().add(
                new Agenda.AppointmentImpl()
                        .withStartTime(start)
                        .withEndTime(end));

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(new Label("HELLO"));

        Scene secondScene = new Scene(secondaryLayout, 800, 600);

        Stage secondStage = new Stage();
        secondStage.setTitle("Agenda View");
        secondStage.setScene(secondScene);

        secondStage.show();

    }
}
