package org.genomesmanager.repositories.genes;

import org.genomesmanager.domain.entities.Mrna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MrnaRepository extends JpaRepository<Mrna, Integer> {
}