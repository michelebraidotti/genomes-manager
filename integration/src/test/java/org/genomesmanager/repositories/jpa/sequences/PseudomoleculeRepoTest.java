package org.genomesmanager.repositories.jpa.sequences;

import static org.junit.Assert.*;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.ChromosomeRepoException;
import org.genomesmanager.repositories.sequences.PseudomoleculeRepo;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PseudomoleculeRepoTest extends AbstractIntegrationTest {
	@Autowired
	private SpeciesRepo speciesRepo;
	@Autowired
	private ChromosomeRepo chromosomeRepo;
	@Autowired
	private SequenceRepo sequenceRepo;
	@Autowired
	private PseudomoleculeRepo pseudomoleculeRepo;
	
	@Test
	public void testCreatePseudomolFromScaffolds() throws SpeciesRepoException, ChromosomeRepoException, SequenceRepoException, SequenceSliceException {
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.insert(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chromosomeRepo.insert(chr);
		int i = 1;
		for (Scaffold scaffold:SequencesOM.GenerateScaffold(5, chr)) {
			scaffold.setOrder(i++);
			scaffold.setIsUnplaced(false);
			sequenceRepo.insert(scaffold);
		}
		for (Scaffold scaffold:SequencesOM.GenerateScaffold(4, chr)) {
			scaffold.setOrder(0);
			scaffold.setIsUnplaced(true);
			sequenceRepo.insert(scaffold);
		}
		StringBuilder sb = pseudomoleculeRepo.getFromChromosome(chr.getId(), false);
		assertTrue(sb.length() > 0);
		StringBuilder sbUn = pseudomoleculeRepo.getFromChromosomeUnplaced(chr.getId(), false);
		assertTrue(sbUn.length() > 0);
	}
	
}
