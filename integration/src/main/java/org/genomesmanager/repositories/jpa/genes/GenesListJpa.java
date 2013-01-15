package org.genomesmanager.repositories.jpa.genes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.genes.GeneRepo;
import org.genomesmanager.repositories.genes.GenesList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("GenesList")
public class GenesListJpa implements GenesList {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	@Autowired
	private GeneRepo geneRepo;
	
    public GenesListJpa() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.GenesList#getAllBySpecies(org.genomesmanager.domain.entities.SpeciesPK, java.lang.Boolean)
	 */
	@Override
	public List<Gene> getAllBySpecies(SpeciesPK id, Boolean greedy) {
		q = em.createNamedQuery("Gene.findAllBySpecies");
		q.setParameter("genus", id.getGenus());
		q.setParameter("species", id.getSpecies());
		q.setParameter("subspecies", id.getSubspecies());
		List<Gene> genes = q.getResultList();
		if ( greedy ) {
			for (Gene g:genes) {
				g = geneRepo.setRelationsEagerly(g);
			}
		}
		return genes;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.GenesList#getAllByChromosome(int, java.lang.Boolean)
	 */
	@Override
	public List<Gene> getAllByChromosome(int chrId, Boolean greedy) {
		q = em.createNamedQuery("Gene.findAllByChromosome");
		q.setParameter("chrId", chrId);
		List<Gene> genes = q.getResultList();
		if ( greedy ) {
			for (Gene g:genes) {
				g = geneRepo.setRelationsEagerly(g);
			}
		}
		return genes;
	}

}
