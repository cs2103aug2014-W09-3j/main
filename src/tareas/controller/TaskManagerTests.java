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
 *         This class contains the unit tests for the TaskManager class
 */
public class TaskManagerTests {

    TaskManager taskManager = TaskManager.getInstance();

    /**
     * TaskManager test for making sure that this TaskManager's latestTasks is empty
     *
     * boundary case for when latestTasks is empty upon initialization
     */
    @Test
    public void latestTasksIsEmpty() throws IOException {
        ArrayList<Task> emptyList = taskManager.get();

        assertEquals(new ArrayList<Task>(), emptyList);
    }

    /**
     * TaskManager test for making sure that TaskManager supports sets the ArrayList of Task properly
     *
     * normal case for when latestTasks is not empty after adding something
     */
    @Test
    public void latestTasksIsNotEmptyAfterSet() throws IOException {
        Task testTask = new Task();
        ArrayList<Task> testArrayListTask = new ArrayList<>();

        testArrayListTask.add(testTask);

        taskManager.set(testArrayListTask);

        assertNotEquals(0, taskManager.get().size());
        assertNotEquals(null, taskManager.get().get(0));

        taskManager.set(new ArrayList<>());
    }

    /**
     * TaskManager test for making sure that TaskManager supports sets Id properly
     *
     * equivalence partitioning test for TaskManager's setting id method - i.e. zero, positive and negative numbers are
     * all tested
     */
    @Test
    public void latestTasksIsIdChangesAfterSettingIt() throws IOException {
        // Zero
        taskManager.setId(0);

        assertEquals(0, taskManager.getId());

        // Positive value
        taskManager.setId(3);

        assertEquals(3, taskManager.getId());

        // Negative value
        taskManager.setId(-1);

        assertEquals(-1, taskManager.getId());

        taskManager.setId(1);
    }

    /**
     * TaskManager test for making sure that this TaskManager's redoStack is an empty stack
     *
     * boundary case for when redoStack is empty upon initialization
     */
    @Test
    public void redoStackIsEmpty() throws IOException {
        Stack<Tasks> emptyStack = taskManager.getRedoStack();

        assertEquals(new Stack<Tasks>(), emptyStack);
    }

    /**
     * TaskManager test for making sure that this TaskManager's historyStack is an empty stack
     *
     * boundary case for when historyStack is empty upon initialization
     */
    @Test
    public void historyStackIsEmpty() throws IOException {
        Stack<Tasks> emptyStack = taskManager.getUndoStack();

        assertEquals(new Stack<Tasks>(), emptyStack);
    }

    /**
     * TaskManager test for making sure that TaskManager with empty redoStack disallows for redoing
     *
     * boundary case for when redoStack is empty upon initialization and should disallow users from doing a redo action
     */
    @Test
    public void unableToRedo() throws IOException {
        boolean isAbleToRedo = taskManager.isAbleToRedo();

        assertEquals(false, isAbleToRedo);
    }

    /**
     * TaskManager test for making sure that TaskManager with empty historyStack disallows for undoing
     *
     * boundary case for when historyStack is empty upon initialization and should disallow users from doing a redo
     * action
     */
    @Test
    public void UnableToUndo() throws IOException {
        boolean isAbleToUndo = taskManager.isAbleToUndo();

        assertEquals(false, isAbleToUndo);
    }

    /**
     * TaskManager test for making sure that TaskManager allows for a task change
     *
     * normal case for whenever a the Tasks state has changed
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
     *
     * normal case for when historyStack is empty upon initialization and should allow for a state to be pushed in
     * through a simulation of a task change action (any action that involves changing the Tasks state)
     */
    @Test
    public void historyStackAllowPushing() throws IOException {
        Task testTask = new Task();
        Task testTask2 = new Task();
        ArrayList<Task> testArrayListTask1 = new ArrayList<>();
        ArrayList<Task> testArrayListTask2 = new ArrayList<>();

        testArrayListTask1.add(testTask);

        // simulate first task added
        taskManager.tasksChanged(testArrayListTask1);

        testArrayListTask2.add(testTask);
        testArrayListTask2.add(testTask2);

        // simulate second task added
        taskManager.tasksChanged(testArrayListTask2);

        Stack<Tasks> testHistoryStack = new Stack<>();

        Tasks testTasks = new Tasks();
        testTasks.set(testArrayListTask1);

        testHistoryStack.push(testTasks);

        Stack<Tasks> undoStack = taskManager.getUndoStack();

        // unable to test for equality of the stack, so we test size and task equality since this test is quite controlled
        assertEquals(testHistoryStack.peek().get().size(), undoStack.peek().get().size());
        assertEquals(testHistoryStack.peek().get().get(0), undoStack.peek().get().get(0));

        taskManager.set(new ArrayList<>());
        taskManager.clearHistoryState();
    }

    /**
     * TaskManager test for making sure that TaskManager's redoStack allows for pushing
     *
     * normal case for when redoStack is empty upon initialization and should allow for a state to be pushed in
     * through a simulation of an undo action
     */
    @Test
    public void redoStackAllowPushing() throws IOException {
        Task testTask = new Task();
        Task testTask2 = new Task();
        ArrayList<Task> testArrayListTask1 = new ArrayList<>();
        ArrayList<Task> testArrayListTask2 = new ArrayList<>();

        testArrayListTask1.add(testTask);

        // simulate first task added
        taskManager.tasksChanged(testArrayListTask1);

        testArrayListTask2.add(testTask);
        testArrayListTask2.add(testTask2);

        // simulate second task added
        taskManager.tasksChanged(testArrayListTask2);

        // simulate an undo action
        taskManager.getUndoState();

        Stack<Tasks> testHistoryStack = new Stack<>();

        Tasks testTasks = new Tasks();
        testTasks.set(testArrayListTask2);

        testHistoryStack.push(testTasks);

        Stack<Tasks> redoStack = taskManager.getRedoStack();

        // unable to test for equality of the stack, so we test size and task equality since this test is quite controlled
        assertEquals(testHistoryStack.peek().get().size(), redoStack.peek().get().size());
        assertEquals(testHistoryStack.peek().get().get(0), redoStack.peek().get().get(0));
        assertEquals(testHistoryStack.peek().get().get(1), redoStack.peek().get().get(1));

        taskManager.set(new ArrayList<>());
        taskManager.clearHistoryState();
        taskManager.clearRedoState();
    }

    /**
     * TaskManager test for making sure that TaskManager with a non-empty historyStack allows for undoing
     *
     * normal case for when redoStack is not empty after simulation of an undo action and allows the user to do a redo
     * action
     */
    @Test
    public void ableToRedo() throws IOException {
        Task testTask = new Task();
        Task testTask2 = new Task();
        ArrayList<Task> testArrayListTask1 = new ArrayList<>();
        ArrayList<Task> testArrayListTask2 = new ArrayList<>();

        testArrayListTask1.add(testTask);

        // simulate first task added
        taskManager.tasksChanged(testArrayListTask1);

        testArrayListTask2.add(testTask);
        testArrayListTask2.add(testTask2);

        // simulate second task added
        taskManager.tasksChanged(testArrayListTask2);

        // simulate an undo action
        taskManager.getUndoState();

        boolean isAbleToRedo = taskManager.isAbleToRedo();

        assertEquals(true, isAbleToRedo);

        taskManager.set(new ArrayList<>());
        taskManager.clearHistoryState();
        taskManager.clearRedoState();
    }

    /**
     * TaskManager test for making sure that TaskManager with a non-empty historyStack allows for undoing
     *
     * normal case for when redoStack is not empty after simulation of a task change and allows the user to do an undo
     * action
     */
    @Test
    public void ableToUndo() throws IOException {
        Task testTask = new Task();
        Task testTask2 = new Task();
        ArrayList<Task> testArrayListTask1 = new ArrayList<>();
        ArrayList<Task> testArrayListTask2 = new ArrayList<>();

        testArrayListTask1.add(testTask);

        // simulate first task added
        taskManager.tasksChanged(testArrayListTask1);

        testArrayListTask2.add(testTask);
        testArrayListTask2.add(testTask2);

        // simulate second task added
        taskManager.tasksChanged(testArrayListTask2);

        boolean isAbleToUndo = taskManager.isAbleToUndo();

        assertEquals(true, isAbleToUndo);

        taskManager.set(new ArrayList<>());
        taskManager.clearHistoryState();
    }

    /**
     * TaskManager test for making sure that TaskManager supports building floating tasks
     *
     * normal case for TaskManager supporting building of floating tasks
     */
    @Test
    public void supportBuildingFloatingTasks() throws IOException {
        TareasCommand testTareasCommand = TareasCommand.fromString("buy ham");

        Task testBuyHamTask = TaskManager.buildTask(testTareasCommand);

        // make sure it has description but not any kind of deadline or start time end time
        assertEquals(testBuyHamTask.getDescription(), "buy ham");
        assertEquals(testBuyHamTask.getStartDateTime(), null);
        assertEquals(testBuyHamTask.getEndDateTime(), null);
        assertEquals(testBuyHamTask.getDeadline(), null);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building floating tagged tasks
     *
     * normal case for TaskManager supporting building of floating tagged tasks
     */
    @Test
    public void supportBuildingFloatingTaggedTasks() throws IOException {
        // TODO add testing for this case and correct assert case (Note: NOT YET SUPPORTED)

        // TODO set to fail once supported
        assertEquals(true, true);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building timed tasks
     *
     * normal case for TaskManager supporting building of timed tasks
     */
    @Test
    public void supportBuildingTimedTasks() throws IOException {
        // TODO add testing for this case and correct assert case (Note: NOT YET SUPPORTED)

        // TODO set to fail once supported
        assertEquals(true, true);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building deadline tasks
     *
     * normal case for TaskManager supporting building of deadline tasks
     */
    @Test
    public void supportBuildingDeadlineTasks() throws IOException {
        // TODO add testing for this case and correct assert case (Note: NOT YET SUPPORTED)

        // TODO set to fail once supported
        assertEquals(true, true);
    }

    /**
     * TaskManager test for making sure that TaskManager supports building recurring tasks
     *
     * normal case for TaskManager supporting building of recurring tasks
     */
    @Test
    public void supportBuildingRecurringTasks() throws IOException {
        // TODO add testing for this case and correct assert case (Note: NOT YET SUPPORTED)

        // TODO set to fail once supported
        assertEquals(true, true);
    }
}
