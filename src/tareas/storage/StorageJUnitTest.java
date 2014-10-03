package tareas.storage;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import tareas.common.*;

public class StorageJUnitTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@Test
	public void testCreateFile() {
		StorageWriter writer = new StorageWriter();
		writer.createFile();
		File f = new File("storage.json");
		assertEquals(true, f.exists());
	}
	
	

}
