package tareas.controller;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import tareas.common.*;
import tareas.gui.*;
import tareas.parser.*;
import tareas.storage.*;

/**
 * @author Yap Jun Hao
 *
 * This class contains the tests for the Tareas Controller.
 */

public class ControllerTests {
	
	TareasController tareasController = new TareasController();

    /**
     * Controller test for adding a floating task
     */
    @Test
    public void addAFloatingTask() throws IOException {
        tareasController.executeCommand("buy milk");

        //TODO add the right assertion
    }

    /**
     * Controller test for adding a floating tagged task
     */
    @Test
    public void addAFloatingTaggedTask() throws IOException {
        executeCommand("buy eggs -tag grocery");

        //TODO add the right assertion
    }

    /**
     * Controller test for adding a timed task
     */
    @Test
    public void addATimedTask() throws IOException {
        executeCommand("meeting with 2103 group -from 22/09/2014 1200 -to 22/09/2014 2200");

        //TODO add the right assertion
    }

    /**
     * Controller test for adding a timed task that stretches past a day
     */
    @Test
    public void addATimedTaskMultipleDays() throws IOException {
        executeCommand("family camping at sentosa -from 23/09/2014 0700 -to 25/09/2014 1200");

        //TODO add the right assertion
    }

    /**
     * Controller test for adding a deadline task
     */
    @Test
    public void addADeadlineTask() throws IOException {
        executeCommand("complete user guide -by 22/09/2014 1500");

        //TODO add the right assertion
    }

    /**
     * Controller test for adding a recurring deadline daily task
     */
    @Test
    public void addARecurringDeadlineDailyTask() throws IOException {
        executeCommand("walk the dog -recurring daily");

        //TODO add the right assertion
    }

    /**
     * Controller test for adding a deadline recurring weekly task
     */
    @Test
    public void addARecurringDeadlineWeeklyTask() throws IOException {
        executeCommand("spa session -recurring weekly on monday");

        //TODO add the right assertion
    }

    /**
     * Controller test for adding a deadline recurring monthly task
     */
    @Test
    public void addARecurringDeadlineMonthlyTask() throws IOException {
        executeCommand("family outing -recurring monthly on 13th");

        //TODO add the right assertion
    }

    /**
     * Controller test for editing a task
     */
    @Test
    public void editATask() throws IOException {
        executeCommand("buy milk");
        executeCommand("-edit 1 -des buy pineapple");

        //TODO add the right assertion
    }

    /**
     * Controller test for deleting a task
     */
    @Test
    public void deleteATask() throws IOException {
        executeCommand("buy milk");
        executeCommand("-delete 1");

        //TODO add the right assertion
    }

    /**
     * Controller test for searching a task through a string that the description contains
     */
    @Test
    public void searchATaskWithContainingString() throws IOException {
        executeCommand("buy watermelon");
        executeCommand("-search watermelon");

        //TODO add the right assertion
    }

    /**
     * Controller test for searching a task through the task ID
     */
    @Test
    public void searchATaskWithId() throws IOException {
        executeCommand("buy watermelon");
        executeCommand("-search 1");

        //TODO add the right assertion
    }

    /**
     * Controller test for searching a task through the tasks' tag(s)
     */
    @Test
    public void searchATaskThroughTags() throws IOException {
        executeCommand("buy watermelon -tag grocery");
        executeCommand("-search grocery");

        //TODO add the right assertion
    }

    /**
     * Controller test for marking a task as done
     */
    @Test
    public void markATaskAsDone() throws IOException {
        executeCommand("buy watermelon");
        executeCommand("-done 1");

        //TODO add the right assertion
    }

    /**
     * Controller test for undoing an action passing
     */
    @Test
    public void undoPass() throws IOException {
        executeCommand("buy milk");
        executeCommand("-undo");

        //TODO add the right assertion
    }

    /**
     * Controller test for undoing an action failure
     */
    @Test
    public void undoFail() throws IOException {
        executeCommand("-undo");

        //TODO add the right assertion
    }

    /**
     * Controller test for redoing an action passing
     */
    @Test
    public void redoPass() throws IOException {
        executeCommand("buy milk");
        executeCommand("-undo");
        executeCommand("-redo");

        //TODO add the right assertion
    }

    /**
     * Controller test for redoing an action failing
     */
    @Test
    public void redoFail() throws IOException {
        executeCommand("buy milk");
        executeCommand("-redo");

        //TODO add the right assertion
    }

    /**
     * Controller test for postponing a task by hours
     */
    @Test
    public void postponeATaskByHours() throws IOException {
        executeCommand("buy watermelon -by 22/09/2014 1500");
        executeCommand("-postpone 1 -by 2 hours");

        //TODO add the right assertion
    }

    /**
     * Controller test for postponing a task by specifying a whole new date
     */
    @Test
    public void postponeATaskWithANewDate() throws IOException {
        executeCommand("buy watermelon -by 22/09/2014 1500");
        executeCommand("-postpone 1 -to 23/09/2014 1700");

        //TODO add the right assertion
    }

    /**
     * Controller test for supporting custom done view
     */
    @Test
    public void viewAllDone() throws IOException {
        executeCommand("-view done");

        //TODO add the right assertion
    }

    /**
     * Controller test for supporting custom today's view
     */
    @Test
    public void viewToday() throws IOException {
        executeCommand("-view today");

        //TODO add the right assertion
    }

    /**
     * Controller test for supporting custom date view
     */
    @Test
    public void viewDate() throws IOException {
        executeCommand("-view 23/09/2014");

        //TODO add the right assertion
    }

    /**
     * Controller test for prioritizing tasks
     */
    @Test
    public void prioritizeATask() throws IOException {
        executeCommand("buy ham");
        executeCommand("-prioritize 1");

        //TODO add the right assertion
    }

    /**
     * Controller test for categorizing tasks
     */
    @Test
    public void categorizingATask() throws IOException {
        executeCommand("buy ham");
        executeCommand("-categorize 1 -to groceries");

        //TODO add the right assertion
    }

    /**
     * Controller test for setting reminders
     */
    @Test
    public void settingATaskWithReminder() throws IOException {
        executeCommand("buy ham");
        executeCommand("-remind 3 -on 13/09/2014 1300");

        //TODO add the right assertion
    }

    /**
     * Controller test for backing up
     */
    @Test
    public void editATask() throws IOException {
        executeCommand("-backup");

        //TODO add the right assertion
    }

    /**
     * Controller test for muting Tareas for days that one does not want to be disturbed or is sick etc.
     */
    @Test
    public void editATask() throws IOException {
        executeCommand("-mute 12/09/2014 -to 15/09/2014");

        //TODO add the right assertion
    }

    /**
     * Controller test for muting Tareas more natural command
     */
    @Test
    public void editATask() throws IOException {
        executeCommand("-mute now -to 15/09/2014");

        //TODO add the right assertion
    }

    /**
     * Controller test for changing font settings
     */
    @Test
    public void setFont() throws IOException {
        executeCommand("-font Helvetica");

        //TODO add the right assertion
    }

    /**
     * Controller test for changing font settings with size to cater to eyesight needs
     */
    @Test
    public void setFontWithSize() throws IOException {
        executeCommand("-font Times New Roman -size 12");

        //TODO add the right assertion
    }

    /**
     * Controller test for colorizing tasks
     */
    @Test
    public void colorizeATask() throws IOException {
        executeCommand("-color 3 -with orange");

        //TODO add the right assertion
    }

}
