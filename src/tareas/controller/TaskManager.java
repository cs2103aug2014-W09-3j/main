//@author A0113694A

package tareas.controller;

import tareas.common.Task;
import tareas.common.Tasks;
import tareas.parser.TareasCommand;
import tareas.parser.Parser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Yap Jun Hao
 *         <p/>
 *         This class manages the Task objects and keeps the historystack and redostack intact to support undo and redo
 */
//@author A0113694A
public class TaskManager {
    private static TaskManager instance = null;

    // Keep a pointer to the latest set of tasks
    private Tasks latestTasks = new Tasks();

    // Keeping a stack of states for both redoing and undoing
    private Stack<Tasks> historyStack = new Stack<>();
    private Stack<Tasks> redoStack = new Stack<>();

    /**
     * private constructor - singleton
     */
    //@author A0113694A
    private TaskManager() {
        // do nothing
    }

    /**
     * use getInstance method to get the TaskManager instead
     */
    //@author A0113694A
    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    /**
     * gets the latest ArrayList of Task in the TaskManager
     *
     * @return an ArrayList of task of the latest tasks
     */
    //@author A0113694A
    public ArrayList<Task> get() {
        return latestTasks.get();
    }

    /**
     * gets the latest state to undo to in the TaskManager
     *
     * @return a Tasks object containing ArrayList of task of the undo state
     */
    //@author A0113694A
    public Tasks getUndoState() {
        Tasks toPushToRedoStack = new Tasks(latestTasks);
        redoStack.push(toPushToRedoStack);

        Tasks historyState = historyStack.pop();

        latestTasks.set(historyState.get());
        latestTasks.setID(historyState.getLatestID());
        return latestTasks;
    }

    /**
     * gets the latest state to redo to in the TaskManager
     *
     * @return a Tasks object containing ArrayList of task of the redo state
     */
    //@author A0113694A
    public Tasks getRedoState() {
        Tasks toPushToHistory = new Tasks(latestTasks);
        historyStack.push(toPushToHistory);

        Tasks redoState = redoStack.pop();

        latestTasks.set(redoState.get());
        latestTasks.setID(redoState.getLatestID());
        return latestTasks;
    }

    /**
     * for testing purposes - returns the redo stack
     *
     * @return the redoStack
     */
    //@author A0113694A
    protected Stack<Tasks> getRedoStack() {
        return this.redoStack;
    }

    /**
     * for testing purposes - returns the history stack
     *
     * @return the historyStack
     */
    //@author A0113694A
    protected Stack<Tasks> getUndoStack() {
        return this.historyStack;
    }

    /**
     * sets the latest tasks of the TaskManager into the one given
     *
     * @param tasks to be set
     */
    //@author A0113694A
    public void set(ArrayList<Task> tasks) {
        latestTasks.set(tasks);
    }

    /**
     * sets the latest tasks id of TaskManager
     *
     * @param id to be set
     */
    //@author A0113694A
    public void setId(int id) {
        latestTasks.setID(id);
    }

    /**
     * for testing purposes - gets the latest tasks id that is most recently set
     *
     * @return latest id to be set
     */
    //@author A0113694A
    protected int getId() {
        return latestTasks.getLatestID();
    }


    /**
     * sets the latestTasks of the TaskManager into the one given
     *
     * @param tasks that has been changed to be updated to task manager
     */
    //@author A0113694A
    public void tasksChanged(ArrayList<Task> tasks) {
        Tasks toPushToHistory = new Tasks(latestTasks);
        historyStack.push(toPushToHistory);
        latestTasks.set(tasks);
    }

    /**
     * checks if there is any redo history to redo
     *
     * @return whether there is anything to redo
     */
    //@author A0113694A
    public boolean isAbleToRedo() {
        return !redoStack.empty();
    }

    /**
     * checks if there is any undo history to undo
     *
     * @return whether there is anything to undo
     */
    //@author A0113694A
    public boolean isAbleToUndo() {
        return !historyStack.empty();
    }

    /**
     * clears the redo history after any other action other than undo
     */
    //@author A0113694A
    public void clearRedoState() {
        redoStack.clear();
    }

    /**
     * for testing purposes - clears the history stack
     */
    //@author A0113694A
    protected void clearHistoryState() {
        historyStack.clear();
    }

    /**
     * builds a task using the command given by the user after being parsed by the parser
     *
     * @param command to be parsed into a Task object
     * @return Task
     */
    //@author A0113694A
    public static Task buildTask(TareasCommand command) {
        Task taskToReturn;

        if (command.hasKey("tag")) {
            String taskDescription = command.getPrimaryArgument();
            String taskTag = command.getArgument("tag");

            taskToReturn = Task.createTaggedTask(taskDescription, taskTag);
        } else if (command.hasKey("from")) {
            String taskDescription = command.getPrimaryArgument();
            LocalDateTime taskStartTime = Parser.getDateTimeFromString(command.getArgument("from"));
            LocalDateTime taskEndTime = Parser.getDateTimeFromString(command.getArgument("to"));

            taskToReturn = Task.createTimedTask(taskDescription, taskStartTime, taskEndTime);
        } else if (command.hasKey("by")) {
            String taskDescription = command.getPrimaryArgument();
            LocalDateTime taskDeadline = Parser.getDateTimeFromString(command.getArgument("by"));

            taskToReturn = Task.createDeadlineTask(taskDescription, taskDeadline);
        } else if (command.hasKey("recurring")) {
            String taskDescription = command.getPrimaryArgument();
            String taskRecurringType = command.getArgument("recurring");

            taskToReturn = Task.createRecurringTask(taskDescription, taskRecurringType);
        } else {
            // By default, the task type will be floating tasks
            String taskDescription = command.getPrimaryArgument();

            taskToReturn = Task.createFloatingTask(taskDescription);
        }

        return taskToReturn;
    }
}
