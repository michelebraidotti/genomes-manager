package org.genomesmanager.repositories.jpa.sequences;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.sequences.ScaffoldsList;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.springframework.stereotype.Repository;

@Repository("ScaffoldsList")
public class ScaffoldsListJpa implements ScaffoldsList {	
	@PersistenceContext
	private EntityManager em;
	private Query q;

	@Override
	@SuppressWarnings("unchecked")
	public List<Scaffold> getAllBySpecies(SpeciesPK speciesPK)
			throws SpeciesRepoException {
    	q = em.createNamedQuery("Scaffold.findAllBySpecies");
		q.setParameter("genus", speciesPK.getGenus());
		q.setParameter("species", speciesPK.getSpecies());
		q.setParameter("subspecies", speciesPK.getSubspecies());
		List<Scaffold> scaffs = q.getResultList();
    	return scaffs;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Scaffold> getAllByChromosome(int chrId) {
		q = em.createNamedQuery("Scaffold.findAllByChromosome");
		q.setParameter("chrId", chrId);
		List<Scaffold> scaffs = q.getResultList();
    	return scaffs;
	}

}
