package org.genomesmanager.repositories.jpa.sequences;

import static org.junit.Assert.*;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Pseudomolecule;
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
		
		List<Pseudomolecule> pseudomolecules = SequencesOM.GeneratePseudomolecule(3, chr); 
		for (Pseudomolecule p:pseudomolecules) {
			sequenceRepo.insert(p);
		}
		
		for (Pseudomolecule p:pseudomolecules) {
			Pseudomolecule pPost = pseudomoleculeRepo.get(p.getId());
			System.out.println("Name: " + p.getName() + ", Sequence: " + p.getSequenceText());
			assertEquals(p, pPost);
		}
		
//		StringBuilder sb = pseudomoleculeRepo.getFromChromosome(chr.getId(), false);
//		assertTrue(sb.length() > 0);
//		StringBuilder sbUn = pseudomoleculeRepo.getFromChromosomeUnplaced(chr.getId(), false);
//		assertTrue(sbUn.length() > 0);
	}
	
}
