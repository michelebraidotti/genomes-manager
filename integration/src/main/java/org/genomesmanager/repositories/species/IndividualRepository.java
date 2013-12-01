package org.genomesmanager.repositories.species;

import java.util.List;

import org.genomesmanager.domain.entities.Individual;
import org.springframework.data.repository.CrudRepository;

public interface IndividualRepository extends CrudRepository<Individual, Integer> { 
	List<Individual> findByDescription(String description);
}