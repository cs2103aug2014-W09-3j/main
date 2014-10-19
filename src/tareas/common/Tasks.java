package tareas.common;

import java.util.ArrayList;

/**
 * @author Yap Jun Hao
 *         <p/>
 *         This class is for Gson to use
 */
public class Tasks {
    private ArrayList<Task> allTasks;

    public  Tasks() {
        allTasks = new ArrayList<Task>();
    }

    public ArrayList<Task> get() {
        return this.allTasks;
    }
}
