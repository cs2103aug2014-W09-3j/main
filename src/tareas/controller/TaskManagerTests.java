package tareas.controller;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Yap Jun Hao
 *         <p/>
 *         This class contains the unit test for the TaskManager class
 */
public class TaskManagerTests {

    TaskManager taskManager = TaskManager.getInstance();

    /**
     * TaskManager test for ...
     */
    @Test
    public void test1() throws IOException {
        taskManager.get();

        assertEquals(false, true);
    }

    /**
     * TaskManager test for ...
     */
    @Test
    public void test2() throws IOException {
        taskManager.get();

        assertEquals(false, true);
    }
}
