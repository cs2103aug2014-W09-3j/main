package tareas.new_gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SampleController implements Initializable{
    private static SampleController instance = null;
    private String input;

    // UI Variables
    public TextField commandLine;
    public Button closeButton;
    public TilePane tilePane;
    public ScrollPane scrollPane;

    // Binding Variables
    public StringProperty displayMessage = new SimpleStringProperty();
    ObservableList<String> listItems = FXCollections.observableArrayList("Add Items here");

    public SampleController() { }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialized!");

        // Initialization of tasks panes
        Pane task = createTaskPane(":)");

        // Initialization of scrollPane
        scrollPane.setContent(tilePane);
        scrollPane.setStyle("-fx-background-color: transparent");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Initialization of Tilepane
        tilePane.setHgap(20);
        tilePane.setVgap(20);
        tilePane.getChildren().add(task);
        tilePane.getStylesheets().add("tareas/new_gui/tilepane.css");

        // Initialize close button
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.close();
            }
        });
    }

    public static SampleController getInstance() {
        if(instance == null) {
            instance = new SampleController();
        }
        return instance;
    }

    public void onEnter() {
        // IMPORTANT!
        // The below line ensures that the instance of this controller the Logic
        // component is using is the same instance as the one FXMLoader is using.
        instance = this;

        input = commandLine.getText();
        commandLine.clear();

        test controller = new test();
        controller.executeCommand(input);

        Pane newpane = createTaskPane(input);
        tilePane.getChildren().add(newpane);
    }

    private Pane createTaskPane(String text) {
        // Initialization of taskPane
        Pane task = new Pane();
        task.setId("taskpane");
        task.setPrefSize(360, 80);
        task.getStylesheets().add("tareas/new_gui/taskpane.css");

        // Task Description
        Label taskDescription = new Label(text);
        taskDescription.setMaxWidth(200);
        taskDescription.setId("taskDescription");
        task.getChildren().add(taskDescription);

        return task;
    }

    public void changeDisplayMessage(String someString) {
        displayMessage.setValue(someString);
        listItems.add(input);
    }

}
