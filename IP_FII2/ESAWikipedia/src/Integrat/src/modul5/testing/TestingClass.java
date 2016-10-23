package modul5.testing;

import static org.junit.Assert.assertNotEquals;

import java.util.LinkedHashMap;

import org.junit.Test;

import modul5.esaWikipedia.ParseText;

/**
 * @author Mihaela Holoca
 *
 */

public class TestingClass {

	@Test
	public void testingName() {

		String nameFile = "index-maping.txt";
		
		ParseText p = new ParseText("Rocarta");
		
		p.setHashConcepte(p.parseTextForName(nameFile));

		LinkedHashMap<String, Double> hashFrecventa = p.sort();
		LinkedHashMap<String, Double> hashFrecventa2 = new LinkedHashMap<String, Double>();
		
		hashFrecventa2.put("606:3", 0.4375);
		hashFrecventa2.put("606:2", 0.3125);
		hashFrecventa2.put("606:7", 0.125);
		hashFrecventa2.put("606:5", 0.125);
		assertNotEquals(hashFrecventa2,hashFrecventa);

	}
}
