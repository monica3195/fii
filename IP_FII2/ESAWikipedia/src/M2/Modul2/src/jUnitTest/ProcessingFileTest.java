package jUnitTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import conceptIdentification.Concepts;
import conceptIdentification.ProcessingFile;

public class ProcessingFileTest {

	@Test
	public void testProcessingFile1() throws Exception {
		ProcessingFile f = new ProcessingFile();
		Concepts c = new Concepts();
		f.processingFile("test\\606\\1", c.getConcepts());
		assertEquals(6, f.getTotalConcepts());

	}

	@Test
	public void testProcessingFile2() throws Exception {
		ProcessingFile f = new ProcessingFile();
		Concepts c = new Concepts();
		f.processingFile("test\\606\\1", c.getConcepts());
		assertEquals(f.getListConcepts().size(), f.getTotalConcepts());
	}

	@Test
	public void testProcessingFile3() throws Exception {
		ProcessingFile f = new ProcessingFile();
		Concepts c = new Concepts();
		f.processingFile("test\\606\\1", c.getConcepts());
		assertTrue(f.getListWord() != null);
	}

	@Test
	public void readTextFile() throws IOException {
		ProcessingFile processing = new ProcessingFile();
		String textFile = processing.getTextFile("test\\606\\30");
		String[] list;
		list = textFile.split(" ");
		assertEquals(18, list.length);

	}

	@Test
	public void readTextFile1() throws IOException {
		ProcessingFile processing = new ProcessingFile();
		String textFile = processing.getTextFile("test\\606\\10");
		String[] list;
		list = textFile.split(" ");
		assertEquals(18, list.length);
	}

	@Test(expected = IOException.class)
	public void throwsExceptionWhenFileNotFound() throws IOException {
		ProcessingFile processing = new ProcessingFile();
		processing.getTextFile("test\\606");
	}

}
