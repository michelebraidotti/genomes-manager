package org.genomesmanager.repositories.repeats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.DnaTeRepeat;
import org.genomesmanager.domain.entities.HelitronRepeat;
import org.genomesmanager.domain.entities.LineRepeat;
import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.MiteRepeat;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.SineRepeat;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.UnknownRepeat;
import org.genomesmanager.domain.entities.testobjectgenerators.ChromosomesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.RepeatsClassificationTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.RepeatsTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SequencesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepeatsStatsTest extends AbstractIntegrationTest {
	@Autowired
	private RepeatsClassificationRepository repeatsClassificationRepo;
	@Autowired
	private RepeatRepository repeatRepository;
	@Autowired
	private SpeciesRepository speciesRepository;
	@Autowired
	private ChromosomeRepository chromosomeRepository;
	@Autowired
	private SequenceRepository sequenceRepository;
	@Autowired
	private RepeatsStats repeatsStats;

	@Test
	public void testCounts() throws Exception {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		sp = speciesRepository.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepository.save(chr);
		Sequence seq = SequencesTestObjectGenerator.Generate(1, chr).get(0);
		seq.setSequenceText(SequencesTestObjectGenerator.GenererateSequence(1000).toString());
		seq.setLength(1000);
		seq = sequenceRepository.save(seq);
		String[] repClassDefinitions = { "I, I, LINE, test, test",
				"II, II, Helitron, test, test", "II, III, MITE, test, test",
				"II, I, DNA_TE, test, test", "I, I, LTR, test, test",
				"UNKNOWN, UNKNOWN, UNKNOWN, test, test",
				"I, I, SINE, test, test" };
		int nOfRepeats = 7;
		List<Repeat> repeats = new ArrayList<Repeat>();
		/* 1. LINE */
		String repClassDefinition = repClassDefinitions[0];
		RepeatsClassification repClass = RepeatsClassificationTestObjectGenerator
				.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (LineRepeat l : RepeatsTestObjectGenerator.GenerateLines(nOfRepeats, repClass, seq)) {
			l = repeatRepository.save(l);
			repeats.add(l);
		}

		/* 2. Helitron */
		repClassDefinition = repClassDefinitions[1];
		repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (HelitronRepeat h : RepeatsTestObjectGenerator.GenerateHelitrons(nOfRepeats,
				repClass, seq)) {
			h = repeatRepository.save(h);
			repeats.add(h);
		}

		/* 3. Mite */
		repClassDefinition = repClassDefinitions[2];
		repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (MiteRepeat m : RepeatsTestObjectGenerator.GenerateMites(nOfRepeats, repClass, seq)) {
			m = repeatRepository.save(m);
			repeats.add(m);
		}

		/* 3. DNATE */
		repClassDefinition = repClassDefinitions[3];
		repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (DnaTeRepeat dnate : RepeatsTestObjectGenerator.GenerateDnaTes(nOfRepeats, repClass,
				seq)) {
			dnate = repeatRepository.save(dnate);
			repeats.add(dnate);
		}

		/* 4. LTR */
		repClassDefinition = repClassDefinitions[4];
		repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (LtrRepeat ltr : RepeatsTestObjectGenerator.GenerateLtrs(nOfRepeats, repClass, seq)) {
			ltr = repeatRepository.save(ltr);
			repeats.add(ltr);
		}

		/* 5. UNKN */
		repClassDefinition = repClassDefinitions[5];
		repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (UnknownRepeat unkn : RepeatsTestObjectGenerator.GenerateUnknowns(nOfRepeats,
				repClass, seq)) {
			unkn = repeatRepository.save(unkn);
			repeats.add(unkn);
		}

		/* 6. Sine */
		repClassDefinition = repClassDefinitions[6];
		repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (SineRepeat sine : RepeatsTestObjectGenerator.GenerateSines(nOfRepeats, repClass,
				seq)) {
			sine = repeatRepository.save(sine);
			repeats.add(sine);
		}

		System.out.println("countAllCompleteHelitrons  "
				+ repeatsStats.countAllCompleteHelitrons());
		System.out.println("countAllCompleteHelitronsNucleotides  "
				+ repeatsStats.countAllCompleteHelitronsNucleotides());
		System.out.println("countAllDnaTeNucleotides  "
				+ repeatsStats.countAllDnaTeNucleotides());
		System.out.println("countAllDnaTes  "
				+ repeatsStats.countAllDnaTes());
		System.out
				.println("countAllLines  " + repeatsStats.countAllLines());
		System.out.println("countAllLinesNucleotides  "
				+ repeatsStats.countAllLinesNucleotides());
		System.out.println("countAllLtrNucleotides  "
				+ repeatsStats.countAllLtrNucleotides());
		System.out.println("countAllLtrs  " + repeatsStats.countAllLtrs());
		System.out
				.println("countAllMites  " + repeatsStats.countAllMites());
		System.out.println("countAllMitesNucleotides  "
				+ repeatsStats.countAllMitesNucleotides());
		System.out.println("countAllPartialHelitrons  "
				+ repeatsStats.countAllPartialHelitrons());
		System.out.println("countAllPartialHelitronsNucleotides  "
				+ repeatsStats.countAllPartialHelitronsNucleotides());
		System.out
				.println("countAllSines  " + repeatsStats.countAllSines());
		System.out.println("countAllSinesNucleotides  "
				+ repeatsStats.countAllSinesNucleotides());
		System.out
				.println("countAllUnkns  " + repeatsStats.countAllUnkns());
		System.out.println("countCompleteLtrs  "
				+ repeatsStats.countCompleteLtrs());
		System.out.println("countOrfCountHelitrons  "
				+ repeatsStats.countOrfCountHelitrons());
		System.out.println("countPotAutonHelitrons  "
				+ repeatsStats.countPotAutonHelitrons());
		System.out.println("countPotCdsCountHelitrons  "
				+ repeatsStats.countPotCdsCountHelitrons());
		System.out.println("countRepeatsAndBases  "
				+ repeatsStats.countRepeatsAndBases().size());
		System.out.println("countRepeatsBasesByChromosome  "
				+ repeatsStats.countRepeatsBasesByChromosome(chr.getId())
						.size());
		System.out
				.println("countSoloLtrs  " + repeatsStats.countSoloLtrs());
		System.out.println("countTruncatedLtrs  "
				+ repeatsStats.countTruncatedLtrs());
		Long nOfRepeatsCheck = new Long(nOfRepeats);
		assertEquals(nOfRepeatsCheck,
				repeatsStats.countAllCompleteHelitrons());
		assertTrue(repeatsStats.countAllCompleteHelitronsNucleotides() > 0);
		assertTrue(repeatsStats.countAllDnaTeNucleotides() > 0);
		assertEquals(nOfRepeatsCheck, repeatsStats.countAllDnaTes());
		assertEquals(nOfRepeatsCheck, repeatsStats.countAllLines());
		assertTrue(repeatsStats.countAllLinesNucleotides() > 0);
		assertTrue(repeatsStats.countAllLtrNucleotides() > 0);
		assertEquals(nOfRepeatsCheck, repeatsStats.countAllLtrs());
		assertEquals(nOfRepeatsCheck, repeatsStats.countAllMites());
		assertTrue(repeatsStats.countAllMitesNucleotides() > 0);
		assertEquals(new Long(0), repeatsStats.countAllPartialHelitrons());
		assertEquals(new Long(0),
				repeatsStats.countAllPartialHelitronsNucleotides());
		assertEquals(nOfRepeatsCheck, repeatsStats.countAllSines());
		assertTrue(repeatsStats.countAllSinesNucleotides() > 0);
		assertEquals(nOfRepeatsCheck, repeatsStats.countAllUnkns());
		assertEquals(nOfRepeatsCheck, repeatsStats.countCompleteLtrs());
		assertEquals(nOfRepeatsCheck, repeatsStats.countOrfCountHelitrons());
		assertEquals(nOfRepeatsCheck, repeatsStats.countPotAutonHelitrons());
		assertTrue(repeatsStats.countPotCdsCountHelitrons() > 0);
		assertTrue(repeatsStats.countRepeatsAndBases().size() > 0);
		assertTrue(repeatsStats.countRepeatsBasesByChromosome(chr.getId())
				.size() > 0);
		assertEquals(new Long(0), repeatsStats.countSoloLtrs());
		assertEquals(new Long(0), repeatsStats.countTruncatedLtrs());

	}
}
