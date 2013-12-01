package org.genomesmanager.repositories.genes;

import org.genomesmanager.domain.entities.Mrna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MrnaRepository extends JpaRepository<Mrna, Integer> {
}