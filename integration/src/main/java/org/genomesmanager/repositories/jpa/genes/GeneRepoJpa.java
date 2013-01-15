package org.genomesmanager.repositories.jpa.genes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Mrna;
import org.genomesmanager.repositories.genes.GeneRepo;
import org.springframework.stereotype.Repository;

@Repository("GeneRepo")
public class GeneRepoJpa implements GeneRepo  {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public GeneRepoJpa() {}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.GeneRepo#get(int)
	 */
	@Override
	public Gene get(int id) {
		return em.find(Gene.class, id);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.GeneRepo#get(java.lang.String)
	 */
	@Override
	public Gene get(String name) {
		q = em.createNamedQuery("Gene.findByName");
		q.setParameter("name", name);
    	@SuppressWarnings("unchecked")
		List<Gene> genes = q.getResultList();
    	if ( genes.size() != 1 ) {
    		return null;
    	}
		return genes.get(0);
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.GeneRepo#getEager(int)
	 */
	@Override
	public Gene getEager(int id) {
		Gene g = em.find(Gene.class, id);
		setRelationsEagerly(g);
		return g;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.GeneRepo#setRelationsEagerly(org.genomesmanager.domain.entities.Gene)
	 */
	@Override
	public Gene setRelationsEagerly(Gene g) {
		g.getMrnas().size();
		for (Mrna m:g.getMrnas()) {
			m.getExons().size();
		}
		return g;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.GeneRepo#insert(org.genomesmanager.domain.entities.Gene)
	 */
	@Override
	public void insert(Gene newGene) {
		em.persist(newGene);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.GeneRepo#update(org.genomesmanager.domain.entities.Gene)
	 */
	@Override
	public void update(Gene gene) {
		em.merge(gene);	
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.GeneRepo#delete(int)
	 */
	@Override
	public void delete(int id) {
		Gene g = em.find(Gene.class, id);
		em.remove(g);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.GeneRepo#delete(org.genomesmanager.domain.entities.Gene)
	 */
	@Override
	public void delete(Gene gene) {
		em.remove(gene);
	}
    
    

}
