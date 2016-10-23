package testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import esaWkipedia.ParseText;

/**
 * @author Mihaela Holoca
 *
 */

public class TestingClass2 {

	@Test
	public void compareTest() {

		String nameFile = "index-maping.txt";
		
		ParseText p = new ParseText("Rocarta");
		
		p.setHashConcepte(p.parseTextForName(nameFile));

		String compareString = p.compare("11328208:9438");
		assertEquals("Etimologie", compareString);
	}
		
	public void compareTest2() {
		
        String nameFile = "index-maping.txt";
		
		ParseText p = new ParseText("Rocarta");
		
		p.setHashConcepte(p.parseTextForName(nameFile));
		
		String compareString1 = p.compare("606:145");
		assertEquals("Inginerie", compareString1);
	}
	
	public void compareTest3() {
       String nameFile = "index-maping.txt";
		
		ParseText p = new ParseText("Rocarta");
		
		p.setHashConcepte(p.parseTextForName(nameFile));
		String compareString2 = p.compare("73174674:183974");
		assertEquals("Proces", compareString2);
	}
	
	public void compareTest4() {
		String nameFile = "index-maping.txt";
		
		ParseText p = new ParseText("Rocarta");
		
		p.setHashConcepte(p.parseTextForName(nameFile));
		String compareString3 = p.compare("18263726:19939");
		assertEquals("Inginer", compareString3);
	}
	
	public void compareTest5() {
		String nameFile = "index-maping.txt";
		
		ParseText p = new ParseText("Rocarta");
		
		p.setHashConcepte(p.parseTextForName(nameFile));
		String compareString4 = p.compare("102266521:279683");
		assertEquals("Obiect", compareString4);
	}
	
}
