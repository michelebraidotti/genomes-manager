package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.Repeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepeatRepository extends JpaRepository<Repeat, Integer>,
		RepeatRepositoryCustom {

}
