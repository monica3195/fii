package modul1.vsm.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import modul1.vsm.ConceptData;
import modul1.vsm.io.DirLoader;

public class TestDirLoader {
	@Test
	public void testItLoadsConcepts() {
		DirLoader l = new DirLoader();
		
		HashMap<Integer, ConceptData> c1 = l.load("test-input\\606");
		HashMap<Integer, ConceptData> c2 = l.load("test-input\\517218");
		
		assertEquals(c1.size(), 5);
		assertNotEquals(c2.size(), 5);
	}
	

	@Test (expected = IOException.class)
	public void throwsExceptionWhenFileNotFound() throws IOException {
		DirLoader l = new DirLoader();
		l.load("test-input\\606");
		
		File file = new File("test-input\\606\\0");
		
		l.loadFile(file);
	}
}
