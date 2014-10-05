package tareas.controller;

import tareas.common.*;
import tareas.gui.*;
import tareas.parser.*;
import tareas.storage.*;

/**
 * @author Yap Jun Hao
 *
 * This class binds the other parts of the program together.
 */

public class TareasController {

    public void executeCommand(String userInput) {
        TareasCommand command = TareasCommand.fromString(userInput);
    }

    public void undo() {
        // TODO STUB
    }

    public void redo() {
        // TODO STUB
    }
}
