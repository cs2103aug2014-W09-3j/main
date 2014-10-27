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
        taskManager.set(tareas.getAllUndoneTasks(1));
        taskManager.setId(tareas.getInitialiseLatestId(1));
    }

    /**
     * Takes the user's input from the GUI and does the right stuff to make the program work
     *
     * @param userInput from GUI
     */
    public void executeCommand(String userInput) {
        // if the user's input is an empty string, we treat it as if nothing happened
        if (userInput.equals("")) {
            return;
        }
        // TODO abstract into a method

        TareasCommand command = TareasCommand.fromString(userInput);

        // asserting to make sure that the command is really a TareasCommand
        assert(command != null);

        switch (Parser.checkCommandValidity(command).getStatus()) {
            case SUCCESS:
                // no feedback, continue on since it's a valid command
                break;
            case UNKNOWN_COMMAND:
                guiController.sendErrorToView("Unrecognized command, please input a recognized command.");
                // TODO make the feedback show something more helpful
                return;
            case MISSING_PRIMARY_ARGUMENT:
                guiController.sendErrorToView("Unrecognized command, please input a recognized command.");
                // TODO make the feedback show something more helpful
                return;
            case UNEXPECTED_PRIMARY_ARGUMENT:
                guiController.sendErrorToView("Unrecognized command, please input a recognized command.");
                // TODO make the feedback show something more helpful
                return;
            case UNKNOWN_KEYWORD:
                guiController.sendErrorToView("Unrecognized command, please input a recognized command.");
                // TODO make the feedback show something more helpful
                return;
            case SIGNATURE_NOT_MATCHED:
                guiController.sendErrorToView("Unrecognized command, please input a recognized command.");
                // TODO make the feedback show something more helpful
                return;
        }
        // TODO abstract into a method

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
        }
        // TODO abstract into a method
    }

    /**
     * helps to initialise GUI view by giving the GUI the set of all tasks
     *
     * @return an ArrayList of Task
     */
    public ArrayList<Task> getInitialiseTasks() {
        return tareas.getAllUndoneTasks(1);
    }

    /**
     * adds a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void addTask(TareasCommand command) {
        Task taskToInsert = TaskManager.buildTask(command);

        tareas.insertTask(taskToInsert, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Task successfully added - "  + taskToInsert.getDescription());

        Date now = new Date();
        Log.i(TAG, "User has performed a task adding action " + now.toString());
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

        if (command.getArgument("start") != null) {
            taskToInsert.setStartDateTime(Parser.getDateTimeFromString(command.getArgument("start")));
        }

        if (command.getArgument("end") != null) {
            taskToInsert.setEndDateTime(Parser.getDateTimeFromString(command.getArgument("end")));
        }

        if (command.getArgument("deadline") != null) {
            taskToInsert.setDeadline(Parser.getDateTimeFromString(command.getArgument("deadline")));
        }

        // TODO abstract edit changes into a method

        int tasksSize = taskManager.get().size();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        taskToInsert.setTaskID(mappedTaskId);

        tareas.editTask(taskToInsert, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Task successfully edited - " + taskToInsert.getDescription());

        Date now = new Date();
        Log.i(TAG, "User has performed a task editing action at " + now.toString());
    }

    /**
     * deletes a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void deleteTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        tareas.deleteTask(mappedTaskId, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Task successfully deleted - " + taskDescriptionForFeedback);

        Date now = new Date();
        Log.i(TAG, "User has performed a task deletion action at " + now.toString());
    }

    /**
     * searches a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void searchTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        tareas.searchTask(taskId, 1);
        // TODO Add in feedback to user on the GUI side of things
        // TODO change feedback to include task description for useful user feedback

        Date now = new Date();
        Log.i(TAG, "User has performed a task search action at " + now.toString());
    }

    /**
     * completes a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void completeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        System.out.println(mappedTaskId);
        
        tareas.markTaskAsCompleted(mappedTaskId, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Successfully completed Task - " + taskDescriptionForFeedback);

        Date now = new Date();
        Log.i(TAG, "User has performed a task completion action at " + now.toString());
    }

    /**
     * postpones a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void postponeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        Task taskToPostpone = taskManager.get().get(tasksSize - taskId);

        if (command.getArgument("to") != null) {
            taskToPostpone.setDeadline(Parser.getDateTimeFromString(command.getArgument("to")));

            // TODO support postpone for timed tasks as well? - ask for opinions first
        }

        if (command.getArgument("by") != null) {
            // TODO support the by format for both deadline and timed tasks - ask for opinions on timed tasks
        }

        tareas.postponeTask(taskToPostpone, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Task has been successfully postponed - " + taskDescriptionForFeedback);

        Date now = new Date();
        Log.i(TAG, "User has performed a task postponing action at " + now.toString());
    }

    /**
     * completes a view request by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void viewRequest(TareasCommand command) {
        // assert that the primary argument viewType to be extracted is a string
        assert(command.getPrimaryArgument().getClass().equals(String.class));

        String viewType = command.getPrimaryArgument();

        ArrayList<Task> tasksToShowToUser = new ArrayList<>();

        // only today view supported for now
        if (viewType.equals("today")) {
            tasksToShowToUser = tareas.getAllUndoneTasks(1);
        }
        // TODO add support for other types of view

        guiController.sendTaskstoView(tasksToShowToUser);
        guiController.sendSuccessToView("View has successfully been changed to " + viewType);

        Date now = new Date();
        Log.i(TAG, "User has performed a view change action at " + now.toString());
    }

    /**
     * prioritize a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void prioritizeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        Task taskToPrioritize= taskManager.get().get(tasksSize - taskId);

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        if (taskToPrioritize.isTaskPriority()) {
            tareas.prioritizeTask(mappedTaskId, false, 1);
            // TODO talk to team about allow de-prioritize stuff
        } else {
            tareas.prioritizeTask(mappedTaskId, true, 1);
        }

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Task has been successfully prioritized - " + taskDescriptionForFeedback);

        Date now = new Date();
        Log.i(TAG, "User has performed a task prioritizing action at " + now.toString());
    }

    /**
     * categorize a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void categorizeTask(TareasCommand command) {
        // Do nothing, categorize no longer supported - TODO remove in future
    }

    /**
     * set a task reminder by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void setTaskReminder(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();
        
        // TODO tell the storage that a task has a reminder set

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Reminder Set for task - " + taskDescriptionForFeedback);

        Date now = new Date();
        Log.i(TAG, "User has performed a task reminder action at " + now.toString());
    }

    /**
     * backups all tasks data by calling the appropriate GUI and storage methods
     */
    private void backup() {
        // Do nothing, backup no longer supported - TODO remove in future
    }

    /**
     * mute Tareas by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void mute(TareasCommand command) {
        // TODO grab the time start and end to be passed to TareasIO
    	
        // TODO tell the storage to mute everything from time to time
        guiController.sendSuccessToView("Tareas successfully muted from time1 to time2");
        // TODO change feedback to include task description for useful user feedback

        Date now = new Date();
        Log.i(TAG, "User has performed a mute action at " + now.toString());
    }

    /**
     * changes Tareas font settings by calling the appropriate GUI method
     *
     * @param command after being parsed from the parser
     */
    private void changeFont(TareasCommand command) {
        // TODO grab the font arguments to be passed to the GUI
    	
        // TODO tell the GUI to change the font
        guiController.sendSuccessToView("Font changed successfully to {{fontType}}");
        // TODO change feedback to include task description for useful user feedback

        Date now = new Date();
        Log.i(TAG, "User has performed a font change action at " + now.toString());
    }

    /**
     * colorize a task by calling the appropriate GUI and storage method
     *
     * @param command after being parsed from the parser
     */
    private void colorizeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();
    	
        // TODO tell the storage to change the color of the task

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        guiController.sendSuccessToView("Successfully changed color of task - " + taskDescriptionForFeedback);

        Date now = new Date();
        Log.i(TAG, "User has performed a task colorization action at " + now.toString());
    }

    /**
     * undoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private void undo() {
        if (taskManager.isAbleToUndo()) {
            Tasks stateToRevertTo = taskManager.getUndoState();

            tareas.undoWrite(stateToRevertTo, 1);

            guiController.sendTaskstoView(stateToRevertTo.get());
            guiController.sendSuccessToView("Undo Successful");
            // TODO make the feedback show something more helpful

            Date now = new Date();
            Log.i(TAG, "User has performed an undo action at " + now.toString());
		} else {
            guiController.sendWarningToView("Nothing to undo");

            Date now = new Date();
            Log.e(TAG, "User tried to undo an action when there is nothing to undo at " + now.toString());
		}
    }

    /**
     * redoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private void redo() {
        if (taskManager.isAbleToRedo()) {
		    Tasks stateToRevertTo = taskManager.getRedoState();

            tareas.redoWrite(stateToRevertTo, 1);

            guiController.sendTaskstoView(stateToRevertTo.get());
            guiController.sendSuccessToView("Redo Successful");
            // TODO make the feedback show something more helpful

            Date now = new Date();
            Log.i(TAG, "User has performed a redo action at " + now.toString());
		} else {
            guiController.sendWarningToView("Nothing to redo");

            Date now = new Date();
            Log.e(TAG, "User tried to redo an action when there is nothing to redo at " + now.toString());
		}
    }
}
