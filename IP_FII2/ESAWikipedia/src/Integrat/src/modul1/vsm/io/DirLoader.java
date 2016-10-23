package modul1.vsm.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import modul1.vsm.ConceptData;

public class DirLoader {
	
	private HashMap<Integer, ConceptData> concepts;

	/**
	 * Load and return concepts from path.
	 * 
	 * @param path
	 * @return the loaded concepts
	 */
	public HashMap<Integer, ConceptData> load(String path) {
		concepts = new HashMap<Integer, ConceptData>();
		
		File dir = new File(path);
		
		for (File file : dir.listFiles()) {
		    if (file.isFile()) {
		    	try {
					loadFile(file);
				} catch (IOException e) {
					System.out.println("FileNotFoundException " + file.getName());
				}
		    }
		}
		
		return concepts;
	}
	
	/**
	 * Load concept from file.
	 * 
	 * @param file
	 * @throws IOException 
	 */
	public void loadFile(File file) throws IOException {
		String line;
		String conceptId;
		int conceptSize;
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		HashMap<String, Double> relatedConcepts = new HashMap<String, Double>();
		
		line = reader.readLine();
		
		String[] list = line.split(" ");
		conceptId = list[0];
		conceptSize = Integer.parseInt(list[1]);
				
		while ((line = reader.readLine()) != null) {
			list = line.split(" ");
			relatedConcepts.put(list[0].trim(), Double.parseDouble(list[1]));
		}
				
		concepts.put(Integer.parseInt(file.getName()), new ConceptData(conceptId, conceptSize, relatedConcepts));
		
		reader.close();
	}
	
}
