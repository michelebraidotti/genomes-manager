package org.genomesmanager.repositories.jpa.repeats;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.RepeatsClassificationOM;
import org.genomesmanager.domain.entities.objectmothers.RepeatsOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.repeats.RepeatRepo;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepo;
import org.genomesmanager.repositories.repeats.RepeatsList;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepeatsListTest extends AbstractIntegrationTest {
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
	private RepeatsList repeatsList;
	

	@Test
	public void test() throws Exception {
		int nOfRepeats = 7;
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.insert(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chromosomeRepo.insert(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		sequenceRepo.insert(seq);
		String repClassDefinition = "I, I, LTR, test, test";
		
		RepeatsClassification repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeatsClassificationRepo.insert(repClass);
		
		for (LtrRepeat ltr:RepeatsOM.GenerateLtrs(nOfRepeats, repClass, seq) ) {
			repeatRepo.insert(ltr);
		}
		assertEquals(nOfRepeats, repeatsList.getAllBySequence(seq.getId()).size());
	}
}

