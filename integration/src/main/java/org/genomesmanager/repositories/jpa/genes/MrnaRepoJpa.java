package org.genomesmanager.repositories.jpa.genes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import  org.genomesmanager.domain.entities.Mrna;
import org.genomesmanager.repositories.genes.MrnaRepo;

@Repository("MrnaRepo")
public class MrnaRepoJpa implements MrnaRepo {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public MrnaRepoJpa() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.MrnaRepo#get(int)
	 */
	@Override
	public Mrna get(int id) {
		return em.find(Mrna.class, id);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.MrnaRepo#get(java.lang.String)
	 */
	@Override
	public Mrna get(String name) {
		q = em.createNamedQuery("Mrna.findByName");
		q.setParameter("name", name);
    	@SuppressWarnings("unchecked")
		List<Mrna> mrnas = q.getResultList();
    	if ( mrnas.size() != 1 ) {
    		return null;
    	}
		return mrnas.get(0);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.MrnaRepo#insert(org.genomesmanager.domain.entities.Mrna)
	 */
	@Override
	public void insert(Mrna mrna) {
		em.persist(mrna);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.MrnaRepo#update(org.genomesmanager.domain.entities.Mrna)
	 */
	@Override
	public void update(Mrna mrna) {
		em.merge(mrna);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.MrnaRepo#delete(int)
	 */
	@Override
	public void delete(int id) {
		Mrna e = em.find(Mrna.class, id);
		em.remove(e);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.genes.MrnaRepo#delete(org.genomesmanager.domain.entities.Mrna)
	 */
	@Override
	public void delete(Mrna mrna) {
		em.remove(mrna);
		
	}

}
