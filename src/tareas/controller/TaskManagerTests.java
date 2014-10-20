package tareas.controller;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import tareas.common.Task;
import tareas.common.Tasks;

import static org.junit.Assert.*;

/**
 * @author Yap Jun Hao
 *         <p/>
 *         This class contains the unit test for the TaskManager class
 */
public class TaskManagerTests {

    TaskManager taskManager = TaskManager.getInstance();

    /**
     * TaskManager test for making sure that this TaskManager's latestTasks is empty
     */
    @Test
    public void latestTasksIsEmpty() throws IOException {
        ArrayList<Task> emptyList = taskManager.get();

        assertEquals(new ArrayList<Task>(), emptyList);
    }

    /**
     * TaskManager test for making sure that this TaskManager's redoStack is empty, a.k.a pop points to null
     */
    @Test
    public void redoStackIsEmpty() throws IOException {
        Stack<Tasks> emptyStack = taskManager.getRedoStack();

        assertEquals(new Stack<Tasks>(), emptyStack);
    }

    /**
     * TaskManager test for making sure that this TaskManager's historyStack is empty
     */
    @Test
    public void historyStackIsEmpty() throws IOException {
        Stack<Tasks> emptyStack = taskManager.getUndoStack();

        assertEquals(new Stack<Tasks>(), emptyStack);
    }

    /**
     * TaskManager test for making sure that TaskManager with empty redoStack disallows for redoing
     */
    @Test
    public void unableToRedo() throws IOException {
        boolean isAbleToRedo = taskManager.isAbleToRedo();

        assertEquals(false, isAbleToRedo);
    }

    /**
     * TaskManager test for making sure that TaskManager with empty historyStack disallows for undoing
     */
    @Test
    public void UnableToUndo() throws IOException {
        boolean isAbleToUndo = taskManager.isAbleToUndo();

        assertEquals(false, isAbleToUndo);
    }
}
