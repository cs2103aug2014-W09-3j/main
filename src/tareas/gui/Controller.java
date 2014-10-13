package tareas.gui;

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

public class Controller implements Initializable{
    private static Controller instance = null;
    private String input;
    private int idCount = 1;

    // FXML Variables
    public TextField commandLine;
    public Button closeButton;
    public TilePane tilePane;
    public ScrollPane scrollPane;
    public Label category;

    // Binding Variables
    public StringProperty displayMessage = new SimpleStringProperty();
    ObservableList<String> listItems = FXCollections.observableArrayList("Add Items here");

    public Controller() { }

    public static Controller getInstance() {
        if(instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialized!");

        // Initialization of category
        category.setText("All Tasks");

        // Initialization of tasks panes
        Pane task = createTaskPane("Finish up 2103 homework");

        // Initialization of scrollPane
        scrollPane.setContent(tilePane);
        scrollPane.setStyle("-fx-background-color: transparent");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Initialization of Tilepane
        tilePane.setHgap(20);
        tilePane.setVgap(20);
        tilePane.getChildren().add(task);
        tilePane.getStylesheets().add("tareas/gui/tilepane.css");

        // Initialize close button
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.close();
            }
        });
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
        tilePane.getChildren().add(0, newpane);
    }

    private Pane createTaskPane(String text) {
        // Initialization of taskPane
        Pane task = new Pane();
        task.setId("taskpane");
        task.setPrefSize(745, 80);
        task.getStylesheets().add("tareas/gui/taskpane.css");

        // Task Description
        task.getChildren().add(getDescriptionLabel(text));

        // ID Label
        task.getChildren().add(getIDLabel(idCount));

        // Deadline Label
        task.getChildren().add(getDeadline("23/10/2014"));

        return task;
    }

    private Label getDescriptionLabel(String text) {
        Label taskDescription = new Label(text);
        taskDescription.setMaxWidth(650);
        taskDescription.setId("taskDescription");
        return taskDescription;
    }

    private Label getIDLabel(int id) {
        Label idLabel = new Label("#" + Integer.toString(id));
        idLabel.setMaxWidth(50);
        idLabel.setId("idLabel");
        idCount++;
        return idLabel;
    }

    private Label getDeadline(String deadline) {
        Label deadlineLabel = new Label("By " + deadline);
        deadlineLabel.setId("deadlineLabel");
        return deadlineLabel;
    }

    public void changeDisplayMessage(String someString) {
        displayMessage.setValue(someString);
        listItems.add(input);
    }

}
