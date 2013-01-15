package org.genomesmanager.repositories.jpa.species;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.genomesmanager.domain.entities.Individual;
import org.genomesmanager.repositories.species.IndividualRepo;
import org.springframework.stereotype.Repository;

@Repository("IndividualRepo")
public class IndividualRepoJpa implements IndividualRepo {
    @PersistenceContext
	private EntityManager em;
    
    public IndividualRepoJpa() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.IndividualRepo#get(int)
	 */
	@Override
	public Individual get(int id) {
		return em.find(Individual.class, id);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.IndividualRepo#insert(org.genomesmanager.domain.entities.Individual)
	 */
	@Override
	public void insert(Individual i) {
		em.persist(i);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.IndividualRepo#update(org.genomesmanager.domain.entities.Individual)
	 */
	@Override
	public void update(Individual i) {
		em.merge(i);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.IndividualRepo#delete(org.genomesmanager.domain.entities.Individual)
	 */
	@Override
	public void delete(Individual i) {
		em.remove(i);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.IndividualRepo#delete(int)
	 */
	@Override
	public void delete(int id) {
		delete(em.find(Individual.class, id));
	}
    
    

}
