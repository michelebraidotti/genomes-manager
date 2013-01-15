package org.genomesmanager.repositories.jpa.snps;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Snp;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.snps.SnpsList;
import org.springframework.stereotype.Repository;


/**
 * Session Bean implementation class SnpsList
 */
@Repository("SnpsList")
public class SnpsListJpa implements SnpsList {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public SnpsListJpa() {
    }
    
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.snps.SnpsList#getAllBySpecies(org.genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public List<Snp> getAllBySpecies(SpeciesPK id) {
		q = em.createNamedQuery("Snp.findAllBySpecies");
		q.setParameter("genus", id.getGenus());
		q.setParameter("species", id.getSpecies());
		q.setParameter("subspecies", id.getSubspecies());
		List<Snp> snps = q.getResultList();
		return snps;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.snps.SnpsList#getAllByChromosome(int)
	 */
	@Override
	public List<Snp> getAllByChromosome(int chrId) {
		q = em.createNamedQuery("Snp.findAllByChromosome");
		q.setParameter("chrId", chrId);
		List<Snp> snp = q.getResultList();
		return snp;
	}

}
