package org.genomesmanager.domain.entities;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.GenesOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.junit.BeforeClass;
import org.junit.Test;

public class GeneTest {
	private static Gene gene;
	private static Sequence seq;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Species sp = SpeciesOM.Generate(1).get(0);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		seq = SequencesOM.Generate(1, chr).get(0);
		gene = GenesOM.Generate(1, seq).get(0);
	}

	@Test
	public void testToGff3Line() {
		// SequenceName0 agi_genomes_db gene 2727 2775 . + . ID=Gene0
		String gff3Line = seq.getName() + "	agi_genomes_db	gene	" + gene.getX()
				+ "	" + gene.getY() + "	.	" + gene.getStrandness() + "	.	ID="
				+ gene.getName();
		assertEquals(gff3Line, gene.toGff3Line());
	}

	@Test
	public void testToGff3WithPseudomolCoordinatesLine() {
		long offset = 12;
		long newX = gene.getX() + offset;
		long newY = gene.getY() + offset;
		String gff3Line = seq.getName() + "	agi_genomes_db	gene	" + newX + "	"
				+ newY + "	.	" + gene.getStrandness() + "	.	ID="
				+ gene.getName();
		assertEquals(gff3Line,
				gene.toGff3WithPseudomolCoordinatesLine(seq.getName(), offset));

	}

	@Test
	public void testExtraAnnot() {
		assertEquals("", gene.extraAnnot());
	}

}
