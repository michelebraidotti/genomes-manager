package org.genomesmanager.repositories.jpa.species;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.species.SpeciesList;
import org.springframework.stereotype.Repository;

@Repository("SpeciesList")
public class SpeciesListJpa implements SpeciesList {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public SpeciesListJpa() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.SpeciesList#getAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Species> getAll() {
		q = em.createNamedQuery("Species.findAll");
		List<Species> sps = q.getResultList();
		return sps;
    }
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.SpeciesList#getRice()
	 */
	@Override
	public List<Species> getRice() {
		q = em.createNamedQuery("Species.findRice");
		List<Species> sps = q.getResultList();
		return sps;
    }
	
}
