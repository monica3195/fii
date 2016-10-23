package conceptIdentification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class ProcessingWikipedia {
	private HashMap<String, String> allWikipediaConcepts;
	private HashMap<String, Integer> conceptsFound;
	private ArrayList<String> listWordLeft;
	private ProcessingFile processingFile;
	private int totalFileProcessed = 0;
	private Writer bw;
	private JProgressBar pbar;
	private JTextArea information;

	public ProcessingWikipedia(JProgressBar pbar, JTextArea information) {
		this.pbar = pbar;
		this.information = information;
	};

	public void processingFileWikipedia(String nameDirector) {
		information.setText("                      Getting concepts");

		allWikipediaConcepts = new Concepts().getConcepts();

		File f = new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + nameDirector);

		new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "DumpConcepts").mkdir();
		
		new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "DumpFisier").mkdir();
	
		
		pbar.setValue(0);

		if (f.isDirectory()) {

			File[] dirs = f.listFiles();

			for (File dir : dirs) {// for each directory

				if (dir.isDirectory()) {

					new File(System.getProperty("user.dir")
							+ System.getProperty("file.separator") + "DumpConcepts/"
							+ dir.getName()).mkdir();

					new File(System.getProperty("user.dir")
							+ System.getProperty("file.separator") + "DumpFisier/"
							+ dir.getName()).mkdir();

					File[] fisiere = dir.listFiles();

					for (File fisier : fisiere) {// for each file
						totalFileProcessed++;
						
						information.setText("       Processed File:"
								+ totalFileProcessed + "/" + dirs.length * 100);
						
						pbar.setValue(totalFileProcessed / dirs.length);
						
						createFile(dir.getName(), fisier);
					}
				}
			}
			information.setText("                          Finnish");
		} else if (f.isFile()) {
			System.out.println("Eroare structura fisiere");
		} else {
			System.out.println("Calea introdusa este eronata.");
		}

	}

	public void createFile(String director, File fisier) {
		processingFile = new ProcessingFile();
		processingFile.processingFile(fisier.getAbsolutePath(),
				allWikipediaConcepts);
		createFileConcepts(director, fisier);
		createFileWord(director, fisier);
	}

	public void createFileConcepts(String director, File fisier) {
		File file = new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "DumpConcepts/" + director
				+ "\\" + fisier.getName() + ".concept");

		try {
		
				file.createNewFile();

			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file.getAbsoluteFile()), "UTF-8"));

			conceptsFound = processingFile.getListConcepts();

			bw.write(director + ":" + fisier.getName() + " "
					+ processingFile.getTotalConcepts() + "\n");

			if (conceptsFound.size() != 0) {
				SortedSet<String> keys = new TreeSet<String>(
						conceptsFound.keySet());
				ArrayList<String> list = new ArrayList<String>();
				list.addAll(keys);

				for (String key : list) {
					bw.write(allWikipediaConcepts.get(key) + " "
							+ conceptsFound.get(key) + " " + key + "\n");
				}
			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createFileWord(String director, File fisier) {
		File file = new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "DumpFisier/" + director
				+ "\\" + fisier.getName() + ".txt");

		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file.getAbsoluteFile()), "UTF-8"));

			listWordLeft = processingFile.getListWord();

			for (String word : listWordLeft)
				bw.write(word + " ");

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public synchronized int getTotalFileProcessed() {
		return totalFileProcessed;
	}

}
