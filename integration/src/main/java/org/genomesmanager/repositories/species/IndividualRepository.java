package org.genomesmanager.repositories.species;

import java.util.List;

import org.genomesmanager.domain.entities.Individual;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IndividualRepository extends CrudRepository<Individual, Integer> { 
	List<Individual> findByDescription(String description);
}