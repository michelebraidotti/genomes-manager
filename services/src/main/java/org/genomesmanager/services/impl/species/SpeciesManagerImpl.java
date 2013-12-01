package org.genomesmanager.services.impl.species;

import java.io.Serializable;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.genomesmanager.services.species.SpeciesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SpeciesManager")
public class SpeciesManagerImpl implements SpeciesManager, Serializable {
	private static final long serialVersionUID = -6384957192110079568L;
	@Autowired
	private SpeciesRepository speciesRepo;

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
	public void delete(String speciesDefinition) throws CannotParseSpeciesDefinitionException {
		SpeciesDefinition sd = new SpeciesDefinition(speciesDefinition);
		Species speciesEntity = speciesRepo.findByGenusAndSpeciesAndSubspecies(sd.genus, sd.species, sd.subspecies);
		speciesRepo.delete(speciesEntity);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.genomesmanager.services.impl.species.SpeciesBrowser#deleteByKey(org
	 * .genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public void delete(String genus, String species, String subspecies) {
		Species speciesEntity = speciesRepo.findByGenusAndSpeciesAndSubspecies(genus, species, subspecies);
		speciesRepo.delete(speciesEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.genomesmanager.services.impl.species.SpeciesBrowser#get(java.lang
	 * .String, java.lang.String, java.lang.String)
	 */
	@Override
	public Species get(String genus, String species, String subspecies) {
		return speciesRepo.findByGenusAndSpeciesAndSubspecies(genus, species, subspecies);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.genomesmanager.services.impl.species.SpeciesBrowser#get(java.lang
	 * .String)
	 */
	@Override
	public Species get(String speciesDefinition) throws CannotParseSpeciesDefinitionException {
		SpeciesDefinition sd = new SpeciesDefinition(speciesDefinition);
		return speciesRepo.findByGenusAndSpeciesAndSubspecies(sd.genus, sd.species, sd.subspecies);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#insert(org.
	 * genomesmanager.domain.entities.Species)
	 */
	@Override
	public void save(Species sp) {
		speciesRepo.save(sp);
	}
	
}
