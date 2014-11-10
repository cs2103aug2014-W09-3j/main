package tareas.storage;

import com.google.gson.Gson;
import tareas.common.Tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Her Lung
 * 
 * IMPORTANT NOTE: Add gson-2.3.jar (found in our root directory) as an external JAR to your IDE.
 * 
 * This class writes information to the storage file. Tasks are stored in JSON notation.
 * The structure of the JSON file is such that Task objects will be wrapped by a Tasks class.
 */

public class StorageWriter {

    /**
     * This method writes all the tasks to the file.
     * @param tasks
     * @param fileName
     * @throws IOException
     */
	public void write(Tasks tasks, String fileName) throws IOException  {
		Gson gson = new Gson();
		String json = gson.toJson(tasks);
		
		FileWriter writer = new FileWriter(fileName);

        writer.write(json);
		writer.close();
	}

    /**
     * This method creates a new file for the storage.
     * @param fileName
     */
    public void createFile (String fileName) {
        File file = new File(fileName);
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
