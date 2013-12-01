package org.genomesmanager.repositories.sequences;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Scaffold;


public interface ScaffoldRepositoryCustom {
	public abstract List<Scaffold> findAllPlacedByChromosome(Chromosome chromosome);
	public abstract List<Scaffold> findAllUnplacedByChromosome(Chromosome chromosome);
}
