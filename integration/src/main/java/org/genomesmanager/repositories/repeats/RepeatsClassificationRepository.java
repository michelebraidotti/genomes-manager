package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RepeatsClassificationRepository extends
		JpaRepository<RepeatsClassification, Integer>,
		RepeatsClassificationRepositoryCustom {
}
