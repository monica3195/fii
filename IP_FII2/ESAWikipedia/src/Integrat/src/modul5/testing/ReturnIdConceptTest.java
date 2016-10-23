package modul5.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import modul5.esaWikipedia.ParseText;

/**
 * @author Madalina Pastrav
 *
 */
public class ReturnIdConceptTest {

	@Test
	public void test() {
		
		String nameFile = "index-maping.txt";
		ParseText test = new ParseText("Rocarta");
		
		test.setHashConcepte(test.parseTextForName(nameFile));
		test.sort();
	
		String nume = "United Artists Records";
		String output = test.returnIdConcept(nume);
		
		assertEquals("1373839", output);
		assertNotEquals("606", output);
		assertNotEquals(606, output);
	}

}
