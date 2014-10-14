package tareas.controller;

import tareas.common.Task;

import java.util.ArrayList;

/**
 * @author Yap Jun Hao
 *
 * This class manages the Task objects as well as creating them for the controller
 */

public class TaskManager {
    private int latestID = 1;
    private ArrayList<Task> allTasks;

    public TaskManager() {
        this.allTasks = new ArrayList<Task>();
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

}
