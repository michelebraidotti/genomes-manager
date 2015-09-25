package org.genomesmanager.domain.entities.objectmothers;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsClassificationException;
import org.genomesmanager.domain.entities.RepeatsOrder;
import org.junit.Test;

public class RepeatsClassificationTest {

	@Test
	public void testGENERATE() throws RepeatsClassificationException {
		String familyDetails = "superfamily=testSuperFamily;family=TESTFAMILY";

		String name = "HELITRONS";
		RepeatsClassification classificationElitronVerify = new RepeatsClassification();
		classificationElitronVerify.setRepClass("II");
		classificationElitronVerify.setSubclass("II");
		classificationElitronVerify.setOrder(RepeatsOrder.HEL.getLabel());
		classificationElitronVerify.setSuperfamily("Helitron");
		classificationElitronVerify.setFamily("NA");
		RepeatsClassification classificationElitron = RepeatsClassification
				.generate(name, "");
		assertEquals(classificationElitronVerify, classificationElitron);
		classificationElitronVerify.setSuperfamily("testSuperFamily");
		classificationElitronVerify.setFamily("TESTFAMILY");
		classificationElitron = RepeatsClassification.generate(name,
				familyDetails);
		assertEquals(classificationElitronVerify, classificationElitron);

		name = "SOLO";
		RepeatsClassification classificationSoloVerify = new RepeatsClassification();
		classificationSoloVerify.setRepClass("I");
		classificationSoloVerify.setSubclass("I");
		classificationSoloVerify.setOrder(RepeatsOrder.LTR.getLabel());
		classificationSoloVerify.setSuperfamily("testSuperFamily");
		classificationSoloVerify.setFamily("TESTFAMILY");
		RepeatsClassification classificationSolo = RepeatsClassification
				.generate(name, familyDetails);
		assertEquals(classificationSoloVerify, classificationSolo);

		name = "LTR";
		RepeatsClassification classificationLtrVerify = new RepeatsClassification();
		classificationLtrVerify.setRepClass("I");
		classificationLtrVerify.setSubclass("I");
		classificationLtrVerify.setOrder(RepeatsOrder.LTR.getLabel());
		classificationLtrVerify.setSuperfamily("testSuperFamily");
		classificationLtrVerify.setFamily("TESTFAMILY");
		RepeatsClassification classificationLtr = RepeatsClassification
				.generate(name, familyDetails);
		assertEquals(classificationLtrVerify, classificationLtr);

		name = "LINE";
		RepeatsClassification classificationLineVerify = new RepeatsClassification();
		classificationLineVerify.setRepClass("I");
		classificationLineVerify.setSubclass("I");
		classificationLineVerify.setOrder(RepeatsOrder.LINE.getLabel());
		classificationLineVerify.setSuperfamily("NA");
		classificationLineVerify.setFamily("NA");
		RepeatsClassification classificationLine = RepeatsClassification
				.generate(name, "");
		assertEquals(classificationLineVerify, classificationLine);
		classificationLineVerify.setSuperfamily("testSuperFamily");
		classificationLineVerify.setFamily("TESTFAMILY");
		classificationLine = RepeatsClassification
				.generate(name, familyDetails);
		assertEquals(classificationLineVerify, classificationLine);

		String[] dnaTeNames = { "DNATE", "DNA_TE",
				"terminal_inverted_repeat_element" };
		for (String dnaTeName : dnaTeNames) {
			RepeatsClassification classificationDnaTeVerify = new RepeatsClassification();
			classificationDnaTeVerify.setRepClass("II");
			classificationDnaTeVerify.setSubclass("I");
			classificationDnaTeVerify.setOrder(RepeatsOrder.DNATE.getLabel());
			classificationDnaTeVerify.setSuperfamily("testSuperFamily");
			classificationDnaTeVerify.setFamily("TESTFAMILY");
			RepeatsClassification classificationDnaTe = RepeatsClassification
					.generate(dnaTeName, familyDetails);
			assertEquals(classificationDnaTeVerify, classificationDnaTe);
		}

		name = "SINE";
		RepeatsClassification classificationSineVerify = new RepeatsClassification();
		classificationSineVerify.setRepClass("I");
		classificationSineVerify.setSubclass("I");
		classificationSineVerify.setOrder(RepeatsOrder.SINE.getLabel());
		classificationSineVerify.setSuperfamily("NA");
		classificationSineVerify.setFamily("NA");
		RepeatsClassification classificationSine = RepeatsClassification
				.generate(name, "");
		assertEquals(classificationSineVerify, classificationSine);
		classificationSineVerify.setSuperfamily("testSuperFamily");
		classificationSineVerify.setFamily("TESTFAMILY");
		classificationSine = RepeatsClassification
				.generate(name, familyDetails);
		assertEquals(classificationSineVerify, classificationSine);

		name = "MITE";
		RepeatsClassification classificationMiteVerify = new RepeatsClassification();
		classificationMiteVerify.setRepClass("II");
		classificationMiteVerify.setSubclass("III");
		classificationMiteVerify.setOrder(RepeatsOrder.MITE.getLabel());
		classificationMiteVerify.setSuperfamily("NA");
		classificationMiteVerify.setFamily("NA");
		RepeatsClassification classificationMite = RepeatsClassification
				.generate(name, "");
		assertEquals(classificationMiteVerify, classificationMite);
		classificationMiteVerify.setSuperfamily("testSuperFamily");
		classificationMiteVerify.setFamily("TESTFAMILY");
		classificationMite = RepeatsClassification
				.generate(name, familyDetails);
		assertEquals(classificationMiteVerify, classificationMite);

		String[] unknNames = { "U", "unclassified", "unknown",
				"dispersed_repeat" };
		for (String unknName : unknNames) {
			RepeatsClassification classificationUnkVerify = new RepeatsClassification();
			classificationUnkVerify.setRepClass("UNKNOWN");
			classificationUnkVerify.setSubclass("UNKNOWN");
			classificationUnkVerify.setOrder(RepeatsOrder.UNKN.getLabel());
			classificationUnkVerify.setSuperfamily("UNKNOWN");
			classificationUnkVerify.setFamily("UNKNOWN");
			RepeatsClassification classificationUnk = RepeatsClassification
					.generate(unknName, "");
			assertEquals(classificationUnkVerify, classificationUnk);
		}

	}

	@Test
	public void testGET_REPEAT_ORDER() throws RepeatsClassificationException {
		RepeatsOrder repOrderLine = RepeatsClassification
				.GET_REPEAT_ORDER("I, I, LINE");
		assertEquals(RepeatsOrder.LINE, repOrderLine);
		RepeatsOrder repOrderHeli = RepeatsClassification
				.GET_REPEAT_ORDER("II, II, Helitron");
		assertEquals(RepeatsOrder.HEL, repOrderHeli);
		RepeatsOrder repOrderDnate = RepeatsClassification
				.GET_REPEAT_ORDER("II, I, DNA_TE");
		assertEquals(RepeatsOrder.DNATE, repOrderDnate);
		RepeatsOrder repOrderUnkn = RepeatsClassification
				.GET_REPEAT_ORDER("UNKNOWN, UNKNOWN, UNKNOWN");
		assertEquals(RepeatsOrder.UNKN, repOrderUnkn);
		RepeatsOrder repOrderSine = RepeatsClassification
				.GET_REPEAT_ORDER("I, I, SINE");
		assertEquals(RepeatsOrder.SINE, repOrderSine);
	}

	@Test
	public void testValidation() throws Exception {
		String[] repClassDefinitions = { "I, I, LINE, test, test",
				"II, II, Helitron, test, test", "II, III, MITE, test, test",
				"II, I, DNA_TE, test, test", "I, I, LTR, test, test",
				"UNKNOWN, UNKNOWN, UNKNOWN, test, test",
				"I, I, SINE, test, test" };
		for (String repClassDefinition : repClassDefinitions) {
			RepeatsClassification repeatsClassification = RepeatsClassificationOM
					.Generate(repClassDefinition);
			repeatsClassification.validate();
		}
	}
}
