package tareas.controller;

import tareas.common.Task;
import tareas.common.Tasks;
import tareas.parser.TareasCommand;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * @author Yap Jun Hao
 *         <p/>
 *         This class manages the Task objects and keeps the historystack and redostack intact to support undo and redo
 */
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
    private TaskManager() {
        // do nothing
    }

    /**
     * use getInstance method to get the TaskManager instead
     */
    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    /**
     * gets the latest ArrayList of Task in the TaskManager
     *
     * @return an arraylist of task of the latest tasks
     */
    public ArrayList<Task> get(){
        return latestTasks.get();
    }

    /**
     * gets the latest state to undo to in the TaskManager
     *
     * @return an arraylist of task of the udno state
     */
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
     * @return an arraylist of task of the redo state
     */
    public Tasks getRedoState() {
        Tasks toPushToHistory = new Tasks(latestTasks);
        historyStack.push(toPushToHistory);

        Tasks redoState = redoStack.pop();

        latestTasks.set(redoState.get());
        latestTasks.setID(redoState.getLatestID());
        return latestTasks;
    }

    /**
     * gets the size of the history stack
     *
     * @return size of history stack
     */
    public int getSize(){
        return historyStack.size();
    }

    /**
     * sets the latestTasks of the TaskManager into the one given
     *
     * @param tasks
     */
    public void set(ArrayList<Task> tasks) {
        latestTasks.set(tasks);
    }

    /**
     * sets the latestTasks's id of TaskManager
     *
     * @param id
     */
    public void setId(int id) {
        latestTasks.setID(id);
    }


    /**
     * sets the latestTasks of the TaskManager into the one given
     *
     * @param tasks
     */
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
    public boolean isAbleToRedo() {
        if (redoStack.empty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * checks if there is any undo history to undo
     *
     * @return whether there is anything to undo
     */
    public boolean isAbleToUndo() {
        if (historyStack.empty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * clears the redo history after any other action other than undo
     */
    public void clearRedoState() {
        redoStack.clear();
    }

    /**
     * builds a task using the command given by the user after being parsed by the parser
     *
     * @param command
     * @return Task
     */
    public static Task buildTask(TareasCommand command) {
        Task taskToReturn = new Task();
        // Can remove in the future once all the different types are supported

        if (command.hasKey("tag")) {
            //TODO support tagged tasks
        } else if (command.hasKey("from")) {
            //TODO support timed tasks
        } else if (command.hasKey("by")) {
            //TODO support deadline tasks
        } else if (command.hasKey("recurring")) {
            //TODO support recurring tasks
        } else {
            // By default, the task type will be floating tasks
            String taskDescription = command.getPrimaryArgument();

            taskToReturn = Task.createFloatingTask(taskDescription);
        }

        return taskToReturn;
    }
}
