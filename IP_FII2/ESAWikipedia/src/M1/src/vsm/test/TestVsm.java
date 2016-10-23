package vsm.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import vsm.ConceptData;
import vsm.Normalizer;
import vsm.Vsm;
import vsm.io.DirLoader;

public class TestVsm {

	@Test
	public void testProcessConcepts() {
		Vsm vsm = new Vsm("", "");
		
		HashMap<Integer, ConceptData> concepts = new DirLoader().load("test-input\\606");
		new Normalizer().normalize(concepts);
		vsm.processConcepts(concepts);
		HashMap<String, Double> c = vsm.getGlobalConcepts();
		
		assertEquals(c.size(), 106);
		
		Double val = 120.48192771084338;
		
		assertEquals(c.get("172735527:610600"), val);
	}
}
