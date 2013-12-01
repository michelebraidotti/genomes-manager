package org.genomesmanager.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.genomesmanager.domain.dtos.RepeatsGff3Order;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.RepeatsClassificationOM;
import org.genomesmanager.domain.entities.objectmothers.RepeatsOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.junit.BeforeClass;
import org.junit.Test;

public class DnaTeRepeatTest {
	private static DnaTeRepeat dnate;
	private static RepeatsClassification repClass;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Species sp = SpeciesOM.Generate(1).get(0);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		String repClassDefinition = "II, I, DNA_TE, test, test";
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		dnate = RepeatsOM.GenerateDnaTes(1, repClass, seq).get(0);
	}

	@Test
	public void testGetType() {
		assertEquals(repClass.getOrder(), dnate.getType());
	}

	@Test
	public void testValidate() throws IntervalFeatureException, RepeatException {
		dnate.setTirX(dnate.length() + 1);
		try {
			dnate.validate();
		} catch (OutOfBoundsException e) {
			assertTrue(true);
		}
		dnate.setTirY(dnate.length() + 1);
		try {
			dnate.validate();
		} catch (OutOfBoundsException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSetAttributes() throws RepeatException {
		Properties attributes = new Properties();
		attributes.setProperty("tir_x", "1");
		attributes.setProperty("tir_y", "2");
		attributes.setProperty("trans_presence", "true");
		attributes.setProperty("trans_sequence", "AAAAA");
		attributes.setProperty("tsd_sequence", "CCCCC");
		dnate.setAttributes(attributes);
		assertEquals(attributes.getProperty("tir_x"), dnate.getTirX() + "");
		assertEquals(attributes.getProperty("tir_y"), dnate.getTirY() + "");
		assertEquals(attributes.getProperty("trans_presence"),
				dnate.getTransPresence() + "");
		assertEquals(attributes.getProperty("trans_sequence"),
				dnate.getTransSequence());
		assertEquals(attributes.getProperty("tsd_sequence"),
				dnate.getTsdSequence());
	}

	@Test
	public void testGetGff3Type() {
		assertEquals(RepeatsGff3Order.DNATE.getLabel(), dnate.getGff3Type());
	}

	@Test
	public void testExtraAnnot() {
		// tir_x=321;tir_y=534;trans_presence=true;trans_sequence=AA;tsd_sequence=CC
		dnate.setTirX(1);
		dnate.setTirY(2);
		dnate.setTransPresence(true);
		dnate.setTransSequence("AAA");
		dnate.setTsdSequence("CCC");
		String annot = ";tir_x=1;tir_y=2;trans_presence=true;trans_sequence=AAA;tsd_sequence=CCC";
		assertTrue(dnate.extraAnnot().endsWith(annot));
	}

	@Test
	public void testIsComplete() {
		dnate.setTirX(1);
		dnate.setTirY(2);
		assertTrue(dnate.isComplete());
		dnate.setTirX(0);
		dnate.setTirY(2);
		assertFalse(dnate.isComplete());
		dnate.setTirX(1);
		dnate.setTirY(0);
		assertFalse(dnate.isComplete());
		dnate.setTirX(0);
		dnate.setTirY(0);
		assertFalse(dnate.isComplete());
	}

}
