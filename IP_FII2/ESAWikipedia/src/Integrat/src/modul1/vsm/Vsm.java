package modul1.vsm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map.Entry;

import modul1.vsm.events.Dispatcher;
import modul1.vsm.io.ConceptWriter;
import modul1.vsm.io.DirLoader;

public class Vsm {

	private String inputPath;
	private String outputPath;

	private DirLoader dirLoader;
	private ConceptWriter writer;
	private Normalizer normalizer = null;
	private Dispatcher events = null;
	
	private HashMap<String, Double> globalConcepts;

	/**
	 * Create a new VSM instance.
	 * 
	 * @param inputDir input directory
	 * @param outputDir output directory
	 */
	public Vsm(String inputPath, String outputPath) {
		this.inputPath = inputPath;
		this.outputPath = outputPath;
		
		this.dirLoader = new DirLoader();
		this.writer = new ConceptWriter();
		this.globalConcepts = new HashMap<String, Double>();
	}
	
	/**
	 * Start processing.
	 */
	public void start() {
		File inputDir = new File(this.inputPath);
		File[] dirs = inputDir.listFiles();
		
		// Start processing event.
		if (events != null) {
			events.startProcessing(dirs.length);
		}
		
		// Normalize + global concepts.
		for (File dir : dirs) {
		    if (! dir.isDirectory()) continue;
		    
		    // Processing directory event.
		    if (events != null) {
		    	events.processingDir(dir);
		    }
		    
		    HashMap<Integer, ConceptData> concepts = dirLoader.load(dir.getPath());
			
		    if (normalizer != null) {
		    	normalizer.normalize(concepts);
		    	
		    	try {
					writer.write(concepts, outputPath, true);
				} catch (FileNotFoundException e) {
					// Error event.
					if (events != null) {
						events.error("FileNotFoundException", e);
					}
				}
		    	
		    	inputPath = outputPath;
		    }
		    
			processConcepts(concepts);
		}
			
		// Calculate distances.
		inputDir = new File(inputPath);
		
		// Start calculating distances event.
		if (events != null) {
			events.startDistances();
		}

		for (File dir : inputDir.listFiles()) {
			if (! dir.isDirectory()) continue;
			
			// Processing directory event.
			if (events != null) {
		    	events.processingDir(dir);
		    }

			HashMap<Integer, ConceptData> concepts = dirLoader.load(dir.getPath());
			
			calculateDistances(concepts);
			
			try {
				writer.write(concepts, outputPath, false);
			} catch (FileNotFoundException e) {
				// Error event.
				if (events != null) {
					events.error("FileNotFoundException", e);
				}
			}
		}
		
		// Finish event.
		if (events != null) {
			events.finish();
		}
 	}

	/**
	 * Process current loaded concepts.
	 * Add key and value to global concepts.
	 * 
	 * @param concepts 
	 */
	public void processConcepts(HashMap<Integer, ConceptData> concepts) {
		for (Entry<Integer, ConceptData> concept : concepts.entrySet()) {
			for (Entry<String, Double> keyword : concept.getValue().getConcepts().entrySet()) {
				if (globalConcepts.containsKey(keyword.getKey())) {
					globalConcepts.put(keyword.getKey(), globalConcepts.get(keyword.getKey()) + keyword.getValue());
				} else {
					globalConcepts.put(keyword.getKey(), keyword.getValue());
				}
			}
		}
	}
	
	/**
	 * Calculate concept distances.
	 * 
	 * @param concepts 
	 */
	public void calculateDistances(HashMap<Integer, ConceptData> concepts) {
		for (Entry<Integer, ConceptData> concept : concepts.entrySet()) {
			for (Entry<String, Double> keyword : concept.getValue().getConcepts().entrySet()) {
				keyword.setValue(keyword.getValue() / globalConcepts.get(keyword.getKey()));
			}
		}
	}
	
	/**
	 * Get global concepts.
	 * 
	 * @return global concepts
	 */
	public HashMap<String, Double> getGlobalConcepts() {
		return globalConcepts;
	}

	/**
	 * Set event dispatcher.
	 * 
	 * @param events
	 */
	public void setDispatcher(Dispatcher events) {
		this.events = events;
	}
	
	/**
	 * Set normalizer.
	 * 
	 * @param normalizer
	 */
	public void setNormalizer(Normalizer normalizer) {
		this.normalizer = normalizer;
	}
}
