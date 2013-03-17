package org.genomesmanager.repositories.jpa.species;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.species.SpeciesNotFound;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("SpeciesRepo")
public class SpeciesRepoJpa implements SpeciesRepo {
    @PersistenceContext
	private EntityManager em;
    private Query q;
    
    public SpeciesRepoJpa() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.species.SpeciesRepo#delete(org.genomesmanager.domain.entities.Species)
	 */
	@Override
	public void delete(Species sp) {
		em.remove(sp);
	}
	
	@Override
	public void delete(String speciesDefinition) throws SpeciesNotFound, SpeciesRepoException {
		em.remove(get(speciesDefinition));
		
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
	public Species get(SpeciesPK spk) throws SpeciesNotFound {
		Species sp = em.find(Species.class, spk);
		if ( sp == null ) {
			throw new SpeciesNotFound("Species: " + spk.getGenus() + 
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
	public Species get(String genus, String species, String subspecies) throws SpeciesNotFound {
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
	public Species get(String speciesDefinition) throws SpeciesRepoException, SpeciesNotFound {
		if ( speciesDefinition == null ) {
			return null;
		}
		String [] spDef = speciesDefinition.split("\\s+");
		if ( spDef.length < 2 ) {
			throw new SpeciesRepoException("Cannot guess genus, species, subspecies " +
					"from species definition '" + speciesDefinition + "'");
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
	
	@Override
	public void updateId(Species oldSpecies, SpeciesPK id) {
		q = em.createQuery(
				"UPDATE Species s SET s.id.genus = :newGenus, s.id.species = :newSpecies, s.id.subspecies = :newSubspecies " +
				"WHERE s.id.genus = :oldGenus and s.id.species = :oldSpecies and s.id.subspecies = :oldSubspecies");
		q.setParameter("newGenus", id.getGenus());
		q.setParameter("newSpecies", id.getSpecies());
		q.setParameter("newSubspecies", id.getSubspecies());
		q.setParameter ("oldGenus", oldSpecies.getId().getGenus());
		q.setParameter ("oldSpecies", oldSpecies.getId().getSpecies());
		q.setParameter ("oldSubspecies", oldSpecies.getId().getSubspecies());
		q.executeUpdate();
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
