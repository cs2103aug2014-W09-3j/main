package tareas.storage;

import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import tareas.common.*;

/**
 * @author Her Lung
 * 
 * IMPORTANT NOTE: Add gson-2.3.jar (found in our root directory) as an external JAR to your IDE.
 * 
 * This class writes information to the storage file. Tasks are stored in JSON notation.
 * The structure of the JSON file is such that Task objects will be wrapped by a Tasks class.
 */

public class StorageWriter {
	
	public void write(Tasks tasks) throws IOException {
		Gson gson = new Gson();
		String json = gson.toJson(tasks);
		
		FileWriter writer = new FileWriter("storage.json");
		writer.write(json);
		writer.close();
	}
	

}
