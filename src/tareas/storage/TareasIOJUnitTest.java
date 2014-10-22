package tareas.storage;

 import org.junit.Test;
 import tareas.common.Task;
 import tareas.common.Tasks;

 import java.io.File;
 import java.io.IOException;
 import java.util.ArrayList;
 import static org.junit.Assert.assertEquals;


public class TareasIOJUnitTest {
    StorageWriter writer = new StorageWriter();
    Tasks tasks = new Tasks();
    ArrayList<Task> newTasks;
    StorageReader reader = new StorageReader();
    TareasIO test = new TareasIO();



    @Test
    public void testTareasIO() throws IOException {
        //Testing multiple insertion.
        Task task3 = new Task();
        task3.setDescription("hello");
        test.insertTask(task3);

        //Multiple input case.
        Task task4 = new Task();
        task4.setDescription("world");
        test.insertTask(task4);

        Task task5 = new Task();
        task5.setDescription("great");
        test.insertTask(task5);

        Tasks result = reader.read();

        assertEquals("hello", result.get().get(0).getDescription());
        assertEquals("world", result.get().get(1).getDescription());
        assertEquals("great", result.get().get(2).getDescription());

        //Testing edit after add.
        Task task1 = new Task();
        task1.setTaskID(0);
        task1.setDescription("I am task one!");
        test.editTask(task1);

        result = reader.read();
        assertEquals("I am task one!", result.get().get(0).getDescription());

        //Testing search task after insertions and edited tasks.
        Task compare = test.searchTask(2);
        assertEquals("great", compare.getDescription());

        //Testing mark task as completed.
        test.markTaskAsCompleted(0);
        result = reader.read();
        assertEquals(true, result.get().get(0).isTaskCompleted());

        //Mark another task as completed.
        test.markTaskAsCompleted(2);
        result = reader.read();
        assertEquals(true, result.get().get(2).isTaskCompleted());

        //Testing delete.
        test.deleteTask(1);
        result = reader.read();
        assertEquals("great", result.get().get(1).getDescription());


    }

}
