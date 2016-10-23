package conceptIdentification;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ProcessingFile {
	private HashMap<String, Integer> conceptsFound;
	private ArrayList<String> listWordLeft;
	private int totalConceptsFound;

	public ProcessingFile() {
	}

	public void processingFile(String file, HashMap<String, String> concepts) {
		totalConceptsFound = 0;
		conceptsFound = new HashMap<>();
		listWordLeft = new ArrayList<String>();

		int j, i, wordLimit;
		boolean conceptFound;
		boolean endConcept;
		String[] list;
		String aux = null;
		String textFile = getTextFile(file).toLowerCase();

		list = textFile.split(" ");
		for (int index = 0; index < list.length; index++) {
			i = index;
			wordLimit = (index + 250 < list.length ? index + 250 : list.length);
			for (; i < wordLimit; i++) {
				endConcept = false;
				conceptFound = false;
				j = i;
				StringBuilder x = new StringBuilder();
				while (endConcept == false && j != wordLimit) {
					x.append(list[j]);
					if (concepts.get(x.toString()) != null) {
						conceptFound = true;
						aux = x.toString();
						i = j;
					} else if (conceptFound == true)
						endConcept = true;
					x.append(" ");
					j++;
				}
				if (conceptFound == true) {
					if (conceptsFound.get(aux) == null)
						conceptsFound.put(aux, 1);
					else
						conceptsFound.put(aux, conceptsFound.get(aux) + 1);
					totalConceptsFound++;
				} else
					listWordLeft.add(list[i]);
			}
			index = i - 1;
		}
	}

	public String getTextFile(String nameFile) {
		StringBuilder string = new StringBuilder();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(nameFile), "UTF8"))) {
			while (in.ready()) {
				string.append(in.readLine());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return string.toString();
	}

	public HashMap<String, Integer> getListConcepts() {
		return conceptsFound;
	}

	public ArrayList<String> getListWord() {
		return listWordLeft;
	}

	public int getTotalConcepts() {
		return totalConceptsFound;
	}

}