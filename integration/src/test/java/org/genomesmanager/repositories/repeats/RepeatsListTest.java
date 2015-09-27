package org.genomesmanager.repositories.repeats;

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
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.repeats.RepeatRepository;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepository;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepeatsListTest extends AbstractIntegrationTest {
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

	@Test
	public void test() throws Exception {
		int nOfRepeats = 7;
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepository.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepository.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq = sequenceRepository.save(seq);

		String repClassDefinition = "I, I, LTR, test, test";
		RepeatsClassification repClass = RepeatsClassificationOM
				.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);

		for (LtrRepeat ltr : RepeatsOM.GenerateLtrs(nOfRepeats, repClass, seq)) {
			repeatRepo.save(ltr);
		}
		assertEquals(nOfRepeats, repeatRepo.findAllRepeatsBySequence(seq.getId()).size());
	}
}
