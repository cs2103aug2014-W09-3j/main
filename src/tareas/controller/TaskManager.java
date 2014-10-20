package tareas.controller;

import tareas.common.Task;
import tareas.common.Tasks;
import tareas.parser.TareasCommand;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Her Lung on 20/10/2014.
 *
 * This class manages the Task objects and keeps the historystack and redostack intact to support undo and redo
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
     */
    public ArrayList<Task> get(){
        return latestTasks.get();
    }

    /**
     * gets the latest state to undo to in the TaskManager
     */
    public ArrayList<Task> getUndoState(){
        redoStack.push(latestTasks);
        latestTasks.set(historyStack.pop().get());
        return latestTasks.get();
    }

    /**
     * gets the latest state to redo to in the TaskManager
     */
    public ArrayList<Task> getRedoState() {
        historyStack.push(latestTasks);
        latestTasks.set(redoStack.pop().get());
        return latestTasks.get();
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
     * adds a task into the TaskManager
     */
    public void add(Task task) {
        Tasks oldTasks = new Tasks(latestTasks);
        historyStack.push(oldTasks);
        ArrayList<Task> newList = latestTasks.get();
        newList.add(task);
        System.out.println(oldTasks.get().size());
        System.out.println(latestTasks.get().size());
    }

    /**
     * removes a task from the TaskManager
     */
    public void remove(int id) {
        historyStack.push(latestTasks);
        ArrayList<Task> newList;
        newList = latestTasks.get();
        Iterator<Task> iter = newList.iterator();
        while(iter.hasNext()) {
            Task task = iter.next();
            if(task.getTaskID() == id) {
                iter.remove();
            }
        }
        latestTasks.set(newList);
    }

    /**
     * sets the latestTasks of the TaskManager into the one given
     */
    public void set(ArrayList<Task> tasks) {
        latestTasks.set(tasks);
    }

    /**
     * checks if there is any redo history to redo
     *
     * @return whether there is anything to redo
     */
    public boolean isAbleToRedo() {
        if(redoStack.empty()) {
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
        if(historyStack.empty()) {
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
