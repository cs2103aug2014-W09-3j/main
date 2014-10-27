package tareas.storage;

import com.google.gson.Gson;
import tareas.common.Tasks;

import java.io.*;

/**
 * @author Lareina Ting
 * 
 * IMPORTANT NOTE: Add gson-2.3.jar (found in our root directory) as an external JAR to your IDE.
 * 
 * This class reads information the storage file. Tasks are stored in JSON notation.
 * The structure of the JSON file is such that Task objects will be wrapped by a Tasks class.
 */

public class StorageReader {

    private Tasks tasks = new Tasks();

	public Tasks read() throws IOException {
		File file = new File("storage.json");
		if (file.exists()) {
			return convertJSONtoObject();
		} else {
			// The below method creates a new file and returns an empty ArrayList<Tasks>
			return createNewFile();
		}
	}

    public void overwrite() {
        File file = new File("storage.json");
        file.delete();
       }

	private Tasks createNewFile() {
		System.out.println("File not created.");
		StorageWriter writer = new StorageWriter();
		writer.createFile("storage.json");
		return tasks;
	}

	private Tasks convertJSONtoObject() throws FileNotFoundException,
			IOException {
		Gson gson = new Gson();
		FileReader fr = new FileReader("storage.json");
		BufferedReader br = new BufferedReader(fr);
		String json = br.readLine();
		br.close();
		tasks = gson.fromJson(json, Tasks.class);
        return tasks;
	}
}
