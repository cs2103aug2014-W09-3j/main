package tareas.storage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import tareas.common.*;

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
		Tasks dummyTasks = generateDummyTasks();
		StorageWriter writer = new StorageWriter();
		writer.write(dummyTasks);
		
		StorageReader reader = new StorageReader();
		Tasks result = reader.read();
		assertEquals("task one", result.get().get(0).getDescription());
	}
	
	@Test
	public void testDeleteTask() throws IOException {
		Tasks dummyTasks = generateDummyTasks();
		dummyTasks.remove(1);
		assertEquals(1, dummyTasks.get().size());
	}
	
	private Tasks generateDummyTasks() {
		Task task1 = new Task();
		task1.setDescription("task one");
		Task task2 = new Task();
		task2.setDescription("task two");
		Tasks allTasks = new Tasks();
		allTasks.add(task1);
		allTasks.add(task2);
		return allTasks;
	}
	

}
