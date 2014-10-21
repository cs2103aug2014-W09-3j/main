package tareas.controller;

import tareas.common.Task;
import tareas.common.Tasks;
import tareas.common.Exceptions;
import tareas.common.Log;
import tareas.parser.TareasCommand;
import tareas.parser.Parser;
import tareas.storage.TareasIO;
import tareas.gui.TareasGUIController;

import java.util.Date;
import java.util.ArrayList;

/**
 * @author Yap Jun Hao
 *         <p/>
 *         This class binds the other parts of the program together.
 */

public class TareasController {
    // Constant for Logging
    private static String TAG = "tareas/tareasController";

    // Instantiate a GUI Controller
    TareasGUIController guiController = TareasGUIController.getInstance();

    // Instantiate a TareasIO
    TareasIO tareas = new TareasIO();

    // Instantiate a TaskManager
    TaskManager taskManager = TaskManager.getInstance();

    /**
     * constructor for controller, will set the pointer for the task manager
     */
    public TareasController() {
        taskManager.set(tareas.getAllUndoneTasks());
        taskManager.setId(tareas.getInitialiseLatestId());
    }

    /**
     * Takes the user's input from the GUI and does the right stuff to make the program work
     *
     * @param userInput from GUI
     */
    public void executeCommand(String userInput) {
        // asserting to make sure that the user's input is not an empty string
        // assert(userInput != "");

        TareasCommand command = TareasCommand.fromString(userInput);

        // asserting to make sure that the command is really a TareasCommand
        assert(command != null);

        switch (Parser.checkCommandValidity(command).getStatus()) {
            case SUCCESS:
                // no feedback, continue on since it's a valid command
                break;
            case UNKNOWN_COMMAND:
                guiController.sendErrorToView("Unrecognized command, please input a recognized command.");
                return;
            case MISSING_PRIMARY_ARGUMENT:
                guiController.sendErrorToView("Unrecognized command, please input a recognized command.");
                return;
            case UNEXPECTED_PRIMARY_ARGUMENT:
                guiController.sendErrorToView("Unrecognized command, please input a recognized command.");
                return;
            case UNKNOWN_KEYWORD:
                guiController.sendErrorToView("Unrecognized command, please input a recognized command.");
                return;
            case SIGNATURE_NOT_MATCHED:
                guiController.sendErrorToView("Unrecognized command, please input a recognized command.");
                return;
        }

        switch (command.getType()) {
            case ADD_COMMAND:
                addTask(command);
                break;
            case EDIT_COMMAND:
                editTask(command);
                break;
            case DELETE_COMMAND:
                deleteTask(command);
                break;
            case SEARCH_COMMAND:
                searchTask(command);
                break;
            case DONE_COMMAND:
                completeTask(command);
                break;
            case UNDO_COMMAND:
                undo();
                break;
            case REDO_COMMAND:
                redo();
                break;
            case POSTPONE_COMMAND:
                postponeTask(command);
                break;
            case VIEW_COMMAND:
                viewRequest(command);
                break;
            case PRIORITIZE_COMMAND:
                prioritizeTask(command);
                break;
            case CATEGORIZE_COMMAND:
                categorizeTask(command);
                break;
            case REMIND_COMMAND:
                setTaskReminder(command);
                break;
            case BACKUP_COMMAND:
                backup();
                break;
            case MUTE_COMMAND:
                mute(command);
                break;
            case FONT_COMMAND:
                changeFont(command);
                break;
            case COLOR_COMMAND:
                colorizeTask(command);
                break;
            default:
            	guiController.sendErrorToView("Unrecognized command, please input a recognized command.");
                // TODO throw a TareasException
        }
    }

    /**
     * helps to initialise GUI view by giving the GUI the set of all tasks
     *
     * @return an ArrayList of Task
     */
    public ArrayList<Task> getInitialiseTasks() {
        return tareas.getAllUndoneTasks();
    }

    /**
     * adds a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void addTask(TareasCommand command) {
        Task taskToInsert = TaskManager.buildTask(command);

        tareas.insertTask(taskToInsert);
        ArrayList<Task> newTasks = tareas.getAllUndoneTasks();
        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();
        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Task successfully added");
        // TODO change feedback to include task description for useful user feedback
        Log.i(TAG, "User has performed a task adding action");
        // TODO change logging to include time stamp
    }

    /**
     * edits a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void editTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());
        Task taskToInsert = new Task();

        if (command.getArgument("des") != null) {
            taskToInsert.setDescription(command.getArgument("des"));
        }

        int tasksSize = taskManager.get().size();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        taskToInsert.setTaskID(mappedTaskId);

        tareas.editTask(taskToInsert);
        ArrayList<Task> newTasks = tareas.getAllUndoneTasks();
        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();
        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Task successfully edited");
        // TODO change feedback to include task description for useful user feedback
        Log.i(TAG, "User has performed a task editing action");
        // TODO change logging to include time stamp
    }

    /**
     * deletes a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void deleteTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        tareas.deleteTask(mappedTaskId);
        ArrayList<Task> newTasks = tareas.getAllUndoneTasks();
        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();
        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Task successfully deleted");
        // TODO change feedback to include task description for useful user feedback
        Log.i(TAG, "User has performed a task deletion action");
        // TODO change logging to include time stamp
    }

    /**
     * searches a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void searchTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        tareas.searchTask(taskId);
        // TODO Add in feedback to user on the GUI side of things
        // TODO change feedback to include task description for useful user feedback
        // TODO Add in Logging
        // TODO change logging to include time stamp
    }

    /**
     * completes a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void completeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        System.out.println(mappedTaskId);
        
        tareas.markTaskAsCompleted(mappedTaskId);
        ArrayList<Task> newTasks = tareas.getAllUndoneTasks();
        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();
        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Successfully completed Task " + taskId);
        // TODO change feedback to include task description for useful user feedback
        Log.i(TAG, "User has performed a task completion action");
        // TODO change logging to include time stamp
    }

    /**
     * postpones a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void postponeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();
        
        // TODO postpone the task to the Storage
        ArrayList<Task> newTasks = tareas.getAllUndoneTasks();
        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();
        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Task has been successfully postponed");
        // TODO change feedback to include task description for useful user feedback
        Log.i(TAG, "User has performed a task postponing action");
        // TODO change logging to include time stamp
    }

    /**
     * completes a view request by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void viewRequest(TareasCommand command) {
        // TODO grab the view type so that can call the right stuff from storage and GUI
        
        // TODO ask from the storage all the stuff needed for the view
        // TODO call the GUI method to display the view request
        guiController.sendSuccessToView("View has successfully been changed");
        // TODO change feedback to include task description for useful user feedback
        Log.i(TAG, "User has performed a view change action");
        // TODO change logging to include time stamp
    }

    /**
     * prioritize a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void prioritizeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();
        
        // TODO tell the storage that a task has been prioritized
        ArrayList<Task> newTasks = tareas.getAllUndoneTasks();
        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();
        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Task has been successfully prioritized");
        // TODO change feedback to include task description for useful user feedback
        Log.i(TAG, "User has performed a task prioritizing action");
        // TODO change logging to include time stamp
    }

    /**
     * categorize a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void categorizeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();
        
        // TODO tell the storage that a task has been categorized
        ArrayList<Task> newTasks = tareas.getAllUndoneTasks();
        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();
        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Task has been successfully categorized");
        // TODO change feedback to include task description for useful user feedback
        Log.i(TAG, "User has performed a task categorization action");
        // TODO change logging to include time stamp
    }

    /**
     * set a task reminder by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void setTaskReminder(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();
        
        // TODO tell the storage that a task has a reminder set
        ArrayList<Task> newTasks = tareas.getAllUndoneTasks();
        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();
        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Reminder Set");
        // TODO change feedback to include task description for useful user feedback
        Log.i(TAG, "User has performed a task reminder action");
        // TODO change logging to include time stamp
    }

    /**
     * backups all tasks data by calling the appropriate GUI and storage methods
     */
    private void backup() {
        // TODO tell the storage to backup the data
        guiController.sendSuccessToView("Backup successfully performed");
        Log.i(TAG, "User has performed a backup action");
        // TODO change logging to include time stamp
    }

    /**
     * mute Tareas by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void mute(TareasCommand command) {
        // TODO grab the time start and end to be passed to TareasIO
    	
        // TODO tell the storage to mute everything from time to time
        guiController.sendSuccessToView("Tareas successfully muted");
        // TODO change feedback to include task description for useful user feedback
        Log.i(TAG, "User has performed a mute action");
        // TODO change logging to include time stamp
    }

    /**
     * changes Tareas font settings by calling the appropriate GUI method
     *
     * @param command after being parsed from the parser
     */
    private void changeFont(TareasCommand command) {
        // TODO grab the font arguments to be passed to the GUI
    	
        // TODO tell the GUI to change the font
        guiController.sendSuccessToView("Font changed successfully");
        // TODO change feedback to include task description for useful user feedback
        Log.i(TAG, "User has performed a font change action");
        // TODO change logging to include time stamp
    }

    /**
     * colorize a task by calling the appropriate GUI and storage method
     *
     * @param command after being parsed from the parser
     */
    private void colorizeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();
    	
        // TODO tell the storage to change the color of the task
        ArrayList<Task> newTasks = tareas.getAllUndoneTasks();
        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();
        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Successfully changed color of task");
        // TODO change feedback to include task description for useful user feedback
        Log.i(TAG, "User has performed a task colorization action");
        // TODO change logging to include time stamp
    }

    /**
     * undoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private void undo() {
        if (taskManager.isAbleToUndo()) {
            Tasks stateToRevertTo = taskManager.getUndoState();

            tareas.undoWrite(stateToRevertTo);
            guiController.sendTaskstoView(stateToRevertTo.get());
            guiController.sendSuccessToView("Successfully undo action");
            Log.i(TAG, "User has performed an undo action");
            // TODO change logging to include time stamp
		} else {
            guiController.sendWarningToView("Nothing to undo");
            Log.e(TAG, "User tried to undo an action when there is nothing to undo");
            // TODO change logging to include time stamp
		}
    }

    /**
     * redoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private void redo() {
        if (taskManager.isAbleToRedo()) {
		    Tasks stateToRevertTo = taskManager.getRedoState();

            tareas.redoWrite(stateToRevertTo);
            guiController.sendTaskstoView(stateToRevertTo.get());
            guiController.sendSuccessToView("Successfully redo action");
            Log.i(TAG, "User has performed a redo action");
            // TODO change logging to include time stamp
		} else {
            guiController.sendWarningToView("Nothing to redo");
            Log.e(TAG, "User tried to redo an action when there is nothing to redo");
            // TODO change logging to include time stamp
		}
    }
}
