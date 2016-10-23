package testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import esaWkipedia.ParseText;


/**
 * @author Madalina Pastrav
 * 
 *
 */
public class PrintRootNameTest {

	

	

	@Test
	public void test() {
		String nameFile = "index-maping.txt";
		
		ParseText test = new ParseText("Rocarta");
		
		test.setHashConcepte(test.parseTextForName(nameFile));
		
		String output = test.printRootName(test.getRoot());
		assertEquals("Rocarta", output);
		
	}

}
