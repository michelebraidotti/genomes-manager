package org.genomesmanager.repositories.jpa.genes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Exon;
import org.genomesmanager.repositories.genes.ExonRepo;
import org.springframework.stereotype.Repository;

@Repository("ExonRepo")
public class ExonRepoJpa implements ExonRepo {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public ExonRepoJpa() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.ExonRepo#get(int)
	 */
	@Override
	public Exon get(int id) {
		return em.find(Exon.class, id);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.ExonRepo#get(java.lang.String)
	 */
	@Override
	public Exon get(String name) {
		q = em.createNamedQuery("Exon.findByName");
		q.setParameter("name", name);
    	@SuppressWarnings("unchecked")
		List<Exon> exons = q.getResultList();
    	if ( exons.size() != 1 ) {
    		return null;
    	}
		return exons.get(0);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.ExonRepo#insert(org.genomesmanager.domain.entities.Exon)
	 */
	@Override
	public void insert(Exon exon) {
		em.persist(exon);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.ExonRepo#update(org.genomesmanager.domain.entities.Exon)
	 */
	@Override
	public void update(Exon exon) {
		em.merge(exon);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.ExonRepo#delete(int)
	 */
	@Override
	public void delete(int id) {
		Exon e = em.find(Exon.class, id);
		em.remove(e);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.ExonRepo#delete(org.genomesmanager.domain.entities.Exon)
	 */
	@Override
	public void delete(Exon exon) {
		em.remove(exon);
		
	}

}
