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

    public void executeCommand(String userInput) {
        TareasCommand command = TareasCommand.fromString(userInput);

        switch (command) {
            case ADD_COMMAND:
                // TODO add correct method(s) to call
            case EDIT_COMMAND:
                // TODO add correct method(s) to call
            case DELETE_COMMAND:
                // TODO add correct method(s) to call
            case SEARCH_COMMAND:
                // TODO add correct method(s) to call
            case DONE_COMMAND:
                // TODO add correct method(s) to call
            case UNDO_COMMAND:
                // TODO add correct method(s) to call
            case REDO_COMMAND:
                // TODO add correct method(s) to call
            case POSTPONE_COMMAND:
                // TODO add correct method(s) to call
            case VIEW_COMMAND:
                // TODO add correct method(s) to call
            case PRIORITIZE_COMMAND:
                // TODO add correct method(s) to call
            case CATEGORIZE_COMMAND:
                // TODO add correct method(s) to call
            case REMIND_COMMAND:
                // TODO add correct method(s) to call
            case BACKUP_COMMAND:
                // TODO add correct method(s) to call
            case MUTE_COMMAND:
                // TODO add correct method(s) to call
            case FONT_COMMAND:
                // TODO add correct method(s) to call
            case COLOR_COMMAND:
                // TODO add correct method(s) to call
            default:
                // TODO throw a TareasException in that nothing is recognised?
        }
    }

    public void undo() {
        if (isAbleToUndo()) {
            tareas.sendUndoState();
            TareasGUI.sendUndoState();
        } else {
            TareasGUI.feedback("Nothing to undo.");
        }
    }

    public void redo() {
        if (isAbleToUndo()) {
            tareas.sendRedoState();
            TareasGUI.sendRedoState();
        } else {
            TareasGUI.feedback("Nothing to redo");
        }
    }

    private boolean isAbleToRedo() {
        if (redoHistory.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isAbleToUndo() {
        if (undoHistory.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void addToUndoHistory(Tasks state) {
        undoHistory.add(state);
    }

    private void addToRedoHistory(Tasks state) {
        redoHistory.add(state);
    }
}
