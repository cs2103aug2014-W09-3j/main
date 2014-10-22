package tareas.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import tareas.common.Task;

/**
 * Created by Her Lung on 13/10/2014.
 */
class TaskPaneGenerator {
    Task task = new Task();

    protected TaskPaneGenerator(Task task) {
        this.task = task;
    }

    protected Pane generateTaskPane() {
        TareasGUIController controller = TareasGUIController.getInstance();

        // Initialization of taskPane
        Pane taskPane = new Pane();
        taskPane.setId("taskpane");
        taskPane.setPrefSize(745, 40);
        taskPane.getStylesheets().add("tareas/gui/css/taskpane.css");

        // Task Description
        taskPane.getChildren().add(getDescriptionLabel(task.getDescription()));

        // ID Label
        taskPane.getChildren().add(getIDLabel(controller.getIdCount()));

        // Deadline Label
        //taskPane.getChildren().add(getDeadline(task.getDeadline()));

        return taskPane;
    }

    private Label getDescriptionLabel(String text) {
        Label taskDescription = new Label(text);
        taskDescription.setMaxWidth(650);
        taskDescription.setId("taskDescription");
        return taskDescription;
    }

    private Label getIDLabel(int id) {
        TareasGUIController controller = TareasGUIController.getInstance();

        Label idLabel = new Label("#" + Integer.toString(id));
        idLabel.setMaxWidth(50);
        idLabel.setId("idLabel");
        controller.incrementIdCount();
        return idLabel;
    }

    private Label getDeadline(String deadline) {
        Label deadlineLabel = new Label("By " + deadline);
        deadlineLabel.setId("deadlineLabel");
        return deadlineLabel;
    }
}
