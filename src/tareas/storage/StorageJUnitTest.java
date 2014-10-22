package tareas.storage;

import org.junit.Test;
import tareas.common.Task;
import tareas.common.Tasks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StorageJUnitTest {
	StorageWriter writer = new StorageWriter();
    Tasks tasks = new Tasks();
    ArrayList<Task> newTasks;
    StorageReader reader = new StorageReader();
    TareasIO test = new TareasIO();


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

        Task task3 = new Task();
        task3.setDescription("task three");
        newTasks.add(task3);
        writer.write(tasks);

        Tasks result = reader.read();
        assertEquals("task one", result.get().get(0).getDescription());
        assertEquals("task two", result.get().get(1).getDescription());
        assertEquals("task three", result.get().get(2).getDescription());


    }


}
