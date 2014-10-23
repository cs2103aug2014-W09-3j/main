package tareas.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import tareas.common.Task;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Her Lung on 23/10/2014.
 */
class DetailedTaskView {
    MouseEvent event;
    Task task;

    protected DetailedTaskView(MouseEvent mouseEvent, Task task) {
        event = mouseEvent;
        this.task = task;
    }

    protected void show() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("TasksDetailedView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();

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

            System.out.println(header.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
