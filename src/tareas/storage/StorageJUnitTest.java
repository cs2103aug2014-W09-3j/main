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
    private Tasks tasks = new Tasks();
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

        newTasks = tasks.get();
        task.setDescription("task one");
        newTasks.add(task);

        test.insertTask(task, 2);
        writer.write(tasks, "testing.json");

	    Tasks result = reader.read(2);
		assertEquals("task one", result.get().get(0).getDescription());
	}

//	@Test
//	public void testDeleteTask() throws IOException {
//        reader.overwrite();
//        newTasks = tasks.get();
//
//        Task task1 = new Task();
//      //  newTasks.add(task1);
//        task1.setDescription("task one");
//        test.insertTask(task1, 2);
//        writer.write(tasks, "testing.json");
//
//        Task task2 = new Task();
//    //    newTasks.add(task2);
//        task2.setDescription("task two");
//        test.insertTask(task2, 2);
//        writer.write(tasks, "testing.json");
//
//        test.deleteTask(1, 2);
//
//        writer.write(tasks, "testing.json");
//
//        Tasks result = reader.read(2);
//		assertEquals("task two", result.get().get(0).getDescription());
//	}
//
////    @Test
////    public void testMassDelete() throws IOException {
////        test.massDelete();
////        Tasks result = reader.read();
////
////        assertEquals(true, result.get().isEmpty());
////
////    }
//
//    @Test
//    public void testInsertTask() throws IOException {
//        Task task3 = new Task();
//        task3.setDescription("task three");
//        test.insertTask(task3, 2);
//
//        Tasks result = reader.read();
//
//        assertEquals("task three", result.get().get(0).getDescription());
//
//    }
//
//    @Test
//    public void testEditTask() throws IOException {
//        Task task3 = new Task();
//        task3.setTaskID(0);
//        task3.setDescription("I am task three");
//        test.editTask(task3, 2);
//
//        //Multiple inputs case.
//        Task task4 = new Task();
//        task4.setDescription("Task four");
//        test.insertTask(task4, 2);
//
//        Task task5 = new Task();
//        test.insertTask(task5, 2);
//
//        Tasks result = reader.read();
//        assertEquals("I am task three", result.get().get(0).getDescription());
//        assertEquals("Task four", result.get().get(1).getDescription());
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
