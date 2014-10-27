package tareas.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.PopOver;
import tareas.common.Log;
import tareas.common.Task;
import tareas.controller.TareasController;

import java.net.URL;
import java.util.*;

public class TareasGUIController implements Initializable {
    private static String TAG = "TareasGUIController";

    // FXML Variables
    public GridPane root;
    public TextField commandLine;
    public Button closeButton;
    public FlowPane flowPane;

    // Data Variables
    private static TareasGUIController instance = null;
    private String input;
    private int pageCount = 1;
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private NotificationPane notificationPane;
    private String categoryText = "Today's Tasks";
    private final int maxTasksPerPage = 10;
    private Stack<String> commandStackBefore = new Stack<>();
    private Stack<String> commandStackAfter = new Stack<>();

    public TareasGUIController() {
    }

    /**
     * This method is for the singleton design pattern
     */
    public static TareasGUIController getInstance() {
        if (instance == null) {
            instance = new TareasGUIController();
        }
        return instance;
    }

    /**
     * This method is to ensure that the singleton pattern will
     * not be violated
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * This is the method the GUI will run first when initialised
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Log.i(TAG, "Initialized!");

        // Set placeholder for command line
        commandLine.setPromptText("Type a command here...");

        // Initialization of Tilepane
        initializeFlowPane();

        TareasController logicController = new TareasController();
        sendTaskstoView(logicController.getInitialiseTasks());

        // Initialize close button
        initializeCloseButton();

        // Initialization of notification bar
        initializeNotifications();

        // Initialization of shortcut keys
        initializeKeyCombinations();

        // TODO: Explore auto-complete
        /*TextFields.bindAutoCompletion(
                commandLine,
                "-add", "-delete");*/
    }

    private void initializeKeyCombinations() {
        root.setOnKeyReleased(new EventHandler<KeyEvent>() {
            final KeyCombination CTRL_RIGHT = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.CONTROL_DOWN);
            final KeyCombination CTRL_LEFT = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.CONTROL_DOWN);
            final KeyCombination CTRL_UP = new KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN);
            final KeyCombination CTRL_DOWN = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN);
            final KeyCombination ESCAPE = new KeyCodeCombination(KeyCode.ESCAPE);
            final KeyCombination UP = new KeyCodeCombination(KeyCode.UP);
            final KeyCombination DOWN = new KeyCodeCombination(KeyCode.DOWN);
            final KeyCombination CTRL_M = new KeyCodeCombination(KeyCode.M, KeyCodeCombination.CONTROL_DOWN);

            public void handle(KeyEvent t) {
                if(CTRL_RIGHT.match(t)) {
                    goToNextPage();
                }
                if(CTRL_LEFT.match(t)) {
                    goToPrevPage();
                }
                if(CTRL_UP.match(t)) {
                    goToFirstPage();
                }
                if(CTRL_DOWN.match(t)) {
                    goToLastPage();
                }
                if(ESCAPE.match(t)) {
                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.close();
                }
                if(UP.match(t)) {
                    if(commandStackBefore.empty()) {
                        //sendWarningToView("No more commands in history.");
                    } else {
                        String prevCommand = commandStackBefore.pop();
                        commandLine.setText(prevCommand);
                        commandStackAfter.push(prevCommand);
                    }
                }
                if(DOWN.match(t)) {
                    if(commandStackAfter.empty()) {
                        commandLine.setText("");
                    } else {
                        String futureCommand = commandStackAfter.pop();
                        commandLine.setText(futureCommand);
                        commandStackBefore.push(futureCommand);
                    }
                }
                if(CTRL_M.match(t)) {
                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.setIconified(true);
                }
            }
        });
    }

    private void initializeFlowPane() {
        flowPane.setHgap(20);
        flowPane.setVgap(3);
        flowPane.getStylesheets().add("tareas/gui/css/flowpane.css");
    }

    private void initializeNotifications() {
        notificationPane = new NotificationPane(flowPane);
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
                Log.i(TAG, "User exited the program.");
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

        TareasController mainController = new TareasController();

        commandStackBefore.push(input);
        commandStackAfter.clear();
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
        pageCount = 1;
        updateView();
    }

    private void updateView() {
        flowPane.getChildren().clear();
        Label categoryLabel = new Label(categoryText);
        categoryLabel.setId("categoryLabel");
        flowPane.getChildren().add(categoryLabel);

        // Inserting listeners to each taskPane
        for (Task task : getPageView()) {
            TaskPaneGenerator generator = new TaskPaneGenerator(task);
            FlowPane taskPane = generator.generateTaskPane();
            taskPane.setOnMouseClicked(event -> {
                setDetailedViewToTaskPane(taskPane, task);
            });
            flowPane.getChildren().add(taskPane);
        }

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

    private void setDetailedViewToTaskPane(FlowPane taskPane, Task task) {
        PopOver detailedView = new PopOver();
        Scene thisScene = taskPane.getScene();

        thisScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                detailedView.hide();
            }
        });
        thisScene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                detailedView.hide();
            }
        });

        detailedView.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        detailedView.setCornerRadius(0);
        detailedView.setOpacity(0.95);
        DetailedTaskViewGenerator gen = new DetailedTaskViewGenerator(task);
        detailedView.setContentNode(gen.generate());
        detailedView.show(taskPane);
    }

    // TODO: Try to make the view appear near the actual taskPane
    public void showDetailedView(Task task) {
        PopOver detailedView = new PopOver();
        Scene thisScene = root.getScene();

        thisScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                detailedView.hide();
            }
        });
        thisScene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                detailedView.hide();
            }
        });

        detailedView.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
        detailedView.setCornerRadius(0);
        detailedView.setOpacity(0.95);
        DetailedTaskViewGenerator gen = new DetailedTaskViewGenerator(task);
        detailedView.setContentNode(gen.generate());
        detailedView.show(commandLine);
    }

    private ArrayList<Task> getPageView() {
        ArrayList<Task> currentPage = new ArrayList<Task>();
            for(int i = 0; i < maxTasksPerPage; i++) {
                if((pageCount-1)*maxTasksPerPage + i > tasks.size()-1) {
                    break;
                }
                Task task = tasks.get((pageCount - 1) * maxTasksPerPage + i);
                // GUI TaskID is set into the Task object's taskID attribute.
                task.setTaskID((pageCount-1)*maxTasksPerPage + i + 1);
                currentPage.add(task);
            }
        return currentPage;
    }

    private boolean isPageNumberValid(int pageNumber) {
        int totalSize = this.tasks.size();
        if(totalSize % maxTasksPerPage > 0) {
            if(pageNumber > (totalSize/maxTasksPerPage)+1 ||
                pageNumber < 1) {
                return false;
            } else {
                return true;
            }
        } else {
            if(pageNumber > (totalSize/maxTasksPerPage) ||
                pageNumber < 1) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void goToNextPage() {
        int nextPage = this.pageCount + 1;
        if(isPageNumberValid(nextPage)) {
            pageCount++;
            updateView();
        } else {
            sendWarningToView("You have reached the last page.");
        }
    }

    public void goToPrevPage() {
        int prevPage = this.pageCount - 1;
        if(isPageNumberValid(prevPage)) {
            pageCount--;
            updateView();
        } else {
            sendWarningToView("You are at the first page.");
        }
    }

    public void goToFirstPage() {
        pageCount = 1;
        updateView();
    }

    public void goToLastPage() {
        int totalNumber = this.tasks.size();
        if(totalNumber % maxTasksPerPage > 0) {
            pageCount = totalNumber / maxTasksPerPage + 1;
        } else {
            pageCount = totalNumber / maxTasksPerPage;
        }
        updateView();
    }
}
