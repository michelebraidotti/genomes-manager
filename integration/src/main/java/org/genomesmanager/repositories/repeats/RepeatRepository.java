package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface RepeatRepository extends JpaRepository<Repeat, Integer>, RepeatRepositoryCustom  {


}
