package tareas.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import tareas.common.Task;

/**
 * Created by Her Lung on 13/10/2014.
 */
class TaskPaneGenerator {
    Task task = new Task();

    protected TaskPaneGenerator(Task task) {
        this.task = task;
    }

    protected FlowPane generateTaskPane() {
        TareasGUIController controller = TareasGUIController.getInstance();

        // Initialization of taskPane
        FlowPane taskPane = new FlowPane();
        taskPane.setId("taskpane");
        taskPane.getStylesheets().add("tareas/gui/css/taskpane.css");

        // ID Label
        taskPane.getChildren().add(getIDLabel(controller.getIdCount()));

        // Task Description
        taskPane.getChildren().add(getDescriptionLabel(task.getDescription()));

        // Deadline Label
        //taskPane.getChildren().add(getDeadline(task.getDeadline()));

        return taskPane;
    }

    private Label getDescriptionLabel(String text) {
        Label taskDescription = new Label(text);
        taskDescription.setId("taskDescription");
        return taskDescription;
    }

    private Label getIDLabel(int id) {
        TareasGUIController controller = TareasGUIController.getInstance();

        Label idLabel = new Label(Integer.toString(id) + ".");
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
