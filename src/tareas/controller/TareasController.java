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
                tareas.insertTask();
            case EDIT_COMMAND:
                // TODO add correct/stub method(s) to call
            case DELETE_COMMAND:
                tareas.deleteTask();
            case SEARCH_COMMAND:
                // TODO add correct/stub method(s) to call
            case DONE_COMMAND:
                // TODO add correct/stub method(s) to call
            case UNDO_COMMAND:
                // TODO add correct/stub method(s) to call
            case REDO_COMMAND:
                // TODO add correct/stub method(s) to call
            case POSTPONE_COMMAND:
                // TODO add correct/stub method(s) to call
            case VIEW_COMMAND:
                // TODO add correct/stub method(s) to call
            case PRIORITIZE_COMMAND:
                // TODO add correct/stub method(s) to call
            case CATEGORIZE_COMMAND:
                // TODO add correct/stub method(s) to call
            case REMIND_COMMAND:
                // TODO add correct/stub method(s) to call
            case BACKUP_COMMAND:
                // TODO add correct/stub method(s) to call
            case MUTE_COMMAND:
                // TODO add correct/stub method(s) to call
            case FONT_COMMAND:
                // TODO add correct/stub method(s) to call
            case COLOR_COMMAND:
                // TODO add correct/stub method(s) to call
            default:
                // TODO throw a TareasException in that nothing is recognised?
        }
    }

    /**
     * undoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private void undo() {
        if (isAbleToUndo()) {
            tareas.sendUndoState();
            TareasGUI.sendUndoState();
        } else {
            TareasGUI.feedback("Nothing to undo.");
        }
    }

    /**
     * redoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private void redo() {
        if (isAbleToUndo()) {
            tareas.sendRedoState();
            TareasGUI.sendRedoState();
        } else {
            TareasGUI.feedback("Nothing to redo");
        }
    }

    /**
     * checks if there is any redo history to redo
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
}
