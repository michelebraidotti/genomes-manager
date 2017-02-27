package org.genomesmanager.repositories.species;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SpeciesRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private SpeciesRepository speciesRepo;

	@Test
	public void test() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Species spPost = speciesRepo.findOne(sp.getId());
		assertEquals(sp, spPost);
		spPost = speciesRepo.findByGenusAndSpeciesAndSubspecies(sp.getGenus(),sp.getSpecies(), sp.getSubspecies());
		assertEquals(sp, spPost);
	}

}
