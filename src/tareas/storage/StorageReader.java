package tareas.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

import tareas.common.Tasks;

/**
 * @author Her Lung
 * 
 * IMPORTANT NOTE: Add gson-2.3.jar (found in our root directory) as an external JAR to your IDE.
 * 
 * This class reads information the storage file. Tasks are stored in JSON notation.
 * The structure of the JSON file is such that Task objects will be wrapped by a Tasks class.
 */

public class StorageReader {
	
	private Tasks allTasks = new Tasks();
	
	public Tasks read() throws IOException {
		File file = new File("storage.json");
		if(file.exists()) {
			return convertJSONtoObject();
		} else {
			// The below method creates a new file and returns an empty ArrayList<Tasks>
			return createNewFile();
		}
	}

	private Tasks createNewFile() {
		System.out.println("File not created.");
		StorageWriter writer = new StorageWriter();
		writer.createFile();
		return allTasks;
	}

	private Tasks convertJSONtoObject() throws FileNotFoundException,
			IOException {
		Gson gson = new Gson();
		FileReader fr = new FileReader("storage.json");
		BufferedReader br = new BufferedReader(fr);
		String json = br.readLine();
		br.close();
		this.allTasks = gson.fromJson(json, Tasks.class);
		return this.allTasks;
	}
}