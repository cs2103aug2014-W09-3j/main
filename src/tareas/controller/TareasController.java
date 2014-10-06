package tareas.controller;

import tareas.common.Task;
import tareas.common.Tasks;
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
    ArrayList<Tasks> redoHistory = new ArrayList<Tasks>();
    ArrayList<Tasks> undoHistory = new ArrayList<Tasks>();

    /**
     * Takes the user's input from the GUI and does the right stuff to make the program work
     *
     * @param userInput the user's input from TareasGUI
     */
    public void executeCommand(String userInput) {
        TareasCommand command = TareasCommand.fromString(userInput);
        //TareasBehavior behavior = command.getBehavior();
        //behavior.run();

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
                mute();
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
    }

    /**
     * builds a task using the command given by the user after being parsed by the parser
     *
     * @param command from the user input so that the task can be built
     */
    private Task buildTask(TareasCommand command) {
        Task taskToReturn = new Task();

        //TODO build the task using the methods provided from TareasCommand, clarify with Kent on 6 OCT

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
        //TODO add the task to the GUI
        clearRedoState();
    }

    /**
     * edits a task by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task can be built
     */
    private void editTask(TareasCommand command) {
        Task taskToEdit = buildTask(command);
        
        //TODO edit the task to the Storage
        //TODO edit the task to the GUI
        clearRedoState();
    }

    /**
     * deletes a task by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task can be built
     */
    private void deleteTask(TareasCommand command) {
        //TODO grab the task id to be deleted to be passed to TareasIO
        //Task deletedTask = tareas.getTask(0);

        tareas.deleteTask(0);
        //TODO inform the GUI that a task has been deleted
        clearRedoState();
    }

    /**
     * searches a task by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void searchTask(TareasCommand command) {
        Task taskToSearch = buildTask(command);
        
        //TODO search the task from the Storage
        //TODO feedback the task searched to the GUI
    }

    /**
     * completes a task by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void completeTask(TareasCommand command) {
        //TODO grab the task id to be marked as done to be passed to TareasIO
        
        //TODO mark the task as done from the Storage
        //TODO tell the GUI that the task has been marked as done
        clearRedoState();
    }

    /**
     * postpones a task by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void postponeTask(TareasCommand command) {
        //TODO grab the task id to be marked as done to be passed to TareasIO
        
        //TODO postpone the task to the Storage
        //TODO tell the GUI that a task has been postponed
        clearRedoState();
    }

    /**
     * completes a view request by calling the appropriate GUI and storage methods
     *
     * @param TareasCommand command from the user input so that the task can be built
     */
    private void viewRequest(TareasCommand command) {
        //TODO grab the view type so that can call the right stuff from storage and GUI
        
        //TODO ask from the storage all the stuff needed for the view
        //TODO call the GUI method to display the view request
    }

    /**
     * prioritize a task by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task can be built
     */
    private void prioritizeTask(TareasCommand command) {
        //TODO grab the task id to be prioritized to be passed to TareasIO
        
        //TODO tell the storage that a task has been prioritized
        //TODO tell the GUI that a task has been prioritized
        clearRedoState();
    }

    /**
     * categorize a task by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task can be built
     */
    private void categorizeTask(TareasCommand command) {
        //TODO grab the task id to be categorized to be passed to TareasIO
        
        //TODO tell the storage that a task has been categorized
        //TODO tell the GUI that a task has been categorized
        clearRedoState();
    }

    /**
     * set a task reminder by calling the appropriate GUI and storage methods
     *
     * @param  command from the user input so that the task can be built
     */
    private void setTaskReminder(TareasCommand command) {
        //TODO grab the task id to have it's reminder set to be passed to TareasIO
        
        //TODO tell the storage that a task has a reminder set
        //TODO tell the GUI that a task has a reminder set
        clearRedoState();
    }

    /**
     * backups all tasks data by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task can be built
     */
    private void backup() {
        //TODO tell the storage to backup the data
        //TODO feedback to the GUI that the backup of data has been done
    }

    /**
     * mute Tareas by calling the appropriate GUI and storage methods
     *
     * @param command from the user input so that the task can be built
     */
    private void mute() {
        //TODO grab the time start and end to be passed to TareasIO
    	
        //TODO tell the storage to mute everything from time to time
        //TODO feedback to the GUI that the muting has been done
    }

    /**
     * changes Tareas font settings by calling the appropriate GUI method
     *
     * @param command from the user input so that the task can be built
     */
    private void changeFont(TareasCommand command) {
        //TODO grab the font arguments to be passed to the GUI
    	
        //TODO tell the GUI to change the font
    }

    /**
     * colorize a task by calling the appropriate GUI and storage method
     *
     * @param command from the user input so that the task can be built
     */
    private void colorizeTask(TareasCommand command) {
        //TODO grab the ID of the task that should be colorized and also the color so that can call the right methods
    	
        //TODO tell the storage to change the color of the task
        //TODO feedback to the GUI that the color has been changed
        clearRedoState();
    }

    /**
     * undoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private void undo() {
        if (isAbleToUndo()) {
		    Tasks stateToRevertTo = undoHistory.remove(undoHistory.size() - 1);

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
		    Tasks stateToRevertTo = redoHistory.remove(redoHistory.size() - 1);

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
     * @return
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
     * @return
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
    private void addToUndoHistory(Tasks state) {
        undoHistory.add(state);
    }

    /**
     * checks if there is any undo history to undo
     *
     * @param state of the Tasks to add into the history
     */
    private void addToRedoHistory(Tasks state) {
        redoHistory.add(state);
    }

    /**
     * clears the redo history after any other action other than undo
     */
    private void clearRedoState() {
        redoHistory.clear();
    }

}
