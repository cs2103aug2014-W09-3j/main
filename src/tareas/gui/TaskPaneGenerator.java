package tareas.gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import tareas.common.Task;
import tareas.parser.Parser;

import java.time.LocalDateTime;

/**
 * Created by Her Lung on 13/10/2014.
 */
class TaskPaneGenerator {
    Task task = new Task();

    protected TaskPaneGenerator(Task task) {
        this.task = task;
    }

    protected FlowPane generateTaskPane(boolean isHighlighted) {
        TareasGUIController controller = TareasGUIController.getInstance();

        // Initialization of taskPane
        FlowPane taskPane = new FlowPane();
        taskPane.setId("taskpane");
        taskPane.getStylesheets().add("tareas/gui/css/taskpane.css");

        if(isHighlighted) {
            taskPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        }

        // ID Label
        taskPane.getChildren().add(getIDLabel(task.getTaskID()));

        // Task Description
        taskPane.getChildren().add(getDescriptionLabel(task.getDescription()));

        // Deadline Label
        if(task.getDeadline() != null) {
            taskPane.getChildren().add(getDeadline(task.getDeadline()));
        } else if(task.getStartDateTime() == null && task.getEndDateTime() != null) {
            taskPane.getChildren().add(getDeadline(task.getEndDateTime()));
        } else if(task.getStartDateTime() != null && task.getEndDateTime() != null){
            taskPane.getChildren().add(getInterval(task.getStartDateTime(), task.getEndDateTime()));
        } else {
            taskPane.getChildren().add(generateEmptyLabel(100, 30));
        }

        // Prioritise picture
        taskPane.getChildren().add(getPriority(task.isTaskPriority()));

        return taskPane;
    }

    private Label getDescriptionLabel(String text) {
        Label taskDescription = new Label(text);
        taskDescription.setId("taskDescription");
        return taskDescription;
    }

    private Label getIDLabel(int id) {
        Label idLabel = new Label(Integer.toString(id) + ".");
        idLabel.setId("idLabel");
        return idLabel;
    }

    private Label getDeadline(LocalDateTime deadline) {
        String date;
        if(deadline == null){
            date = "";
        } else {
            date = Parser.getStringFromDateTime(deadline);
        }
        Label deadlineLabel = new Label(date);
        deadlineLabel.setId("deadlineLabel");
        return deadlineLabel;
    }

    // TODO: Create a double line label
    private Label getInterval(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        String startDate;
        String endDate;
        if(startDateTime == null && endDateTime == null) {
            startDate = "";
            endDate = "";
        } else {
            startDate = Parser.getStringFromDateTime(startDateTime);
            endDate = Parser.getStringFromDateTime(endDateTime);
        }
        Label startEndDateTime = new Label(startDate + "\n" + endDate);
        startEndDateTime.setId("startEndLabel");
        return startEndDateTime;
    }

    private ImageView getPriority(boolean hasPriority) {
        Image image = new Image("bookmark.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(40);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setId("prioritisePicture");

        if(!hasPriority) {
            imageView.setOpacity(0);
        }

        return imageView;
    }

    private Label generateEmptyLabel(int width, int height) {
        Label label = new Label();
        label.setPrefSize(width, height);
        return label;
    }
}
