package org.genomesmanager.repositories.jpa.species;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.genomesmanager.domain.entities.Variety;
import org.genomesmanager.repositories.species.VarietyRepo;
import org.genomesmanager.repositories.species.VarietyRepoException;
import org.springframework.stereotype.Repository;

@Repository("VarietyRepo")
public class VarietyRepoJpa implements VarietyRepo {
    @PersistenceContext
	private EntityManager em;
    
    public VarietyRepoJpa() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.VarietyRepo#get(java.lang.String)
	 */
	@Override
	public Variety get(String name) throws VarietyRepoException {
		Variety v = em.find(Variety.class, name);
		if ( v != null) {
			return v;
		}
		else {
			throw new VarietyRepoException("No variety with name " + name + " found");
		}
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.VarietyRepo#insert(org.genomesmanager.domain.entities.Variety)
	 */
	@Override
	public void insert(Variety v) {
		em.persist(v);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.VarietyRepo#update(org.genomesmanager.domain.entities.Variety)
	 */
	@Override
	public void update(Variety v) {
		em.merge(v);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.VarietyRepo#delete(org.genomesmanager.domain.entities.Variety)
	 */
	@Override
	public void delete(Variety v) {
		em.remove(v);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.VarietyRepo#deleteByKey(java.lang.String)
	 */
	@Override
	public void deleteByKey(String name) {
		delete(em.find(Variety.class, name));
	}
    
    

}
