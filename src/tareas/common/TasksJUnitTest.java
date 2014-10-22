package tareas.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Her Lung on 22/10/2014.
 */
public class TasksJUnitTest {
    Tasks tasks = new Tasks();

    public TasksJUnitTest() {
    }

    @Test
    // This is the test for a constructor that is meant to clone an object belonging
    // to the Tasks class. We need to clone the Tasks object for the purpose of undo-ing
    // and redo-ing where we save states.
    public void cloneTasksTest() {
        // Checks that both clonedTasks and tasks are not referencing
        // to the same object.
        Tasks clonedTasks = new Tasks(tasks);
        assertEquals(false, clonedTasks.get() == tasks.get() &&
            clonedTasks.getClass().equals(tasks.getClass()));

        // Ensure that cloned tasks also carry the same ID
        tasks.setID(5);
        Tasks clonedTasksWithID = new Tasks(tasks);
        assertEquals(5, clonedTasksWithID.getLatestID());
    }

    @Test
    // This is a test case to show that the incrementID method of Tasks is working as
    // expected. This test also somewhat showcases the boundary value analysis heuristics.
    public void incrementIDTest() {
        tasks.setID(0);
        tasks.incrementID();
        assertEquals(false, tasks.getLatestID() == 0);
        assertEquals(true, tasks.getLatestID() == 1);
        assertEquals(false, tasks.getLatestID() == 2);
    }

}
