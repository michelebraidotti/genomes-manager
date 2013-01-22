package org.genomesmanager.repositories.jpa.sequences;

import static org.junit.Assert.*;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeList;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.ChromosomeRepoException;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ChromosomesListTest extends AbstractIntegrationTest {
	@Autowired
	private SpeciesRepo speciesRepo;
	@Autowired
	private ChromosomeRepo chromosomeRepo;
	@Autowired
	private ChromosomeList chromosomeList;
	
	@Test
	public void test() throws SpeciesRepoException, ChromosomeRepoException {
		int nOfChrs = 3;
		int nOfChrsPre = chromosomeList.getAll().size();
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.insert(sp);
		for ( Chromosome chr:ChromosomesOM.Generate(nOfChrs, sp) ) {
			chromosomeRepo.insert(chr);
		}
		assertEquals(nOfChrsPre + nOfChrs, chromosomeList.getAll().size());
	}
	
}
