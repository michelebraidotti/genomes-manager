package org.genomesmanager.repositories.jpa.species;

import static org.junit.Assert.*;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SpeciesRepoTest extends AbstractIntegrationTest {
	@Autowired
	private SpeciesRepo speciesRepo;

	@Test
	public void test() throws SpeciesRepoException {
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.insert(sp);
		Species spPost = speciesRepo.get(sp.getId());
		assertEquals(sp, spPost);
	}

}
