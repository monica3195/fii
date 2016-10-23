package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import esaWkipedia.ParseText;

/**
 * @author Madalina Pastrav
 *
 */
public class ReturnIdConceptTest {

	@Test
	public void equals() {
		
		String nameFile = "index-maping.txt";
		ParseText test = new ParseText("Rocarta");
		
		test.setHashConcepte(test.parseTextForName(nameFile));
		test.sort();
	
		String nume = "United Artists Records";
		String output = test.returnIdConcept(nume);
		
		assertEquals("1373839", output);
	}
	
	public void notEquals1(){
		
		String nameFile = "index-maping.txt";
		ParseText test = new ParseText("Rocarta");
		
		test.setHashConcepte(test.parseTextForName(nameFile));
		test.sort();
	
		String nume = "United Artists Records";
		String output = test.returnIdConcept(nume);
		assertNotEquals("606", output);
	}
	
	public void notEquals2(){
		String nameFile = "index-maping.txt";
		ParseText test = new ParseText("Rocarta");
		
		test.setHashConcepte(test.parseTextForName(nameFile));
		test.sort();
	
		String nume = "United Artists Records";
		String output = test.returnIdConcept(nume);
		
		assertNotEquals(606, output);
	}

}
