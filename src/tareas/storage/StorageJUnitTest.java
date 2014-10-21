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

	
	@Test
	public void testCreateFile() {
		StorageWriter writer = new StorageWriter();
		writer.createFile();
		File f = new File("storage.json");
		assertEquals(true, f.exists());
	}
	
	@Test
	public void testWriteToFile() throws IOException {
        Task task = new Task();

        newTasks = tasks.get();
        task.setDescription("task one");
        newTasks.add(task);

        writer.write(tasks);

	    Tasks result = reader.read();
		assertEquals("task one", result.get().get(0).getDescription());
	}
	
	@Test
	public void testDeleteTask() throws IOException {
        newTasks = tasks.get();

        Task task2 = new Task();
        newTasks.add(task2);
        task2.setDescription("task two");
        writer.write(tasks);

//        TareasIO delete = new TareasIO();
        test.deleteTask(1);

        Tasks result = reader.read();
		assertEquals("task two", result.get().get(0).getDescription());
	}

    @Test
    public void testMassDelete() throws IOException {
        test.massDelete();
        Tasks result = reader.read();

        assertEquals(true, result.get().isEmpty());
        
    }

    @Test
    public void testInsertTask() throws IOException {
        Task task3 = new Task();
        task3.setDescription("task three");
        test.insertTask(task3);

        Tasks result = reader.read();

        assertEquals("task three", result.get().get(0).getDescription());

    }


	
	private ArrayList<Task> generateDummyTasks() {
		Task task1 = new Task();
		task1.setDescription("task one");
		Task task2 = new Task();
		task2.setDescription("task two");
		ArrayList<Task> allTasks = new ArrayList<Task>();
		allTasks.add(task1);
		allTasks.add(task2);
		return allTasks;
	}
	

}
