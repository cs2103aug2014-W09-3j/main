package tareas.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TareasGUIView extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    //@author A0065490A
    @Override
    public void start(final Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/TareasGUI.fxml"));

        primaryStage.setTitle("Tareas");
        primaryStage.initStyle(StageStyle.TRANSPARENT);

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
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        DashboardView dashboardView = new DashboardView();
        dashboardView.showDashboard();
    }


    //@author A0065490A
    public static void main(String[] args) {
        launch(args);
    }
}
