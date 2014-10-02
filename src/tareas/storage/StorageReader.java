package tareas.storage;

import java.io.BufferedReader;
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
		Gson gson = new Gson();
		FileReader fr = new FileReader("storage.json");
		BufferedReader br = new BufferedReader(fr);
		String json = br.readLine();
		br.close();
		this.allTasks = gson.fromJson(json, Tasks.class);
		return this.allTasks;
	}
}
