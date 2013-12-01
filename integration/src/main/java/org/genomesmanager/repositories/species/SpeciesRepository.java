package org.genomesmanager.repositories.species;

import org.genomesmanager.domain.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeciesRepository extends JpaRepository<Species, Integer> {
	public abstract Species findByGenusAndSpeciesAndSubspecies(String genus, String species, String subspecies);
}
