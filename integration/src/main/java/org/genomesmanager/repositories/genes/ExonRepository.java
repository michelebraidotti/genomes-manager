package org.genomesmanager.repositories.genes;

import org.genomesmanager.domain.entities.Exon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ExonRepository extends JpaRepository<Exon, Integer> {
}