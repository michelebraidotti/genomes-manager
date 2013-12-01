package org.genomesmanager.domain.entities;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChromosomeTest {
	private static Chromosome chr;
	private static Species sp;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sp = SpeciesOM.Generate(1).get(0);
		chr = ChromosomesOM.Generate(1, sp).get(0);
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

}
