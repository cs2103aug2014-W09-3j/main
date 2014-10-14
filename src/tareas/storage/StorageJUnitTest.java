package tareas.storage;

import tareas.controller.TaskManager;
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
		TaskManager dummyTasks = generateDummyTasks();
		StorageWriter writer = new StorageWriter();
		writer.write(dummyTasks);
		
		StorageReader reader = new StorageReader();
		TaskManager result = reader.read();
		assertEquals("task one", result.get().get(0).getDescription());
	}
	
	@Test
	public void testDeleteTask() throws IOException {
		TaskManager dummyTasks = generateDummyTasks();
		dummyTasks.remove(1);
		assertEquals(1, dummyTasks.get().size());
	}
	
	private TaskManager generateDummyTasks() {
		Task task1 = new Task();
		task1.setDescription("task one");
		Task task2 = new Task();
		task2.setDescription("task two");
		TaskManager allTasks = new TaskManager();
		allTasks.add(task1);
		allTasks.add(task2);
		return allTasks;
	}
	

}
