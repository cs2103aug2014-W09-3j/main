package tareas.controller;

import tareas.storage.TareasIO;
import tareas.common.Task;
import tareas.parser.TareasCommand;

import java.util.ArrayList;

/**
 * @author Yap Jun Hao
 *
 * This class manages the Task objects as well as creating them for the controller
 */

public class TaskManager {
    private int latestID = 1;
    private ArrayList<Task> allTasks;
    private TareasIO tareasIo = new TareasIO();

    public TaskManager() {
        this.allTasks = tareasIo.getAllTasks().get();
    }

    public void add(Task task) {
        this.allTasks.add(task);
    }

    public void remove(int id) {
        this.allTasks.remove(id);
    }

    public ArrayList<Task> get() {
        return this.allTasks;
    }

    public void set(ArrayList<Task> tasks) {
        this.allTasks = tasks;
    }

    public int getNextID() {
        int nextID = this.latestID;
        this.latestID++;
        return nextID;
    }

    /**
     * builds a task using the command given by the user after being parsed by the parser
     *
     * @param command from the user input so that the task can be built
     */
    protected Task buildTask(TareasCommand command) {
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
            String taskDescription = command.getPrimaryArgument();

            taskToReturn = Task.createFloatingTask(taskDescription);
        }

        return taskToReturn;
    }

}
