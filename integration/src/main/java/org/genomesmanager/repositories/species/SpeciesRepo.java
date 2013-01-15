package org.genomesmanager.repositories.species;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.SpeciesPK;

public interface SpeciesRepo {

	public abstract void delete(Species sp);

	public abstract void deleteByKey(SpeciesPK spk);

	public abstract Species get(SpeciesPK spk) throws SpeciesRepoException;

	public abstract Species get(String genus, String species, String subspecies)
			throws SpeciesRepoException;

	public abstract Species get(String speciesDefinition)
			throws SpeciesRepoException;

	public abstract void insert(Species sp);

	public abstract void update(Species sp);

	public abstract List<Chromosome> getChromosomes(SpeciesPK spk)
			throws SpeciesRepoException;

}