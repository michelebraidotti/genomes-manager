package org.genomesmanager.repositories.sequences;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PseudomoleculeRepository extends JpaRepository<Pseudomolecule, Integer> {
	List<Pseudomolecule> findByName(String name);
	List<Pseudomolecule> findByNameAndVersion(String name, String version);
	List<Pseudomolecule> findByChromosome(Chromosome chromosome);
	List<Pseudomolecule> findByChromosomeSpecies(Species species);
}
