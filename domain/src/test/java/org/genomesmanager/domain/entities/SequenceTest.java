/**
 * 
 */
package org.genomesmanager.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.junit.Before;
import org.junit.Test;

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
		Species sp = SpeciesOM.Generate(1).get(0);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		seq = SequencesOM.Generate(1, chr).get(0);
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
	public void testSetCreateDefaults() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#setUpdateDefaults()}.
	 */
	@Test
	public void testSetUpdateDefaults() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#getMaskedSequence()}.
	 */
	@Test
	public void testGetMaskedSequence() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#getSlice(int, int)}.
	 */
	@Test
	public void testGetSlice() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#getReverseComplementSlice(int, int)}
	 * .
	 */
	@Test
	public void testGetReverseComplementSlice() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#descr()}.
	 */
	@Test
	public void testDescr() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.genomesmanager.domain.entities.Sequence#humanName()}.
	 */
	@Test
	public void testHumanName() {
		fail("Not yet implemented");
	}

}
