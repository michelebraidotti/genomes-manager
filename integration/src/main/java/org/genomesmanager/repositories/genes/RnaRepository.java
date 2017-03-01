package org.genomesmanager.repositories.genes;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.domain.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RnaRepository extends JpaRepository<Rna, Integer> {
	public abstract List<Rna> findBySequenceChromosome(Chromosome chromosome);
	public abstract List<Rna> findBySequenceChromosomeSpecies(Species species);
}