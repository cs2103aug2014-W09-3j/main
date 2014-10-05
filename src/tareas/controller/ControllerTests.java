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
    public void searchExactString() throws IOException {
        executeCommand("buy milk");

        assertEquals(getTask(1), "buy milk");
    }
}
