package tareas.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tareas.common.Task;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Her Lung on 23/10/2014.
 */
class DetailedTaskView {
    private MouseEvent event;
    private Task task;
    private double xOffset = 0;
    private double yOffset = 0;

    protected DetailedTaskView(MouseEvent mouseEvent, Task task) {
        event = mouseEvent;
        this.task = task;
    }

    protected void show() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("TasksDetailedView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Tasks Detailed View");
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(root, 450, 450));
            stage.show();

            // To allow the stage to be draggable
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });

            //hide this current window (if this is want you want
            //((Node)(event.getSource())).getScene().getWindow().hide();

            // Retrieve all children of root
            ArrayList<FlowPane> nodes = new ArrayList<>();
            for(Node node : root.getChildrenUnmodifiable()) {
                nodes.add((FlowPane)node);
            }

            FlowPane header = nodes.get(0);
            FlowPane body = nodes.get(1);
            FlowPane footer = nodes.get(2);

            header.getChildren().add(new Label("TEST"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
