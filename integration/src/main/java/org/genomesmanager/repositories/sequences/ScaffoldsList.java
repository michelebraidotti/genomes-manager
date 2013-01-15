package org.genomesmanager.repositories.sequences;

import java.util.List;

import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.species.SpeciesRepoException;

public interface ScaffoldsList {
	public List<Scaffold> getAllBySpecies(SpeciesPK speciesPK) throws SpeciesRepoException;    
	public List<Scaffold> getAllByChromosome(int chrId);
	
}
