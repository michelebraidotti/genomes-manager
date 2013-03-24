package org.genomesmanager.repositories.jpa.repeats;

import static org.junit.Assert.*;

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
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.RepeatsClassificationOM;
import org.genomesmanager.domain.entities.objectmothers.RepeatsOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.repeats.RepeatRepo;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepo;
import org.genomesmanager.repositories.repeats.RepeatsStatsRepo;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepeatsStatsRepoTest extends AbstractIntegrationTest {
	@Autowired
	private RepeatsClassificationRepo repeatsClassificationRepo;
	@Autowired
	private RepeatRepo repeatRepo;
	@Autowired
	private SpeciesRepo speciesRepo;
	@Autowired
	private ChromosomeRepo chromosomeRepo;
	@Autowired
	private SequenceRepo sequenceRepo;
	@Autowired
	private RepeatsStatsRepo repeatsStatsRepo;
	

	@Test
	public void test() throws Exception {
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.insert(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chromosomeRepo.insert(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq.setSequenceText(SequencesOM.GenererateSequence(1000).toString());
		seq.setLength(1000);
		sequenceRepo.insert(seq);
		String [] repClassDefinitions = {
				 "I, I, LINE, test, test",
				 "II, II, Helitron, test, test",
				 "II, III, MITE, test, test",
				 "II, I, DNA_TE, test, test",
				 "I, I, LTR, test, test",
				 "UNKNOWN, UNKNOWN, UNKNOWN, test, test",
				 "I, I, SINE, test, test"
		};
		int nOfRepeats = 7;
		List<Repeat> repeats = new ArrayList<Repeat>();
		/* 1. LINE */
		String repClassDefinition = repClassDefinitions[0];
		RepeatsClassification repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		for (LineRepeat l:RepeatsOM.GenerateLines(nOfRepeats, repClass, seq)) {
			repeatRepo.insert(l);
			repeats.add(l);
		}
		
		/* 2. Helitron */
		repClassDefinition = repClassDefinitions[1];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		for (HelitronRepeat h:RepeatsOM.GenerateHelitrons(nOfRepeats, repClass, seq)) {
			repeatRepo.insert(h);
			repeats.add(h);
		}
		
		/* 3. Mite */
		repClassDefinition = repClassDefinitions[2];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		for (MiteRepeat m:RepeatsOM.GenerateMites(nOfRepeats, repClass, seq)) {
			repeatRepo.insert(m);
			repeats.add(m);
		}

		/* 3. DNATE */
		repClassDefinition = repClassDefinitions[3];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		for (DnaTeRepeat dnate:RepeatsOM.GenerateDnaTes(nOfRepeats, repClass, seq)) {
			repeatRepo.insert(dnate);
			repeats.add(dnate);
		}
		
		/* 4. LTR */
		repClassDefinition = repClassDefinitions[4];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		for (LtrRepeat ltr:RepeatsOM.GenerateLtrs(nOfRepeats, repClass, seq)) {
			repeatRepo.insert(ltr);
			repeats.add(ltr);
		}

		/* 5. UNKN */
		repClassDefinition = repClassDefinitions[5];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		for (UnknownRepeat unkn:RepeatsOM.GenerateUnknowns(nOfRepeats, repClass, seq)) {
			repeatRepo.insert(unkn);
			repeats.add(unkn);
		}

		/* 6. Sine */
		repClassDefinition = repClassDefinitions[6];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		for (SineRepeat sine:RepeatsOM.GenerateSines(nOfRepeats, repClass, seq)) {
			repeatRepo.insert(sine);
			repeats.add(sine);
		}

		System.out.println("countAllCompleteHelitrons  " + repeatsStatsRepo.countAllCompleteHelitrons());
		System.out.println("countAllCompleteHelitronsNucleotides  " + repeatsStatsRepo.countAllCompleteHelitronsNucleotides());
		System.out.println("countAllDnaTeNucleotides  " + repeatsStatsRepo.countAllDnaTeNucleotides());
		System.out.println("countAllDnaTes  " + repeatsStatsRepo.countAllDnaTes());
		System.out.println("countAllLines  " + repeatsStatsRepo.countAllLines());
		System.out.println("countAllLinesNucleotides  " + repeatsStatsRepo.countAllLinesNucleotides());
		System.out.println("countAllLtrNucleotides  " + repeatsStatsRepo.countAllLtrNucleotides());
		System.out.println("countAllLtrs  " + repeatsStatsRepo.countAllLtrs());
		System.out.println("countAllMites  " + repeatsStatsRepo.countAllMites());
		System.out.println("countAllMitesNucleotides  " + repeatsStatsRepo.countAllMitesNucleotides());
		System.out.println("countAllPartialHelitrons  " + repeatsStatsRepo.countAllPartialHelitrons());
		System.out.println("countAllPartialHelitronsNucleotides  " + repeatsStatsRepo.countAllPartialHelitronsNucleotides());
		System.out.println("countAllSines  " + repeatsStatsRepo.countAllSines());
		System.out.println("countAllSinesNucleotides  " + repeatsStatsRepo.countAllSinesNucleotides());
		System.out.println("countAllUnkns  " + repeatsStatsRepo.countAllUnkns());
		System.out.println("countCompleteLtrs  " + repeatsStatsRepo.countCompleteLtrs());
		System.out.println("countOrfCountHelitrons  " + repeatsStatsRepo.countOrfCountHelitrons());
		System.out.println("countPotAutonHelitrons  " + repeatsStatsRepo.countPotAutonHelitrons());
		System.out.println("countPotCdsCountHelitrons  " + repeatsStatsRepo.countPotCdsCountHelitrons());
		System.out.println("countRepeatsAndBases  " + repeatsStatsRepo.countRepeatsAndBases().size());
		System.out.println("countRepeatsBasesByChromosome  " + repeatsStatsRepo.countRepeatsBasesByChromosome(chr.getId()).size());
		System.out.println("countSoloLtrs  " + repeatsStatsRepo.countSoloLtrs());
		System.out.println("countTruncatedLtrs  " + repeatsStatsRepo.countTruncatedLtrs());
		Long nOfRepeatsCheck = new Long(nOfRepeats);
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countAllCompleteHelitrons());
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
		assertEquals(new Long(0), repeatsStatsRepo.countAllPartialHelitronsNucleotides());
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countAllSines());
		assertTrue(repeatsStatsRepo.countAllSinesNucleotides() > 0);
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countAllUnkns());
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countCompleteLtrs());
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countOrfCountHelitrons());
		assertEquals(nOfRepeatsCheck, repeatsStatsRepo.countPotAutonHelitrons());
		assertTrue(repeatsStatsRepo.countPotCdsCountHelitrons() > 0);
		assertTrue(repeatsStatsRepo.countRepeatsAndBases().size() > 0);
		assertTrue(repeatsStatsRepo.countRepeatsBasesByChromosome(chr.getId()).size() > 0);
		assertEquals(new Long(0), repeatsStatsRepo.countSoloLtrs());
		assertEquals(new Long(0), repeatsStatsRepo.countTruncatedLtrs());
		
	}
}

