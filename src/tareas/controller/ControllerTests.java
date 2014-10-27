package tareas.controller;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Yap Jun Hao
 *         <p/>
 *         This class contains the stub integration tests for Tareas
 */

public class ControllerTests {

    TareasController tareasController = new TareasController();

    /**
     * Controller Integration Testing for adding a floating task
     */
    @Test
    public void addAFloatingTask() throws IOException {
        tareasController.executeCommand("buy milk");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for adding a floating tagged task
     */
    @Test
    public void addAFloatingTaggedTask() throws IOException {
        tareasController.executeCommand("buy eggs /tag grocery");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for adding a timed task
     */
    @Test
    public void addATimedTask() throws IOException {
        tareasController.executeCommand("meeting with 2103 group /from 22-09014 12:00 /to 22-09-14 22:00");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for adding a timed task that stretches past a day
     */
    @Test
    public void addATimedTaskMultipleDays() throws IOException {
        tareasController.executeCommand("family camping at California /from 23-09-14 07:00 /to 25-09-14 12:00");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for adding a deadline task
     */
    @Test
    public void addADeadlineTask() throws IOException {
        tareasController.executeCommand("complete user guide /by 22-09-14 15:00");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for editing a task
     */
    @Test
    public void editATask() throws IOException {
        tareasController.executeCommand("buy milk");
        tareasController.executeCommand("/edit 1 /des buy pineapple");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for deleting a task
     */
    @Test
    public void deleteATask() throws IOException {
        tareasController.executeCommand("buy milk");
        tareasController.executeCommand("/delete 1");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for searching a task through a string that the description contains
     */
    @Test
    public void searchATaskWithContainingString() throws IOException {
        tareasController.executeCommand("buy watermelon");
        tareasController.executeCommand("/search watermelon");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for searching a task through the task ID
     */
    @Test
    public void searchATaskWithId() throws IOException {
        tareasController.executeCommand("buy watermelon");
        tareasController.executeCommand("/search 1");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for searching a task through the tasks' tag(s)
     */
    @Test
    public void searchATaskThroughTags() throws IOException {
        tareasController.executeCommand("buy watermelon -tag grocery");
        tareasController.executeCommand("/search grocery");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for marking a task as done
     */
    @Test
    public void markATaskAsDone() throws IOException {
        tareasController.executeCommand("buy watermelon");
        tareasController.executeCommand("/done 1");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for undoing an action passing
     */
    @Test
    public void undoPass() throws IOException {
        tareasController.executeCommand("buy milk");
        tareasController.executeCommand("/undo");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for undoing an action failure
     */
    @Test
    public void undoFail() throws IOException {
        tareasController.executeCommand("/undo");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for redoing an action passing
     */
    @Test
    public void redoPass() throws IOException {
        tareasController.executeCommand("buy milk");
        tareasController.executeCommand("/undo");
        tareasController.executeCommand("/redo");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for redoing an action failing
     */
    @Test
    public void redoFail() throws IOException {
        tareasController.executeCommand("buy milk");
        tareasController.executeCommand("/redo");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for postponing a task by hours
     */
    @Test
    public void postponeATaskByHours() throws IOException {
        tareasController.executeCommand("buy watermelon /by 22-09-14 15:00");
        tareasController.executeCommand("/postpone 1 /by 2 hours");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for postponing a task by specifying a whole new date
     */
    @Test
    public void postponeATaskWithANewDate() throws IOException {
        tareasController.executeCommand("buy watermelon /by 22-09-14 15:00");
        tareasController.executeCommand("/postpone 1 /to 23-09-14 17:00");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for supporting custom done view
     */
    @Test
    public void viewAllDone() throws IOException {
        tareasController.executeCommand("/view done");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for supporting custom undone view
     */
    @Test
    public void viewAllUndone() throws IOException {
        tareasController.executeCommand("/view undone");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for supporting custom today's view
     */
    @Test
    public void viewToday() throws IOException {
        tareasController.executeCommand("/view today");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for supporting custom date view
     */
    @Test
    public void viewDate() throws IOException {
        tareasController.executeCommand("/view 23-09-14");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for supporting agenda view
     */
    @Test
    public void viewAgenda() throws IOException {
        tareasController.executeCommand("/view agenda");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for supporting help view
     */
    @Test
    public void viewHelp() throws IOException {
        tareasController.executeCommand("/help");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for prioritizing tasks
     */
    @Test
    public void prioritizeATask() throws IOException {
        tareasController.executeCommand("buy ham");
        tareasController.executeCommand("/prioritize 1");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for setting reminders
     */
    @Test
    public void settingATaskWithReminder() throws IOException {
        tareasController.executeCommand("buy ham");
        tareasController.executeCommand("/remind 3 /on 13-09-2014 13:00");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for muting Tareas for days that one does not want to be disturbed or is sick etc.
     */
    @Test
    public void muteTareas() throws IOException {
        tareasController.executeCommand("/mute 12-09-2014 13:00 /to 15/09/2014 13:00");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for muting Tareas more natural command
     */
    @Test
    public void muteTareasNaturalCommand() throws IOException {
        tareasController.executeCommand("/mute now /to 15/09/2014 13:00");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for changing font settings
     */
    @Test
    public void setFont() throws IOException {
        tareasController.executeCommand("/font Helvetica");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for changing font settings with size to cater to eyesight needs
     */
    @Test
    public void setFontWithSize() throws IOException {
        tareasController.executeCommand("/font Times New Roman /size 12");

        assertEquals(true, false);
    }

    /**
     * Controller Integration Testing for colorizing tasks
     */
    @Test
    public void colorizeATask() throws IOException {
        tareasController.executeCommand("/color 3 /with orange");

        assertEquals(true, false);
    }

}
