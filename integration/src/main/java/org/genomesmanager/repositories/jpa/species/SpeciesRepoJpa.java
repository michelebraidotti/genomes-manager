package org.genomesmanager.repositories.jpa.species;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.springframework.stereotype.Repository;

@Repository("SpeciesRepo")
public class SpeciesRepoJpa implements SpeciesRepo {
    @PersistenceContext
	private EntityManager em;
    
    public SpeciesRepoJpa() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.SpeciesRepo#delete(org.genomesmanager.domain.entities.Species)
	 */
	@Override
	public void delete(Species sp) {
		em.remove(sp);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.SpeciesRepo#deleteByKey(org.genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public void deleteByKey(SpeciesPK spk) {
		em.remove(em.find(Species.class, spk));
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.SpeciesRepo#get(org.genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public Species get(SpeciesPK spk) throws SpeciesRepoException {
		Species sp = em.find(Species.class, spk);
		if ( sp == null ) {
			throw new SpeciesRepoException("Species: " + spk.getGenus() + 
					", " + spk.getSpecies() + ", " + spk.getSubspecies() + 
					" not found");
		}
		sp.getChromosomes().size();
		sp.getVarieties().size();
		return sp;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.SpeciesRepo#get(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Species get(String genus, String species, String subspecies) throws SpeciesRepoException {
		SpeciesPK spk = new SpeciesPK();
		spk.setGenus(genus);
		spk.setSpecies(species);
		spk.setSubspecies(subspecies);
		return get(spk);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.SpeciesRepo#get(java.lang.String)
	 */
	@Override
	public Species get(String speciesDefinition) throws SpeciesRepoException {
		if ( speciesDefinition == null ) {
			return null;
		}
		String [] spDef = speciesDefinition.split("\\s+");
		if ( spDef.length < 2 ) {
			throw new SpeciesRepoException("Cannot guess genus, species, subspecies " +
					"from species definition " + speciesDefinition);
		}
		String genus = "";
		String species = "";
		String subspecies = "";
		if ( spDef.length == 2 ) {
			genus = spDef[0];
			species = spDef[1];
		}
		else if ( spDef.length == 3 ) {
			genus = spDef[0];
			species = spDef[1];
			subspecies = spDef[2];
		}
		else {
			throw new SpeciesRepoException("Cannot guess genus, species, subspecies " +
					"from species definition " + speciesDefinition);
		}
		return get(genus, species, subspecies);
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.SpeciesRepo#insert(org.genomesmanager.domain.entities.Species)
	 */
	@Override
	public void insert(Species sp) {
		em.persist(sp);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.SpeciesRepo#update(org.genomesmanager.domain.entities.Species)
	 */
	@Override
	public void update(Species sp) {
		em.merge(sp);
	} 
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.SpeciesRepo#getChromosomes(org.genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public List<Chromosome> getChromosomes(SpeciesPK spk) throws SpeciesRepoException {
		Species sp = this.get(spk);
		sp.getChromosomes().size();
		return sp.getChromosomes();
	}
}
