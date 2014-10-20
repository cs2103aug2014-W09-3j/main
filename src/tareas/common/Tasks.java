package tareas.common;

import java.util.ArrayList;

/**
 * @author Wong Her Lung
 *         <p/>
 *         This class is for Gson to use
 */
public class Tasks {
    private int latestID;
    private ArrayList<Task> allTasks;

    public Tasks() {
        allTasks = new ArrayList<>();
        latestID = 0;
    }

    public Tasks(Tasks another) {
        this.allTasks = (ArrayList<Task>) another.allTasks.clone();
        this.latestID = another.getLatestID();
    }

    public ArrayList<Task> get() {
        return this.allTasks;
    }

    public void set(ArrayList<Task> tasks){
        this.allTasks = tasks;
    }

    public int getLatestID() {
        return this.latestID;
    }

    public void incrementID() {
        latestID++;
    }

    public void setID(int id) {
        this.latestID = id;
    }
}
