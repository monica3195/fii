package modul5.testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modul5.esaWikipedia.ParseText;

/**
 * @author Victor Tintar
 *
 */
public class ReturnFolderTest {

	

	@Test
	public void test() {
String nameFile = "index-maping.txt";
		
		ParseText test = new ParseText("Rocarta");
		
		test.setHashConcepte(test.parseTextForName(nameFile));
	test.sort();
	
	String output = test.returnFolder("1");
	
	//assertNotEquals("0", output);
	assertEquals("606", output);
	}

}
