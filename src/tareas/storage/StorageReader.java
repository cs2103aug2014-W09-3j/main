package tareas.storage;

import com.google.gson.Gson;
import tareas.common.Tasks;

import java.io.*;

/**
 *
 * 
 * IMPORTANT NOTE: Add gson-2.3.jar (found in our root directory) as an external JAR to your IDE.
 * 
 * This class reads information the storage file. Tasks are stored in JSON notation.
 * The structure of the JSON file is such that Task objects will be wrapped by a Tasks class.
 */
//@author A0112151A

public class StorageReader {

    private Tasks tasks = new Tasks();

	public Tasks read(int runType) throws IOException {
        File file ;

        if(runType == 1) {
            file = new File("storage.json");
            if (file.exists()) {
                return convertJSONtoObject("storage.json");
            } else {
                // The below method creates a new file and returns an empty ArrayList<Tasks>
                return createNewFile("storage.json");
            }
        }
        else if (runType == 2) {
            file = new File("testing.json");
            if (file.exists()) {
                return convertJSONtoObject("testing.json");
            } else {
                // The below method creates a new file and returns an empty ArrayList<Tasks>
                return createNewFile("testing.json");
            }
        }

        return createNewFile("storage.json");
    }

    public void overwrite() {
        File file;
        file = new File("testing.json");

        System.gc();
        boolean deleted = file.delete();

        //Testing purposes.
        try {
            if (!file.exists())
                System.out.println("It doesn't exist in the first place.");
            else if (file.isDirectory() && file.list().length > 0)
                System.out.println("It's a directory and it's not empty.");
            else
                System.out.println("Somebody else has it open.");
        } catch (SecurityException e) {
            System.out.println("No filesystem access.");
        }

        if(deleted){
            System.out.println("Testing.json is deleted");
        }
        else{
            System.out.println("Testing.json is not deleted");
        }
        createNewFile("testing.json");
       }

	private Tasks createNewFile(String fileName) {
		System.out.println("File not created.");
		StorageWriter writer = new StorageWriter();
		writer.createFile(fileName);

        Tasks tasks = new Tasks();

        try{
            writer.write(tasks, fileName);
        }
        catch (IOException e){
            e.printStackTrace();
        }
		return tasks;
	}

	private Tasks convertJSONtoObject(String fileName) throws FileNotFoundException,
			IOException {
		Gson gson = new Gson();
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
        File file = new File(fileName);

        if(!file.exists()){
            throw new FileNotFoundException("File Mising!");
        }

		String json = br.readLine();
		br.close();
        fr.close();
		tasks = gson.fromJson(json, Tasks.class);
        return tasks;
	}
}
