package org.genomesmanager.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.genomesmanager.domain.entities.testobjectgenerators.ChromosomesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChromosomeTest {
	private static Chromosome chr;
	private static Species sp;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
	}

	@Test
	public void testSpeciesString() {
		assertEquals(sp.toString(), chr.speciesString());
	}

	@Test
	public void testToString() {
		chr.setNumber("9");
		assertEquals(chr.getNumber(), chr.toString());
	}

	@Test
	public void testDescString() {
		sp.setGenus("Genus");
		sp.setSpecies("Species");
		sp.setSubspecies("Subspecies");
		chr.setNumber("1");
		String descr = "G. Spec. Subs. Chr1";
		assertEquals(descr, chr.descString());
	}

	@Test
	public void testEquals() {
		Chromosome otherChr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		otherChr.setNumber("somethingelse");
		assertFalse(chr.equals(otherChr));
		otherChr.setNumber(chr.getNumber());
		otherChr.setSpecies(chr.getSpecies());
		assertTrue(chr.equals(otherChr));
	}
}
