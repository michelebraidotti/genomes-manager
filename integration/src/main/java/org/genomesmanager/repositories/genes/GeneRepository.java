package org.genomesmanager.repositories.genes;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneRepository extends JpaRepository<Gene, Integer> {
	public abstract List<Gene> findBySequenceChromosome(Chromosome chromosome);
	public abstract List<Gene> findBySequenceChromosomeSpecies(Species species);
}