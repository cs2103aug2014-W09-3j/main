package tareas.common;

import java.util.ArrayList;

/**
 * @author Her Lung
 * 
 * This class stores an ArrayList of Task objects.
 */

public class Tasks {
	private ArrayList<Task> allTasks;
	
	public Tasks() {
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
}
