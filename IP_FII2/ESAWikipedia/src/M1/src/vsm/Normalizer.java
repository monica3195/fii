package vsm;

import java.util.HashMap;
import java.util.Map.Entry;

public class Normalizer {
	
	private int rate = 10000;

	/**
	 * Create a new Normalizer instance.
	 * 
	 * @param rate
	 */
	public Normalizer(int rate) {
		this.rate = rate;
	}

	public Normalizer() {
		
	}

	/**
	 * Normalize concepts.
	 * 
	 * @param concepts
	 */
	public void normalize(HashMap<Integer, ConceptData> concepts) {
		for (Entry<Integer, ConceptData> concept : concepts.entrySet()) {
			int size = 0;
			
			for (Entry<String, Double> keyword : concept.getValue().getConcepts().entrySet()) {
				size += keyword.getValue();
			}
			
			for (Entry<String, Double> keyword : concept.getValue().getConcepts().entrySet()) {
				keyword.setValue((keyword.getValue() * rate) / size);
			}
		}
	}

}
