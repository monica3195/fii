package jUnitTest;

import static org.junit.Assert.assertEquals;

import java.io.File;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import org.junit.Test;

import conceptIdentification.ProcessingWikipedia;

public class ProcessingWikipediaTest {
	private JProgressBar bar;  
	private JTextArea information;
	@Test
	public void testReadFile() {
		bar=new JProgressBar();
		information= new JTextArea();
		ProcessingWikipedia processing =new ProcessingWikipedia(bar,information);
		processing.processingFileWikipedia("DUML\\DumpWikiRO");
		assertEquals(800,processing.getTotalFileProcessed());
		
	}
	@Test
	public void testCreateFileConcepts() {
		int totalFile=0;
		File f = new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "DumpConcepts");
	if (f.isDirectory()) 
	{
		File[] dirs = f.listFiles();
		for (File dir : dirs) {
		File[] fisiere = dir.listFiles();
		totalFile+=fisiere.length;
		}
	}
	assertEquals(800,totalFile);
	}
	@Test
	public void testCreateFileFisier() {
		int totalFile=0;
		File f = new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "DumpFisier");
	if (f.isDirectory()) 
	{
		File[] dirs = f.listFiles();
		for (File dir : dirs) {
		File[] fisiere = dir.listFiles();
		totalFile+=fisiere.length;
		}
	}
	assertEquals(800,totalFile);
	}
}
