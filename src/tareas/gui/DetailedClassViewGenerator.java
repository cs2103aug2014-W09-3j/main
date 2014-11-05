package tareas.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import tareas.common.Task;
import tareas.parser.Parser;

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
            ArrayList<FlowPane> nodes = new ArrayList<FlowPane>();
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
                    getEndTime(), getCompleted(), getPriority(), getReminderDateTime());

            FlowPane footer = nodes.get(2);
            footer.setId("footer");
            Label quitMessage = new Label("Press <Tab> to go back...");
            quitMessage.setId("exitMessage");
            footer.getChildren().add(quitMessage);

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
            value = Parser.getStringFromDateTime(task.getDeadline());
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
            value = Parser.getStringFromDateTime(task.getStartDateTime());
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
            value = Parser.getStringFromDateTime(task.getEndDateTime());
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
            value = "Yes";
        } else {
            value = "No";
        }
        TextFlow textFlow = new TextFlow();
        textFlow.setPrefWidth(193);
        Text label = new Text("Completed: ");
        label.setStyle("-fx-font-weight: bold;");
        Text field = new Text(value);
        if(task.isTaskCompleted()) {
            field.setStyle("-fx-text-fill: green;");
        } else {
            field.setStyle("-fx-text-fill: maroon;");
        }
        textFlow.getChildren().addAll(label, field);
        textFlow.setId("getCompleted");

        return textFlow;
    }

    private TextFlow getPriority() {
        String value;
        if(task.isTaskPriority()) {
            value = "Yes";
        } else {
            value = "No";
        }
        TextFlow textFlow = new TextFlow();
        textFlow.setPrefWidth(193);
        Text label = new Text("Priority: ");
        label.setStyle("-fx-font-weight: bold;");
        Text field = new Text(value);
        if(task.isTaskPriority()) {
            field.setStyle("-fx-text-fill: green;");
        } else {
            field.setStyle("-fx-text-fill: maroon;");
        }
        textFlow.getChildren().addAll(label, field);
        textFlow.setId("getPriority");

        return textFlow;
    }

    private TextFlow getReminderDateTime() {
        String value;
        if(task.getReminderDateTime() == null) {
            value = " - ";
        } else {
            value = task.getReminderDateTime().toString();
        }
        TextFlow textFlow = new TextFlow();
        textFlow.setPrefWidth(193);
        Text label = new Text("Reminder: ");
        label.setStyle("-fx-font-weight: bold");
        Text field = new Text(value);
        textFlow.getChildren().addAll(label, field);
        textFlow.setId("reminderDateTime");

        return textFlow;
    }
}
