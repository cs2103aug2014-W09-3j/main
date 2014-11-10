//@author A0113694A

package tareas.controller;

import tareas.common.Task;
import tareas.parser.Parser;
import tareas.storage.TareasIO;

import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author Yap Jun Hao
 *         <p/>
 *         This class contains the stub integration tests for Tareas
 */
public class ControllerTests {
    TareasController tareasController = new TareasController();

    TareasIO tareas = new TareasIO();

    /**
     * Controller Integration Testing for adding a floating task
     */
    @Test
    public void addAFloatingTask() throws IOException {
        tareas.massDelete(1);

        tareasController.executeCommand("buy milk", true);

        Task testTask = new Task();
        testTask.setDescription("buy milk");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();
        Task actualGeneratedTask = allTasks.get(0);

        assertEquals(testTask.getDescription(), actualGeneratedTask.getDescription());

        tareas.massDelete(1);
    }

    /**
     * Controller Integration Testing for adding a floating tagged task
     */
    @Test
    public void addAFloatingTaggedTask() throws IOException {
        tareas.massDelete(1);

        tareasController.executeCommand("buy milk /tag yolo", true);

        Task testTask = new Task();
        testTask.setDescription("buy milk");
        testTask.addTag("yolo");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();
        Task actualGeneratedTask = allTasks.get(0);

        assertEquals(testTask.getDescription(), actualGeneratedTask.getDescription());
        assertEquals(testTask.getTags().get(0), actualGeneratedTask.getTags().get(0));

        tareas.massDelete(1);
    }

    /**
     * Controller Integration Testing for adding a timed task
     */
    @Test
    public void addATimedTask() throws IOException {
        tareas.massDelete(1);

        tareasController.executeCommand("meeting with 2103 group /from 22-09-14 12:00 /to 22-09-14 22:00", true);

        LocalDateTime startDateTime = Parser.getDateTimeFromString("22-09-14 12:00");
        LocalDateTime endDateTime = Parser.getDateTimeFromString("22-09-14 22:00");

        Task testTask = new Task();
        testTask.setDescription("meeting with 2103 group");
        testTask.setStartDateTime(startDateTime);
        testTask.setEndDateTime(endDateTime);

        ArrayList<Task> allTasks = tareas.getTasks(1).get();
        Task actualGeneratedTask = allTasks.get(0);


        assertEquals(testTask.getDescription(), actualGeneratedTask.getDescription());
        assertEquals(testTask.getStartDateTime(), actualGeneratedTask.getStartDateTime());
        assertEquals(testTask.getEndDateTime(), actualGeneratedTask.getEndDateTime());

        tareas.massDelete(1);
    }

    /**
     * Controller Integration Testing for adding a timed task that stretches past a day
     */
    @Test
    public void addATimedTaskMultipleDays() throws IOException {
        tareas.massDelete(1);

        tareasController.executeCommand("family camping at California /from 23-09-14 07:00 /to 25-09-14 12:00", true);

        LocalDateTime startDateTime = Parser.getDateTimeFromString("23-09-14 07:00");
        LocalDateTime endDateTime = Parser.getDateTimeFromString("25-09-14 12:00");

        Task testTask = new Task();
        testTask.setDescription("family camping at California");
        testTask.setStartDateTime(startDateTime);
        testTask.setEndDateTime(endDateTime);

        ArrayList<Task> allTasks = tareas.getTasks(1).get();
        Task actualGeneratedTask = allTasks.get(0);

        assertEquals(testTask.getDescription(), actualGeneratedTask.getDescription());
        assertEquals(testTask.getStartDateTime(), actualGeneratedTask.getStartDateTime());
        assertEquals(testTask.getEndDateTime(), actualGeneratedTask.getEndDateTime());

        tareas.massDelete(1);
    }

    /**
     * Controller Integration Testing for adding a deadline task
     */
    @Test
    public void addADeadlineTask() throws IOException {
        tareas.massDelete(1);

        tareasController.executeCommand("complete user guide /by 22-09-14 15:00", true);

        LocalDateTime deadline = Parser.getDateTimeFromString("22-09-14 15:00");

        Task testTask = new Task();
        testTask.setDescription("complete user guide");
        testTask.setDeadline(deadline);

        ArrayList<Task> allTasks = tareas.getTasks(1).get();
        Task actualGeneratedTask = allTasks.get(0);

        assertEquals(testTask.getDescription(), actualGeneratedTask.getDescription());
        assertEquals(testTask.getDeadline(), actualGeneratedTask.getDeadline());

        tareas.massDelete(1);
    }

    /**
     * Controller Integration Testing for editing a task description
     */
    @Test
    public void editATaskDescription() throws IOException {
        tareas.massDelete(1);

        tareasController.executeCommand("buy milk", true);
        tareasController.executeCommand("/edit 1 /des buy pineapple", true);

        Task testTask = new Task();
        testTask.setDescription("buy pineapple");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();
        Task actualEditedTask = allTasks.get(0);

        assertEquals(testTask.getDescription(), actualEditedTask.getDescription());

        tareas.massDelete(1);
    }

    /**
     * Controller Integration Testing for editing a task deadline
     */
    @Test
    public void editATaskDeadline() throws IOException {
        tareas.massDelete(1);

        tareasController.executeCommand("buy milk /by 1-11-14 13:00", true);
        tareasController.executeCommand("/edit 1 /deadline 1-11-14 15:00", true);

        LocalDateTime editedDate = Parser.getDateTimeFromString("1-11-14 15:00");

        Task testTask = new Task();
        testTask.setDescription("buy milk");
        testTask.setDeadline(editedDate);

        ArrayList<Task> allTasks = tareas.getTasks(1).get();
        Task actualEditedTask = allTasks.get(0);

        assertEquals(testTask.getDescription(), actualEditedTask.getDescription());
        assertEquals(testTask.getDeadline(), actualEditedTask.getDeadline());

        tareas.massDelete(1);
    }

    /**
     * Controller Integration Testing for deleting a task
     */
    @Test
    public void deleteATask() throws IOException {
        tareas.massDelete(1);

        tareasController.executeCommand("buy milk", true);
        tareasController.executeCommand("/delete 1", true);

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        assertEquals(new ArrayList<Task>(), allTasks);

        tareas.massDelete(1);
    }


    /**
     * Controller Integration Testing for marking a task as done
     */
    @Test
    public void markATaskAsDone() throws IOException {
        tareas.massDelete(1);

        tareasController.executeCommand("buy watermelon", true);
        tareasController.executeCommand("/done 1", true);

        ArrayList<Task> allTasks = tareas.getTasks(1).get();

        assertEquals(true, allTasks.get(0).isTaskCompleted());

        tareas.massDelete(1);
    }

    /**
     * Controller Integration Testing for postponing a task by specifying a whole new date
     */
    @Test
    public void postponeATaskWithANewDate() throws IOException {
        tareas.massDelete(1);

        tareasController.executeCommand("buy watermelon /by 22-09-14 15:00", true);
        tareasController.executeCommand("/postpone 1 /to 23-09-14 17:00", true);

        LocalDateTime newDateTime = Parser.getDateTimeFromString("23-09-14 17:00");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();
        Task actualEditedTask = allTasks.get(0);

        assertEquals(newDateTime, actualEditedTask.getDeadline());

        tareas.massDelete(1);
    }

    /**
     * Controller Integration Testing for prioritizing tasks
     */
    @Test
    public void prioritizeATask() throws IOException {
        tareas.massDelete(1);

        tareasController.executeCommand("buy ham", true);
        tareasController.executeCommand("/prioritize 1", true);

        ArrayList<Task> allTasks = tareas.getTasks(1).get();
        Task actualPrioritizedTask = allTasks.get(0);

        assertEquals(true, actualPrioritizedTask.isTaskPriority());

        tareas.massDelete(1);
    }

    /**
     * Controller Integration Testing for setting reminders
     */
    @Test
    public void settingATaskWithReminder() throws IOException {
        tareas.massDelete(1);

        tareasController.executeCommand("buy ham", true);
        tareasController.executeCommand("/remind 1 /on 13-09-14 13:00", true);

        LocalDateTime reminderDateTime = Parser.getDateTimeFromString("13-09-14 13:00");

        ArrayList<Task> allTasks = tareas.getTasks(1).get();
        Task actualTaskWithReminder = allTasks.get(0);

        assertEquals(reminderDateTime, actualTaskWithReminder.getReminderDateTime());

        tareas.massDelete(1);
    }

}
