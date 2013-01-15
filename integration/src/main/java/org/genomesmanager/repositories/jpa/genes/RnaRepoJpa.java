package org.genomesmanager.repositories.jpa.genes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.repositories.genes.RnaRepo;
import org.springframework.stereotype.Repository;

@Repository("RnaRepo")
public class RnaRepoJpa implements RnaRepo {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public RnaRepoJpa() {}
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.RnaRepo#get(int)
	 */
	@Override
	public Rna get(int id) {
		return em.find(Rna.class, id);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.RnaRepo#get(java.lang.String)
	 */
	@Override
	public Rna get(String name) {
		q = em.createNamedQuery("Rna.findByName");
		q.setParameter("name", name);
    	@SuppressWarnings("unchecked")
		List<Rna> rnas = q.getResultList();
    	if ( rnas.size() != 1 ) {
    		return null;
    	}
		return rnas.get(0);
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.RnaRepo#insert(org.genomesmanager.domain.entities.Rna)
	 */
	@Override
	public void insert(Rna newRna) {
		em.persist(newRna);
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.RnaRepo#update(org.genomesmanager.domain.entities.Rna)
	 */
	@Override
	public void update(Rna rna) {
		em.merge(rna);
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.RnaRepo#delete(int)
	 */
	@Override
	public void delete(int id) {
		delete(get(id));
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.RnaRepo#delete(org.genomesmanager.domain.entities.Rna)
	 */
	@Override
	public void delete(Rna rna) {
		em.remove(rna);
	}
}
