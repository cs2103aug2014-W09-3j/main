package tareas.storage;

import org.junit.Test;
import tareas.common.Task;
import tareas.common.Tasks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StorageJUnitTest {
	private StorageWriter writer = new StorageWriter();
    private Tasks tasks = new Tasks();
    private ArrayList<Task> newTasks;
    private StorageReader reader = new StorageReader();
    private TareasIO test = new TareasIO();

    public static void main(String[] args) {
		// TODO Auto-generated method stub

    }


	//Testing createFile method in StorageWriter.
	@Test
	public void testCreateFile() {
		StorageWriter writer = new StorageWriter();
		writer.createFile();
		File f = new File("storage.json");
		assertEquals(true, f.exists());
	}

    //Testing write method in StorageWriter.
	@Test
	public void testWriteToFile() throws IOException {
        Task task = new Task();

        newTasks = tasks.get();
        task.setDescription("task one");
        newTasks.add(task);

        //Multiple input case.
        Task task2 = new Task();
        task2.setDescription("task two");
        newTasks.add(task2);

        writer.write(tasks);

        Tasks result = reader.read();
        assertEquals("task one", result.get().get(0).getDescription());
        assertEquals("task two", result.get().get(1).getDescription());


    }

  //  Testing deleteTask method in TareasIO.
	@Test
	public void testDeleteTask() throws IOException {
        newTasks = tasks.get();

        Task task3 = new Task();
        newTasks.add(task3);
        task3.setDescription("task three");
        writer.write(tasks);

        test.deleteTask(0);

        Tasks result = reader.read();
		assertEquals("task three", result.get().get(0).getDescription());
    }


    @Test
    public void testMassDelete() throws IOException {
        test.massDelete();
        writer.write(tasks);

        Tasks result = reader.read();

        assertEquals(true, result.get().isEmpty());

    }

    @Test
    public void testInsertTask() throws IOException {
        Task task3 = new Task();
        task3.setDescription("task three");
        test.insertTask(task3);

        //Multiple input case.
        Task task4 = new Task();
        task4.setDescription("task four");
        test.insertTask(task4);

        Tasks result = reader.read();

        assertEquals("task three", result.get().get(0).getDescription());
        assertEquals("task four", result.get().get(1).getDescription());

    }

    @Test
    public void testEditTask() throws IOException {
        Task task3 = new Task();
        task3.setTaskID(0);
        task3.setDescription("I am task three");
        test.editTask(task3);

        //Multiple inputs case.
        Task task4 = new Task();
        task4.setDescription("Task four");
        test.insertTask(task4);

        Task task5 = new Task();
        test.insertTask(task5);

        Tasks result = reader.read();
        assertEquals("I am task three", result.get().get(0).getDescription());
        assertEquals("Task four", result.get().get(1).getDescription());

    }

    @Test
    public void testMarkTaskCompleted() throws IOException {
//        Tasks result = reader.read();
//        Task task3 = result.get().get(0);

        test.markTaskAsCompleted(0);
//        task3.markTaskCompleted();

        Tasks result = reader.read();

        assertEquals(true, result.get().get(0).isTaskCompleted());
    }




}
