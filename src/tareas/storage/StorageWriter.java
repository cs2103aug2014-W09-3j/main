package tareas.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
	
	public void write(ArrayList<Task> tasks) throws IOException {
		Gson gson = new Gson();
		String json = gson.toJson(tasks);
		
		FileWriter writer = new FileWriter("storage.json");
		writer.write(json);
		writer.close();
	}
	
    public void createFile () {
        File file = new File("storage.json");
        boolean fileCreated = false;
        try {
            fileCreated = file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error when creating file: " + e);
        }

        if (fileCreated) {
            System.out.println("Empty file is created successfully: " + file.getPath());
        } else {
            System.out.println("Failed to created empty file: " + file.getPath());
        }
    }

}
