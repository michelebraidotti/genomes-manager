package org.genomesmanager.services.species;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.species.SpeciesRepoException;

public interface SpeciesBrowser {

	public abstract List<Species> getAll();
	public abstract List<Species> getAll(boolean greedy);
	public abstract List<Species> getRice();
	public abstract List<Chromosome> getChromosomes(SpeciesPK spk)
			throws SpeciesRepoException;

}