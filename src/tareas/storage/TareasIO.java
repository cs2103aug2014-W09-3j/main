package tareas.storage;

import java.io.File;
import java.io.IOException;

public class TareasIO {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public void createFile (String fileName) {
        File file = new File("storage.txt");
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
