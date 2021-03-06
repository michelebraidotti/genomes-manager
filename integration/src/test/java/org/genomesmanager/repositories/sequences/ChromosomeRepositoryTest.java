package org.genomesmanager.repositories.sequences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.testobjectgenerators.ChromosomesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
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
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Chromosome chrPost = chromosomeRepo.findOne(chr.getId());
		assertEquals(chr, chrPost);
	}
	
	@Test
	public void testFind() {
		int nOfChrs = 3;
		int nOfChrsPre = chromosomeRepo.findAll().size();
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		for ( Chromosome chr: ChromosomesTestObjectGenerator.Generate(nOfChrs, sp) ) {
			chromosomeRepo.save(chr);
		}
		assertEquals(nOfChrsPre + nOfChrs, chromosomeRepo.findAll().size());
		assertEquals(nOfChrs, chromosomeRepo.findBySpecies(sp).size());
	}

	@Test
	public void testFindByChromosomeNumberAndSpecies() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Chromosome chrPost = chromosomeRepo.findByChromosomeNumberAndSpecies(chr.getNumber(), sp);
		assertEquals(chr, chrPost);
	}
}
