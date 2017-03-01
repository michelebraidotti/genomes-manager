package org.genomesmanager.repositories.sequences;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SequenceRepository extends JpaRepository<Sequence, Integer>, SequenceRepositoryCustom {
	public List<Sequence> findByName(String name);
	public List<Sequence> findByNameAndVersion(String name, String version);
	public List<Sequence> findByChromosome(Chromosome chromosome);
	public abstract List<Sequence> findByChromosomeSpecies(Species species);
}

