package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepeatsClassificationRepository extends
		JpaRepository<RepeatsClassification, Integer>,
		RepeatsClassificationRepositoryCustom {
}
