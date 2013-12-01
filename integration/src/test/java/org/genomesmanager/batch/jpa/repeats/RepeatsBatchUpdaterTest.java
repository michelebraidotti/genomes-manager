package org.genomesmanager.batch.jpa.repeats;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.batch.repeats.RepeatsBatchUpdater;
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
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepeatsBatchUpdaterTest extends AbstractIntegrationTest {
	@Autowired
	private RepeatsClassificationRepository repeatsClassificationRepo;
	@Autowired
	private RepeatRepo repeatRepo;
	@Autowired
	private SpeciesRepository speciesRepo;
	@Autowired
	private ChromosomeRepository chromosomeRepo;
	@Autowired
	private SequenceRepository sequenceRepo;
	@Autowired
	private RepeatsBatchUpdater repeatsBatchUpdater;
	
	@Test
	public void test() throws Exception {
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq = sequenceRepo.save(seq);
		RepeatsClassification repClass = RepeatsClassificationOM.Generate("I, I, LTR, test, test");
		repeatsClassificationRepo.insert(repClass);
		LtrRepeat parentLtr = RepeatsOM.GenerateLtrs(1, repClass, seq).get(0);
		repeatRepo.insert(parentLtr);
		LtrRepeat nestedLtr = RepeatsOM.GenerateLtrs(1, repClass, seq).get(0);
		nestedLtr.setX(parentLtr.getX() + 1);
		nestedLtr.setY(parentLtr.getY() - 1);
		nestedLtr.setPbsX(nestedLtr.getX() + 1);
		nestedLtr.setPbsY(nestedLtr.getY() - 1);
		nestedLtr.setPptX(nestedLtr.getX() + 1);
		nestedLtr.setPptY(nestedLtr.getY() - 1);
		repeatRepo.insert(nestedLtr);
		
		repeatsBatchUpdater.updateRepatsParent();
		assertEquals(nestedLtr.getParent(), parentLtr);
	}
}
