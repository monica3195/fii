package vsm;

import java.util.HashMap;

public class ConceptData {
	
	protected String id;
	protected int size;
	protected HashMap<String, Double> concepts;

	/**
	 * Create a new ConceptData instance.
	 * 
	 * @param conceptID
	 * @param conceptSize
	 * @param concepts
	 */
	public ConceptData(String id, int size, HashMap<String, Double> concepts) {
		this.id = id;
		this.size = size;
		this.concepts = concepts;
	}

	/**
	 * Get concept id.
	 * 
	 * @return the concept id
	 */
	public String getID() {
		return id;
	}

	/**
	 * Get concept size.
	 * 
	 * @return the concept size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Get concepts.
	 * 
	 * @return the concepts
	 */
	public HashMap<String, Double> getConcepts() {
		return concepts;
	}
}
