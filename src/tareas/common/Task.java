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
	
	public Task() {
		// Set default values for recurrence
		this.recurrence.put("frequency", null);
		this.recurrence.put("date", null);
		this.recurrence.put("day", null);
	}
	
	public int getTaskID() {
		return this.taskID;
	}
	
	public void setTaskID(int id) {
		this.taskID = id;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDeadline() {
		return this.deadline;
	}
	
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public String getStartDateTime() {
		return this.startDatetime;
	}
	
	public void setStartDateTime(String dateTime) {
		this.startDatetime = dateTime;
	}
	
	public String getEndDateTime() {
		return this.endDateTime;
	}
	
	public void setEndDateTime(String dateTime) {
		this.endDateTime = dateTime;
	}
	
	public String getRecurrenceFrequency() {
		return this.recurrence.get("frequency");
	}
	
	public void setRecurrenceFrequency(String freq) {
		this.recurrence.put("frequency", freq);
	}
	
	public String getRecurrenceDate() {
		return this.recurrence.get("date");
	}
	
	public void setRecurrenceDate(String date) {
		this.recurrence.put("date", date);
	}
	
	public String getRecurrenceDay() {
		return this.recurrence.get("day");
	}
	
	public void setRecurrenceDay(String day) {
		this.recurrence.put("day", day);
	}
	
	public ArrayList<String> getTags() {
		return this.tags;
	}
	
	public void addTag(String tag) {
		this.tags.add(tag);
	}
	
	public boolean isTaskCompleted() {
		return this.completed;
	}
	
	public void markTaskCompleted() {
		this.completed = true;
	}
	
	public boolean isTaskPriority() {
		return this.priority;
	}
	
	public void setTaskAsPriority() {
		this.priority = true;
	}
	
	public String getReminderDateTime() {
		return this.reminderDateTime;
	}
	
	public void setReminderDateTime(String dateTime) {
		this.reminderDateTime = dateTime;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * This method creates a floating task.
	 * @param description
	 * @return Task
	 */
	public static Task createFloatingTask(String description) {
		Task floatingTask = new Task();
		floatingTask.setDescription(description);
		return floatingTask;
	}
	
	
}
