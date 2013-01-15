package org.genomesmanager.repositories.jpa.genes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.genes.RnasList;
import org.springframework.stereotype.Repository;

@Repository("RnaList")
public class RnasListJpa implements RnasList {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public RnasListJpa() {
    }

    /* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.RnasList#getAllBySpecies(org.genomesmanager.domain.entities.SpeciesPK)
	 */
    @Override
	public List<Rna> getAllBySpecies(SpeciesPK id) {
    	q = em.createNamedQuery("Rna.findAllBySpecies");
		q.setParameter("genus", id.getGenus());
		q.setParameter("species", id.getSpecies());
		q.setParameter("subspecies", id.getSubspecies());
		List<Rna> rnas = q.getResultList();
		return rnas;
    }
    
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.RnasList#getAllByChromosome(int)
	 */
	@Override
	public List<Rna> getAllByChromosome(int chrId) {
		q = em.createNamedQuery("Rna.findAllByChromosome");
		q.setParameter("chrId", chrId);
		List<Rna> rnas = q.getResultList();
		return rnas;
	}
    
}
