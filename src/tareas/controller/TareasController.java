package tareas.controller;

import tareas.common.Task;
import tareas.parser.TareasCommand;
import tareas.storage.TareasIO;

import java.util.ArrayList;

/**
 * @author Yap Jun Hao
 *         <p/>
 *         This class binds the other parts of the program together.
 */

public class TareasController {

    // Instantiate a TareasIO
    TareasIO tareas = new TareasIO();

    // Keeping an ArrayList of states for both redoing and undoing
    ArrayList<TaskManager> redoHistory = new ArrayList<TaskManager>();
    ArrayList<TaskManager> undoHistory = new ArrayList<TaskManager>();

    /**
     * Takes the user's input from the GUI and does the right stuff to make the program work
     *
     * @param userInput the user's input from TareasGUI
     */
    public TareasCommand executeCommand(String userInput) {
        TareasCommand command = TareasCommand.fromString(userInput);
        //TareasBehavior behavior = command.getBehavior();
        //behavior.run();
        // dummy

        switch (command.getType()) {
            case ADD_COMMAND:
                addTask(command);
                return command;
            case EDIT_COMMAND:
                editTask(command);
                return command;
            case DELETE_COMMAND:
                deleteTask(command);
                return command;
            case SEARCH_COMMAND:
                searchTask(command);
                return command;
            case DONE_COMMAND:
                completeTask(command);
                return command;
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
            	//TODO add a feedback to the user giving them a feedback
                //TODO should we throw a TareasException or the sort?
        }
        // QUICKFIX
        return command;
    }

    /**
     * builds a task using the command given by the user after being parsed by the parser
     *
     * @param command from the user input so that the task can be built
     */
    private Task buildTask(TareasCommand command) {
        Task taskToReturn = new Task();
        // Can remove in the future once all the different types are supported

        if (command.hasKey("tag")) {
            //TODO support tagged tasks
        } else if (command.hasKey("from")) {
            //TODO support timed tasks
        } else if (command.hasKey("by")) {
            //TODO support deadline tasks
        } else if (command.hasKey("recurring")) {
            //TODO support recurring tasks
        } else {
            String taskDescription = command.getPrimaryArgument();

            taskToReturn = Task.createFloatingTask(taskDescription);
        }

        return taskToReturn;
    }

    /**
     * adds a task by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task can be built
     */
    private void addTask(TareasCommand command) {
        Task taskToInsert = buildTask(command);

        tareas.insertTask(taskToInsert);
        //TODO sync the state of the undo history
        clearRedoState();
    }

    /**
     * edits a task by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task can be edited
     */
    private void editTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());
        Task taskToInsert = new Task();

        if (command.getArgument("des") != null) {
            taskToInsert.setDescription(command.getArgument("des"));
        }
        
        taskToInsert.setTaskID(taskId);

        tareas.editTask(taskToInsert);
        //TODO sync the state of the undo history
        clearRedoState();
    }

    /**
     * deletes a task by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task can be deleted
     */
    private void deleteTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        tareas.deleteTask(taskId);
        //TODO sync the state of the undo history
        clearRedoState();
    }

    /**
     * searches a task by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task can be searched
     */
    private void searchTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());
        
        tareas.searchTask(taskId);
    }

    /**
     * completes a task by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task to be marked as done can be identified
     */
    private void completeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());
        
        tareas.markTaskAsCompleted(taskId);
        //TODO sync the state of the undo history
        clearRedoState();
    }

    /**
     * postpones a task by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task to be postponed can be identified
     */
    private void postponeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());
        
        //TODO postpone the task to the Storage
        //TODO sync the state of the undo history
        //TODO tell the GUI that a task has been postponed
        clearRedoState();
    }

    /**
     * completes a view request by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the right view to show can be identified
     */
    private void viewRequest(TareasCommand command) {
        //TODO grab the view type so that can call the right stuff from storage and GUI
        
        //TODO ask from the storage all the stuff needed for the view
        //TODO call the GUI method to display the view request
    }

    /**
     * prioritize a task by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task to be prioritized can be identified
     */
    private void prioritizeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());
        
        //TODO tell the storage that a task has been prioritized
        //TODO sync the state of the undo history
        //TODO tell the GUI that a task has been prioritized
        clearRedoState();
    }

    /**
     * categorize a task by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task can be built
     */
    private void categorizeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());
        
        //TODO tell the storage that a task has been categorized
        //TODO sync the state of the undo history
        //TODO tell the GUI that a task has been categorized
        clearRedoState();
    }

    /**
     * set a task reminder by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task can be identified
     */
    private void setTaskReminder(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());
        
        //TODO tell the storage that a task has a reminder set
        //TODO sync the state of the undo history
        //TODO tell the GUI that a task has a reminder set
        clearRedoState();
    }

    /**
     * backups all tasks data by calling the appropriate GUI and storage methods
     */
    private void backup() {
        //TODO tell the storage to backup the data
        //TODO feedback to the GUI that the backup of data has been done
    }

    /**
     * mute Tareas by calling the appropriate GUI and storage methods
     */
    private void mute(TareasCommand command) {
        //TODO grab the time start and end to be passed to TareasIO
    	
        //TODO tell the storage to mute everything from time to time
        //TODO sync the state of the undo history
        //TODO feedback to the GUI that the muting has been done
    }

    /**
     * changes Tareas font settings by calling the appropriate GUI method
     *
     * @param command from the user input so that the font to be changed to can be identified
     */
    private void changeFont(TareasCommand command) {
        //TODO grab the font arguments to be passed to the GUI
    	
        //TODO tell the GUI to change the font
    }

    /**
     * colorize a task by calling the appropriate GUI and storage method
     *
     * @param command from the user input so that the right task can be colored
     */
    private void colorizeTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());
    	
        //TODO tell the storage to change the color of the task
        //TODO sync the state of the undo history
        //TODO feedback to the GUI that the color has been changed
        clearRedoState();
    }

    /**
     * undoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private void undo() {
        if (isAbleToUndo()) {
		    TaskManager stateToRevertTo = undoHistory.remove(undoHistory.size() - 1);

		    addToRedoHistory(stateToRevertTo);
		    //TODO send the state to revert to to the Storage
		    //TODO send the state to revert to to the GUI
		} else {
			//TODO feedback to the user that there is nothing to undo
		}
    }

    /**
     * redoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private void redo() {
        if (isAbleToRedo()) {
		    TaskManager stateToRevertTo = redoHistory.remove(redoHistory.size() - 1);

		    addToUndoHistory(stateToRevertTo);
		    //TODO send the state to revert to to the Storage
		    //TODO send the state to revert to to the GUI
		} else {
			//TODO feedback to the user that there is nothing to undo
		}
    }

    /**
     * checks if there is any redo history to redo
     *
     * @return whether there is anything to redo
     */
    private boolean isAbleToRedo() {
        if (redoHistory.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * checks if there is any undo history to undo
     *
     * @return whether there is anything to undo
     */
    private boolean isAbleToUndo() {
        if (undoHistory.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * checks if there is any undo history to undo
     *
     * @param state of the Tasks to add into the history
     */
    private void addToUndoHistory(TaskManager state) {
        undoHistory.add(state);
    }

    /**
     * checks if there is any undo history to undo
     *
     * @param state of the Tasks to add into the history
     */
    private void addToRedoHistory(TaskManager state) {
        redoHistory.add(state);
    }

    /**
     * clears the redo history after any other action other than undo
     */
    private void clearRedoState() {
        redoHistory.clear();
    }

}
