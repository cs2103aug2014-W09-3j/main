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
        // Control Test - To show that cloning can't be done using single equals
        Tasks notClonedTasks = tasks;
        assertEquals(true, notClonedTasks == tasks);

        // Actual Test - To show that the clone constructor really works
        Tasks clonedTasks = new Tasks(tasks);
        assertEquals(false, clonedTasks == tasks);
    }

}
