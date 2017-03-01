package org.genomesmanager.repositories.species;

import org.genomesmanager.domain.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SpeciesRepository extends JpaRepository<Species, Integer> {
	public abstract Species findByGenusAndSpeciesAndSubspecies(String genus, String species, String subspecies);
}
