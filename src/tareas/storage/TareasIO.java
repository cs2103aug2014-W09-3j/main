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

    private Tasks tasks = new Tasks();
	
	private void initialize() {
		StorageReader reader = new StorageReader();
		try {
            tasks = reader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void write() {
		StorageWriter writer = new StorageWriter();
		try {
			writer.write(tasks);
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
        Iterator<Task> iter = getAllTasks().iterator();
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
     *
	 * @param task
	 */
	public void insertTask(Task task) {
		initialize();
		task.setTaskID(tasks.getLatestID());
        tasks.incrementID();
        ArrayList<Task> newTasks;
        newTasks = tasks.get();
        newTasks.add(task);
        tasks.set(newTasks);
		write();
	}

	/**
	 * Removes a task with a given ID
     *
	 * @param id
	 */
	public void deleteTask(int id) {
		initialize();
		if(id < 1 || id > getTasks().getLatestID()) {
			// TODO: Add exception for Invalid ID.
			System.out.println("Invalid Task ID.");
		} else {
            int taskIdToRemove = -1;

			Iterator<Task> iter = getAllTasks().iterator();

            while (iter.hasNext()) {
                Task task = iter.next();
                if(task.getTaskID() == id) {
                    taskIdToRemove = id;
                }
            }

            ArrayList<Task> temp = getAllTasks();

            if (taskIdToRemove != -1) {
                removeTaskFromArray(taskIdToRemove, temp);
            }

            tasks.set(temp);

			write();
		}
	}

    /**
     * This method updates tasks in storage.
     *
     * @param newTask
     */
    public void editTask(Task newTask) {
        initialize();
        int id = newTask.getTaskID();

        Iterator<Task> iter = getAllTasks().iterator();

        Task taskToChange = new Task();

        int taskIdToChange = -1;

        while(iter.hasNext()) {
            Task taskToBuild = iter.next();
            if (taskToBuild.getTaskID() == id) {
                if (newTask.getDescription() != null) {
                    taskToBuild.setDescription(newTask.getDescription());
                }
                if (newTask.getCategory() != null) {
                    taskToBuild.setCategory(newTask.getCategory());
                }
                if (newTask.getDeadline() != null) {
                    taskToBuild.setDeadline(newTask.getDeadline());
                }
                if (newTask.getStartDateTime() != null) {
                    taskToBuild.setStartDateTime(newTask.getStartDateTime());
                }
                if (newTask.getEndDateTime() != null) {
                    taskToBuild.setEndDateTime(newTask.getEndDateTime());
                }
                if (newTask.getRecurrenceFrequency() != null) {
                    taskToBuild.setRecurrenceFrequency(newTask.getRecurrenceFrequency());
                }
                if (newTask.getRecurrenceDate() != null) {
                    taskToBuild.setRecurrenceDate(newTask.getRecurrenceDate());
                }
                if (newTask.getRecurrenceDay() != null) {
                    taskToBuild.setRecurrenceDay(newTask.getRecurrenceDay());
                }

                // TODO: tags are left out first

                if (newTask.isTaskCompleted() != taskToBuild.isTaskCompleted()) {
                    if(newTask.isTaskCompleted()) {
                        taskToBuild.markTaskCompleted();
                    } else {
                        taskToBuild.markTaskUncompleted();
                    }
                }

                if (newTask.isTaskPriority() != taskToBuild.isTaskPriority()) {
                    if(newTask.isTaskPriority()) {
                        taskToBuild.setTaskAsPriority();
                    } else {
                        taskToBuild.setTaskAsNotPriority();
                    }
                }

                if (newTask.getReminderDateTime() != null) {
                    taskToBuild.setReminderDateTime(newTask.getReminderDateTime());
                }

                if (newTask.getColor() != null) {
                    taskToBuild.setColor(newTask.getColor());
                }

                taskToChange = taskToBuild;
            }
        }

        ArrayList<Task> temp = getAllTasks();

        for (int i = 0; i < temp.size(); i++) {
            Task task = temp.get(i);
            if (task.getTaskID() == id) {
                taskIdToChange = i;
            }
        }

        if (taskIdToChange != -1) {
            temp.set(taskIdToChange, taskToChange);
        }

        tasks.set(temp);

        write();
    }

    /**
     * This method searches for a task using the ID
     *
     * @param id
     * @return Task
     */
    public Task searchTask(int id) {
        initialize();
        return getTask(id);
    }

    /**
     * This method allows tasks to be marked as completed.
     *
     * @param id
     */
    public void markTaskAsCompleted(int id) {
        initialize();

        ArrayList<Task> temp = getAllTasks();

        int taskIdToComplete = -1;

        for (int i = 0; i < temp.size(); i++) {
            Task task = temp.get(i);
            if (task.getTaskID() == id) {
                taskIdToComplete = i;
            }
        }

        if (taskIdToComplete != -1) {
            temp.get(taskIdToComplete).markTaskCompleted();
        }

        tasks.set(temp);

        write();
    }

	/**
	 * Returns all tasks in Tareas.
     *
	 * @return allTasks
	 */
	private ArrayList<Task> getAllTasks() {
		StorageReader reader = new StorageReader();
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            tasks = reader.read().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tasks;
	}

    /**
     * Returns Tasks in Tareas
     *
     * @return Tasks
     */
    public Tasks getTasks() {
        StorageReader reader = new StorageReader();
        Tasks tasks = new Tasks();

        try {
            tasks = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    /**
     * Returns the id to set to
     *
     * @return int id
     */
    public int getInitialiseLatestId() {
        StorageReader reader = new StorageReader();
        Tasks tasks = new Tasks();

        try {
            tasks = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tasks.getLatestID();
    }

    /**
     * Returns all undone tasks in Tareas.
     *
     * @return ArrayList of Task
     */
    public ArrayList<Task> getAllUndoneTasks() {
        StorageReader reader = new StorageReader();
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            tasks = reader.read().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tasksSize = tasks.size();

        for (int i = 0; i < tasksSize;) {
            if (tasks.get(i).isTaskCompleted()) {
                tasks.remove(i);
                tasksSize--;
            } else {
                i++;
            }
        }

        return tasks;
    }

    /**
     * This method writes completely to the storage after an undo action
     *
     * @param stateToRevertTo
     */
    public void undoWrite(Tasks stateToRevertTo) {
        tasks.set(stateToRevertTo.get());
        tasks.setID(stateToRevertTo.getLatestID());
        write();
    }

    /**
     * This method writes completely to the storage after a redo action
     *
     * @param stateToRevertTo
     */
    public void redoWrite(Tasks stateToRevertTo) {
        tasks.set(stateToRevertTo.get());
        tasks.setID(stateToRevertTo.getLatestID());
        write();
    }

    /**
     * This method sets the priority of the task.
     * @param id
     * @param priority
     */
    public void prioritizeTask(int id, boolean priority) {
        initialize();

        ArrayList<Task> temp = getAllTasks();

        int taskIdToPrioritize = -1;

        for (int i = 0; i < temp.size(); i++) {
            Task task = temp.get(i);
            if (task.getTaskID() == id) {
                taskIdToPrioritize = i;
            }
        }

        if (taskIdToPrioritize != -1) {
            temp.get(taskIdToPrioritize).setTaskAsPriority();
        }

        tasks.set(temp);

        write();
    }

    /**
     * This method postpones tasks to different deadlines.
     * @param task
     */
    public void postponeTask(Task task){
        editTask(task);
    }

    /**
     * This method deletes all ongoing tasks in the list.
     */
    public void massDelete(){
        tasks.removeAll();
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
