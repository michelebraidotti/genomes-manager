package org.genomesmanager.repositories.jpa.repeats;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsOrder;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.repeats.RepeatsList;
import org.springframework.stereotype.Repository;

@Repository("RepeatsList")
public class RepeatsListJpa implements RepeatsList {
	// all methods moved to RepeatRepositoryCustomImpl
	// delete me once done refactoring services

}
