package org.genomesmanager.repositories.jpa.sequences;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.sequences.ChromosomeList;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("ChromosomesList")
public class ChromosomesListJpa implements ChromosomeList {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	@Autowired
	private SpeciesRepo speciesRepo;
	
    public ChromosomesListJpa() {}
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.ChromosomeList#getAll()
	 */
    @Override
	@SuppressWarnings("unchecked")
	public List<Chromosome> getAll() {
		q = em.createNamedQuery("Chromosome.findAll");
		List<Chromosome> res = q.getResultList();
		return res;
    }

	/*
	@SuppressWarnings("unchecked")
	public List<ChromosomeName> getAllChrNames() {
		q = em.createNamedQuery("Chromosome.findAllNames");
		List<Object[]> res = q.getResultList();
		List<ChromosomeName> chrNames = new  ArrayList<ChromosomeName>();
		for(Object [] line:res) {
			ChromosomeName chrName = new ChromosomeName();
			chrName.setId( (Integer)line[0] );
			chrName.setChromosomeNum( (String)line[1] );
			SpeciesPK spk = ((SpeciesPK) line[3]);
			chrName.setGenus(spk.getGenus());
			chrName.setSpecies(spk.getSpecies());
			chrName.setSubspecies(spk.getSubspecies());
			chrNames.add(chrName);
		}
		return chrNames;
	}
	*/
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.ChromosomeList#getAllBySpecies(org.genomesmanager.domain.entities.Species)
	 */
	@Override
	public List<Chromosome> getAllBySpecies(Species sp) throws SpeciesRepoException {
		Species s = speciesRepo.get(sp.getId());
		return s.getChromosomes();
	}

}
