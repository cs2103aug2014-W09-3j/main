package tareas.storage;

import tareas.common.Task;
import tareas.common.Tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Her Lung
 *
 * This class acts as the API for the Storage component.
 */

public class TareasIO {

	private Tasks allTasks = new Tasks();
	
	private void initialize() {
		StorageReader reader = new StorageReader();
		try {
			this.allTasks = reader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void write() {
		StorageWriter writer = new StorageWriter();
		try {
			writer.write(allTasks);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void removeTaskFromArray(int id, ArrayList<Task> temp) {
		Iterator<Task> iter = temp.iterator();
		while(iter.hasNext()) {
			Task task = iter.next();
			if(task.getTaskID() == id) {
				iter.remove();
			}
		}
	}

    private Task getTask(int id) {
        Iterator<Task> iter = allTasks.get().iterator();
        Task searchTask = new Task();
        while(iter.hasNext()) {
            Task task = iter.next();
            if(task.getTaskID() == id) {
                searchTask = task;
                break;
            }
        }
        return searchTask;
    }
	
	/**
	 * Inserts a task into Tareas using a Task object
	 * @param task
	 */
	public void insertTask(Task task) {
		initialize();
        if (allTasks == null) {
            allTasks = new Tasks();
        }
		task.setTaskID(allTasks.getNextID());
		allTasks.add(task);
		write();
	}

	/**
	 * Removes a task with a given ID
	 * @param id
	 */
	public void deleteTask(int id) {
		initialize();
		if(id < 1 || id > allTasks.get().size()) {
			// TODO: Add exception for Invalid ID.
			System.out.println("Inavlid Task ID.");
		} else {
			ArrayList<Task> temp = allTasks.get();
			removeTaskFromArray(id, temp);
			allTasks.set(temp);
			write();
		}
	}

    /**
     * This method updates tasks in storage.
     * @param newTask
     */
    public void editTask(Task newTask) {
        initialize();
        int id = newTask.getTaskID();

        Iterator<Task> iter = allTasks.get().iterator();
        while(iter.hasNext()) {
            Task task = iter.next();
            if(task.getTaskID() == id) {
                if(newTask.getDescription() != null) {
                    task.setDescription(newTask.getDescription());
                }
                if(newTask.getCategory() != null) {
                    task.setCategory(newTask.getCategory());
                }
                if(newTask.getDeadline() != null) {
                    task.setDeadline(newTask.getDeadline());
                }
                if(newTask.getStartDateTime() != null) {
                    task.setStartDateTime(newTask.getStartDateTime());
                }
                if(newTask.getEndDateTime() != null) {
                    task.setEndDateTime(newTask.getEndDateTime());
                }
                if(newTask.getRecurrenceFrequency() != null) {
                    task.setRecurrenceFrequency(newTask.getRecurrenceFrequency());
                }
                if(newTask.getRecurrenceDate() != null) {
                    task.setRecurrenceDate(newTask.getRecurrenceDate());
                }
                if(newTask.getRecurrenceDay() != null) {
                    task.setRecurrenceDay(newTask.getRecurrenceDay());
                }

                // TODO: tags are left out first

                if(newTask.isTaskCompleted() != task.isTaskCompleted()) {
                    if(newTask.isTaskCompleted()) {
                        task.markTaskCompleted();
                    } else {
                        task.markTaskUncompleted();
                    }
                }

                if(newTask.isTaskPriority() != task.isTaskPriority()) {
                    if(newTask.isTaskPriority()) {
                        task.setTaskAsPriority();
                    } else {
                        task.setTaskAsNotPriority();
                    }
                }

                if(newTask.getReminderDateTime() != null) {
                    task.setReminderDateTime(newTask.getReminderDateTime());
                }

                if(newTask.getColor() != null) {
                    task.setColor(newTask.getColor());
                }
            }
        }
        write();
    }

    /**
     * This method searches for a task using the ID
     * @param id
     * @return Task
     */
    public Task searchTask(int id) {
        initialize();
        return getTask(id);
    }

    /**
     * This method allows tasks to be marked as completed.
     * @param id
     */
    public void markTaskAsCompleted(int id) {
        initialize();
        Task task = getTask(id);
        task.markTaskCompleted();
        write();
    }

	/**
	 * Returns all tasks in Tareas.
	 * @return allTasks
	 */
	// TODO sort the tasks.
	public Tasks getAllTasks() {
		StorageReader reader = new StorageReader();
		Tasks allTasks = new Tasks();
		try {
			allTasks = reader.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allTasks;
	}

    //TODO undo the latest task.
    public void undoTask(){

    }

    //TODO redo the lastest task.
    public void redoTask(){

    }


    /**
     * This method sets the priority of the task.
     * @param id
     * @param priority
     */
    public void prioritizeTask(int id, boolean priority){
        Task task = getTask(id);
        if(priority){
            task.setTaskAsPriority();
        }
        else{
            task.setTaskAsNotPriority();
        }
        write();
    }

    /**
     * This method postpones tasks to different deadlines.
     * @param task
     */
    public void postponeTask(Task task){
        allTasks.remove(task.getTaskID());
        allTasks.add(task);
        write();
    }

    /**
     * This method deletes all ongoing tasks in the list.
     */
    public void massDelete(){
        allTasks.removeAll();
        write();
    }

    /**
     * This method sets the frequency, day and date of a recurring task.
     * @param id
     * @param frequency
     * @param day
     * @param date
     */
    public void recurringTask(int id, String frequency, String day, String date){
        Task task = getTask(id);
        task.setRecurrenceFrequency(frequency);
        task.setRecurrenceDay(day);
        task.setRecurrenceDate(date);
        write();
    }

    public void addingTags(int id, String tagDescription){
        Task task = getTask(id);
        task.addTag(tagDescription);
        write();
    }


}
