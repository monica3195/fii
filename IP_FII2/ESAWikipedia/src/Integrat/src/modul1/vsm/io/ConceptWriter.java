package modul1.vsm.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map.Entry;

import modul1.vsm.ConceptData;

public class ConceptWriter {
	
	/**
	 * Write concepts.
	 * 
	 * @param concepts
	 * @param dir
	 * @param round
	 * @throws FileNotFoundException
	 */
	public void write(HashMap<Integer, ConceptData> concepts, String dir, boolean round) throws FileNotFoundException {
		for (Entry<Integer, ConceptData> concept : concepts.entrySet()) {
			String[] list = concept.getValue().getID().split(":");
			File folder = new File(dir + "/" + list[0]);
			
			if (!folder.exists()) {
				folder.mkdirs();
			}

			File file = new File(folder.getAbsolutePath() + "/" + list[1]);
			PrintWriter writer = new PrintWriter(file);
			HashMap<String, Double> currentConcept = concept.getValue().getConcepts();
			
			writer.println(concept.getValue().getID() + " " + concept.getValue().getSize());

			for (Entry<String, Double> data : currentConcept.entrySet()) {
				if (round) {
					writer.println(data.getKey() + " " + round(data.getValue(), 2));
				} else {
					writer.println(data.getKey() + " " + String.format("%.19f", new BigDecimal(data.getValue())));
				}
			}

			writer.close();
		}
	}
	
	/**
	 * Round value.
	 * 
	 * @param value
	 * @param places
	 * @return rounded value
	 */
	private static double round(double value, int places) {
		if (places < 0) places = 0;
		
		long factor = (long) Math.pow(10, places);
		long tmp = Math.round(value * factor);
		
		return (double) tmp / factor;
	}
}
