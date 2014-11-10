package tareas.storage;

import tareas.common.Log;
import tareas.common.Task;
import tareas.common.Tasks;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 *
 * This class acts as the API for the Storage component.
 */

//@author A0112151A

//runType in the parameter is for testing purposes.
//There are 2 values for the runType. Value "1" is used for storage.json. It is
//for normal usage when the program runs.
//Value "2" is used for testing.json. It is used when unit testing is done.

public class TareasIO {

    private Tasks tasks = new Tasks();
    private static String TAG = "tareas/tareasStorageIO";

    /**
     * This method initializes the reader to keep the tasks updated.
     * @param runType
     */
	private void initialize(int runType) {
		StorageReader reader = new StorageReader();
		try {
            tasks = reader.read(runType);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * This method writes tasks to the storage.
     * @param runType
     */
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

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "Storage has performed a file writing action at " + now.toString());

	}

    /**
     * This method remove Task from the arraylist.
     * @param id
     * @param temp
     */
	private void removeTaskFromArray(int id, ArrayList<Task> temp) {
		Iterator<Task> iter = temp.iterator();
		while(iter.hasNext()) {
			Task task = iter.next();
			if(task.getTaskID() == id) {
				iter.remove();
			}
		}
	}

    /**
     * This method get the particular task requested by the user.
     * @param id
     * @param runType
     * @return
     */
    private Task getTask(int id, int runType) {
        Iterator<Task> iter = getAllTasks(runType).iterator();

        // asserting to make sure id is not a negative value.
        assert (!(id < 0));

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
     * This method inserts new tasks to the storage.
     * @param task
     * @param runType
     */
	public void insertTask(Task task, int runType) {
		initialize(runType);
		task.setTaskID(tasks.getLatestID());
        tasks.incrementID();
        ArrayList<Task> newTasks;
        newTasks = tasks.get();
        newTasks.add(task);
        tasks.set(newTasks);
		write(runType);

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "Storage has performed a task insertion action at " + now.toString());

	}

    /**
     * This method deletes tasks from the storage.
     * @param id
     * @param runType
     * @throws InvalidParameterException
     */
	public void deleteTask(int id, int runType) throws InvalidParameterException{
		initialize(runType);
        if(tasks == null) {
            System.out.println("Array is empty");
        }
		if(id < 0 || id > getTasks(runType).getLatestID()) {
            throw new InvalidParameterException("Invalid id");
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

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "Storage has performed a task deletion action at " + now.toString());

	}

    /**
     * This method edits a particular task and receive a task object from the caller.
     * @param newTask
     * @param runType
     */
    public void editTask(Task newTask, int runType) {
        initialize(runType);
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
                if (newTask.getTags() != null) {
                    for(int i = 0; i < newTask.getTags().size(); i++) {
                        taskToBuild.addTag(newTask.getTags().get(i));
                    }
                }

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

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "Storage has performed a task editing action at " + now.toString());

    }

    /**
     * This method search for the particular task by the given task id.
     * @param id
     * @param runType
     * @return
     */
    public Task detailedTask(int id, int runType) {
        initialize(runType);
        return getTask(id, runType);
    }

    /**
     * This method searches for the required task by receive the string of word in the parameter.
     * @param searchString
     * @param runType
     * @return
     */
    public ArrayList<Task> searchTask(String searchString, int runType) {
        initialize(runType);

        ArrayList<Task> searchedTasks = new ArrayList<>();

        ArrayList<Task> searchedTagTasks = searchTags(searchString, runType);
        ArrayList<Task> searchedDescriptionTasks = searchByDescription(searchString, runType);

        // searchedTasks.addAll(searchedTagTasks);
        searchedTasks.addAll(searchedDescriptionTasks);

        return searchedTasks;
    }

    /**
     * This method marks tasks as completed in the storage.
     * @param id
     * @param runType
     */
    public void markTaskAsCompleted(int id, int runType) {
        initialize(runType);
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
     * This method retrieves all tasks available in the database.
     * @param runType
     * @return
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
     * This method gets all tasks from the storage.
     * @param runType
     * @return
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
     * This method returns the lastest ID for the new task to be set as.
     * @param runType
     * @return
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
     *This method writes completely to the storage after an undo action
     * @param stateToRevertTo
     * @param runType
     */
    public void undoWrite(Tasks stateToRevertTo, int runType) {
        tasks.set(stateToRevertTo.get());
        tasks.setID(stateToRevertTo.getLatestID());
        write(runType);
    }

    /**
     * This method writes completely to the storage after a redo action
     * @param stateToRevertTo
     * @param runType
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
     * @param runType
     */
    public void prioritizeTask(int id, boolean priority, int runType) {
        initialize(runType);

        ArrayList<Task> temp = getAllTasks(runType);

        int taskIdToPrioritize = -1;

        for (int i = 0; i < temp.size(); i++) {
            Task task = temp.get(i);
            if (task.getTaskID() == id) {
                taskIdToPrioritize = i;
            }
        }

        if (taskIdToPrioritize != -1 && priority) {
            temp.get(taskIdToPrioritize).setTaskAsPriority();
        }

        if (taskIdToPrioritize != -1 && !priority) {
            temp.get(taskIdToPrioritize).setTaskAsNotPriority();
        }

        tasks.set(temp);

        write(runType);
    }


    /**
     * This method postpones tasks to different deadlines.
     * @param task
     * @param runType
     */
    public void postponeTask(Task task, int runType){
        editTask(task, runType);
    }

    /**
     * This method retrieves an arrayList of task for different task type.
     * Task type can be of undone, today's, tomorrow's or done type of task,
     * deadline task, timed task, floating task, important task, overdue tasks.
     * @param runType
     * @param taskType
     * @return
     */
    public ArrayList<Task> getAllUndoneTasks(int runType, String taskType) {
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

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

        switch(taskType) {
            case "undone":
                for (int i = 0; i < tasksSize; i++) {
                    if (tasks.get(i).isTaskCompleted()) {
                        tasks.remove(i);
                        i--;
                        tasksSize--;
                    }
                }
                break;
             case "today":
                 tasks = removeFloatingTasks(tasks);
                 tasksSize = tasks.size();
                 LocalDate today = LocalDate.now();
                 tasks = getParticularDateTask(1, today);
                 break;
            case "tomorrow":
                tasks = removeFloatingTasks(tasks);
                tasksSize = tasks.size();
                tasks = getParticularDateTask(1, tomorrow.toLocalDate());
                break;
            case "done":
                for (int i = 0; i < tasksSize; i++) {
                    if (!tasks.get(i).isTaskCompleted()) {
                        tasks.remove(i);
                        i--;
                        tasksSize--;
                    }
                }
                break;
            case "all":
                // all tasks, no filtering - do nothing
                break;
            case "deadline":
                tasks = removeFloatingTasks(tasks);
                tasksSize = tasks.size();
                for (int i = 0; i < tasksSize; i++) {
                    if (tasks.get(i).getDeadline() == null || tasks.get(i).isTaskCompleted()) {
                        tasks.remove(i);
                        i--;
                        tasksSize--;
                    }
                }
                break;
            case "timed":
                tasks = removeFloatingTasks(tasks);
                tasksSize = tasks.size();
                for (int i = 0; i < tasksSize; i++) {
                    if (tasks.get(i).getStartDateTime() == null || tasks.get(i).getEndDateTime() == null ||
                            tasks.get(i).isTaskCompleted()) {
                        tasks.remove(i);
                        i--;
                        tasksSize--;
                    }
                }
                break;
            case "floating":
                for (int i = 0; i < tasksSize; i++) {
                    if (tasks.get(i).getDeadline() != null || tasks.get(i).getEndDateTime() != null ||
                            tasks.get(i).getStartDateTime() != null || tasks.get(i).isTaskCompleted()) {
                        tasks.remove(i);
                        i--;
                        tasksSize--;
                    }
                }
                break;
            case "important":
                for (int i = 0; i < tasksSize; i++) {
                    if (!tasks.get(i).isTaskPriority() || tasks.get(i).isTaskCompleted()) {
                        tasks.remove(i);
                        i--;
                        tasksSize--;
                    }
                }
                break;
            case "overdue":
                tasks = removeFloatingTasks(tasks);
                tasksSize = tasks.size();
                for (int i = 0; i < tasksSize; i++) {
                    if (tasks.get(i).getDeadline() != null && tasks.get(i).getDeadline().isAfter(now) ||
                            tasks.get(i).isTaskCompleted()) {
                        tasks.remove(i);
                        i--;
                        tasksSize--;
                    }

                    if (tasks.get(i).getEndDateTime() != null && tasks.get(i).getStartDateTime() != null &&
                            tasks.get(i).getEndDateTime().isAfter(now) || tasks.get(i).isTaskCompleted()) {
                        tasks.remove(i);
                        i--;
                        tasksSize--;
                    }
                }
                break;
           default:
               System.out.println("This is an error!");
               // it should never reach here
               Log.w("getAllUndoneTasks has met with an unexpected failure that allows it to fall into default" +
                       " which should never happen", TAG);
               break;
        }

        return tasks;
    }

    /**
     * This method removes floating tasks and return the arrayList of the list of floating tasks.
     * @param allTasks
     * @return
     */
    private ArrayList<Task> removeFloatingTasks(ArrayList<Task> allTasks) {

        ArrayList<Task> tasksToReturn = new ArrayList<>();

        for (Task task: allTasks) {
            if (task.getDeadline() != null || task.getStartDateTime() != null) {
                tasksToReturn.add(task);
            }
        }

        return tasksToReturn;
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

        tasks = removeFloatingTasks(tasks);


        int tasksSize = tasks.size();

        ArrayList<Task> taskToReturn = new ArrayList<>();

        for (int i = 0; i < tasksSize; i++) {
                    LocalDate taskDate = tasks.get(i).getDeadline().toLocalDate();

                    if (taskDate.isEqual(particularDate)) {
                        taskToReturn.add(tasks.get(i));
                    }
            }


        return taskToReturn;

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
     * @param tag
     * @param runType
     */
    public void addTags(int id, String tag, int runType){
        ArrayList<Task> temp = getAllTasks(runType);

        int taskIdToAddTags = -1;

        for (int i = 0; i < temp.size(); i++) {
            Task task = temp.get(i);
            if (task.getTaskID() == id) {
                taskIdToAddTags = i;
            }
        }

        if (taskIdToAddTags != -1) {
            temp.get(taskIdToAddTags).addTag(tag);
        }

        tasks.set(temp);

        write(runType);
    }

    /**
     * This method search for tags and return an arrayList of related tasks.
     * @param searchTag
     * @param runType
     * @return
     */
    private ArrayList<Task> searchTags(String searchTag, int runType){
        StorageReader reader = new StorageReader();
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            tasks = reader.read(runType).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tasksSize = tasks.size();

        for (int i = 0; i < tasksSize; i++) {
            ArrayList<String> taskTags = tasks.get(i).getTags();

            for (String tag : taskTags) {
                if (!(tag.equals(searchTag))) {
                    tasks.remove(i);
                    i--;
                    tasksSize--;
                    break;
                }
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
    protected ArrayList<Task> searchByDescription(String description, int runType){
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
                i--;
                tasksSize--;
            }
        }

        return tasks;

    }

    /**
     * This method add reminder(s) to tasks
     * @param id
     * @param reminderDateTime
     * @param runType
     */
    public void setTaskReminder(int id, LocalDateTime reminderDateTime, int runType) {
        ArrayList<Task> temp = getAllTasks(runType);

        int taskIdToSetReminder = -1;

        for (int i = 0; i < temp.size(); i++) {
            Task task = temp.get(i);
            if (task.getTaskID() == id) {
                taskIdToSetReminder = i;
            }
        }

        if (taskIdToSetReminder != -1) {
            temp.get(taskIdToSetReminder).setReminderDateTime(reminderDateTime);
        }

        tasks.set(temp);

        write(runType);
    }
}
