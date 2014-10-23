package tareas.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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

            //hide this current window (if this is want you want
            ((Node)(event.getSource())).getScene().getWindow().hide();

            Stage stage = new Stage();
            stage.setTitle("Tasks Detailed View");
            stage.initStyle(StageStyle.TRANSPARENT);

            Scene scene = new Scene(root, 800, 600);
            scene.setFill(Color.TRANSPARENT);

            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    // Hide current window
                    stage.hide();

                    // Reset ID count back to 1 since contoller is a Singleton
                    TareasGUIController guiController = TareasGUIController.getInstance();
                    guiController.resetIdCount();

                    // Show back main view
                    Scene mainScene = ((Node)(event.getSource())).getScene();
                    Stage newStage = new Stage();
                    //newStage.setScene(mainScene);
                    TareasGUIView mainView = new TareasGUIView();
                    try {
                        mainView.start(newStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            stage.setScene(scene);
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
