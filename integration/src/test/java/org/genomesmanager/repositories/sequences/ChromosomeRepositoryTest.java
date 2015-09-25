package org.genomesmanager.repositories.sequences;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ChromosomeRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private SpeciesRepository speciesRepo;
	@Autowired
	private ChromosomeRepository chromosomeRepo;
	
	@Test
	public void basicTest() {
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Chromosome chrPost = chromosomeRepo.findOne(chr.getId());
		assertEquals(chr, chrPost);
	}
	
	@Test
	public void testFind() {
		int nOfChrs = 3;
		int nOfChrsPre = chromosomeRepo.findAll().size();
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.save(sp);
		for ( Chromosome chr:ChromosomesOM.Generate(nOfChrs, sp) ) {
			chromosomeRepo.save(chr);
		}
		assertEquals(nOfChrsPre + nOfChrs, chromosomeRepo.findAll().size());
		assertEquals(nOfChrs, chromosomeRepo.findBySpecies(sp).size());
	}	
}
