package tareas.new_gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SampleController implements Initializable{
    private static SampleController instance = null;
    private String input;

    // UI Variables
    public TextField commandLine;
    public ListView<String> listView;
    public Button closeButton;
    public TilePane tilePane;

    // Binding Variables
    public StringProperty displayMessage = new SimpleStringProperty();
    ObservableList<String> listItems = FXCollections.observableArrayList("Add Items here");

    public SampleController() { }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialized!");

        // Initialization of TilePane
        tilePane = new TilePane();
        tilePane.setVgap(3);
        Text monDay = new Text("Mon");
        tilePane.getChildren().add(monDay);

        // Initialize List of tasks
//        listView.setItems(listItems);
//        listView.setMouseTransparent(true);
//        listView.setFocusTraversable(false);

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
    }

    public void changeDisplayMessage(String someString) {
        displayMessage.setValue(someString);
        listItems.add(input);
    }

}
