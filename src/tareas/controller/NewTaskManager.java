package tareas.controller;

import tareas.common.Task;
import tareas.common.Tasks;
import tareas.parser.TareasCommand;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Her Lung on 20/10/2014.
 */
public class NewTaskManager {
    private static NewTaskManager instance = null;

    private Tasks latestTasks = new Tasks();
    private Stack<Tasks> historyStack = new Stack<Tasks>();
    private Stack<Tasks> redoStack = new Stack<Tasks>();

    public static NewTaskManager getInstance() {
        if (instance == null) {
            instance = new NewTaskManager();
        }
        return instance;
    }

    public ArrayList<Task> get(){
        return latestTasks.get();
    }

    public ArrayList<Task> getUndoState(){
        redoStack.push(latestTasks);
        latestTasks.set(historyStack.pop().get());
        return latestTasks.get();
    }

    public ArrayList<Task> getRedoState() {
        historyStack.push(latestTasks);
        latestTasks.set(redoStack.pop().get());
        return latestTasks.get();
    }

    public int getSize(){
        return historyStack.size();
    }

    public void add(Task task) {
        Tasks oldTasks = new Tasks(latestTasks);
        historyStack.push(oldTasks);
        ArrayList<Task> newList = new ArrayList<Task>();
        newList = latestTasks.get();
        newList.add(task);
        System.out.println(oldTasks.get().size());
        System.out.println(latestTasks.get().size());
    }

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

    public void set(ArrayList<Task> tasks) {
        latestTasks.set(tasks);
    }

    public boolean isAbleToRedo() {
        if(redoStack.empty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isAbleToUndo() {
        if(historyStack.empty()) {
            return false;
        } else {
            return true;
        }
    }

    public void clearRedoState() {
        redoStack.clear();
    }

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
