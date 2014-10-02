package tareas.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import tareas.common.Task;
import tareas.common.Tasks;

/**
 * @author Her Lung
 *
 * This class acts as the API for the Storage component.
 */

public class TareasIO {

	private Tasks allTasks = new Tasks();
	
	private void initialize() {
		StorageReader reader = new StorageReader();
		try {
			this.allTasks = reader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void write() {
		StorageWriter writer = new StorageWriter();
		try {
			writer.write(allTasks);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void removeTaskFromArray(int id, ArrayList<Task> temp) {
		Iterator<Task> iter = temp.iterator();
		while(iter.hasNext()) {
			Task task = iter.next();
			if(task.getTaskID() == id) {
				iter.remove();
			}
		}
	}
	
	public void insertTask(Task task) {
		initialize();
		allTasks.add(task);
		write();
	}

	public void deleteTask(int id) {
		initialize();
		if(id < 1 || id > allTasks.get().size()) {
			// TODO: Add exception for Invalid ID.
			System.out.println("Inavlid Task ID.");
		} else {
			ArrayList<Task> temp = allTasks.get();
			removeTaskFromArray(id, temp);
			allTasks.set(temp);
			write();
		}
	}

}
