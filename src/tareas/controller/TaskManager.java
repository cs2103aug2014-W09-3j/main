package tareas.controller;

import tareas.common.Task;
import tareas.parser.TareasCommand;

import java.util.ArrayList;

/**
 * @author Yap Jun Hao
 *
 * This class manages the Task objects and keeps the redohistory and undohistory intact to support undo and redo
 */

public class TaskManager {
    private int latestID = 1;
    private ArrayList<ArrayList<Task>> allTasks;
    private static TaskManager instance = null;

    // Keeping an ArrayList of states for both redoing and undoing
    private ArrayList<ArrayList<Task>> redoHistory = new ArrayList<ArrayList<Task>>();

    /**
     * private constructor - singleton
     */
    private TaskManager() {
        this.allTasks = new ArrayList<ArrayList<Task>>();
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
    public ArrayList<Task> get() {
        int latestId = this.allTasks.size();
        return this.allTasks.get(latestId);
    }

    /**
     * adds a task into the TaskManager by adding a new ArrayList of Task to allTasks
     */
    public void add(Task task) {
        ArrayList<Task> latestTaskList = this.get();
        ArrayList<Task> withTaskAdded = new ArrayList<Task>();

        int latestId = this.allTasks.size();

        for (int i = 0; i < latestId; i++) {
            withTaskAdded.add(latestTaskList.get(i));
        }

        withTaskAdded.add(task);
        this.allTasks.add(withTaskAdded);
    }

    /**
     * removes a task from the TaskManager by adding a new ArrayList of Task to allTasks without the task to remove
     */
    public void remove(int id) {
        ArrayList<Task> latestTaskList = this.get();
        ArrayList<Task> withTaskRemoved = new ArrayList<Task>();

        int latestId = this.allTasks.size();

        for (int i = 0; i < latestId; i++) {
            withTaskRemoved.add(latestTaskList.get(i));
        }

        withTaskRemoved.remove(id);
        this.allTasks.remove(id);
    }

    /**
     * sets the latestTaskList of the TaskManager into the one given
     */
    public void set(ArrayList<Task> tasks) {
        int latestId = this.allTasks.size();

        this.allTasks.set(latestId, tasks);
    }

    /**
     * gets the next ID to assign to a task being built
     */
    public int getNextID() {
        int nextID = this.latestID;
        this.latestID++;
        return nextID;
    }

    /**
     * gets the latest redo history from the task manager
     */
    public ArrayList<ArrayList<Task>> getRedoHistory() {
        return this.redoHistory;
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
