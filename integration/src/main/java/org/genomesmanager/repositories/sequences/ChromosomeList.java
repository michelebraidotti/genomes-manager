package org.genomesmanager.repositories.sequences;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.species.SpeciesRepoException;

public interface ChromosomeList {

	public abstract List<Chromosome> getAll();

	public abstract List<Chromosome> getAllBySpecies(Species sp)
			throws SpeciesRepoException;

}