package jUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import conceptIdentification.Concepts;

public class ConceptsTest {

	@Test
	public void testConceptsLoad() {
		Concepts concepts=new Concepts();
		assertTrue(concepts.getConcepts()!=null);
	}
	@Test
	public void testConceptsSize() {
		Concepts concepts=new Concepts();
		assertTrue(concepts.getConcepts().size()>0);
	}
	@Test
	public void testConceptsReadOk() {
		Concepts concepts=new Concepts();
		assertTrue(concepts.getConcepts().get("rocarta")!=null);
	}
	@Test
	public void testConceptsReadFail() {
		Concepts concepts=new Concepts();
		assertTrue(concepts.getConcepts().get("fisier:Blason Marseille.jpg")!=null);
	}
}
