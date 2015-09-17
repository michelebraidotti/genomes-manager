package org.genomesmanager.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

	@Test
	public void testEquals() throws IntervalFeatureException {
		Repeat otherRepeat = RepeatsOM.Generate(1, repClass, seq).get(0);
		otherRepeat.setX(repeat.getX() + 1);
		otherRepeat.setY(repeat.getY() - 1);
		assertFalse(repeat.equals(otherRepeat));
		otherRepeat.setX(repeat.getX());
		otherRepeat.setY(repeat.getY());
		otherRepeat.setSequence(repeat.getSequence());
		otherRepeat.setRepeatsClassification(repeat.getRepeatsClassification());
		assertTrue(repeat.equals(otherRepeat));
	}

	@Test
	public  void testGetNew() throws Exception {
		String[] repClassDefinitions = {
				"I, I, LINE, test, test",
				"II, II, Helitron, test, test", "II, III, MITE, test, test",
				"II, I, DNA_TE, test, test", "I, I, LTR, test, test",
				"UNKNOWN, UNKNOWN, UNKNOWN, test, test",
				"I, I, SINE, test, test" };
		/* 1. LINE */
		String repClassDefinition = repClassDefinitions[0];
		RepeatsClassification repClass = RepeatsClassificationOM
				.Generate(repClassDefinition);
		LineRepeat lineRepeat = (LineRepeat) Repeat.getNew(repClass);
		assertEquals(repClass, lineRepeat.getRepeatsClassification());

		/* 2. Helitron */
		repClassDefinition = repClassDefinitions[1];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		HelitronRepeat helitronRepeat = (HelitronRepeat) Repeat.getNew(repClass);
		assertEquals(repClass, helitronRepeat.getRepeatsClassification());

		/* 3. Mite */
		repClassDefinition = repClassDefinitions[2];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		MiteRepeat miteRepeat = (MiteRepeat) Repeat.getNew(repClass);
		assertEquals(repClass, miteRepeat.getRepeatsClassification());

		/* 3. DNATE */
		repClassDefinition = repClassDefinitions[3];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		DnaTeRepeat dnaTeRepeat = (DnaTeRepeat) Repeat.getNew(repClass);
		assertEquals(repClass, dnaTeRepeat.getRepeatsClassification());

		/* 4. LTR */
		repClassDefinition = repClassDefinitions[4];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		LtrRepeat ltrRepeat = (LtrRepeat) Repeat.getNew(repClass);
		assertEquals(repClass, ltrRepeat.getRepeatsClassification());

		/* 5. UNKN */
		repClassDefinition = repClassDefinitions[5];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		UnknownRepeat unknownRepeat = (UnknownRepeat) Repeat.getNew(repClass);
		assertEquals(repClass, unknownRepeat.getRepeatsClassification());

		/* 6. Sine */
		repClassDefinition = repClassDefinitions[6];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		SineRepeat sineRepeat = (SineRepeat) Repeat.getNew(repClass);
		assertEquals(repClass, sineRepeat.getRepeatsClassification());

	}

}
