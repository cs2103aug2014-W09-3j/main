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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import tareas.common.Log;
import tareas.common.Task;
import tareas.controller.TareasController;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class TareasGUIController implements Initializable {
    private static String TAG = "TareasGUIController";

    private static TareasGUIController instance = null;
    private String input;
    private int idCount = 1;

    // FXML Variables
    public GridPane root;
    public TextField commandLine;
    public Button closeButton;
    public FlowPane flowPane;
    public ScrollPane scrollPane;

    // Data Variables
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private NotificationPane notificationPane;
    private String categoryText = "Today's Tasks";

    public TareasGUIController() {
    }

    public static TareasGUIController getInstance() {
        if (instance == null) {
            instance = new TareasGUIController();
        }
        return instance;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Log.i(TAG, "Initialized!");

        // Set placeholder for command line
        commandLine.setPromptText("Type a command here...");

        // Initialization of scrollPane
        initializeScrollPane();

        // Initialization of Tilepane
        initializeFlowPane();

        TareasController logicController = new TareasController();
        sendTaskstoView(logicController.getInitialiseTasks());

        // Initialize close button
        initializeCloseButton();

        // Initialization of notification bar
        initializeNotifications();

        // TODO: Explore auto-complete
        /*TextFields.bindAutoCompletion(
                commandLine,
                "-add", "-delete");*/
    }

    private void initializeFlowPane() {
        flowPane.setHgap(20);
        flowPane.setVgap(3);
        flowPane.getStylesheets().add("tareas/gui/css/flowpane.css");
    }

    private void initializeNotifications() {
        notificationPane = new NotificationPane(scrollPane);
        notificationPane.setShowFromTop(false);
        notificationPane.setMinSize(800, 100);
        root.add(notificationPane, 0, 1);
    }

    private void initializeCloseButton() {
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void initializeScrollPane() {
        scrollPane.setContent(flowPane);
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

        TareasController mainController = new TareasController();

        Log.i(TAG, "User entered in command: " + input);
        mainController.executeCommand(input);
    }

    public void changeCategoryName(String newCategory) {
        categoryText = newCategory;
    }

    public void sendWarningToView(String message) {
        sendNotificationToView(message, "warning");
    }

    public void sendErrorToView(String message) {
        sendNotificationToView(message, "error");
    }

    public void sendSuccessToView(String message) {
        sendNotificationToView(message, "success");
    }

    private void sendNotificationToView(String message, String status) {
        // Notifications (Code for notifications with picture)
        Image logo;

        if (status.equals("error")) {
            logo = new Image("error.png");
        } else if (status.equals("warning")) {
            logo = new Image("warning.png");
        } else {
            logo = new Image("tick.png");
        }

        ImageView notificationLogo = new ImageView(logo);
        notificationLogo.setFitWidth(25);
        notificationLogo.setFitHeight(25);
        notificationPane.show(message, notificationLogo);
        hideNotificationAfter(3000);
    }

    public void sendTaskstoView(ArrayList<Task> tasks) {
        Collections.reverse(tasks);
        this.tasks = tasks;
        updateView();
    }

    private void updateView() {
        flowPane.getChildren().clear();
        Label categoryLabel = new Label(categoryText);
        categoryLabel.setId("categoryLabel");
        flowPane.getChildren().add(categoryLabel);

        for (Task task : this.tasks) {
            TaskPaneGenerator generator = new TaskPaneGenerator(task);
            FlowPane taskPane = generator.generateTaskPane();
            taskPane.setOnMouseClicked(event -> {
                DetailedTaskView detailedTaskView =
                        new DetailedTaskView(event, task);
                detailedTaskView.show();
            });
            flowPane.getChildren().add(taskPane);
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
