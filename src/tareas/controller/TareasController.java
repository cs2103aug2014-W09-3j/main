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
        taskManager.set(tareas.getAllUndoneTasks(1, 0));
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

        TareasCommand command = TareasCommand.fromString(userInput);

        // asserting to make sure that the command is really a TareasCommand
        assert(command != null);

        checkCommandValidity(command);

        checkCommandAndExecute(command);
    }

    /**
     * helps to initialise GUI view by giving the GUI the set of all tasks
     *
     * @return an ArrayList of Task
     */
    public ArrayList<Task> getInitialiseTasks() {
        return tareas.getAllUndoneTasks(1, 0);
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
     * 8. Number of uncompleted tasks today
     * 9. Number of completed tasks one day from now
     * 10. Number of uncompleted tasks one day from now
     * 11. Number of tasks completed two days from now
     * 11. Number of tasks uncompleted two days from now
     * 12. Number of tasks completed three days from now
     * 13. Number of tasks uncompleted three days from now
     * 14. Number of tasks completed four days from now
     * 15. Number of tasks uncompleted four days from now
     * 16. Number of tasks completed five from now
     * 17. Number of tasks uncompleted five from now
     * 18. Number of tasks completed six days from now
     * 19. Number of tasks uncompleted six days from now
     *
     * @return a Stack of Integers
     */
    public Stack<Integer> getInitialiseValues() {
        Stack<Integer> values = new Stack<>();

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        int numberOfCompletedTasks = getNumberOfCompletedTasks(allTasks);
        values.push(numberOfCompletedTasks);

        int numberOfOverdueTasks = getNumberOfOverdueTasks(allTasks);
        values.push(numberOfOverdueTasks);

        int numberOfUncompletedDeadlineTasks = getNumberOfUncompletedDeadlineTasks(allTasks);
        values.push(numberOfUncompletedDeadlineTasks);

        int numberOfUncompletedTimedTasks = getNumberOfUncompletedTimedTasks(allTasks);
        values.push(numberOfUncompletedTimedTasks);

        int numberOfUncompletedFloatingTasks = getNumberOfUncompletedFloatingTasks(allTasks);
        values.push(numberOfUncompletedFloatingTasks);

        int numberOfUncompletedImportantTasks = getNumberOfUncompletedImportantTasks(allTasks);
        values.push(numberOfUncompletedImportantTasks);

        int numberOfCompletedTaskToday = getNumberOfCompletedTasksToday(allTasks);
        int numberOfUncompletedTaskToday = getNumberOfUncompletedTasksToday(allTasks);
        values.push(numberOfCompletedTaskToday);
        values.push(numberOfUncompletedTaskToday);

        int numberOfCompletedTaskSecondDay = getNumberOfCompletedTasksSecondDay(allTasks);
        int numberOfUncompletedTaskSecondDay = getNumberOfUncompletedTasksSecondDay(allTasks);
        values.push(numberOfCompletedTaskSecondDay);
        values.push(numberOfUncompletedTaskSecondDay);

        int numberOfCompletedTaskThirdDay = getNumberOfCompletedTasksThirdDay(allTasks);
        int numberOfUncompletedTaskThirdDay = getNumberOfUncompletedTasksThirdDay(allTasks);
        values.push(numberOfCompletedTaskThirdDay);
        values.push(numberOfUncompletedTaskThirdDay);

        int numberOfCompletedTaskFourthDay = getNumberOfCompletedTasksFourthDay(allTasks);
        int numberOfUncompletedTaskFourthDay = getNumberOfUncompletedTasksFourthDay(allTasks);
        values.push(numberOfCompletedTaskFourthDay);
        values.push(numberOfUncompletedTaskFourthDay);

        int numberOfCompletedTaskFifthDay = getNumberOfCompletedTasksFifthDay(allTasks);
        int numberOfUncompletedTaskFifthDay = getNumberOfUncompletedTasksFifthDay(allTasks);
        values.push(numberOfCompletedTaskFifthDay);
        values.push(numberOfUncompletedTaskFifthDay);

        int numberOfCompletedTaskSixthDay = getNumberOfCompletedTasksSixthDay(allTasks);
        int numberOfUncompletedTaskSixthDay = getNumberOfUncompletedTasksSixthDay(allTasks);
        values.push(numberOfCompletedTaskSixthDay);
        values.push(numberOfUncompletedTaskSixthDay);

        int numberOfCompletedTaskSeventhDay = getNumberOfCompletedTasksSeventhDay(allTasks);
        int numberOfUncompletedTaskSeventhDay = getNumberOfUncompletedTasksSeventhDay(allTasks);
        values.push(numberOfCompletedTaskSeventhDay);
        values.push(numberOfUncompletedTaskSeventhDay);

        return values;
    }

    /**
     * Finds the number of completed tasks for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of completed tasks
     */
    private int getNumberOfCompletedTasks(ArrayList<Task> allTasks) {
        int numberOfCompletedTasks = 0;

        for (Task task : allTasks) {
            if (task.isTaskCompleted()) {
                numberOfCompletedTasks++;
            }
        }

        return numberOfCompletedTasks;
    }

    /**
     * Finds the number of overdue tasks for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of overdue tasks
     */
    private int getNumberOfOverdueTasks(ArrayList<Task> allTasks) {
        int numberOfOverdueTasks = 0;

        LocalDateTime now = LocalDateTime.now();

        for (Task task : allTasks) {
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
        }

        return numberOfOverdueTasks;
    }

    /**
     * Finds the number of uncompleted deadline tasks for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of uncompleted deadline tasks
     */
    private int getNumberOfUncompletedDeadlineTasks(ArrayList<Task> allTasks) {
        int numberOfUncompletedDeadlineTasks = 0;

        for (Task task : allTasks) {
            if (task.getDeadline() != null && !task.isTaskCompleted()) {
               numberOfUncompletedDeadlineTasks++;
            }
        }

        return numberOfUncompletedDeadlineTasks;
    }

    /**
     * Finds the number of uncompleted timed tasks for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of uncompleted time tasks
     */
    private int getNumberOfUncompletedTimedTasks(ArrayList<Task> allTasks) {
        int numberOfUncompletedTimedTasks = 0;

        for (Task task : allTasks) {
            if (task.getStartDateTime() != null && task.getEndDateTime() != null && !task.isTaskCompleted()) {
                numberOfUncompletedTimedTasks++;
            }
        }

        return numberOfUncompletedTimedTasks;
    }

    /**
     * Finds the number of uncompleted floating tasks for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of uncompleted floating tasks
     */
    private int getNumberOfUncompletedFloatingTasks(ArrayList<Task> allTasks) {
        int numberOfUncompletedFloatingTasks = 0;

        for (Task task : allTasks) {
            if (task.getStartDateTime() == null && task.getEndDateTime() == null &&
                    task.getDeadline() == null && !task.isTaskCompleted()) {
                numberOfUncompletedFloatingTasks++;
            }
        }

        return numberOfUncompletedFloatingTasks;
    }

    /**
     * Finds the number of uncompleted important tasks for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of uncompleted important tasks
     */
    private int getNumberOfUncompletedImportantTasks(ArrayList<Task> allTasks) {
        int numberOfUncompletedImportantTasks = 0;

        for (Task task : allTasks) {
            if (task.isTaskPriority()) {
                numberOfUncompletedImportantTasks++;
            }
        }

        return numberOfUncompletedImportantTasks;
    }

    /**
     * Finds the number of completed tasks for today for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of completed tasks for today
     */
    private int getNumberOfCompletedTasksToday(ArrayList<Task> allTasks) {
        int numberOfCompletedTasksToday = 0;

        LocalDate today = LocalDate.now();

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(today) && task.isTaskCompleted()) {
                    numberOfCompletedTasksToday++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(today) && task.isTaskCompleted()) {
                    numberOfCompletedTasksToday++;
                }
            }
        }

        return numberOfCompletedTasksToday;
    }

    /**
     * Finds the number of uncompleted tasks for today for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of uncompleted tasks for today
     */
    private int getNumberOfUncompletedTasksToday(ArrayList<Task> allTasks) {
        int numberOfUncompletedTasksToday = 0;

        LocalDate today = LocalDate.now();

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(today) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksToday++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(today) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksToday++;
                }
            }
        }

        return numberOfUncompletedTasksToday;
    }

    /**
     * Finds the number of completed tasks for second day for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of completed tasks for second day
     */
    private int getNumberOfCompletedTasksSecondDay(ArrayList<Task> allTasks) {
        int numberOfCompletedTasksSecondDay = 0;

        LocalDate secondDay = LocalDate.now().plusDays(1);

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(secondDay) && task.isTaskCompleted()) {
                    numberOfCompletedTasksSecondDay++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(secondDay) && task.isTaskCompleted()) {
                    numberOfCompletedTasksSecondDay++;
                }
            }
        }

        return numberOfCompletedTasksSecondDay;
    }

    /**
     * Finds the number of uncompleted tasks for second day for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of uncompleted tasks for second day
     */
    private int getNumberOfUncompletedTasksSecondDay(ArrayList<Task> allTasks) {
        int numberOfUncompletedTasksSecondDay = 0;

        LocalDate secondDay = LocalDate.now().plusDays(1);

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(secondDay) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksSecondDay++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(secondDay) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksSecondDay++;
                }
            }
        }

        return numberOfUncompletedTasksSecondDay;
    }

    /**
     * Finds the number of completed tasks for third day for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of completed tasks for third day
     */
    private int getNumberOfCompletedTasksThirdDay(ArrayList<Task> allTasks) {
        int numberOfCompletedTasksThirdDay = 0;

        LocalDate thirdDay = LocalDate.now().plusDays(2);

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(thirdDay) && task.isTaskCompleted()) {
                    numberOfCompletedTasksThirdDay++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(thirdDay) && task.isTaskCompleted()) {
                    numberOfCompletedTasksThirdDay++;
                }
            }
        }

        return numberOfCompletedTasksThirdDay;
    }

    /**
     * Finds the number of uncompleted tasks for third day for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of uncompleted tasks for third day
     */
    private int getNumberOfUncompletedTasksThirdDay(ArrayList<Task> allTasks) {
        int numberOfUncompletedTasksThirdDay = 0;

        LocalDate thirdDay = LocalDate.now().plusDays(2);

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(thirdDay) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksThirdDay++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(thirdDay) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksThirdDay++;
                }
            }
        }

        return numberOfUncompletedTasksThirdDay;
    }

    /**
     * Finds the number of completed tasks for fourth day for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of completed tasks for fourth day
     */
    private int getNumberOfCompletedTasksFourthDay(ArrayList<Task> allTasks) {
        int numberOfCompletedTasksFourthDay = 0;

        LocalDate fourthDay = LocalDate.now().plusDays(3);

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(fourthDay) && task.isTaskCompleted()) {
                    numberOfCompletedTasksFourthDay++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(fourthDay) && task.isTaskCompleted()) {
                    numberOfCompletedTasksFourthDay++;
                }
            }
        }

        return numberOfCompletedTasksFourthDay;
    }

    /**
     * Finds the number of uncompleted tasks for fourth day for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of uncompleted tasks for fourth day
     */
    private int getNumberOfUncompletedTasksFourthDay(ArrayList<Task> allTasks) {
        int numberOfUncompletedTasksFourthDay = 0;

        LocalDate fourthDay = LocalDate.now().plusDays(3);

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(fourthDay) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksFourthDay++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(fourthDay) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksFourthDay++;
                }
            }
        }

        return numberOfUncompletedTasksFourthDay;
    }

    /**
     * Finds the number of completed tasks for fifth day for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of completed tasks for fifth day
     */
    private int getNumberOfCompletedTasksFifthDay(ArrayList<Task> allTasks) {
        int numberOfCompletedTasksFifthDay = 0;

        LocalDate fifthDay = LocalDate.now().plusDays(4);

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(fifthDay) && task.isTaskCompleted()) {
                    numberOfCompletedTasksFifthDay++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(fifthDay) && task.isTaskCompleted()) {
                    numberOfCompletedTasksFifthDay++;
                }
            }
        }

        return numberOfCompletedTasksFifthDay;
    }

    /**
     * Finds the number of uncompleted tasks for fifth day for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of uncompleted tasks for fifth day
     */
    private int getNumberOfUncompletedTasksFifthDay(ArrayList<Task> allTasks) {
        int numberOfUncompletedTasksToday = 0;

        LocalDate fifthDay = LocalDate.now().plusDays(4);

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(fifthDay) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksToday++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(fifthDay) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksToday++;
                }
            }
        }

        return numberOfUncompletedTasksToday;
    }

    /**
     * Finds the number of completed tasks for sixth day for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of completed tasks for sixth day
     */
    private int getNumberOfCompletedTasksSixthDay(ArrayList<Task> allTasks) {
        int numberOfCompletedTasksSixthDay = 0;

        LocalDate sixthDay = LocalDate.now().plusDays(5);

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(sixthDay) && task.isTaskCompleted()) {
                    numberOfCompletedTasksSixthDay++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(sixthDay) && task.isTaskCompleted()) {
                    numberOfCompletedTasksSixthDay++;
                }
            }
        }

        return numberOfCompletedTasksSixthDay;
    }

    /**
     * Finds the number of uncompleted tasks for sixth day for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of uncompleted tasks for sixth day
     */
    private int getNumberOfUncompletedTasksSixthDay(ArrayList<Task> allTasks) {
        int numberOfUncompletedTasksSixthDay = 0;

        LocalDate sixthDay = LocalDate.now().plusDays(5);

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(sixthDay) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksSixthDay++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(sixthDay) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksSixthDay++;
                }
            }
        }

        return numberOfUncompletedTasksSixthDay;
    }

    /**
     * Finds the number of completed tasks for seventh day for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of completed tasks for seventh day
     */
    private int getNumberOfCompletedTasksSeventhDay(ArrayList<Task> allTasks) {
        int numberOfCompletedTasksSeventhDay = 0;

        LocalDate seventhDay = LocalDate.now().plusDays(6);

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(seventhDay) && task.isTaskCompleted()) {
                    numberOfCompletedTasksSeventhDay++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(seventhDay) && task.isTaskCompleted()) {
                    numberOfCompletedTasksSeventhDay++;
                }
            }
        }

        return numberOfCompletedTasksSeventhDay;
    }

    /**
     * Finds the number of uncompleted tasks for seventh day for dashboard view
     *
     * @param allTasks all the task stored in Storage
     * @return int number of uncompleted tasks for seventh day
     */
    private int getNumberOfUncompletedTasksSeventhDay(ArrayList<Task> allTasks) {
        int numberOfUncompletedTasksSeventhDay = 0;

        LocalDate seventhDay = LocalDate.now().plusDays(6);

        LocalDate taskDate;

        for (Task task : allTasks) {
            if (task.getDeadline() != null) {
                taskDate = task.getDeadline().toLocalDate();
                if (taskDate.equals(seventhDay) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksSeventhDay++;
                }
            }

            if (task.getEndDateTime() != null) {
                taskDate = task.getEndDateTime().toLocalDate();
                if (taskDate.equals(seventhDay) && !task.isTaskCompleted()) {
                    numberOfUncompletedTasksSeventhDay++;
                }
            }
        }

        return numberOfUncompletedTasksSeventhDay;
    }

    /**
     * checks whether the command parsed is valid
     *
     * @param command the command formed by the parser
     */
    private void checkCommandValidity(TareasCommand command) {
        switch (Parser.checkCommandValidity(command).getStatus()) {
            case SUCCESS:
                // no feedback, continue on since it's a valid command
                break;
            case UNKNOWN_COMMAND:
                guiController.sendErrorToView("Unrecognized command, please input a recognized command");
                return;
            case MISSING_PRIMARY_ARGUMENT:
                guiController.sendErrorToView("Please input something after the action - " +
                        command.getPrimaryKey());
                return;
            case UNEXPECTED_PRIMARY_ARGUMENT:
                guiController.sendErrorToView("Please input a valid input after the action - " +
                        command.getPrimaryKey());
                return;
            case UNKNOWN_KEYWORD:
                guiController.sendErrorToView("Please input a valid action - " + command.getPrimaryKey() +
                        " is not recognized");
                return;
            case SIGNATURE_NOT_MATCHED:
                guiController.sendErrorToView("Please input matching actions - refer to /help for reference");
                return;
            default:
                // do nothing - should not reach here ever, if it does it means bad stuff is happening
        }
    }

    /**
     * checks the type of the command and executes it
     *
     * @param command the command formed by the parser
     */
    private void checkCommandAndExecute(TareasCommand command) {
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
            case HELP_COMMAND:
                helpRequest();
                break;
            case PRIORITIZE_COMMAND:
                prioritizeTask(command);
                break;
            case REMIND_COMMAND:
                setTaskReminder(command);
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
    private void addTask(TareasCommand command) {
        Task taskToInsert = TaskManager.buildTask(command);

        tareas.insertTask(taskToInsert, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1 , 0);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        // TODO think about how to settle the view whenever an action is done v0.4
        guiController.sendSuccessToView("Task successfully added - "  + taskToInsert.getDescription());

        setPreviousActionType("Task with description " + taskToInsert.getDescription() + " removed");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task adding action " + now.toString());
    }

    /**
     * edits a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void editTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());
        Task taskToUpdate = new Task();

        taskToUpdate = updateTask(command, taskToUpdate);

        int tasksSize = taskManager.get().size();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        taskToUpdate.setTaskID(mappedTaskId);

        tareas.editTask(taskToUpdate, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, 0);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        // TODO think about how to settle the view whenever an action is done v0.4
        guiController.sendSuccessToView("Task successfully edited - " + taskToUpdate.getDescription());

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
    private void deleteTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        tareas.deleteTask(mappedTaskId, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1 , 0);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        // TODO think about how to settle the view whenever an action is done v0.4
        guiController.sendSuccessToView("Task successfully deleted - " + taskDescriptionForFeedback);

        setPreviousActionType("Task with description " + taskDescriptionForFeedback + " added back");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task deletion action at " + now.toString());
    }

    /**
     * searches a task by calling the appropriate GUI and storage methods
     *
     * @param command after being parsed from the parser
     */
    private void searchTask(TareasCommand command) {
        int taskId = Integer.parseInt(command.getPrimaryArgument());

        int tasksSize = taskManager.get().size();

        String taskDescriptionForFeedback = taskManager.get().get(tasksSize - taskId).getDescription();

        int mappedTaskId = taskManager.get().get(tasksSize - taskId).getTaskID();

        Task taskToShow = tareas.searchTask(mappedTaskId, 1);

        guiController.showDetailedView(taskToShow);
        // TODO think about how to settle the view whenever an action is done v0.4
        guiController.sendSuccessToView("Task successfully deleted - " + taskDescriptionForFeedback);

        LocalDateTime now = LocalDateTime.now();
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

        tareas.markTaskAsCompleted(mappedTaskId, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, 0);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        // TODO think about how to settle the view whenever an action is done v0.4
        guiController.sendSuccessToView("Successfully completed Task - " + taskDescriptionForFeedback);

        setPreviousActionType("Task with description " + taskDescriptionForFeedback + " no longer completed");

        LocalDateTime now = LocalDateTime.now();
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
        }

        if (command.getArgument("by") != null) {
            // TODO support for more natural-ish command for postponing from parser, if logic here gets too long, might
            // TODO want to abstract into a method v0.3, if cannot v0.4
        }

        tareas.postponeTask(taskToPostpone, 1);

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, 0);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        // TODO think about how to settle the view whenever an action is done v0.4
        guiController.sendSuccessToView("Task has been successfully postponed - " + taskDescriptionForFeedback);

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

        guiController.sendTaskstoView(tasksToShowToUser);
        // TODO think about how to settle the view whenever an action is done v0.4
        guiController.sendSuccessToView("View has successfully been changed to " + viewType);

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
                        viewType.equals("overdue") || viewType.equals("dashboard")) {

            if (viewType.equals("all")) {
                // tasksToShowToUser = tareas.getAllUndoneTasks(0, 0);
                // TODO use the correct method once Lareina supports it on the storage side v0.3
            }

            if (viewType.equals("deadline")) {
                // tasksToShowToUser = tareas.getAllUndoneTasks(0, 0);
                // TODO use the correct method once Lareina supports it on the storage side v0.3
            }

            if (viewType.equals("timed")) {
                // tasksToShowToUser = tareas.getAllUndoneTasks(0, 0);
                // TODO use the correct method once Lareina supports it on the storage side v0.3
            }

            if (viewType.equals("floating")) {
                // tasksToShowToUser = tareas.getAllUndoneTasks(0, 0);
                // TODO use the correct method once Lareina supports it on the storage side v0.3
            }

            if (viewType.equals("today")) {
                // tasksToShowToUser = tareas.getParticularDateTask(0, someDate);
                // TODO use the correct method once Lareina supports it on the storage side v0.3
            }

            if (viewType.equals("tomorrow")) {
                // tasksToShowToUser = tareas.getParticularDateTask(0, someDate);
                // TODO use the correct method once Lareina supports it on the storage side v0.3
            }

            if (viewType.equals("done")) {
                // tasksToShowToUser = tareas.getAllDoneTasks(0, 0);
                // TODO use the correct method once Lareina supports it on the storage side v0.3
            }

            if (viewType.equals("undone")) {
                // tasksToShowToUser = tareas.getAllUndoneTasks(0, 0);
                // TODO use the correct method once Lareina supports it on the storage side v0.3
            }

            if (viewType.equals("important")) {
                // tasksToShowToUser = tareas.getAllUndoneTasks(0, 0);
                // TODO use the correct method once Lareina supports it on the storage side v0.3
            }

            if (viewType.equals("overdue")) {
                // tasksToShowToUser = tareas.getAllUndoneTasks(0, 0);
                // TODO use the correct method once Lareina supports it on the storage side v0.3
            }

            if (viewType.equals("dashboard")) {
                // guiController.setViewToDashboard();
                // TODO use the correct method once Her Lung supports it on the GUI side  v0.3
            }

        } else {
            // if it's not then it's a particular date then we parse it into a date type
            LocalDateTime timeToPassToStorage = Parser.getDateTimeFromString(viewType);

            // tasksToShowToUser = tareas.getParticularDateTask(0, someDate);
            // TODO use the correct method once Lareina supports it on the storage side v0.3
        }

        return tasksToShowToUser;
    }

    /**
     * sets the view to the help view to give the user quick help tips
     */
    private void helpRequest() {
        // guiController.setViewToHelp();
        // TODO get Her Lung to have such a view v0.3 hopefully, if not then v0.4
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

        String prioritizedOrNot;

        if (taskToPrioritize.isTaskPriority()) {
            tareas.prioritizeTask(mappedTaskId, false, 1);
            prioritizedOrNot = "prioritized";
        } else {
            tareas.prioritizeTask(mappedTaskId, true, 1);
            prioritizedOrNot = "unprioritized";
        }

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, 0);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        // TODO think about how to settle the view whenever an action is done v0.4
        guiController.sendSuccessToView("Task has been successfully " + prioritizedOrNot + " - " + taskDescriptionForFeedback);

        setPreviousActionType("Task with description " + taskDescriptionForFeedback + " no longer prioritized");

        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "User has performed a task prioritizing action at " + now.toString());
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

        // tareas.setTaskReminder(mappedTaskId, reminderDateTime);
        // TODO once supported by Storage - Lareina v0.3

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, 0);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        // TODO think about how to settle the view whenever an action is done v0.4
        guiController.sendSuccessToView("Reminder Set for task - " + taskDescriptionForFeedback);

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

        ArrayList<Task> newTasks = tareas.getAllUndoneTasks(1, 0);

        taskManager.tasksChanged(newTasks);
        taskManager.clearRedoState();

        guiController.sendTaskstoView(newTasks);
        // TODO think about how to settle the view whenever an action is done v0.4
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
            // TODO think about how to settle the view whenever an action is done v0.4
            guiController.sendSuccessToView("Undo Successful - " + getPreviousActionType());
            // TODO allow for multiple redo and a proper feedback - now is like anyhow v0.4

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
            // TODO think about how to settle the view whenever an action is done v0.4
            guiController.sendSuccessToView("Redo Successful - " + getPreviousActionType());
            // TODO allow for multiple redo and a proper feedback - now is like anyhow v0.4

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
