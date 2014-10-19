package tareas.storage;

import tareas.controller.TaskManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

/**
 * @author Her Lung
 * 
 * IMPORTANT NOTE: Add gson-2.3.jar (found in our root directory) as an external JAR to your IDE.
 * 
 * This class reads information the storage file. Tasks are stored in JSON notation.
 * The structure of the JSON file is such that Task objects will be wrapped by a Tasks class.
 */

public class StorageReader {
	
	private TaskManager allTasks = TaskManager.getInstance();
	
	public TaskManager read() throws IOException {
		File file = new File("storage.json");
		if(file.exists()) {
			return convertJSONtoObject();
		} else {
			// The below method creates a new file and returns an empty ArrayList<Tasks>
			return createNewFile();
		}
	}

	private TaskManager createNewFile() {
		System.out.println("File not created.");
		StorageWriter writer = new StorageWriter();
		writer.createFile();
		return allTasks;
	}

	private TaskManager convertJSONtoObject() throws FileNotFoundException,
			IOException {
		Gson gson = new Gson();
		FileReader fr = new FileReader("storage.json");
		BufferedReader br = new BufferedReader(fr);
		String json = br.readLine();
		br.close();
		this.allTasks = gson.fromJson(json, TaskManager.class);
		return this.allTasks;
	}
}
