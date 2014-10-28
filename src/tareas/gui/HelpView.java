package tareas.gui;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Created by Her Lung on 28/10/2014.
 */
class HelpView {
    FlowPane root;

    protected HelpView() {
        root = new FlowPane();
    }

    protected void showHelpView() {
        root.setId("help-view");
        root.getStylesheets().add("tareas/gui/css/helpView.css");

        setContent(250, 200);

        Scene scene = new Scene(root, 800, 800);
        scene.setFill(Color.TRANSPARENT);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                closeHelpView(scene);
            }
        });
        scene.setOnMouseClicked(t -> {
            closeHelpView(scene);
        });

        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Dashboard");
        stage.setScene(scene);

        stage.setAlwaysOnTop(true);
        stage.requestFocus();

        stage.show();
    }

    private void setContent(int width, int height) {
        FlowPane addGrouping = new FlowPane();
        addGrouping.setId("grouping");
        addGrouping.setPrefSize(780, 110);
        addGrouping.getChildren().addAll(
                labelGenerator("Add Tasks", "sub-title", 760),
                floatingCommands(width-10, 50),
                deadlineCommands(width-10, 50),
                importantCommands(width-10, 50),
                timedCommands(490, 50),
                tagCommands(width-10, 50)
        );

        FlowPane editGrouping = new FlowPane();
        editGrouping.setId("grouping");
        editGrouping.setPrefSize(780, 110);
        editGrouping.getChildren().addAll(
                labelGenerator("Edit, Delete Tasks & Others", "sub-title", 760),
                editCommands(490, 50),
                deleteCommands(width-10, 50),
                doneCommands(width-10, 30),
                prioritizeCommands(width-10, 30),
                undoRedoCommands(width-10, 30)
        );

        FlowPane peripheralGrouping = new FlowPane();
        peripheralGrouping.setId("grouping");
        peripheralGrouping.setPrefSize(780, 110);
        peripheralGrouping.getChildren().addAll(
                labelGenerator("Peripheral Commands", "sub-title", 760),
                viewCommands(350, 100),
                shortcutCommands(350, 100)
        );
        this.root.getChildren().addAll(
                labelGenerator("List of Commands", "title", 780),
                addGrouping,
                editGrouping,
                peripheralGrouping,
                labelGenerator("Press any key to continue...",
                        "sub-title", 780)
        );
    }

    private FlowPane floatingCommands(int width, int height) {
        FlowPane floating = new FlowPane();
        floating.setId("module");
        floating.setPrefSize(width, height);

        floating.getChildren().addAll(
                labelGenerator("Add a floating task", "sub-title", width-10),
                labelGenerator("Just type the task description in\n the command line.", "command", width-10)
        );

        return floating;
    }

    private FlowPane deadlineCommands(int width, int height) {
        FlowPane deadline = new FlowPane();
        deadline.setId("module");
        deadline.setPrefSize(width, height);

        deadline.getChildren().addAll(
                labelGenerator("Add a deadline task", "sub-title", width-10),
                labelGenerator("<task description> /by dd-mm-yy HH:mm", "command", width-10)
        );

        return deadline;
    }

    private FlowPane importantCommands(int width, int height) {
        FlowPane important = new FlowPane();
        important.setId("module");
        important.setPrefSize(width, height);

        important.getChildren().addAll(
                labelGenerator("Add an important task", "sub-title", width-10),
                labelGenerator("/prioritize <task number>", "command", width-10)
        );

        return important;
    }

    private FlowPane timedCommands(int width, int height) {
        FlowPane timed = new FlowPane();
        timed.setId("module");
        timed.setPrefSize(width, height);

        timed.getChildren().addAll(
                labelGenerator("Add a timed task", "sub-title", width-10),
                labelGenerator("<task description> /from dd-mm-yy hh:mm "+
                        "/to dd-mm-yy hh:mm", "command", width-10)
        );

        return timed;
    }

    private FlowPane editCommands(int width, int height) {
        FlowPane edit = new FlowPane();
        edit.setId("module");
        edit.setPrefSize(width, height);

        edit.getChildren().addAll(
                labelGenerator("Edit a task", "sub-title", width-10),
                labelGenerator("/edit <task number> /by dd-mm-yy hh:mm\n" +
                        "/edit <task number> /from dd-mm-yy hh:mm /to dd-mm-yy hh:mm\n" +
                        "/edit <task number> /tag", "command", width-10)
        );

        return edit;
    }

    private FlowPane prioritizeCommands(int width, int height) {
        FlowPane prioritize = new FlowPane();
        prioritize.setId("module");
        prioritize.setPrefSize(width, height);

        prioritize.getChildren().addAll(
                labelGenerator("Prioritize a task", "sub-title", width-10),
                labelGenerator("/prioritize <task number>", "command", width-10)
        );

        return prioritize;
    }

    private FlowPane undoRedoCommands(int width, int height) {
        FlowPane prioritize = new FlowPane();
        prioritize.setId("module");
        prioritize.setPrefSize(width, height);

        prioritize.getChildren().addAll(
                labelGenerator("Undo or redo", "sub-title", width-10),
                labelGenerator("/undo OR /redo", "command", width-10)
        );

        return prioritize;
    }

    private FlowPane tagCommands(int width, int height) {
        FlowPane tag = new FlowPane();
        tag.setId("module");
        tag.setPrefSize(width, height);

        tag.getChildren().addAll(
                labelGenerator("Tag a task", "sub-title", width-10),
                labelGenerator("<task description> /tag <tag>", "command", width-10)
        );

        return tag;
    }

    private FlowPane deleteCommands(int width, int height) {
        FlowPane delete = new FlowPane();
        delete.setId("module");
        delete.setPrefSize(width, height);

        delete.getChildren().addAll(
                labelGenerator("Delete a task", "sub-title", width-10),
                labelGenerator("/delete <task number>", "command", width-10)
        );

        return delete;
    }

    private FlowPane doneCommands(int width, int height) {
        FlowPane done = new FlowPane();
        done.setId("module");
        done.setPrefSize(width, height);

        done.getChildren().addAll(
                labelGenerator("Mark a task as done", "sub-title", width-10),
                labelGenerator("/done <task number>", "command", width-10)
        );

        return done;
    }

    private FlowPane viewCommands(int width, int height) {
        FlowPane view = new FlowPane();
        view.setId("module");
        view.setPrefSize(width, height);

        view.getChildren().addAll(
                labelGenerator("Change views", "sub-title", width-10),
                labelGenerator("All Tasks: /view all\n"+
                        "All done tasks: /view done\n"+
                        "All undone tasks: /view undone\n"+
                        "All deadline tasks: /view deadline\n"+
                        "All timed tasks: /view timed\n"+
                        "All floating tasks: /view floating\n"+
                        "All important tasks: /view important\n"+
                        "Today's tasks: /view today\n"+
                        "Tomorrow's tasks: /view tomorrow\n"+
                        "View some other date's tasks: /view dd-mm-yy\n"+
                        "View dashboard: /view dashboard", "command", width-10)
        );

        return view;
    }

    private FlowPane shortcutCommands(int width, int height) {
        FlowPane shortcut = new FlowPane();
        shortcut.setId("module");
        shortcut.setPrefSize(width, height);

        shortcut.getChildren().addAll(
                labelGenerator("Shortcut keys", "sub-title", width-10),
                labelGenerator("Next page: Ctrl + <Right>\n"+
                        "Previous page: Ctrl + <left>\n"+
                        "First page: Ctrl + <Up>\n"+
                        "Last page: Ctrl + <Down>\n"+
                        "Minimise Window: Ctrl + M\n"+
                        "Exit: Esc", "command", width-10)
        );

        return shortcut;
    }

    private Label labelGenerator(String value, String id, int width) {
        Label label = new Label(value);
        label.setId(id);
        label.setPrefWidth(width);
        return label;
    }

    private void closeHelpView(Scene scene) {
        FadeTransition ft = new FadeTransition(Duration.millis(250), scene.getRoot());
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.play();
        ft.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                Stage innerStage = (Stage) scene.getWindow();
                innerStage.close();
            }
        });
    }
}
