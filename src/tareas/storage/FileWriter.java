package tareas.storage;

import java.util.ArrayList;

import com.google.gson.Gson;

import tareas.common.*;

/**
 * @author Her Lung
 * 
 * IMPORTANT NOTE: Add gson-2.3.jar (found in our root directory) as an external JAR to your IDE.
 * 
 * This class writes information to the storage file. Tasks are stored in JSON notation.
 */

public class FileWriter {

	public static void main(String[] args) {
		Task newTask = new Task();
		newTask.setTaskID(1);
		newTask.setDescription("Get eggs for Mum.");
		newTask.setCategory("Singapore");
		
		Tasks list = new Tasks();
		list.add(newTask);
		list.add(newTask);
		
		Gson gson = new Gson();
		String json = gson.toJson(list);
		System.out.println(json);
		
		Tasks obj = gson.fromJson(json, Tasks.class);
		System.out.println(obj.get().get(0).getDescription());
		
	}

}
