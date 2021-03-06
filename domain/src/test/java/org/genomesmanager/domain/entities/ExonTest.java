package org.genomesmanager.domain.entities;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.testobjectgenerators.*;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExonTest {
	private static Exon exon;
	private static Sequence seq;
	private static Mrna mrna;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		seq = SequencesTestObjectGenerator.Generate(1, chr).get(0);
		Gene gene = GenesTestObjectGenerator.Generate(1, seq).get(0);
		mrna = MrnasTestObjectGenerator.Generate(1, gene).get(0);
		exon = ExonsTestObjectGenerator.Generate(1, mrna).get(0);
	}

	@Test
	public void testToGff3Line() {
		String gff3Line = seq.getName() + "	agi_genomes_db	exon	" + exon.getX()
				+ "	" + exon.getY() + "	.	" + exon.getStrandness() + "	.	ID="
				+ exon.getName() + ";Parent=" + mrna.getName();
		assertEquals(gff3Line, exon.toGff3Line());
	}

	@Test
	public void testToGff3WithPseudomolCoordinatesLine() {
		long offset = 12;
		long newX = exon.getX() + offset;
		long newY = exon.getY() + offset;
		String gff3ithPseudomolCoordinatesLine = seq.getName()
				+ "	agi_genomes_db	exon	" + newX + "	" + newY + "	.	"
				+ exon.getStrandness() + "	.	ID=" + exon.getName() + ";Parent="
				+ mrna.getName();
		assertEquals(gff3ithPseudomolCoordinatesLine,
				exon.toGff3WithPseudomolCoordinatesLine(seq.getName(), offset));
	}

	@Test
	public void testExtraAnnot() {
		assertEquals("", exon.extraAnnot());
	}

}
