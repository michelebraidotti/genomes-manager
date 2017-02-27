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

public class RepeatsStatsRepoTest extends AbstractIntegrationTest {
	@Autowired
	private RepeatsClassificationRepository repeatsClassificationRepo;
	@Autowired
	private RepeatRepository repeatRepo;
	@Autowired
	private SpeciesRepository speciesRepository;
	@Autowired
	private ChromosomeRepository chromosomeRepository;
	@Autowired
	private SequenceRepository sequenceRepository;
	@Autowired
	private RepeatsStatsRepository repeatsStatsRepo;

	@Test
	public void test() throws Exception {
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
			l = repeatRepo.save(l);
			repeats.add(l);
		}

		/* 2. Helitron */
		repClassDefinition = repClassDefinitions[1];
		repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (HelitronRepeat h : RepeatsTestObjectGenerator.GenerateHelitrons(nOfRepeats,
				repClass, seq)) {
			h = repeatRepo.save(h);
			repeats.add(h);
		}

		/* 3. Mite */
		repClassDefinition = repClassDefinitions[2];
		repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (MiteRepeat m : RepeatsTestObjectGenerator.GenerateMites(nOfRepeats, repClass, seq)) {
			m = repeatRepo.save(m);
			repeats.add(m);
		}

		/* 3. DNATE */
		repClassDefinition = repClassDefinitions[3];
		repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (DnaTeRepeat dnate : RepeatsTestObjectGenerator.GenerateDnaTes(nOfRepeats, repClass,
				seq)) {
			dnate = repeatRepo.save(dnate);
			repeats.add(dnate);
		}

		/* 4. LTR */
		repClassDefinition = repClassDefinitions[4];
		repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (LtrRepeat ltr : RepeatsTestObjectGenerator.GenerateLtrs(nOfRepeats, repClass, seq)) {
			ltr = repeatRepo.save(ltr);
			repeats.add(ltr);
		}

		/* 5. UNKN */
		repClassDefinition = repClassDefinitions[5];
		repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (UnknownRepeat unkn : RepeatsTestObjectGenerator.GenerateUnknowns(nOfRepeats,
				repClass, seq)) {
			unkn = repeatRepo.save(unkn);
			repeats.add(unkn);
		}

		/* 6. Sine */
		repClassDefinition = repClassDefinitions[6];
		repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);
		for (SineRepeat sine : RepeatsTestObjectGenerator.GenerateSines(nOfRepeats, repClass,
				seq)) {
			sine = repeatRepo.save(sine);
			repeats.add(sine);
		}

		System.out.println("countAllCompleteHelitrons  "
				+ repeatsStatsRepo.countAllCompleteHelitrons());
		System.out.println("countAllCompleteHelitronsNucleotides  "
				+ repeatsStatsRepo.countAllCompleteHelitronsNucleotides());
		System.out.println("countAllDnaTeNucleotides  "
				+ repeatsStatsRepo.countAllDnaTeNucleotides());
		System.out.println("countAllDnaTes  "
				+ repeatsStatsRepo.countAllDnaTes());
		System.out
				.println("countAllLines  " + repeatsStatsRepo.countAllLines());
		System.out.println("countAllLinesNucleotides  "
				+ repeatsStatsRepo.countAllLinesNucleotides());
		System.out.println("countAllLtrNucleotides  "
				+ repeatsStatsRepo.countAllLtrNucleotides());
		System.out.println("countAllLtrs  " + repeatsStatsRepo.countAllLtrs());
		System.out
				.println("countAllMites  " + repeatsStatsRepo.countAllMites());
		System.out.println("countAllMitesNucleotides  "
				+ repeatsStatsRepo.countAllMitesNucleotides());
		System.out.println("countAllPartialHelitrons  "
				+ repeatsStatsRepo.countAllPartialHelitrons());
		System.out.println("countAllPartialHelitronsNucleotides  "
				+ repeatsStatsRepo.countAllPartialHelitronsNucleotides());
		System.out
				.println("countAllSines  " + repeatsStatsRepo.countAllSines());
		System.out.println("countAllSinesNucleotides  "
				+ repeatsStatsRepo.countAllSinesNucleotides());
		System.out
				.println("countAllUnkns  " + repeatsStatsRepo.countAllUnkns());
		System.out.println("countCompleteLtrs  "
				+ repeatsStatsRepo.countCompleteLtrs());
		System.out.println("countOrfCountHelitrons  "
				+ repeatsStatsRepo.countOrfCountHelitrons());
		System.out.println("countPotAutonHelitrons  "
				+ repeatsStatsRepo.countPotAutonHelitrons());
		System.out.println("countPotCdsCountHelitrons  "
				+ repeatsStatsRepo.countPotCdsCountHelitrons());
		System.out.println("countRepeatsAndBases  "
				+ repeatsStatsRepo.countRepeatsAndBases().size());
		System.out.println("countRepeatsBasesByChromosome  "
				+ repeatsStatsRepo.countRepeatsBasesByChromosome(chr.getId())
						.size());
		System.out
				.println("countSoloLtrs  " + repeatsStatsRepo.countSoloLtrs());
		System.out.println("countTruncatedLtrs  "
				+ repeatsStatsRepo.countTruncatedLtrs());
		Long nOfRepeatsCheck = new Long(nOfRepeats);
		assertEquals(nOfRepeatsCheck,
				repeatsStatsRepo.countAllCompleteHelitrons());
		assertTrue(repeatsStatsRepo.countAllCompleteHelitronsNucleotides() > 0);
		assertTrue(repeatsStatsRepo.countAllDnaTeNucleotides() > 0);
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countAllDnaTes());
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countAllLines());
		assertTrue(repeatsStatsRepo.countAllLinesNucleotides() > 0);
		assertTrue(repeatsStatsRepo.countAllLtrNucleotides() > 0);
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countAllLtrs());
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countAllMites());
		assertTrue(repeatsStatsRepo.countAllMitesNucleotides() > 0);
		assertEquals(new Long(0), repeatsStatsRepo.countAllPartialHelitrons());
		assertEquals(new Long(0),
				repeatsStatsRepo.countAllPartialHelitronsNucleotides());
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countAllSines());
		assertTrue(repeatsStatsRepo.countAllSinesNucleotides() > 0);
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countAllUnkns());
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countCompleteLtrs());
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countOrfCountHelitrons());
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countPotAutonHelitrons());
		assertTrue(repeatsStatsRepo.countPotCdsCountHelitrons() > 0);
		assertTrue(repeatsStatsRepo.countRepeatsAndBases().size() > 0);
		assertTrue(repeatsStatsRepo.countRepeatsBasesByChromosome(chr.getId())
				.size() > 0);
		assertEquals(new Long(0), repeatsStatsRepo.countSoloLtrs());
		assertEquals(new Long(0), repeatsStatsRepo.countTruncatedLtrs());

	}
}
