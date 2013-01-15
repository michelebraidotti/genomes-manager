package org.genomesmanager.repositories.jpa.sequences;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.ChromosomeRepoException;
import org.springframework.stereotype.Repository;

@Repository("ChromosomeRepo")
public class ChromosomeRepoJpa implements ChromosomeRepo {
	@PersistenceContext
	private EntityManager em;
		
    public ChromosomeRepoJpa() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.ChromosomeRepo#delete(org.genomesmanager.domain.entities.Chromosome)
	 */
	@Override
	public void delete(Chromosome chr) {
		em.remove(chr);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.ChromosomeRepo#deleteByKey(int)
	 */
	@Override
	public void deleteByKey(int chrId) {
		em.remove(em.find(Chromosome.class, chrId));
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.ChromosomeRepo#get(int)
	 */
	@Override
	public Chromosome get(int chrId) throws ChromosomeRepoException {
		Chromosome chr = em.find(Chromosome.class, chrId);
		if ( chr == null ) {
			throw new ChromosomeRepoException("Chromosome id: " + chrId + " not found");
		}
		return chr;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.ChromosomeRepo#getGreedy(int)
	 */
	@Override
	public Chromosome getGreedy(int chrId) throws ChromosomeRepoException {
		Chromosome chr = get(chrId);
		chr.getSequences().size();
		return chr;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.ChromosomeRepo#get(java.lang.String, org.genomesmanager.domain.entities.Species)
	 */
	@Override
	public Chromosome get(String number, Species sp) {
		List<Chromosome> chrs = sp.getChromosomes();
		for (Chromosome chr:chrs) {
			if ( chr.getNumber().equals(number) ) {
				return chr;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.ChromosomeRepo#insert(org.genomesmanager.domain.entities.Chromosome)
	 */
	@Override
	public void insert(Chromosome chr) {
		em.persist(chr);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.ChromosomeRepo#update(org.genomesmanager.domain.entities.Chromosome)
	 */
	@Override
	public void update(Chromosome chr) {
		em.merge(chr);
	} 

}
