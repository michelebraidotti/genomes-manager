package org.genomesmanager.services.species;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.species.SpeciesNotFound;
import org.genomesmanager.repositories.species.SpeciesRepoException;

public interface SpeciesManager {
	
	public abstract void delete(Species sp);
	
	public abstract void delete(String speciesDefinition) throws SpeciesNotFound, SpeciesRepoException ;

	public abstract void deleteByKey(SpeciesPK spk);

	public abstract Species get(SpeciesPK spk) throws SpeciesNotFound;

	public abstract Species get(String genus, String species, String subspecies)
			throws SpeciesNotFound;

	public abstract Species get(String speciesDefinition)
			throws SpeciesRepoException, SpeciesNotFound;

	public abstract void insert(Species sp);

	public abstract void update(Species sp);
	
	public abstract void update(Species oldSpecies, Species newSpecies);
}