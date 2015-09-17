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
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepository;
import org.genomesmanager.repositories.repeats.RepeatsList;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepeatsListTest extends AbstractIntegrationTest {
	@Autowired
	private RepeatsClassificationRepository repeatsClassificationRepo;
	@Autowired
	private RepeatRepo repeatRepo;
	@Autowired
	private SpeciesRepository speciesRepository;
	@Autowired
	private ChromosomeRepository chromosomeRepository;
	@Autowired
	private SequenceRepository sequenceRepository;
	@Autowired
	private RepeatsList repeatsList;

	@Test
	public void test() throws Exception {
		int nOfRepeats = 7;
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepository.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chromosomeRepository.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		sequenceRepository.save(seq);
		String repClassDefinition = "I, I, LTR, test, test";

		RepeatsClassification repClass = RepeatsClassificationOM
				.Generate(repClassDefinition);
		repeatsClassificationRepo.save(repClass);

		for (LtrRepeat ltr : RepeatsOM.GenerateLtrs(nOfRepeats, repClass, seq)) {
			repeatRepo.insert(ltr);
		}
		assertEquals(nOfRepeats, repeatsList.getAllBySequence(seq.getId())
				.size());
	}
}
