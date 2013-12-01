package org.genomesmanager.domain.entities;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.RepeatsClassificationOM;
import org.genomesmanager.domain.entities.objectmothers.RepeatsOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.junit.BeforeClass;
import org.junit.Test;

public class RepeatTest {
	private static RepeatsClassification repClass;
	private static Repeat repeat;
	private static Sequence seq;
	private static Sequence outdatedSeq;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Species sp = SpeciesOM.Generate(1).get(0);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		seq = SequencesOM.Generate(1, chr).get(0);
		String repClassDefinition = "TEST, TEST, TEST, test, test";
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeat = RepeatsOM.Generate(1, repClass, seq).get(0);

		// let's create an outdated sequence
		outdatedSeq = SequencesOM.Generate(1, chr).get(0);
		Sequence newSeq = SequencesOM.Generate(1, chr).get(0);
		outdatedSeq.setSupersededBy(newSeq);
	}

	@Test
	public void testGetType() {
		assertEquals(null, repeat.getType());
	}

	@Test
	public void testGetStructuralDesc() {
		assertEquals("", repeat.getStructuralDesc());
	}

	@Test
	public void testValidateOutOfBoundsException()
			throws IntervalFeatureException, RepeatException {

		repeat.setSequence(seq);
		repeat.setX(-1);
		try {
			repeat.validate();
		} catch (OutOfBoundsException e) {
			// OK
		}
		repeat.setX(seq.getLength() + 1);
		try {
			repeat.validate();
		} catch (OutOfBoundsException e) {
			// OK
		}

		repeat.setY(-1);
		try {
			repeat.validate();
		} catch (OutOfBoundsException e) {
			// OK
		}
		repeat.setY(seq.getLength() + 1);
		try {
			repeat.validate();
		} catch (OutOfBoundsException e) {
			// OK
		}

		// Fixing it
		repeat.setY(seq.getLength() - 1);
		repeat.setX(1);
	}

	@Test
	public void testValidateRepeatException() throws OutOfBoundsException,
			IntervalFeatureException {
		repeat.setSequence(null);
		try {
			repeat.validate();
		} catch (RepeatException e) {
			// OK
		}

		repeat.setX(seq.getLength() - 1);
		repeat.setY(1);
		try {
			repeat.validate();
		} catch (RepeatException e) {
			// OK
		}

		// Fixing it
		repeat.setY(seq.getLength() - 1);
		repeat.setX(1);
	}

	@Test
	public void testIsOutdated() {
		repeat.setSequence(null);
		assertEquals(false, repeat.isOutdated());
		repeat.setSequence(outdatedSeq);
		assertEquals(true, repeat.isOutdated());
		repeat.setSequence(seq);
		assertEquals(false, repeat.isOutdated());
	}

	@Test
	public void testSetAttributes() throws RepeatException {
		Properties attributes = new Properties();
		attributes.setProperty("notes", "notesvalue");
		repeat.setAttributes(attributes);
		assertEquals(attributes.getProperty("notes"), repeat.getNotes());
	}

	@Test
	public void testGetGff3Type() {
		assertEquals("", repeat.getGff3Type());
	}

	@Test
	public void testToGff3Line() throws IntervalFeatureException {
		seq.setName("SequenceName0");
		repeat.setX(1);
		repeat.setY(seq.getLength() - 1);
		repeat.setStrandness("+");
		repeat.setId(0);
		String gff3line = "SequenceName0	agi_genomes_db		" + repeat.getX()
				+ "	" + repeat.getY() + "	.	+	.	ID=" + repeat.getId();
		assertEquals(gff3line, repeat.toGff3Line());
	}

	@Test
	public void testToGff3WithPseudomolCoordinatesLine()
			throws IntervalFeatureException {
		long offset = 12;
		seq.setName("SequenceName0");
		repeat.setX(1);
		repeat.setY(seq.getLength() - 1);
		repeat.setStrandness("+");
		repeat.setId(0);
		long x = repeat.getX() + offset;
		long y = repeat.getY() + offset;
		String gff3WithPseudomolCoordinatesLine = "SequenceName0	agi_genomes_db		"
				+ x + "	" + y + "	.	+	.	ID=" + repeat.getId();
		assertEquals(
				gff3WithPseudomolCoordinatesLine,
				repeat.toGff3WithPseudomolCoordinatesLine(seq.getName(), offset));

	}

	@Test
	public void testExtraAnnot() {
		String extraAnnot = ";rclass=" + repClass.getRepClass() + ";subclass="
				+ repClass.getSubclass() + ";rorder=" + repClass.getOrder()
				+ ";superfamily=" + repClass.getSuperfamily() + ";family="
				+ repClass.getFamily() + ";notes=" + repeat.getNotes();
		assertEquals(extraAnnot, repeat.extraAnnot());
	}

	@Test
	public void testGetFastaId() {
		String fastaId = ">" + repeat.getId() + "|" + repeat.getType() + "|"
				+ seq.toString() + "|" + repeat.getX() + "|" + repeat.getY()
				+ "|" + repeat.getStrandness();
		assertEquals(fastaId, repeat.getFastaId());
	}

	@Test
	public void testGetSequenceText() throws SequenceSliceException {
		String seqText = "";
		if (repeat.getStrandness().equals("+")) {
			seqText = seq.getSlice(repeat.getX(), repeat.getY());
		} else {
			seqText = seq.getReverseComplementSlice(repeat.getX(),
					repeat.getY());
		}
		assertEquals(seqText, repeat.getSequenceText());
	}

}
