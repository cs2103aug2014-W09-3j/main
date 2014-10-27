/**
 * @author Lareina Ting
 *
 * This class is used to test methods for the storageWriter.
 */

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
   // private Tasks tasks = new Tasks();
    private ArrayList<Task> newTasks;
    private StorageReader reader = new StorageReader();
   // private TareasIO test = new TareasIO();
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

//    @Test
//    public void testInsertTask() throws IOException {
//        reader.overwrite();
//        Tasks tasks = new Tasks();
//
//        TareasIO test = new TareasIO();
//
//        Task task3 = new Task();
//        task3.setDescription("task three");
//        test.insertTask(task3, 2);
//
//        Tasks result = reader.read(2);
//
//        assertEquals("task three", result.get().get(0).getDescription());
//
//    }
//	@Test
//	public void testDeleteTask() throws IOException {
//        reader.overwrite();
//        TareasIO test = new TareasIO();
//
//
//        Task task1 = new Task();
//        task1.setDescription("task one");
//        test.insertTask(task1, 2);
//
//        Task task2 = new Task();
//        task2.setDescription("task two");
//        test.insertTask(task2, 2);
//
//        test.deleteTask(0, 2);
//
//        Tasks result = reader.read(2);
//		assertEquals("task two", result.get().get(0).getDescription());
//	}
//

//
//
//    @Test
//    public void testEditTask() throws IOException {
//        reader.overwrite();
//        TareasIO test = new TareasIO();
//
//        Task task3 = new Task();
//        task3.setDescription("Start with Three");
//        test.editTask(task3, 2);
//
//        //Multiple inputs case.
//        Task task4 = new Task();
//        task4.setDescription("And Four");
//        test.insertTask(task4, 2);
//
//
//        Tasks result = reader.read(2);
//        assertEquals("Start with Three", result.get().get(0).getDescription());
//        assertEquals("And Four", result.get().get(1).getDescription());
//
//    }


//    @Test
//    public void testSearchTask() throws IOException {
//        Tasks result = reader.read();
//
//        Task task4 = result.get().get(1);
//
//        Task taskTesting = test.searchTask(2);
//
//
//      //  assertEquals(false, task4.equals(taskTesting));
//        assertEquals("Task four", test.searchTask(1).getDescription());
//
//
//    }




}
