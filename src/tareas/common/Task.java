package tareas.common;

import java.util.ArrayList;
import java.util.HashMap;

import java.time.LocalDateTime;

/**
 * @author Her Lung
 *         <p/>
 *         This class models all types of tasks that can be created in Tareas.
 *         If any attributes do not apply to the task that is being created, leave it as null.
 */

public class Task {
    // Unique ID of the task
    private int taskID;

    // Category name of the task
    private String category;

    // Description of the task
    private String description;

    // Deadline of the task
    private LocalDateTime deadline;

    // Start date-time of the task
    private LocalDateTime startDatetime;

    // End date-time of the task
    private LocalDateTime endDateTime;

    // Recurrence of the task;
    private HashMap<String, String> recurrence = new HashMap<>();

    // Tags given to the task
    private ArrayList<String> tags = new ArrayList<>();

    // Flag to determine if task is already done or not
    private boolean completed = false;

    // Flag to determine if task is high priority or not
    private boolean priority = false;

    // Date-time for reminder for this task
    private LocalDateTime reminderDateTime;

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

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getStartDateTime() {
        return this.startDatetime;
    }

    public void setStartDateTime(LocalDateTime dateTime) {
        this.startDatetime = dateTime;
    }

    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    public void setEndDateTime(LocalDateTime dateTime) {
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

    public void markTaskUncompleted() {
        this.completed = false;
    }

    public void markTaskCompleted() {
        this.completed = true;
    }

    public boolean isTaskPriority() {
        return this.priority;
    }

    public void setTaskAsNotPriority() {
        this.priority = false;
    }

    public void setTaskAsPriority() {
        this.priority = true;
    }

    public LocalDateTime getReminderDateTime() {
        return this.reminderDateTime;
    }

    public void setReminderDateTime(LocalDateTime dateTime) {
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
     *
     * @param description
     * @return Task
     */
    public static Task createFloatingTask(String description) {
        Task floatingTask = new Task();
        floatingTask.setDescription(description);
        return floatingTask;
    }

    /**
     * This method creates a tagged floating task
     *
     * @param description
     * @return Task
     */
    public static Task createTaggedTask(String description, String tag) {
        Task floatingTaskWithTag = new Task();
        floatingTaskWithTag.setDescription(description);
        floatingTaskWithTag.addTag(tag);
        return floatingTaskWithTag;
    }

    /**
     * This method creates a deadline task.
     *
     * @param description
     * @param deadline
     * @return Task
     */
    public static Task createDeadlineTask(String description, LocalDateTime deadline) {
        Task deadlineTask = new Task();
        deadlineTask.setDescription(description);
        deadlineTask.setDeadline(deadline);
        return deadlineTask;
    }

    /**
     * This method creates a recurring task
     *
     * @param description
     * @param taskRecurringType
     * @return Task
     */
    public static Task createRecurringTask(String description, String taskRecurringType) {
        Task deadlineTask = new Task();
        deadlineTask.setDescription(description);
        deadlineTask.setRecurrenceFrequency(taskRecurringType);
        return deadlineTask;
    }

    /**
     * This method creates a timed task.
     *
     * @param description
     * @param startDateTime
     * @param endDateTime
     * @return Task
     */
    public static Task createTimedTask(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Task timedTask = new Task();
        timedTask.setDescription(description);
        timedTask.setStartDateTime(startDateTime);
        timedTask.setEndDateTime(endDateTime);
        return timedTask;
    }
}
