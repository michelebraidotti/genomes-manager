package org.genomesmanager.services.impl.species;

import java.io.Serializable;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.species.SpeciesList;
import org.genomesmanager.repositories.species.SpeciesNotFound;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.species.SpeciesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SpeciesManager")
public class SpeciesManagerImpl implements SpeciesManager, Serializable {
	private static final long serialVersionUID = -6384957192110079568L;
	@Autowired
	private SpeciesList speciesList;
	@Autowired
	private SpeciesRepo speciesRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genomesmanager.services.impl.specie* @see org.genomesmanager.services.impl.species.SpeciesBrowser#getAll()
	 s.SpeciesBrowser#delete(org.
	 * genomesmanager.domain.entities.Species)
	 */
	@Override
	public void delete(Species sp) {
		speciesRepo.delete(sp);
	}
	

	@Override
	public void delete(String speciesDefinition) throws SpeciesNotFound, SpeciesRepoException  {
		speciesRepo.delete(speciesDefinition);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.genomesmanager.services.impl.species.SpeciesBrowser#deleteByKey(org
	 * .genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public void deleteByKey(SpeciesPK spk) {
		speciesRepo.deleteByKey(spk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#get(org.
	 * genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public Species get(SpeciesPK spk) throws SpeciesNotFound {
		return speciesRepo.get(spk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.genomesmanager.services.impl.species.SpeciesBrowser#get(java.lang
	 * .String, java.lang.String, java.lang.String)
	 */
	@Override
	public Species get(String genus, String species, String subspecies)
			throws SpeciesNotFound {
		return speciesRepo.get(genus, species, subspecies);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.genomesmanager.services.impl.species.SpeciesBrowser#get(java.lang
	 * .String)
	 */
	@Override
	public Species get(String speciesDefinition) throws SpeciesRepoException, SpeciesNotFound {
		return speciesRepo.get(speciesDefinition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#insert(org.
	 * genomesmanager.domain.entities.Species)
	 */
	@Override
	public void insert(Species sp) {
		speciesRepo.insert(sp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#update(org.
	 * genomesmanager.domain.entities.Species)
	 */
	@Override
	public void update(Species sp) {
		speciesRepo.update(sp);
	}


	@Override
	public void update(Species oldSpecies, Species newSpecies) {
		if (! oldSpecies.getId().equals(newSpecies.getId()) ) {
			speciesRepo.updateId(oldSpecies, newSpecies.getId());
		}
		update(newSpecies);
	}
	
}
