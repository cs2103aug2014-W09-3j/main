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
     * TaskManager test for making sure that TaskManager supports sets the ArrayList of Task properly
     */
    @Test
    public void latestTasksIsNotEmptyAfterSet() throws IOException {
        // TODO add testing for this case and correct assert case

        assertEquals(true, false);
    }

    /**
     * TaskManager test for making sure that TaskManager supports sets Id properly
     */
    @Test
    public void latestTasksIsIdChangesAfterSettingIt() throws IOException {
        // TODO add testing for this case and correct assert case

        assertEquals(true, false);
    }

    /**
     * TaskManager test for making sure that this TaskManager's redoStack is an empty stack
     */
    @Test
    public void redoStackIsEmpty() throws IOException {
        Stack<Tasks> emptyStack = taskManager.getRedoStack();

        assertEquals(new Stack<Tasks>(), emptyStack);
    }

    /**
     * TaskManager test for making sure that this TaskManager's historyStack is an empty stack
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

    /**
     * TaskManager test for making sure that TaskManager allows for a task change
     */
    @Test
    public void taskChangedSupported() throws IOException {
        // TODO add testing for this case and correct assert case

        assertEquals(false, false);
    }

    /**
     * TaskManager test for making sure that TaskManager's redoStack allows for pushing
     */
    @Test
    public void redoStackAllowPushing() throws IOException {
        // TODO add testing for this case and correct assert case

        assertEquals(true, false);
    }

    /**
     * TaskManager test for making sure that TaskManager historyStack allows for pushing
     */
    @Test
    public void historyStackAllowPushing() throws IOException {
        // TODO add testing for this case and correct assert case

        assertEquals(true, false);
    }

    /**
     * TaskManager test for making sure that TaskManager with a non-empty historyStack allows for undoing
     */
    @Test
    public void ableToRedo() throws IOException {
        // TODO add testing for this case and correct assert case

        assertEquals(true, false);
    }

    /**
     * TaskManager test for making sure that TaskManager with a non-empty historyStack allows for undoing
     */
    @Test
    public void ableToUndo() throws IOException {
        // TODO add testing for this case and correct assert case

        assertEquals(true, false);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building floating tasks
     */
    @Test
    public void supportBuildingFloatingTasks() throws IOException {
        // TODO add testing for this case and correct assert case

        assertEquals(true, false);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building floating tagged tasks
     */
    @Test
    public void supportBuildingFloatingTaggedTasks() throws IOException {
        // TODO add testing for this case and correct assert case

        assertEquals(true, false);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building timed tasks
     */
    @Test
    public void supportBuildingTimedTasks() throws IOException {
        // TODO add testing for this case and correct assert case

        assertEquals(true, false);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building deadline tasks
     */
    @Test
    public void supportBuildingDeadlineTasks() throws IOException {
        // TODO add testing for this case and correct assert case

        assertEquals(true, false);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building recurring tasks
     */
    @Test
    public void supportBuildingRecurringTasks() throws IOException {
        // TODO add testing for this case and correct assert case

        assertEquals(true, false);
    }
}
