package org.genomesmanager.repositories.sequences;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ChromosomeRepository extends ChromosomeRepositoryCustom, JpaRepository<Chromosome, Integer> {
	public abstract List<Chromosome> findBySpecies(Species sp);
}