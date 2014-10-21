package tareas.controller;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import tareas.common.Task;
import tareas.common.Tasks;
import tareas.parser.TareasCommand;

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
        Task testTask = new Task();
        ArrayList<Task> testArrayListTask = new ArrayList<>();

        testArrayListTask.add(testTask);

        taskManager.set(testArrayListTask);

        assertNotEquals(0, taskManager.get().size());

        taskManager.set(new ArrayList<>());
    }

    /**
     * TaskManager test for making sure that TaskManager supports sets Id properly
     */
    @Test
    public void latestTasksIsIdChangesAfterSettingIt() throws IOException {
        taskManager.setId(0);

        assertEquals(0, taskManager.getId());

        taskManager.setId(3);

        assertEquals(3, taskManager.getId());

        taskManager.setId(-1);

        assertEquals(-1, taskManager.getId());

        taskManager.setId(1);
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
        Task testTask = new Task();
        ArrayList<Task> testArrayListTask = new ArrayList<>();

        testArrayListTask.add(testTask);

        taskManager.tasksChanged(testArrayListTask);

        assertEquals(testArrayListTask, taskManager.get());

        taskManager.set(new ArrayList<>());
        taskManager.clearHistoryState();
    }

    /**
     * TaskManager test for making sure that TaskManager historyStack allows for pushing
     */
    @Test
    public void historyStackAllowPushing() throws IOException {
        Task testTask = new Task();
        Task testTask2 = new Task();
        ArrayList<Task> testArrayListTask1 = new ArrayList<>();
        ArrayList<Task> testArrayListTask2 = new ArrayList<>();

        testArrayListTask1.add(testTask);

        taskManager.tasksChanged(testArrayListTask1);

        testArrayListTask2.add(testTask);
        testArrayListTask2.add(testTask2);

        taskManager.tasksChanged(testArrayListTask2);

        Stack<Tasks> testHistoryStack = new Stack<>();

        Tasks testTasks = new Tasks();
        testTasks.set(testArrayListTask1);

        testHistoryStack.push(testTasks);

        // unable to test for equality of the stack, so we test size since this test is quite controlled
        assertEquals(testHistoryStack.peek().get().size(), taskManager.getUndoStack().peek().get().size());

        taskManager.set(new ArrayList<>());
        taskManager.clearHistoryState();
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
        Task testTask = new Task();
        Task testTask2 = new Task();
        ArrayList<Task> testArrayListTask1 = new ArrayList<>();
        ArrayList<Task> testArrayListTask2 = new ArrayList<>();

        testArrayListTask1.add(testTask);

        taskManager.tasksChanged(testArrayListTask1);

        testArrayListTask2.add(testTask);
        testArrayListTask2.add(testTask2);

        taskManager.tasksChanged(testArrayListTask2);

        boolean isAbleToUndo = taskManager.isAbleToUndo();

        assertEquals(true, isAbleToUndo);

        taskManager.set(new ArrayList<>());
        taskManager.clearHistoryState();
    }

    /**
     * TaskManager test for making sure that TaskManager supports building floating tasks
     */
    @Test
    public void supportBuildingFloatingTasks() throws IOException {
        TareasCommand testTareasCommand = TareasCommand.fromString("buy ham");

        Task testBuyHamTask = TaskManager.buildTask(testTareasCommand);

        assertEquals(testBuyHamTask.getDescription(), "buy ham");
        assertEquals(testBuyHamTask.getStartDateTime(), null);
        assertEquals(testBuyHamTask.getEndDateTime(), null);
        assertEquals(testBuyHamTask.getDeadline(), null);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building floating tagged tasks
     */
    @Test
    public void supportBuildingFloatingTaggedTasks() throws IOException {
        // TODO add testing for this case and correct assert case (Note: NOT YET SUPPORTED)

        // TODO set to fail once supported
        assertEquals(true, true);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building timed tasks
     */
    @Test
    public void supportBuildingTimedTasks() throws IOException {
        // TODO add testing for this case and correct assert case (Note: NOT YET SUPPORTED)

        // TODO set to fail once supported
        assertEquals(true, true);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building deadline tasks
     */
    @Test
    public void supportBuildingDeadlineTasks() throws IOException {
        // TODO add testing for this case and correct assert case (Note: NOT YET SUPPORTED)

        // TODO set to fail once supported
        assertEquals(true, true);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building recurring tasks
     */
    @Test
    public void supportBuildingRecurringTasks() throws IOException {
        // TODO add testing for this case and correct assert case (Note: NOT YET SUPPORTED)

        // TODO set to fail once supported
        assertEquals(true, true);
    }
}
