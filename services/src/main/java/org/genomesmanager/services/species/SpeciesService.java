package org.genomesmanager.services.species;

import org.genomesmanager.domain.dtos.CannotParseSpeciesDefinitionException;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;

import java.util.List;

public interface SpeciesService {
	
	public abstract void delete(Species sp);
	
	public abstract void delete(String speciesDefinition) throws CannotParseSpeciesDefinitionException;

	public abstract void delete(String genus, String species, String subspicies);

	public abstract Species get(String genus, String species, String subspecies);

	public abstract Species get(String speciesDefinition) throws CannotParseSpeciesDefinitionException;

	public abstract void save(Species sp);

	public abstract List<Species> getAll();

	public abstract List<Species> getAll(boolean greedy);

	public abstract List<Species> getRice();

	List<Chromosome> getChromosomes(String genus, String species, String subspecies);
}