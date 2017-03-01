package org.genomesmanager.repositories.species;

import java.util.List;

import org.genomesmanager.domain.entities.Variety;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VarietyRepository extends CrudRepository<Variety, Integer>{
	List<Variety> findByName(String name);
}