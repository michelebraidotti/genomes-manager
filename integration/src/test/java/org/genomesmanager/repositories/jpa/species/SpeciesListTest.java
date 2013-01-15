package org.genomesmanager.repositories.jpa.species;

import static org.junit.Assert.*;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.species.SpeciesList;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SpeciesListTest extends AbstractIntegrationTest {
	@Autowired
	private SpeciesList speciesList;
	@Autowired
	private SpeciesRepo speciesRepo;

	@Test
	public void test() {
		int nOfSpecies = 7;
		int preCount = speciesList.getAll().size();
		for ( Species s:SpeciesOM.Generate(nOfSpecies) ) {
			speciesRepo.insert(s);
		}
		assertEquals(preCount + nOfSpecies, speciesList.getAll().size());
	}
}
