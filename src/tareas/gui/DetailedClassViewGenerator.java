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
            // Insert Deadline
            body.getChildren().add(getDeadline());

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
        textFlow.setLayoutY(30);
        textFlow.setLayoutX(100);
        Text label = new Text("Deadline: ");
        label.setStyle("-fx-font-weight: bold");
        Text field = new Text(value);
        textFlow.getChildren().addAll(label, field);
        textFlow.setId("deadline");

        return textFlow;
    }
}
