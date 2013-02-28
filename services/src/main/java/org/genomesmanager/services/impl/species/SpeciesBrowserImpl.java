package org.genomesmanager.services.impl.species;

import java.io.Serializable;
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
public class SpeciesBrowserImpl implements SpeciesBrowser, Serializable {
	private static final long serialVersionUID = -6384957192110079568L;
	@Autowired
	private SpeciesList speciesList;
	@Autowired
	private SpeciesRepo speciesRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#getAll()
	 */
	@Override
	public List<Species> getAll() {
		return speciesList.getAll();
	}

	@Override
	public List<Species> getAll(boolean greedy) {
		return speciesList.getAll(greedy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#getRice()
	 */
	@Override
	public List<Species> getRice() {
		return speciesList.getRice();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.genomesmanager.services.impl.species.SpeciesBrowser#getChromosomes
	 * (org.genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public List<Chromosome> getChromosomes(SpeciesPK spk)
			throws SpeciesRepoException {
		return speciesRepo.getChromosomes(spk);
	}

}
