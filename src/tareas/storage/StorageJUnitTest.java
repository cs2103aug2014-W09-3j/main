package tareas.storage;

import org.junit.Test;
import tareas.common.Task;
import tareas.common.Tasks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StorageJUnitTest {
	
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
		ArrayList<Task> dummyTasks = generateDummyTasks();
		StorageWriter writer = new StorageWriter();
		//writer.write(dummyTasks);
		
		StorageReader reader = new StorageReader();
	    Tasks result = reader.read();
		assertEquals("task one", result.get().get(0).getDescription());
	}
	
	@Test
	public void testDeleteTask() throws IOException {
		ArrayList<Task> dummyTasks = generateDummyTasks();
		dummyTasks.remove(1);
		assertEquals(1, dummyTasks.size());
	}

    @Test
    public void testMassDelete() throws IOException {
        ArrayList<Task> dummyTasks = generateDummyTasks();
        dummyTasks.removeAll(dummyTasks);
        assertEquals(0, dummyTasks.size());
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
