//@author A0113694A

package tareas.controller;

import tareas.common.Task;
import tareas.common.Tasks;
// import tareas.common.Exceptions;
import tareas.common.Log;
import tareas.parser.TareasCommand;
import tareas.parser.Parser;
import tareas.storage.TareasIO;
import tareas.gui.TareasGUIController;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Stack;
import java.util.ArrayList;

/**
 * @author Yap Jun Hao
 *         <p/>
 *         This class binds the other parts of the program together.
 */

public class TareasController {
    // Constant for Logging
    private static String TAG = "tareas/tareasController";

    // Instantiate a String for feedback when a user does a redo / undo
    private String previousActionType = "No previous action";

    TareasGUIController guiController = TareasGUIController.getInstance();

    TareasIO tareas = new TareasIO();

    TaskManager taskManager = TaskManager.getInstance();

    // TODO MASSIVE ABSTRACTION OF MANY MAGIC STRINGS INTO CONSTANTS IN TAREAS CONSTANTS v0.5
    // TODO Handle SOME exceptions v0.5
    // TODO allow for multiple redo and a proper feedback - now is like anyhow v0.5

    /**
     * constructor for controller, will set the pointer for the task manager
     */
    public TareasController() {
        taskManager.set(tareas.getAllUndoneTasks(1, "undone"));
        taskManager.setId(tareas.getInitialiseLatestId(1));
    }

    /**
     * Takes the user's input from the GUI and does the right stuff to make the program work
     *
     * @param userInput from GUI
     */
    public void executeCommand(String userInput, boolean test) {
        // if the user's input is an empty string, we treat it as if nothing happened
        if (userInput.equals("")) {
            return;
        }

        TareasCommand command = TareasCommand.fromString(userInput);

        // asserting to make sure that the command is really a TareasCommand
        assert(command != null);

        if (!checkCommandValidity(command)) {
            // if command is not valid, do not continue on into execution
            return;
        }

        checkCommandAndExecute(command, test);
    }

    /**
     * helps to initialise GUI view by giving the GUI the set of all tasks
     *
     * @return an ArrayList of Task
     */
    public ArrayList<Task> getInitialiseTasks() {
        guiController.changeCategoryName("All Tasks");
        return tareas.getAllUndoneTasks(1, "undone");
    }

    /**
     * helps to initialise dashboard view by giving the GUI a stack of String values to use
     *
     * Important: Sequence of values
     * 1. Number of completed tasks
     * 2. Number of overdue tasks
     * 3. Number of uncompleted deadline tasks
     * 4. Number of uncompleted timed tasks
     * 5. Number of uncompleted floating tasks
     * 6. Number of uncompleted important tasks
     * 7. Number of completed tasks today
     * 8. Number of tasks completed one day from now
     * 9. Number of tasks completed two days from now
     * 10. Number of tasks completed three days from now
     * 11. Number of tasks completed four days from now
     * 12. Number of tasks completed five from now
     * 13. Number of tasks completed six days from now
     * 14. Number of uncompleted tasks today
     * 15. Number of tasks uncompleted one day from now
     * 16. Number of tasks uncompleted two days from now
     * 17. Number of tasks uncompleted three days from now
     * 18. Number of tasks uncompleted four days from now
     * 19. Number of tasks uncompleted five from now
     * 20. Number of tasks uncompleted six days from now
     *
     * @return a Stack of Integers
     */
    public Stack<Integer> getInitialiseValues() {
        Stack<Integer> values = new Stack<>();

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        ArrayList<Integer> statValues = getStatValues(allTasks);

        for (Integer value : statValues) {
            values.push(value);
        }

        for (int i = 0; i < 7; i++) {
            LocalDate day = LocalDate.now().plusDays(i);

            int numberOfCompletedTaskOnDay = getNumberOfTasksOnDay(allTasks, day, true);

            values.push(numberOfCompletedTaskOnDay);
        }

        for (int i = 0; i < 7; i++) {
            LocalDate day = LocalDate.now().plusDays(i);

            int numberOfUncompletedTaskOnDay = getNumberOfTasksOnDay(allTasks, day, false);

            values.push(numberOfUncompletedTaskOnDay);
        }

        return values;
    }

    /**
     * helper function to grab all the stat values that Tareas will show on the dashboard
     *
     * @param allTasks a list of all tasks done or undone from the storage
     * @return an ArrayList of Integers for the receiving method to process
     */
    private ArrayList<Integer> getStatValues (ArrayList<Task> allTasks) {
        ArrayList<Integer> valuesToReturn = new ArrayList<>();

        int numberOfCompletedTasks = 0;
        int numberOfOverdueTasks = 0;
        int numberOfUncompletedDeadlineTasks = 0;
        int numberOfUncompletedTimedTasks = 0;
        int numberOfUncompletedFloatingTasks = 0;
        int numberOfUncompletedImportantTasks = 0;

        LocalDateTime now = LocalDateTime.now();

        for (Task task : allTasks) {
            if (task.isTaskCompleted()) {
                numberOfCompletedTasks++;
            }

            if (task.getEndDateTime() != null) {
                LocalDateTime endDateTime = task.getEndDateTime();

                if (endDateTime.isAfter(now)) {
                    numberOfOverdueTasks++;
                }
            }

            if (task.getDeadline() != null) {
                LocalDateTime deadline = task.getDeadline();

                if (deadline.isAfter(now)) {
                    numberOfOverdueTasks++;
                }
            }

            if (task.getDeadline() != null && !task.isTaskCompleted()) {
                numberOfUncompletedDeadlineTasks++;
            }

            if (task.getStartDateTime() != null && task.getEndDateTime() != null && !task.isTaskCompleted()) {
                numberOfUncompletedTimedTasks++;
            }

            if (task.getStartDateTime() == null && task.getEndDateTime() == null &&
                    task.getDeadline() == null && !task.isTaskCompleted()) {
                numberOfUncompletedFloatingTasks++;
            }

            if (task.isTaskPriority()) {
                numberOfUncompletedImportantTasks++;
            }
        }

        valuesToReturn.add(numberOfCompletedTasks);
        valuesToReturn.add(numberOfOverdueTasks);
        valuesToReturn.add(numberOfUncompletedDeadlineTasks);
        valuesToReturn.add(numberOfUncompletedTimedTasks);
        valuesToReturn.add(numberOfUncompletedFloatingTasks);
        valuesToReturn.add(numberOfUncompletedImportantTasks);

        return valuesToReturn;
    }

    /**
     * Finds the number of completed tasks for given date for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @param dayToGetFrom the date to get the tasks from
     * @param completed where we're looking for completed or uncompleted tasks
     * @return int number of completed tasks for given date
     */
    private int getNumberOfTasksOnDay(ArrayList<Task> allTasks, LocalDate dayToGetFrom, boolean completed) {
        int numberOfTasks = 0;

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(dayToGetFrom)) {
                    if (task.isTaskCompleted() == completed) {
                        numberOfTasks++;
                    }
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(dayToGetFrom)) {
                    if (task.isTaskCompleted() == completed) {
                        numberOfTasks++;
                    }
                }
            }
        }

        return numberOfTasks;
    }

    /**
     * checks whether the command parsed is valid
     *
     * @param command the command formed by the parser
     */
    private boolean checkCommandValidity(TareasCommand command) {
        switch (Parser.checkCommandValidity(command).getStatus()) {
            case SUCCESS:
                // no feedback, continue on since it's a valid command
                return true;
            case UNKNOWN_COMMAND:
                guiController.sendErrorToView("Unrecognized command, please input a recognized command");
                return false;
            case MISSING_PRIMARY_ARGUMENT:
                guiController.sendErrorToView("Please input something after the action - " +
                        command.getPrimaryKey());
                return false;
            case UNEXPECTED_PRIMARY_ARGUMENT:
                guiController.sendErrorToView("Please input a valid input after the action - " +
                        command.getPrimaryKey());
                return false;
            case UNKNOWN_KEYWORD:
                guiController.sendErrorToView("Please input a valid action - " + command.getPrimaryKey() +
                        " is not recognized");
                return false;
            case SIGNATURE_NOT_MATCHED:
                guiController.sendErrorToView("Please input matching actions - refer to /help for reference");
                return false;
            default:
                Log.w("checkCommandValidity has met with an erronous checking allow it to fall into default" +
                        " which should never happen", TAG);
                return false;
        }
    }

    /**
     * checks the type of the command and executes it
     *
     * @param command the command formed by the parser
     */
    private void checkCommandAndExecute(TareasCommand command, boolean test) {
        switch (command.getType()) {
            case ADD_COMMAND:
                addTask(command, test);
                break;
            case EDIT_COMMAND:
                editTask(command, test);
                break;
            case DELETE_COMMAND:
                deleteTask(command, test);
                break;
            case SEARCH_COMMAND:
                searchTask(command, test);
                break;
            case DETAILED_COMMAND:
                detailedTask(command);
                break;
            case TAG_COMMAND:
                addTagToTask(command, test);
                break;
            case DONE_COMMAND:
                completeTask(command, test);
                break;
            case UNDO_COMMAND:
                undo();
                break;
            case REDO_COMMAND:
                redo();
                break;
            case POSTPONE_COMMAND:
                postponeTask(command, test);
                break;
            case VIEW_COMMAND:
                viewRequest(command);
                break;
            case PRIORITIZE_COMMAND:
                prioritizeTask(command, test);
                break;
            case REMIND_COMMAND:
                setTaskReminder(command, test);
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
                // do nothing - unrecognized command, view feedback handled by check command validity
        }
    }

    /**
     * adds a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void addTask(TareasCommand command, boolean test) {
        Task taskToInsert = TaskManager.buildTask(command);

        tareas.insertTask(taskToInsert, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, "undone");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        taskManager.tasksChanged(allTasks);
        taskManager.clearRedoState();

        if (!test) {
            guiController.sendTaskstoView(newTasks);
            guiController.changeCategoryName("All Tasks");
            guiController.sendSuccessToView("Task successfully added - "  + taskToInsert.getDescription());
        }

        setPreviousActionType("Task with description " + taskToInsert.getDescription() + " removed");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task adding action " + now.toString());
    }

    /**
     * edits a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void editTask(TareasCommand command, boolean test) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());
        Task taskToUpdate = new Task();

        taskToUpdate = updateTask(command, taskToUpdate);

        int tasksSize = taskManager.get().size();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        taskToUpdate.setTaskID(mappedTaskId);

        tareas.editTask(taskToUpdate, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, "undone");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        taskManager.tasksChanged(allTasks);
        taskManager.clearRedoState();

        if (!test) {
            guiController.sendTaskstoView(newTasks);
            guiController.changeCategoryName("All Tasks");
            guiController.sendSuccessToView("Task successfully edited - " + taskToUpdate.getDescription());
        }

        setPreviousActionType("Task with description " + taskToUpdate.getDescription() + " edit reverted");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task editing action at " + now.toString());
    }

    /**
     * helper method for editTask to update the task with supported editing types
     *
     * @param command after being parsed from the parser and taskToUpdate the task being updated
     */
    private Task updateTask(TareasCommand command, Task taskToUpdate) {
        if (command.getArgument("des") != null) {
            taskToUpdate.setDescription(command.getArgument("des"));
        }

        if (command.getArgument("start") != null) {
            taskToUpdate.setStartDateTime(Parser.getDateTimeFromString(command.getArgument("start")));
        }

        if (command.getArgument("end") != null) {
            taskToUpdate.setEndDateTime(Parser.getDateTimeFromString(command.getArgument("end")));
        }

        if (command.getArgument("deadline") != null) {
            taskToUpdate.setDeadline(Parser.getDateTimeFromString(command.getArgument("deadline")));
        }

        return taskToUpdate;
    }

    /**
     * deletes a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void deleteTask(TareasCommand command, boolean test) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        tareas.deleteTask(mappedTaskId, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1 , "undone");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        taskManager.tasksChanged(allTasks);
        taskManager.clearRedoState();

        if (!test) {
            guiController.sendTaskstoView(newTasks);
            guiController.changeCategoryName("All Tasks");
            guiController.sendSuccessToView("Task successfully deleted - " + taskDescriptionForFeedback);
        }

        setPreviousActionType("Task with description " + taskDescriptionForFeedback + " added back");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task deletion action at " + now.toString());
    }

    /**
     * adds a tag to a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void addTagToTask(TareasCommand command, boolean test) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        String tagToAdd = command.getArgument("with");

        tareas.addTags(mappedTaskId, tagToAdd, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1 , "undone");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        taskManager.tasksChanged(allTasks);
        taskManager.clearRedoState();

        if (!test) {
            guiController.sendTaskstoView(newTasks);
            guiController.changeCategoryName("All Tasks");
            guiController.sendSuccessToView("Tag successfully added to task - " + taskDescriptionForFeedback);
        }

        setPreviousActionType("Tag successfully added to task - " + taskDescriptionForFeedback + " reverted");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task deletion action at " + now.toString());
    }

    /**
     * shows a detailed view of a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void detailedTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        Task taskToShow = tareas.detailedTask(mappedTaskId, 1);

        guiController.showDetailedView(taskToShow);
        guiController.changeCategoryName("All Tasks");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task search action at " + now.toString());
    }

    /**
     * searches a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void searchTask(TareasCommand command, boolean test) {
        String searchString = command.getPrimaryArgument();

        ArrayList<Task> tasksToShow = tareas.searchTask(searchString, 1);

        guiController.sendTaskstoView(tasksToShow);
        guiController.changeCategoryName("Tasks Found - '" + searchString + "'");

        if (!test) {
            if (tasksToShow.size() == 0) {
                guiController.sendErrorToView("No tasks found - provide another search term or tag?");
            } else {
                guiController.sendSuccessToView(tasksToShow.size() + " tasks found");
            }
        }

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task search action at " + now.toString());
    }

    /**
     * completes a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void completeTask(TareasCommand command, boolean test) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        tareas.markTaskAsCompleted(mappedTaskId, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, "undone");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        taskManager.tasksChanged(allTasks);
        taskManager.clearRedoState();

        if (!test) {
            guiController.sendTaskstoView(newTasks);
            guiController.changeCategoryName("All Tasks");
            guiController.sendSuccessToView("Successfully completed Task - " + taskDescriptionForFeedback);
        }

        setPreviousActionType("Task with description " + taskDescriptionForFeedback + " no longer completed");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task completion action at " + now.toString());
    }

    /**
     * postpones a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void postponeTask(TareasCommand command, boolean test) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        Task taskToPostpone = taskManager.get().get(tasksSize - taskId);

        if (command.getArgument("to") != null) {
            taskToPostpone.setDeadline(Parser.getDateTimeFromString(command.getArgument("to")));
        }

        if (command.getArgument("by") != null) {
            taskToPostpone.setDeadline(Parser.getDateTimeFromString(command.getArgument("by")));
        }

        tareas.postponeTask(taskToPostpone, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, "undone");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        taskManager.tasksChanged(allTasks);
        taskManager.clearRedoState();

        if (!test) {
            guiController.sendTaskstoView(newTasks);
            guiController.changeCategoryName("All Tasks");
            guiController.sendSuccessToView("Task has been successfully postponed - " + taskDescriptionForFeedback);
        }

        setPreviousActionType("Task with description " + taskDescriptionForFeedback + " unpostponed");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task postponing action at " + now.toString());
    }

    /**
     * completes a view request by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void viewRequest(TareasCommand command) {
        String viewType = command.getPrimaryArgument();

        ArrayList<Task> tasksToShowToUser;

        tasksToShowToUser = checkViewTypeAndExecute(viewType);

        if (!viewType.equals("dashboard") && !viewType.equals("help")) {
            guiController.sendTaskstoView(tasksToShowToUser);
            // View type is handled at checkViewTypeAndExecute instead for this method
            guiController.sendSuccessToView("View has successfully been changed to " + viewType);
        }

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a view change action at " + now.toString());
    }

    /**
     * completes a view request by calling the appropriate GUI and storage methods
     *
     * @param viewType the view type that is parsed by the parser
     * @return the ArrayList of task that is gotten from the Storage
     */
    private ArrayList<Task> checkViewTypeAndExecute(String viewType) {
        ArrayList<Task> tasksToShowToUser = new ArrayList<>();

        // if the view type is equal to the view types supported for natural languages
        if (viewType.equals("all") || viewType.equals("deadline") || viewType.equals("timed") ||
                viewType.equals("floating") || viewType.equals("today") || viewType.equals("tomorrow") ||
                    viewType.equals("done") || viewType.equals("undone") || viewType.equals("important") ||
                        viewType.equals("overdue") || viewType.equals("dashboard") || viewType.equals("help")) {

            if (viewType.equals("all")) {
                tasksToShowToUser = tareas.getAllUndoneTasks(1, "undone");
                guiController.changeCategoryName("All Tasks");
            }

            if (viewType.equals("deadline")) {
                tasksToShowToUser = tareas.getAllUndoneTasks(1, "deadline");
                guiController.changeCategoryName("Deadline Tasks");
            }

            if (viewType.equals("timed")) {
                tasksToShowToUser = tareas.getAllUndoneTasks(1, "timed");
                guiController.changeCategoryName("Timed Tasks");
            }

            if (viewType.equals("floating")) {
                tasksToShowToUser = tareas.getAllUndoneTasks(1, "floating");
                guiController.changeCategoryName("Floating Tasks");
            }

            if (viewType.equals("undone")) {
                tasksToShowToUser = tareas.getAllUndoneTasks(1, "undone");
                guiController.changeCategoryName("Undone Tasks");
            }

            if (viewType.equals("today")) {
                tasksToShowToUser = tareas.getAllUndoneTasks(1, "today");
                guiController.changeCategoryName("Today's Tasks");
            }

            if (viewType.equals("tomorrow")) {
                tasksToShowToUser = tareas.getAllUndoneTasks(1, "tomorrow");
                guiController.changeCategoryName("Tomorrow's Tasks");
            }

            if (viewType.equals("done")) {
                tasksToShowToUser = tareas.getAllUndoneTasks(1, "done");
                guiController.changeCategoryName("Done Tasks");
            }

            if (viewType.equals("important")) {
                tasksToShowToUser = tareas.getAllUndoneTasks(1, "important");
                guiController.changeCategoryName("Important Tasks");
            }

            if (viewType.equals("overdue")) {
                tasksToShowToUser = tareas.getAllUndoneTasks(1, "overdue");
                guiController.changeCategoryName("Overdue Tasks");
            }

            if (viewType.equals("dashboard")) {
                // send empty - view is kept on GUI side
                guiController.showDashboard();
            }

            if (viewType.equals("help")) {
                // send empty - view is kept on GUI side
                guiController.showHelpView();
            }

        } else {
            // if it's not then it's a particular date then we parse it into a date type
            LocalDate dateToPassToStorage = Parser.getDateTimeFromString(viewType).toLocalDate();

            tasksToShowToUser = tareas.getParticularDateTask(1, dateToPassToStorage);
            guiController.changeCategoryName(dateToPassToStorage.toString() + "'s Tasks");
        }

        return tasksToShowToUser;
    }
    /**
     * prioritize a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void prioritizeTask(TareasCommand command, boolean test) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        Task taskToPrioritize= taskManager.get().get(tasksSize - taskId);

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        String prioritizedOrNot;

        if (taskToPrioritize.isTaskPriority()) {
            tareas.prioritizeTask(mappedTaskId, false, 1);
            prioritizedOrNot = "prioritized";
        } else {
            tareas.prioritizeTask(mappedTaskId, true, 1);
            prioritizedOrNot = "unprioritized";
        }

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, "undone");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        taskManager.tasksChanged(allTasks);
        taskManager.clearRedoState();

        if (!test) {
            guiController.sendTaskstoView(newTasks);
            guiController.changeCategoryName("All Tasks");
            guiController.sendSuccessToView("Task has been successfully " + prioritizedOrNot + " - " + taskDescriptionForFeedback);
        }

        setPreviousActionType("Task with description " + taskDescriptionForFeedback + " no longer prioritized");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task prioritizing action at " + now.toString());
    }

    /**
     * set a task reminder by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void setTaskReminder(TareasCommand command, boolean test) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        LocalDateTime reminderDateTime = Parser.getDateTimeFromString(command.getArgument("on"));

        tareas.setTaskReminder(mappedTaskId, reminderDateTime, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, "undone");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        taskManager.tasksChanged(allTasks);
        taskManager.clearRedoState();

        if (!test) {
            guiController.sendTaskstoView(newTasks);
            guiController.changeCategoryName("All Tasks");
            guiController.sendSuccessToView("Reminder Set for task - " + taskDescriptionForFeedback);
        }

        setPreviousActionType("Task with description " + taskDescriptionForFeedback + " reminder removed");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task reminder action at " + now.toString());
    }

    /**
     * mute Tareas by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void mute(TareasCommand command) {
        LocalDateTime startTime = Parser.getDateTimeFromString(command.getPrimaryArgument());
        LocalDateTime endTime = Parser.getDateTimeFromString(command.getArgument("to"));

        // tareas.addMuteTiming(startTime, endTime);
        // TODO once supported by Storage - Lareina v0.4

        guiController.sendSuccessToView("Tareas successfully muted from " + startTime.toString() + " " + endTime.toString());

        setPreviousActionType("Mute timing from " + startTime.toString() + " " + endTime.toString() + " removed");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a mute action at " + now.toString());
    }

    /**
     * changes Tareas font settings by calling the appropriate GUI method
     *
     * @param command after being parsed from the parser
     */
    private void changeFont(TareasCommand command) {
        String newFontType = command.getPrimaryArgument();
        // String previousFontType = tareas.getFontType();

        // tareas.saveFontType(fontType, fontSize);

        guiController.sendSuccessToView("Font changed successfully to - " + newFontType);

        // setPreviousActionType("Font changed back to " + previousFontType);
        // TODO once supported by Storage - Lareina v0.4

        LocalDateTime now = LocalDateTime.now();
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

        // tareas.changeTaskColor(mappedTaskId, color);
        // TODO once supported by Storage - Lareina v0.4

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, "undone");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        taskManager.tasksChanged(allTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        guiController.changeCategoryName("All Tasks");
        guiController.sendSuccessToView("Successfully changed color of task - " + taskDescriptionForFeedback);

        setPreviousActionType("Task with description " + taskDescriptionForFeedback + " color unset");

        LocalDateTime now = LocalDateTime.now();
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
            guiController.changeCategoryName("All Tasks");
            guiController.sendSuccessToView("Undo Successful - " + getPreviousActionType());

            LocalDateTime now = LocalDateTime.now();
            Log.i(TAG, "User has performed an undo action at " + now.toString());
		} else {
            guiController.sendWarningToView("Nothing to undo");

            LocalDateTime now = LocalDateTime.now();
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
            guiController.changeCategoryName("All Tasks");
            guiController.sendSuccessToView("Redo Successful - " + getPreviousActionType());

            LocalDateTime now = LocalDateTime.now();
            Log.i(TAG, "User has performed a redo action at " + now.toString());
		} else {
            guiController.sendWarningToView("Nothing to redo");

            LocalDateTime now = LocalDateTime.now();
            Log.e(TAG, "User tried to redo an action when there is nothing to redo at " + now.toString());
		}
    }

    /**
     * redoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private String getPreviousActionType() {
        return previousActionType;
    }

    /**
     * redoes the user's action by returning the state for both UI and Storage, parser is not needed here
     */
    private void setPreviousActionType(String actionType) {
        previousActionType = actionType;
    }
}
