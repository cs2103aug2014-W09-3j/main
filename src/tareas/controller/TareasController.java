package tareas.controller;

import tareas.common.*;
import tareas.gui.*;
import tareas.parser.*;
import tareas.storage.*;
import java.util.ArrayList;

/**
 * @author Yap Jun Hao
 *
 * This class binds the other parts of the program together.
 */

public class TareasController {

    // Instantiate a TareasIO
    TareasIO tareas = new TareasIO();

    // Keeping an ArrayList of states for both redoing and undoing
    ArrayList<Tasks> redoHistory = new ArrayList<Tasks>();
    ArrayList<Tasks> undoHistory = new ArrayList<Tasks>();

    /**
     * Takes the user's input from the GUI and does the right stuff to make the program work
     *
     * @param userInput the user's input from TareasGUI
     */
    public void executeCommand(String userInput) {
        TareasCommand command = TareasCommand.fromString(userInput);

        switch (command) {
            case ADD_COMMAND:
                addTask(command);
            case EDIT_COMMAND:
                editTask(command);
            case DELETE_COMMAND:
                deleteTask(command);
            case SEARCH_COMMAND:
                searchTask(command);
            case DONE_COMMAND:
                completeTask(command);
            case UNDO_COMMAND:
                undo();
            case REDO_COMMAND:
                redo();
            case POSTPONE_COMMAND:
                postponeTask(command);
            case VIEW_COMMAND:
                viewRequest(command);
            case PRIORITIZE_COMMAND:
                prioritizeTask(command);
            case CATEGORIZE_COMMAND:
                categorizeTask(command);
            case REMIND_COMMAND:
                setTaskReminder(command);
            case BACKUP_COMMAND:
                backup();
            case MUTE_COMMAND:
                mute();
            case FONT_COMMAND:
                changeFont(command);
            case COLOR_COMMAND:
                colorizeTask(command);
            default:
                TareasGUI.feedback("Unrecognised command passed in");
                //TODO should we throw a TareasException or the sort?
        }
    }

    /**
     * adds a task by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void addTask(TareasCommand command) {
        tareas.insertTask();
        TareasGUI.taskInserted();
    }

    /**
     * edits a task by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void editTask(TareasCommand command) {
        tareas.editTask();
        TareasGUI.taskEdited();
    }

    /**
     * deletes a task by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void deleteTask(TareasCommand command) {
        tareas.deleteTask();
        TareasGUI.taskDeleted();
    }

    /**
     * searches a task by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void searchTask(TareasCommand command) {
        tareas.searchTask();
        TareasGUI.taskSearched();
    }

    /**
     * completes a task by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void completeTask(TareasCommand command) {
        tareas.markTaskAsDone();
        TareasGUI.taskDone();
    }

    /**
     * postpones a task by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void postponeTask(TareasCommand command) {
        tareas.postponeTask();
        TareasGUI.taskPostponed();
    }

    /**
     * completes a view request by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void viewRequest(TareasCommand command) {
        tareas.getTaskForView();
        TareasGUI.viewType();
    }

    /**
     * prioritize a task by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void prioritizeTask(TareasCommand command) {
        tareas.prioritizeTask();
        TareasGUI.taskPrioritized();
    }

    /**
     * categorize a task by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void categorizeTask(TareasCommand command) {
        tareas.categorizeTask();
        TareasGUI.taskcategorized();
    }

    /**
     * set a task reminder by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void setTaskReminder(TareasCommand command) {
        tareas.setTaskReminder();
        TareasGUI.taskReminderSet();
    }

    /**
     * backups all tasks data by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void backup() {
        tareas.backupData();
        TareasGUI.feedback("data backup-ed!");
    }

    /**
     * mute Tareas by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void mute() {
        tareas.muteTareas();
        TareasGUI.feedback("Tareas muted from ...");;
    }

    /**
     * changes Tareas font settings by calling the appropriate GUI method
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void changeFont(TareasCommand command) {
        TareasGUI.font();
    }

    /**
     * colorize a task by calling the appropriate GUI and storage method
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void colorizeTask(TareasCommand command) {
        tareas.editTask();
        TareasGUI.taskColorChanged();
    }

    /**
     * undoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private void undo() {
        try {
            if (isAbleToUndo()) {
                Tasks stateToRevertTo = undoHistory.remove(undoHistory.size() - 1);

                addToRedoHistory(statetoRevertTo);
                tareas.sendUndoState(stateToRevertTo);
                TareasGUI.sendUndoState(stateToRevertTo);
            } else {
                TareasGUI.feedback("Nothing to undo.");
            }
        } catch (IOException e) {
            //TODO throw a TareasException if something really bad happens?
        }
    }

    /**
     * redoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private void redo() {
        try {
            if (isAbleToUndo()) {
                Tasks stateToRevertTo = redoHistory.remove(redoHistory.size() - 1);

                addToUndoHistory(statetoRevertTo);
                tareas.sendRedoState(stateToRevertTo);
                TareasGUI.sendRedoState(stateToRevertTo);
            } else {
                TareasGUI.feedback("Nothing to redo");
            }
        } catch (IOException e) {
            //TODO throw a TareasException if something really bad happens?
        }
    }

    /**
     * checks if there is any redo history to redo
     *
     * @return
     */
    private boolean isAbleToRedo() {
        if (redoHistory.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * checks if there is any undo history to undo
     *
     * @return
     */
    private boolean isAbleToUndo() {
        if (undoHistory.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * checks if there is any undo history to undo
     *
     * @param state of the Tasks to add into the history
     */
    private void addToUndoHistory(Tasks state) {
        undoHistory.add(state);
    }

    /**
     * checks if there is any undo history to undo
     *
     * @param state of the Tasks to add into the history
     */
    private void addToRedoHistory(Tasks state) {
        redoHistory.add(state);
    }

    /**
     * clears the redo history after any other action other than undo
     */
    private void clearRedoState() {
        redoHistory.clear();
    }
}
