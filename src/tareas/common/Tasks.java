package tareas.common;

import java.util.ArrayList;

/**
 * @author Yap Jun Hao
 *         <p/>
 *         This class is for Gson to use
 */
public class Tasks {
    private int latestID;
    private ArrayList<Task> allTasks;

    public  Tasks() {
        allTasks = new ArrayList<Task>();
        latestID = 0;
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
}
