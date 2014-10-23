package tareas.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import tareas.common.Task;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Her Lung on 23/10/2014.
 */
class DetailedTaskViewGenerator {
    private Task task;

    protected DetailedTaskViewGenerator(Task task) {
        this.task = task;
    }

    protected FlowPane generate() {
        FlowPane root = new FlowPane();
        try {
            root = FXMLLoader.load(getClass().getResource("fxml/TasksDetailedView.fxml"));

            // Retrieve all children of root
            ArrayList<FlowPane> nodes = new ArrayList<>();
            for(Node node : root.getChildrenUnmodifiable()) {
                nodes.add((FlowPane)node);
            }

            FlowPane header = nodes.get(0);
            header.setId("header");
            // Insert Task Description
            header.getChildren().add(getTaskDescription());

            FlowPane body = nodes.get(1);
            body.setId("body");
            body.setVgap(10);
            // Insert contents
            body.getChildren().addAll(getDeadline(), getStartTime(),
                    getEndTime(), getCompleted());

            FlowPane footer = nodes.get(2);
            footer.setId("footer");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    private Label getTaskDescription() {
        Label taskDescription = new Label(task.getDescription());
        taskDescription.setWrapText(true);
        taskDescription.setId("taskDescription");
        return taskDescription;
    }

    private TextFlow getDeadline() {
        String value;
        if(task.getDeadline() == null) {
            value = " - ";
        } else {
            value = task.getDeadline();
        }
        TextFlow textFlow = new TextFlow();
        textFlow.setPrefWidth(193);
        Text label = new Text("Deadline: ");
        label.setStyle("-fx-font-weight: bold");
        Text field = new Text(value);
        textFlow.getChildren().addAll(label, field);
        textFlow.setId("deadline");

        return textFlow;
    }

    private TextFlow getStartTime() {
        String value;
        if(task.getStartDateTime() == null) {
            value = " - ";
        } else {
            value = task.getStartDateTime();
        }
        TextFlow textFlow = new TextFlow();
        textFlow.setPrefWidth(193);
        Text label = new Text("Start: ");
        label.setStyle("-fx-font-weight: bold");
        Text field = new Text(value);
        textFlow.getChildren().addAll(label, field);
        textFlow.setId("startDateTime");

        return textFlow;
    }

    private TextFlow getEndTime() {
        String value;
        if(task.getEndDateTime() == null) {
            value = " - ";
        } else {
            value = task.getEndDateTime();
        }
        TextFlow textFlow = new TextFlow();
        textFlow.setPrefWidth(193);
        Text label = new Text("End: ");
        label.setStyle("-fx-font-weight: bold");
        Text field = new Text(value);
        textFlow.getChildren().addAll(label, field);
        textFlow.setId("endDateTime");

        return textFlow;
    }

    private TextFlow getCompleted() {
        String value;
        if(task.isTaskCompleted()) {
            value = "Completed";
        } else {
            value = "Not Completed";
        }
        TextFlow textFlow = new TextFlow();
        textFlow.setPrefWidth(193);
        Text label = new Text("Status: ");
        label.setStyle("-fx-font-weight: bold;");
        Text field = new Text(value);
        if(task.isTaskCompleted()) {
            field.setStyle("-fx-text-fill: green");
        } else {
            field.setStyle("-fx-text-fill: maroon");
        }
        textFlow.getChildren().addAll(label, field);
        textFlow.setId("getCompleted");

        return textFlow;
    }
}
