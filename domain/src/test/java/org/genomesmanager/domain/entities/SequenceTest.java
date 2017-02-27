/**
 * 
 */
package org.genomesmanager.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.StringUtils;
import org.genomesmanager.domain.entities.testobjectgenerators.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

/**
 * @author michele
 * 
 */
public class SequenceTest {

	private Sequence seq;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		seq = SequencesTestObjectGenerator.Generate(1, chr).get(0);
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#getType()}.
	 */
	@Test
	public void testGetType() {
		assertEquals("", seq.getType());
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#getFastaHeader()}.
	 */
	@Test
	public void testGetFastaHeader() {
		String fastaHeader = ">" + seq.getId();
		assertEquals(fastaHeader, seq.getFastaHeader());
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(seq.descr(), seq.toString());
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#setCreateDefaults()}.
	 */
	@Test
	public void testSetCreateDefaults() throws InterruptedException {
		Calendar beforeSettingsDefaults = Calendar.getInstance();
		Thread.sleep(1000);
		seq.setCreateDefaults();
		Thread.sleep(1000);
		Calendar afterSettingsDefaults = Calendar.getInstance();
		assertEquals(seq.getDateCreated(), seq.getDateModified());
		assertEquals(-1, beforeSettingsDefaults.compareTo(seq.getDateCreated()));
		assertEquals(1, afterSettingsDefaults.compareTo(seq.getDateCreated()));
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#setUpdateDefaults()}.
	 */
	@Test
	public void testSetUpdateDefaults() throws InterruptedException {
		Calendar beforeSettingsDefaults = Calendar.getInstance();
		Thread.sleep(1000);
		seq.setUpdateDefaults();
		assertEquals(-1, beforeSettingsDefaults.compareTo(seq.getDateModified()));
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#getMaskedSequence()}.
	 */
	@Test
	public void testGetMaskedSequence() throws Exception {

		LtrRepeat ltr = RepeatsTestObjectGenerator.GenerateLtrs(1, RepeatsClassificationTestObjectGenerator.Generate("I, I, LTR, test, test"),seq).get(0);
		seq.getRepeats().add(ltr);
		ltr.setX(seq.getSequenceText().length() + 1 );
		try {
			seq.getMaskedSequence();
			assertTrue(false);
		}
		catch (SequenceSliceException sse) {
			// O.K.
			assertTrue(true);
		}
		ltr.setX( -1 );
		try {
			seq.getMaskedSequence();
			assertTrue(false);
		}
		catch (SequenceSliceException sse) {
			// O.K.
			assertTrue(true);
		}

		ltr.setY(seq.getSequenceText().length() + 1 );
		try {
			seq.getMaskedSequence();
			assertTrue(false);
		}
		catch (SequenceSliceException sse) {
			// O.K.
			assertTrue(true);
		}
		ltr.setY( -1 );
		try {
			seq.getMaskedSequence();
			assertTrue(false);
		}
		catch (SequenceSliceException sse) {
			// O.K.
			assertTrue(true);
		}

		ltr.setX(1);
		ltr.setY(seq.getSequenceText().length());
		String maskedSeq = seq.getMaskedSequence();
		String expectedSeq = StringUtils.repeat('N', seq.getSequenceText().length());
		assertEquals(expectedSeq, maskedSeq);

		ltr.setX(2);
		ltr.setY(seq.getSequenceText().length() - 1);
		maskedSeq = seq.getMaskedSequence();
		expectedSeq = seq.getSequenceText().charAt(0)
				+ StringUtils.repeat('N', seq.getSequenceText().length() - 2)
				+ seq.getSequenceText().charAt(seq.getSequenceText().length() - 1);
		assertEquals(expectedSeq, maskedSeq);
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#getSlice(int, int)}.
	 */
	@Test
	public void testGetSlice() {

		try {
			seq.getSlice(5, 3);
			assertTrue(false);
		}
		catch (SequenceSliceException sse) {
			assertTrue(true);
		}
		try {
			seq.getSlice(-1, 11);
			assertTrue(false);
		}
		catch (SequenceSliceException sse) {
			assertTrue(true);
		}
		try {
			seq.getSlice(5, seq.getSequenceText().length() + 1);
			assertTrue(false);
		}
		catch (SequenceSliceException sse) {
			assertTrue(true);
		}
		try {
			String subseq = seq.getSlice(1, 5);
			assertTrue(subseq.length() == 5);
		} catch (SequenceSliceException e) {
			assertTrue(false);
		}

	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#getReverseComplementSlice(int, int)}
	 * .
	 */
	@Test
	public void testGetReverseComplementSlice() {
		String backupSeqText = seq.getSequenceText();
		try {
			seq.setSequenceText("ACTG");
			String complementReverse = seq.getReverseComplementSlice(1, 4);
			assertEquals("CAGT", complementReverse);
			seq.setSequenceText("actg");
			String complementReverse1 = seq.getReverseComplementSlice(1, 4);
			assertEquals("cagt", complementReverse1);
			seq.setSequenceText("AAACTGGGAAAZXCFNNNNAACTGgaagtttaaa");
			String complementReverse2 = seq.getReverseComplementSlice(1, 34);
			assertEquals("tttaaacttcCAGTTNNNNFGXZTTTCCCAGTTT", complementReverse2);
		}
		catch (SequenceSliceException sse) {
			assertTrue(false);
		}
		finally {
			seq.setSequenceText(backupSeqText);
		}

	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#descr()}.
	 */
	@Test
	public void testDescr() {

		assertEquals(seq.getName() + Sequence.NAME_SEPARATOR + seq.getVersion(), seq.descr());
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#humanName()}.
	 */
	@Test
	public void testHumanName() {

		assertEquals(seq.getName(), seq.humanName());
	}

}
