package org.genomesmanager.services.species;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;

public interface SpeciesBrowser {

	public abstract List<Species> getAll();
	public abstract List<Species> getAll(boolean greedy);
	public abstract List<Species> getRice();
	List<Chromosome> getChromosomes(String genus, String species, String subspecies);
}