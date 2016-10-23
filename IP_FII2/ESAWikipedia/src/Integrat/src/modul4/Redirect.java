package modul4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Redirect {
	private static Vector<Vector<String>> dumpIndex = new Vector<Vector<String>>();

	public Redirect(String index) {
		try {
			File file = new File(index);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split(":");
				Vector<String> elem = new Vector<String>();
				elem.add(parts[0]);
				elem.add(parts[1]);
				elem.add(parts[2]);
				dumpIndex.add(elem);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(new JPanel(),
							"Nu exista fisierul cu index, sau nu are numele \"rodumpindex.txt\"");
			System.exit(0);
		}
	};

	private String getIdByTitle(String title) {
		for (int i = 0; i < dumpIndex.size(); i++)
			if (dumpIndex.get(i).get(2).equalsIgnoreCase(title))
				return dumpIndex.get(i).get(1);
		return null;
	}

	public void setRedirect(String redirectLine, String id) {

		String concept = redirectLine.substring(redirectLine.indexOf(' ') + 1);
		concept = concept.substring(concept.indexOf('[') + 2);
		concept = concept.substring(0, concept.indexOf(']'));
		String redTo = getIdByTitle(concept);
		if (redTo != null) {
			Integer cheie = Integer.valueOf(id);
			Integer valoare = Integer.valueOf(redTo);
			OnlyRedirects.getInstance().add(cheie, valoare);
			// System.out.println(cheie + "->" + valoare);
		}

	}
}
