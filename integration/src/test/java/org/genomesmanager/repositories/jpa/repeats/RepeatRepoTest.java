package org.genomesmanager.repositories.jpa.repeats;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.DnaTeRepeat;
import org.genomesmanager.domain.entities.HelitronRepeat;
import org.genomesmanager.domain.entities.LineRepeat;
import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.MiteRepeat;
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
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepeatRepoTest extends AbstractIntegrationTest {
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
	

	@Test
	public void test() throws Exception {
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.insert(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chromosomeRepo.insert(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
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
		/* 1. LINE */
		String repClassDefinition = repClassDefinitions[0];
		RepeatsClassification repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		LineRepeat line = RepeatsOM.GenerateLines(1, repClass, seq).get(0);
		repeatRepo.insert(line);
		LineRepeat postLine = repeatRepo.getLine(line.getId());
		assertEquals(line,postLine);
		/* 2. Helitron */
		repClassDefinition = repClassDefinitions[1];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		HelitronRepeat hel = RepeatsOM.GenerateHelitrons(1, repClass, seq).get(0);
		repeatRepo.insert(hel);
		HelitronRepeat postHel = repeatRepo.getHelitron(hel.getId());
		assertEquals(hel,postHel);
		/* 3. Mite */
		repClassDefinition = repClassDefinitions[2];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		MiteRepeat mite = RepeatsOM.GenerateMites(1, repClass, seq).get(0);
		repeatRepo.insert(mite);
		MiteRepeat postMite = repeatRepo.getMite(mite.getId());
		assertEquals(mite,postMite);
		/* 3. DNATE */
		repClassDefinition = repClassDefinitions[3];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		DnaTeRepeat dnate = RepeatsOM.GenerateDnaTes(1, repClass, seq).get(0);
		repeatRepo.insert(dnate);
		DnaTeRepeat postDnate = repeatRepo.getDnaTe(dnate.getId());
		assertEquals(dnate,postDnate);
		/* 4. LTR */
		repClassDefinition = repClassDefinitions[4];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		LtrRepeat ltr = RepeatsOM.GenerateLtrs(1, repClass, seq).get(0);
		repeatRepo.insert(ltr);
		LtrRepeat postLtr = repeatRepo.getLtr(ltr.getId());
		assertEquals(ltr,postLtr);
		/* 5. UNKN */
		repClassDefinition = repClassDefinitions[5];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		UnknownRepeat unkn = RepeatsOM.GenerateUnknowns(1, repClass, seq).get(0);
		repeatRepo.insert(unkn);
		UnknownRepeat postUnkn = repeatRepo.getUnkn(unkn.getId());
		assertEquals(unkn, postUnkn);
		/* 6. Sine */
		repClassDefinition = repClassDefinitions[6];
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		SineRepeat sine = RepeatsOM.GenerateSines(1, repClass, seq).get(0);
		repeatRepo.insert(sine);
		SineRepeat postSine = repeatRepo.getSine(sine.getId());
		assertEquals(sine, postSine);
	}
}
