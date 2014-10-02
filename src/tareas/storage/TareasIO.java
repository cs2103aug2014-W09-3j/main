package tareas.storage;

import java.io.IOException;

import tareas.common.Task;
import tareas.common.Tasks;

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
	
	public void insertTask(Task task) {
		initialize();
		StorageWriter writer = new StorageWriter();
		allTasks.add(task);
		try {
			writer.write(allTasks);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
