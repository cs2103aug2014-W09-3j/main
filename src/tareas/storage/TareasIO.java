package tareas.storage;

import tareas.common.Task;
import tareas.common.Tasks;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Lareina
 *
 * This class acts as the API for the Storage component.
 */

public class TareasIO {

    private Tasks tasks = new Tasks();
	
	private void initialize() {
		StorageReader reader = new StorageReader();
		try {
            tasks = reader.read(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void write(int runType) {
		StorageWriter writer = new StorageWriter();
		try {
            if(runType == 1) {
                writer.write(tasks, "storage.json");
            }
            else if(runType == 2){
                writer.write(tasks, "testing.json");
            }
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

    private Task getTask(int id, int runType) {
        Iterator<Task> iter = getAllTasks(runType).iterator();

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
	public void insertTask(Task task, int runType) {
		initialize();
		task.setTaskID(tasks.getLatestID());
        tasks.incrementID();
        ArrayList<Task> newTasks;
        newTasks = tasks.get();
        newTasks.add(task);
        tasks.set(newTasks);
		write(runType);
	}

	/**
	 * Removes a task with a given ID
     *
	 * @param id
	 */
	public void deleteTask(int id, int runType) {
		initialize();
		if(id < 1 || id > getTasks(runType).getLatestID()) {
			// TODO: Add exception for Invalid ID.
			System.out.println("Invalid Task ID.");
		} else {
            int taskIdToRemove = -1;

			Iterator<Task> iter = getAllTasks(runType).iterator();


            while (iter.hasNext()) {
                Task task = iter.next();
                if(task.getTaskID() == id) {
                    taskIdToRemove = id;
                }
            }

            ArrayList<Task> temp = getAllTasks(runType);

            if (taskIdToRemove != -1) {
                removeTaskFromArray(taskIdToRemove, temp);
            }

            tasks.set(temp);

			write(runType);
		}
	}

    /**
     * This method updates tasks in storage.
     *
     * @param newTask
     */
    public void editTask(Task newTask, int runType) {
        initialize();
        int id = newTask.getTaskID();

        Iterator<Task> iter = getAllTasks(runType).iterator();

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

        ArrayList<Task> temp = getAllTasks(runType);

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

        write(runType);
    }

    /**
     * This method searches for a task using the ID
     *
     * @param id
     * @return Task
     */
    public Task searchTask(int id, int runType) {
        initialize();
        return getTask(id, runType);
    }

    /**
     * This method allows tasks to be marked as completed.
     *
     * @param id
     */
    public void markTaskAsCompleted(int id, int runType) {
        initialize();
        ArrayList<Task> temp = getAllTasks(runType);

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

        write(runType);
    }

	/**
	 * Returns all tasks in Tareas.
     *
	 * @return allTasks
	 */
	private ArrayList<Task> getAllTasks(int runType) {
		StorageReader reader = new StorageReader();
        ArrayList<Task> tasks = new ArrayList<>();
        try {
              tasks = reader.read(runType).get();

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
    public Tasks getTasks(int runType) {
        StorageReader reader = new StorageReader();
        Tasks tasks = new Tasks();

        try {
             tasks = reader.read(runType);
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
    public int getInitialiseLatestId(int runType) {
        StorageReader reader = new StorageReader();
        Tasks tasks = new Tasks();

        try {
                tasks = reader.read(runType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tasks.getLatestID();
    }


    /**
     * This method writes completely to the storage after an undo action
     *
     * @param stateToRevertTo
     */
    public void undoWrite(Tasks stateToRevertTo, int runType) {
        tasks.set(stateToRevertTo.get());
        tasks.setID(stateToRevertTo.getLatestID());
        write(runType);
    }

    /**
     * This method writes completely to the storage after a redo action
     *
     * @param stateToRevertTo
     */
    public void redoWrite(Tasks stateToRevertTo, int runType) {
        tasks.set(stateToRevertTo.get());
        tasks.setID(stateToRevertTo.getLatestID());
        write(runType);
    }

    /**
     * This method sets the priority of the task.
     * @param id
     * @param priority
     */
    public void prioritizeTask(int id, boolean priority, int runType) {
        initialize();

        ArrayList<Task> temp = getAllTasks(runType);

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

        write(runType);
    }

    /**
     * This method postpones tasks to different deadlines.
     * @param task
     */
    public void postponeTask(Task task, int runType){
        editTask(task, runType);
    }

    /**
     * This method retrieves all task today. Parameter runType is to determine
     * whether the file is under testing or running.
     * @param runType
     * @return
     */
    public ArrayList<Task> retrieveTodayTask(int runType){
        StorageReader reader = new StorageReader();
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            tasks = reader.read(runType).get();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        int tasksSize = tasks.size();

        LocalDateTime currentTime = LocalDateTime.now();

        for(int i = 0; i < tasksSize;){
            if(tasks.get(i).getDeadline().equals(currentTime)) {
                tasks.remove(i);
                tasksSize--;
            }
            else {
                i++;
            }
        }

        return tasks;
    }


    /**
     * This method retrieves an arrayList of task for different task type.
     * Task type can be of undone, today's, tomorrow's or done type of task.
     * @param runType
     * @param taskType
     * @return
     */
    public ArrayList<Task> getAllUndoneTasks(int runType, int taskType) {
        StorageReader reader = new StorageReader();
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            tasks = reader.read(runType).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tasksSize = tasks.size();
        LocalDate currentDate = LocalDate.now();
        LocalDate tmrDate = currentDate.plusDays(1);

        switch(taskType){
            //retrieve all undone tasks.
            case 0:
                for (int i = 0; i < tasksSize; i++) {

                    if (tasks.get(i).isTaskCompleted()) {
                        tasks.remove(i);
                        tasksSize--;
                    }
                }
                break;

            //retrieve today tasks.
             case 1:
                 for (int i = 0; i < tasksSize; i++) {
                     LocalDate taskDate = tasks.get(i).getDeadline().toLocalDate();

                     if (tasks.get(i).isTaskCompleted() || !taskDate.isEqual(currentDate)) {
                         tasks.remove(i);
                         tasksSize--;
                     }
                 }
                 break;

             //retrieve tomorrow's tasks.
            case 2:
                for (int i = 0; i < tasksSize; i++) {
                    LocalDate taskDate = tasks.get(i).getDeadline().toLocalDate();

                    if (tasks.get(i).isTaskCompleted() || !taskDate.isEqual(tmrDate)) {
                        tasks.remove(i);
                        tasksSize--;
                    }
                }
                break;

            //retrieve all done tasks.
            case 3:
                for (int i = 0; i < tasksSize; i++) {

                    if (!tasks.get(i).isTaskCompleted()) {
                        tasks.remove(i);
                        tasksSize--;
                    }
                }
                break;

           default:
               System.out.println("This is an error!");
               break;
        }

        return tasks;
    }

    /**
     * This method retrieves the list of task that is on a particular date.addde
     * @param runType
     * @param particularDate
     * @return
     */
    public ArrayList<Task> getParticularDateTask(int runType, LocalDate particularDate) {
        StorageReader reader = new StorageReader();
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            tasks = reader.read(runType).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tasksSize = tasks.size();

        for (int i = 0; i < tasksSize; i++) {
                LocalDate taskDate = tasks.get(i).getDeadline().toLocalDate();

                if (!taskDate.isEqual(particularDate)) {
                    tasks.remove(i);
                    tasksSize--;
                }
            }

        return tasks;

    }

    /**
     * This method deletes all ongoing tasks in the list.
     */
    public void massDelete(int runType){
        tasks.removeAll();
        write(runType);
    }


    /**
     * This method add tags to tasks.
     * @param id
     * @param tagDescription
     * @param runType
     */
    public void addingTags(int id, String tagDescription, int runType){
        Task task = getTask(id, runType);
        task.addTag(tagDescription);
        write(runType);
    }


    /**
     * This method search for tags and return an arrayList of related tasks.
     * @param tagDescription
     * @param runType
     * @return
     */
    public ArrayList<Task> searchTags(String tagDescription, int runType){
        StorageReader reader = new StorageReader();
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            tasks = reader.read(runType).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tasksSize = tasks.size();

        for (int i = 0; i < tasksSize; i++) {

            if (!tasks.get(i).getTags().equals(tagDescription)) {
                tasks.remove(i);
                tasksSize--;
            }
        }

        return tasks;

    }

    /**
     * This method search tasks by description and returns a list of related tasks.
     * @param description
     * @param runType
     * @return
     */
    public ArrayList<Task> searchByDescription(String description, int runType){
        StorageReader reader = new StorageReader();
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            tasks = reader.read(runType).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tasksSize = tasks.size();

        for (int i = 0; i < tasksSize; i++) {

            if (!tasks.get(i).getDescription().contains(description)) {
                tasks.remove(i);
                tasksSize--;
            }
        }

        return tasks;

    }

}
