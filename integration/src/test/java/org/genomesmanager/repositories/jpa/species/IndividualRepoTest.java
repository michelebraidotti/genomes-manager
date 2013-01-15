package org.genomesmanager.repositories.jpa.species;

import static org.junit.Assert.*;

import org.genomesmanager.domain.entities.Individual;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.Variety;
import org.genomesmanager.domain.entities.objectmothers.IndividualsOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.domain.entities.objectmothers.VarietiesOM;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.species.IndividualRepo;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.VarietyRepo;
import org.genomesmanager.repositories.species.VarietyRepoException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IndividualRepoTest extends AbstractIntegrationTest {
	@Autowired
	private VarietyRepo varietyRepo;
	@Autowired
	private SpeciesRepo speciesRepo;
	@Autowired
	private IndividualRepo individualRepo;
	
	@Test
	public void test() throws VarietyRepoException {
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.insert(sp);
		Variety variety = VarietiesOM.Generate(1, sp).get(0);
		varietyRepo.insert(variety);
		Individual individual = IndividualsOM.Generate(1, variety).get(0);
		individualRepo.insert(individual);
		Individual individualPost = individualRepo.get(individual.getId());
		assertEquals(individual, individualPost);
	}
}
