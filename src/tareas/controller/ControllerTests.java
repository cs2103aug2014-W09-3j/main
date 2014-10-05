package tareas.controller;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import tareas.common.*;
import tareas.gui.*;
import tareas.parser.*;
import tareas.storage.*;

/**
 * @author Yap Jun Hao
 *
 * This class contains the tests for the Tareas Controller.
 */

public class ControllerTests {

    /**
     * Controller test for adding a task
     */
    @Test
    public void addATask() throws IOException {
        executeCommand("buy milk");

        assertEquals(getTask(1), "buy milk");
    }

    /**
     * Controller test for undoing an action
     */
    @Test
    public void undo() throws IOException {
        executeCommand("buy milk");
        executeCommand("undo");

        assertEquals(getTask(1), "nothing");
    }

    /**
     * Controller test for undoing an action failure
     */
    @Test
    public void redo() throws IOException {
        executeCommand("undo");

        assertEquals("failure");
    }

    /**
     * Controller test for redoing an action
     */
    @Test
    public void redo() throws IOException {
        executeCommand("buy milk");
        executeCommand("undo");
        executeCommand("redo");

        assertEquals(getTask(1), "buy milk");
    }

    /**
     * Controller test for redoing an action
     */
    @Test
    public void redo() throws IOException {
        executeCommand("buy milk");
        executeCommand("redo");

        assertEquals("failure");
    }
}
