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

    //Testing insertTask method in TareasIO.
    @Test
    public void testInsertTask() throws IOException {
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

    }

//    @Test
//    public void testEditTask() throws IOException {
//        newTasks = tasks.get();
//        Task task6 = new Task();
//        task6.setDescription("morning");
//        test.insertTask(task6);
//
//
//    }

//    //Testing deleteTask method in TareasIO.
//	@Test
//	public void testDeleteTask() throws IOException {
//        newTasks = tasks.get();
//
//        Task task6 = new Task();
//        task6.setDescription("morning");
//        test.insertTask(task6);
//
//        test.deleteTask(2);
//
//        Tasks result = reader.read();
//		assertEquals("morning", result.get().get(2).getDescription());
//
//        //Testing adding after deletion.
//
//        Task task7 = new Task();
//        task7.setDescription("drink water");
//        test.insertTask(task7);
//
//        result = reader.read();
//        assertEquals("drink water", result.get().get(3).getDescription());
//
//    }



//    //Testing massDelete method in TareasIO.
//    @Test
//    public void testMassDelete() throws IOException {
//        test.massDelete();
//
//
//        Tasks result = reader.read();
//
//        assertEquals(true, result.get().isEmpty());
//
//    }



    //Testing editTask in TareasIO.
//    @Test
//    public void testEditTask() throws IOException {
//        newTasks = tasks.get();
//
////        Task task6 = new Task();
////        task6.setDescription("night");
////        task6.setTaskID(5);
////        test.insertTask(task6);
////
////
////        Task task1 = new Task();
////        task1.setTaskID(0);
////        task1.setDescription("I am task one!");
////        test.editTask(task1);
//
////        //Multiple inputs case.
////        Task task4 = new Task();
////        task4.setDescription("Task four");
////        test.insertTask(task4);
////
////        Task task5 = new Task();
////        test.insertTask(task5);
////
////        Tasks result = reader.read();
////        assertEquals("I am task three", result.get().get(0).getDescription());
////        assertEquals("Task four", result.get().get(1).getDescription());
//
//    }
//
//    //Testing getAllTask in TareasIO.
//    @Test
//    public void testGetAllTask() throws IOException {
//
//        Tasks result = reader.read();
//        assertEquals(false, result.equals(null));
//    }



}
