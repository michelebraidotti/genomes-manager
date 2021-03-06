package org.genomesmanager.repositories.species;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.Variety;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.VarietiesTestObjectGenerator;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class VarietyRepoTest extends AbstractIntegrationTest {
	@Autowired
	private VarietyRepository varietyRepo;
	@Autowired
	private SpeciesRepository speciesRepo;
	
	@Test
	public void test()  {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Variety variety = VarietiesTestObjectGenerator.Generate(1, sp).get(0);
		varietyRepo.save(variety);
		Variety varPost = varietyRepo.findByName(variety.getName()).get(0);
		assertEquals(variety, varPost);
	}
}
