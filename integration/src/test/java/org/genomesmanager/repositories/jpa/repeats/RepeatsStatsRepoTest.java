package org.genomesmanager.repositories.jpa.repeats;

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
		seq.setSequence(SequencesOM.GenererateSequence(1000).toString());
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
		
		repeatsStatsRepo.countAllCompleteHelitrons();
		repeatsStatsRepo.countAllCompleteHelitronsNucleotides();
		repeatsStatsRepo.countAllDnaTeNucleotides();
		repeatsStatsRepo.countAllDnaTes();
		repeatsStatsRepo.countAllLines();
		repeatsStatsRepo.countAllLinesNucleotides();
		repeatsStatsRepo.countAllLtrNucleotides();
		repeatsStatsRepo.countAllLtrs();
		repeatsStatsRepo.countAllMites();
		repeatsStatsRepo.countAllMitesNucleotides();
		repeatsStatsRepo.countAllPartialHelitrons();
		repeatsStatsRepo.countAllPartialHelitronsNucleotides();
		repeatsStatsRepo.countAllSines();
		repeatsStatsRepo.countAllSinesNucleotides();
		repeatsStatsRepo.countAllUnkns();
		repeatsStatsRepo.countCompleteLtrs();
		repeatsStatsRepo.countOrfCountHelitrons();
		repeatsStatsRepo.countPotAutonHelitrons();
		repeatsStatsRepo.countPotCdsCountHelitrons();
		repeatsStatsRepo.countRepeatsAndBases();
		repeatsStatsRepo.countRepeatsBasesByChromosome(chr.getId());
		repeatsStatsRepo.countSoloLtrs();
		repeatsStatsRepo.countTruncatedLtrs();
	}
}

