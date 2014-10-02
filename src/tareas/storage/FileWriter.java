package tareas.storage;

import java.util.ArrayList;
import com.google.gson.Gson;
import tareas.common.*;

public class FileWriter {

	public static void main(String[] args) {
		Task newTask = new Task();
		newTask.setTaskID(1);
		newTask.setDescription("Get eggs for Mum.");
		newTask.setCategory("Singapore");
		
		ArrayList<Task> test = new ArrayList<Task>();
		test.add(newTask);
		test.add(newTask);
		Gson gson = new Gson();
		String json = gson.toJson(test);
		System.out.println(json);
	}

}
