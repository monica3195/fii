package modul4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SaveFile {
	private DumpFile file;
	private String StartFolder;
	private Integer folder, maxFiles;
	private ArrayList<String> folderNames;

	public SaveFile(String index) {
		StartFolder = "DumpWikiRo\\";
		new File(StartFolder).mkdir();
		folder = -1;
		maxFiles = -1;
		folderNames = getFolderNames(index);
	}

	public void addFile(DumpFile d) {
		file = d;
	}

	public void saveFile() {
		FileWriter f;
		File a;
		checkFileAndFolder();
		try {
			String aaa = StartFolder + folderNames.get(folder) + "\\"
					+ file.getName();
			System.out.println("\t file: " + aaa);
			a = new File(aaa);
			f = new FileWriter(a);
			f.write(file.getContent());
			f.close();
		} catch (IOException ex) {
			Logger.getLogger(SaveFile.class.getName()).log(Level.SEVERE, null,
					ex);
		}

	}

	private void checkFileAndFolder() {
		if (maxFiles == -1) {
			maxFiles = 99;
			folder++;
			String name = StartFolder + folderNames.get(folder) + "\\";
			new File(StartFolder + folderNames.get(folder)).mkdir();
			System.out.println(name);
		}
		maxFiles--;
	}

	private ArrayList<String> getFolderNames(String index) {
		ArrayList<String> setFolderNames = new ArrayList<String>();
		try {
			File file = new File(index);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split(":");
				if (!setFolderNames.contains(parts[0]))
					setFolderNames.add(parts[0]);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(new JPanel(),
							"Nu exista fisierul cu index, sau nu are numele \"rodumpindex.txt\"");
			System.exit(0);
		}
		System.out.println(setFolderNames.toString());

		return setFolderNames;
	}
}
