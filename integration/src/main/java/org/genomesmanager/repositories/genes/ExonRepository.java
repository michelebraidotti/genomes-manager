package org.genomesmanager.repositories.genes;

import org.genomesmanager.domain.entities.Exon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExonRepository extends JpaRepository<Exon, Integer> {
}