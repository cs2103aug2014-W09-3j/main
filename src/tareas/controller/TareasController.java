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

    TareasIO tareas = new TareasIO();

    ArrayList<Tasks> redoHistory = new ArrayList<Tasks>();
    ArrayList<Tasks> undoHistory = new ArrayList<Tasks>();

    public void executeCommand(String userInput) {
        TareasCommand command = TareasCommand.fromString(userInput);
    }

    public void undo() {

    }

    public void redo() {

    }

    private boolean isAbleToRedo() {
        if (history.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isAbleToUndo() {
        if (history.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
