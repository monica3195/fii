package modul4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewIndex extends Thread {

	private List<String> stopWords;
	private String filepath;
	Observer o;

	public NewIndex(Observer obs, String index) {
		o = obs;
		filepath = index;
	}

	private void setStopWords() {
		BufferedReader in = null;
		stopWords = new ArrayList<String>();
		try {
			in = new BufferedReader(new FileReader("stopWords.txt"));
			String word;
			while ((word = in.readLine()) != null) {
				stopWords.add(word);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		int numberOfFiles = 0;
		setStopWords();
		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			in = new BufferedReader(new FileReader(filepath));
			out = new BufferedWriter(new FileWriter("indexFaraStopWords.txt"));
			String line;
			while ((line = in.readLine()) != null) {
				numberOfFiles++;
				String[] words = line.split(":");
				if (stopWords.contains(words[2].toLowerCase()))
					continue;
				out.write(line + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		o.update(numberOfFiles);
	}
}
