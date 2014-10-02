package tareas.common;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Her Lung
 * 
 * This class models all types of tasks that can be created in Tareas.
 * If any attributes do not apply to the task that is being created, leave it as null.
 */

public class Task {
	// Unique ID of the task
	private int taskID;
	
	// Category name of the task
	private String category;
	
	// Description of the task
	private String description;
	
	// Deadline of the task
	private String deadline;
	
	// Start date-time of the task
	private String startDatetime;
	
	// End date-time of the task
	private String endDateTime;
	
	// Recurrence of the task;
	private HashMap<String, String> recurrence = new HashMap<String, String>();
	
	// Tags given to the task
	private ArrayList<String> tags = new ArrayList<String>();
	
	// Flag to determine if task is already done or not
	private boolean completed = false;
	
	// Flag to determine if task is high priority or not
	private boolean priority = false;
	
	// Date-time for reminder for this task
	private String reminderDateTime;
	
	// Color of task
	private String color;
	
	Task () {
		// Set default values for recurrence
		this.recurrence.put("frequency", null);
		this.recurrence.put("Date", null);
		this.recurrence.put("Day", null);
	}
	
}
