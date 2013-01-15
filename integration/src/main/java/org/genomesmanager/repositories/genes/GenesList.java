package org.genomesmanager.repositories.genes;

import java.util.List;

import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.SpeciesPK;

public interface GenesList {
	
	public abstract List<Gene> getAllBySpecies(SpeciesPK id, Boolean greedy);

	public abstract List<Gene> getAllByChromosome(int chrId, Boolean greedy);
}