package org.genomesmanager.repositories.jpa.snps;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.genomesmanager.domain.entities.Snp;
import org.genomesmanager.repositories.snps.SnpRepo;
import org.springframework.stereotype.Repository;

@Repository("SnpRepo")
public class SnpRepoJpa implements SnpRepo {
	@PersistenceContext
	private EntityManager em;
	
    public SnpRepoJpa() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.snps.SnpRepo#get(int)
	 */
	@Override
	public Snp get(int id) {
		return em.find(Snp.class, id);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.snps.SnpRepo#insert(org.genomesmanager.domain.entities.Snp)
	 */
	@Override
	public void insert(Snp newSnp) {
		em.persist(newSnp);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.snps.SnpRepo#update(org.genomesmanager.domain.entities.Snp)
	 */
	@Override
	public void update(Snp snp) {
		em.merge(snp);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.snps.SnpRepo#delete(int)
	 */
	@Override
	public void delete(int id) {
		delete(em.find(Snp.class, id));
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.snps.SnpRepo#delete(org.genomesmanager.domain.entities.Snp)
	 */
	@Override
	public void delete(Snp snp) {
		em.remove(snp);
	}
    
    

}
