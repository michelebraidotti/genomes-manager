package org.genomesmanager.services.impl.species;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.species.SpeciesList;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.species.SpeciesBrowser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SpeciesBrowser")
public class SpeciesBrowserImpl implements SpeciesBrowser {
	@Autowired
	private SpeciesList speciesList;
	@Autowired
	private SpeciesRepo speciesRepo;
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#getAll()
	 */
	@Override
	public List<Species> getAll() {
		return speciesList.getAll();
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#getRice()
	 */
	@Override
	public List<Species> getRice() {
		return speciesList.getRice();
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#delete(org.genomesmanager.domain.entities.Species)
	 */
	@Override
	public void delete(Species sp) {
		speciesRepo.delete(sp);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#deleteByKey(org.genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public void deleteByKey(SpeciesPK spk) {
		speciesRepo.deleteByKey(spk);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#get(org.genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public Species get(SpeciesPK spk) throws SpeciesRepoException {
		return speciesRepo.get(spk);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#get(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Species get(String genus, String species, String subspecies)
			throws SpeciesRepoException {
		return speciesRepo.get(genus, species, subspecies);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#get(java.lang.String)
	 */
	@Override
	public Species get(String speciesDefinition) throws SpeciesRepoException {
		return speciesRepo.get(speciesDefinition);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#insert(org.genomesmanager.domain.entities.Species)
	 */
	@Override
	public void insert(Species sp) {
		speciesRepo.insert(sp);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#update(org.genomesmanager.domain.entities.Species)
	 */
	@Override
	public void update(Species sp) {
		speciesRepo.update(sp);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#getChromosomes(org.genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public List<Chromosome> getChromosomes(SpeciesPK spk)
			throws SpeciesRepoException {
		return speciesRepo.getChromosomes(spk);
	}
	
	
	
}
