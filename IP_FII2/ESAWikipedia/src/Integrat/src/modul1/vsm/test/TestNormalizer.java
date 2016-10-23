package modul1.vsm.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Test;

import modul1.vsm.ConceptData;
import modul1.vsm.Normalizer;
import modul1.vsm.io.DirLoader;

public class TestNormalizer {

	@Test
	public void testNormalizeConcepts() {
		HashMap<Integer, ConceptData> concepts = new DirLoader().load("test-input\\606");
		
		new Normalizer().normalize(concepts);
		
		for (Entry<Integer, ConceptData> concept : concepts.entrySet()) {
			double size = 0;
			
			for (Entry<String, Double> keyword : concept.getValue().getConcepts().entrySet()) {
				size += keyword.getValue();
			}
			
			if (size != 0.0) {
				assertTrue(size > 9999 && size < 10001);
			}
		}
	}

}
