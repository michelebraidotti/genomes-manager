package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RepeatRepository extends JpaRepository<Repeat, Integer>, RepeatRepositoryCustom  {


}
