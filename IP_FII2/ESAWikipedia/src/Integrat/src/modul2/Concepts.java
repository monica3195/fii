package modul2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class Concepts {
	private HashMap<String, String> concepts = new HashMap<>();
	private static String dumpName = "indexFaraStopWords.txt";

	public Concepts() {
		File file = new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + dumpName);

		String[] list;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "UTF8"))) {

			while (in.ready()) {

				list = in.readLine().split(":");

				if (list.length < 4)
					concepts.put(list[2].toLowerCase(), list[0] + ':' + list[1]);

			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void showConcepts() {
		SortedSet<String> keys = new TreeSet<String>(concepts.keySet());
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(keys);
		for (String key : list) {
			System.out.println(concepts.get(key) + " " + key);
		}
		System.out.println(concepts.size());
	}

	public HashMap<String, String> getConcepts() {
		return concepts;
	}

	public String findConcept(String conceptName) {
		return concepts.get(conceptName);
	}

}
