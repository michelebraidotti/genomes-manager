package org.genomesmanager.services.species;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.services.impl.species.CannotParseSpeciesDefinitionException;

public interface SpeciesManager {
	
	public abstract void delete(Species sp);
	
	public abstract void delete(String speciesDefinition) throws CannotParseSpeciesDefinitionException;

	public abstract void delete(String genus, String species, String subspicies);

	public abstract Species get(String genus, String species, String subspecies);

	public abstract Species get(String speciesDefinition) throws CannotParseSpeciesDefinitionException;

	public abstract void save(Species sp);
}