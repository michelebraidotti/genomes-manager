package org.genomesmanager.repositories.species;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Individual;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.Variety;
import org.genomesmanager.domain.entities.objectmothers.IndividualsOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.domain.entities.objectmothers.VarietiesOM;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IndividualRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private VarietyRepository varietyRepo;
	@Autowired
	private SpeciesRepository speciesRepo;
	@Autowired
	private IndividualRepository individualRepo;
	
	@Test
	public void test() {
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.save(sp);
		Variety variety = VarietiesOM.Generate(1, sp).get(0);
		varietyRepo.save(variety);
		Individual individual = IndividualsOM.Generate(1, variety).get(0);
		individualRepo.save(individual);
		Individual individualPost = individualRepo.findOne(individual.getId());
		assertEquals(individual, individualPost);
		individualPost = individualRepo.findByDescription(individual.getDescription()).get(0);
		assertEquals(individual, individualPost);
	}
}
