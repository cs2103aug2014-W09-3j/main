// This class is used to test methods for the Storage component.

//@author A0112151A
package tareas.storage;

import org.junit.Test;
import tareas.common.Task;
import tareas.common.Tasks;

import java.io.*;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class StorageJUnitTest {
	private StorageWriter writer = new StorageWriter();
    private ArrayList<Task> newTasks;
    private StorageReader reader = new StorageReader();
    private TareasIO test = new TareasIO();
    private String fileName = "testing.json";

    public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

    @Test
    public void testCreateFile() {
        StorageWriter writer = new StorageWriter();
        writer.createFile("testing.json");
        File f = new File("testing.json");
        assertEquals(true, f.exists());
    }

    @Test
    public void testWriteToFile() throws IOException {
        reader.overwrite();

        Task task = new Task();
        TareasIO test = new TareasIO();
        Tasks tasks = new Tasks();


        newTasks = tasks.get();
        task.setDescription("task one");
        newTasks.add(task);

        test.insertTask(task, 2);

        Task task1 = new Task();
        task1.setDescription("it is great");
        test.insertTask(task1, 2);

        Tasks result = reader.read(2);
        assertEquals("task one", result.get().get(0).getDescription());
    }

    @Test
    public void testDeleteTask() throws IOException {
        reader.overwrite();
        Tasks tasks = new Tasks();

        TareasIO test = new TareasIO();

        Task task3 = new Task();
        task3.setDescription("task three");
        test.insertTask(task3, 2);

        Task task4 = new Task();
        task4.setDescription("task four");
        test.insertTask(task4, 2);

        test.deleteTask(1, 2);

        Task task5 = new Task();
        task5.setDescription("task five");
        test.insertTask(task5, 2);

        Tasks result = reader.read(2);

        assertEquals("task five", result.get().get(1).getDescription());

    }
    @Test
    public void testInsertTask() throws IOException {
        reader.overwrite();
        TareasIO test = new TareasIO();


        Task task1 = new Task();
        task1.setDescription("task one");
        test.insertTask(task1, 2);

        Task task2 = new Task();
        task2.setDescription("task two");
        test.insertTask(task2, 2);

        Tasks result = reader.read(2);
        assertEquals("task one", result.get().get(0).getDescription());
        assertEquals("task two", result.get().get(1).getDescription());

    }




    @Test
    public void testEditTask() throws IOException {
        reader.overwrite();
        TareasIO test = new TareasIO();

        Task task3 = new Task();
        test.insertTask(task3, 2);
        task3.setDescription("New day");

        task3.setDescription("Start with Three");
        test.editTask(task3, 2);

        Tasks result = reader.read(2);
        assertEquals("Start with Three", result.get().get(0).getDescription());

    }


    @Test
    public void testSearchTask () throws IOException {
        reader.overwrite();

        Task task3 = new Task();
        task3.setDescription("hello");
        test.insertTask(task3, 2);

        //Multiple input case.
        Task task4 = new Task();
        task4.setDescription("world");
        test.insertTask(task4, 2);

        String word = "world";
        //ArrayList<Task> temp  = test.searchByDescription("world", 2);
        //System.out.println(temp.get(0).getDescription());
        //String tempDescription = temp.get(0).getDescription();

        //assertEquals(true, word.equals(tempDescription));

    }

    //Testing for markAsCompleted.
    @Test
    public void testMarkAsComplete() throws IOException {
        reader.overwrite();

        Task task3 = new Task();
        task3.setDescription("hello");
        test.insertTask(task3, 2);

        Task task4 = new Task();
        task4.setDescription("world");
        test.insertTask(task4, 2);

        test.markTaskAsCompleted(0, 2);
        Tasks result = reader.read(2);
        assertEquals(true, result.get().get(0).isTaskCompleted());

        //Mark another task as completed.
        test.markTaskAsCompleted(1, 2);
        result = reader.read(2);
        assertEquals(true, result.get().get(1).isTaskCompleted());


    }

    //Testing for getLatestID.
    @Test
    public void testGetLatestID() throws IOException {
        reader.overwrite();

        Task task3 = new Task();
        task3.setDescription("hello");
        test.insertTask(task3, 2);

        //Multiple input case.
        Task task4 = new Task();
        task4.setDescription("world");
        test.insertTask(task4, 2);

        Tasks result = reader.read(2);
        int latestID = result.getLatestID();
        assertEquals(2, latestID);

    }

    //Testing for getAllTasks.
    @Test
    public void testGetAllTasks() throws IOException {
        reader.overwrite();

        Task task3 = new Task();
        task3.setDescription("hello");
        test.insertTask(task3, 2);

        //Multiple input case.
        Task task4 = new Task();
        task4.setDescription("world");
        test.insertTask(task4, 2);

        ArrayList<Task> allTask = new ArrayList<>();
        allTask.add(task3);
        allTask.add(task4);

        Tasks result = reader.read(2);
        String allTask1 = allTask.get(0).getDescription();
        String result1 = result.get().get(0).getDescription();
        String allTask2 = allTask.get(1).getDescription();
        String result2 = result.get().get(1).getDescription();

        assertEquals(true, allTask1.equals(result1));
        assertEquals(true, allTask2.equals(result2));

    }
}
