package org.genomesmanager.repositories.repeats;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
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
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		sp = speciesRepository.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepository.save(chr);
		Sequence seq = SequencesTestObjectGenerator.Generate(1, chr).get(0);
		seq = sequenceRepository.save(seq);

		String repClassDefinition = "I, I, LTR, test, test";
		RepeatsClassification repClass = RepeatsClassificationTestObjectGenerator
				.Generate(repClassDefinition);
		repClass = repeatsClassificationRepo.save(repClass);

		for (LtrRepeat ltr : RepeatsTestObjectGenerator.GenerateLtrs(nOfRepeats, repClass, seq)) {
			repeatRepo.save(ltr);
		}
		assertEquals(nOfRepeats, repeatRepo.findAllRepeatsBySequence(seq.getId()).size());
	}
}
