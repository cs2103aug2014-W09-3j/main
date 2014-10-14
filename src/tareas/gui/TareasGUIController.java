package tareas.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import tareas.common.Task;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TareasGUIController implements Initializable{
    private static TareasGUIController instance = null;
    private String input;
    private int idCount = 1;

    // FXML Variables
    public GridPane root;
    public TextField commandLine;
    public Button closeButton;
    public TilePane tilePane;
    public ScrollPane scrollPane;
    public Label category;

    // Data Variables
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private NotificationPane notificationPane;

    public TareasGUIController() { }

    public static TareasGUIController getInstance() {
        if(instance == null) {
            instance = new TareasGUIController();
        }
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialized!");

        // Initialization of category
        category.setText("All Tasks");

        // Initialization of scrollPane
        initializeScrollPane();

        // Initialization of Tilepane
        tilePane.setHgap(20);
        tilePane.setVgap(20);
        tilePane.getStylesheets().add("tareas/gui/css/tilepane.css");

        // TODO Add JH new method to get all tasks for initialization
        ArrayList<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task());
        taskList.add(new Task());
        sendTaskstoView(taskList);

        // Initialize close button
        InitializeCloseButton();

        // Initialization of notification bar
        initializeNotifications();

        // TODO: Explore auto-complete
        /*TextFields.bindAutoCompletion(
                commandLine,
                "-add", "-delete");*/
    }

    private void initializeNotifications() {
        notificationPane = new NotificationPane(scrollPane);
        notificationPane.setShowFromTop(false);
        notificationPane.setMinSize(800, 100);
        root.add(notificationPane, 0, 1);
    }

    private void InitializeCloseButton() {
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void initializeScrollPane() {
        scrollPane.setContent(tilePane);
        scrollPane.setStyle("-fx-background-color: transparent");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void onEnter() {
        // IMPORTANT!
        // The below line ensures that the instance of this controller the Logic
        // component is using is the same instance as the one FXMLoader is using.
        instance = this;

        input = commandLine.getText();
        commandLine.clear();

        TaskPaneGenerator generator = new TaskPaneGenerator(new Task());
        Pane newpane = generator.generateTaskPane();
        tilePane.getChildren().add(0, newpane);

        // Notifications
        Image tick = new Image("tick1.png");
        ImageView tickLogo = new ImageView(tick);
        tickLogo.setFitWidth(25);
        tickLogo.setFitHeight(25);
        notificationPane.show(input, tickLogo);
        hideNotificationAfter(3000);
    }

    public void changeCategoryName(String newCategory) {
        category.setText(newCategory);
    }

    public void sendNotificationToView(String message) {
        notificationPane.show(message);
        hideNotificationAfter(3000);
    }

    public void sendTaskstoView(ArrayList<Task> tasks) {
        this.tasks = tasks;
        updateView();
    }

    private void updateView() {
        tilePane.getChildren().removeAll();
        for(Task task : tasks) {
            TaskPaneGenerator generator = new TaskPaneGenerator(task);
            tilePane.getChildren().add(generator.generateTaskPane());
        }
        idCount = 1;
    }

    private void hideNotificationAfter(int ms) {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        notificationPane.hide();
                    }
                },
                ms
        );
    }

    protected int getIdCount() {
        return idCount;
    }

    protected void incrementIdCount() {
        idCount++;
    }
}
