package tareas.common;

import java.util.ArrayList;

/**
 * @author Her Lung
 * 
 * This class stores an ArrayList of Task objects.
 */

public class Tasks {
	private int latestID = 1;
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
	
	public void set(ArrayList<Task> tasks) {
		this.allTasks = tasks;
	}
	
	public int getNextID() {
		int nextID = this.latestID;
		this.latestID++;
		return nextID;
	}
	
}
