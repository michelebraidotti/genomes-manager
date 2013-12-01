package org.genomesmanager.repositories.sequences;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScaffoldRepository extends JpaRepository<Scaffold, Integer>, ScaffoldRepositoryCustom {
	List<Scaffold> findByName(String name);
	List<Scaffold> findByNameAndVersion(String name, String version);
	List<Scaffold> findByChromosome(Chromosome chromosome);
	List<Scaffold> findByChromosomeSpecies(Species species);
}
