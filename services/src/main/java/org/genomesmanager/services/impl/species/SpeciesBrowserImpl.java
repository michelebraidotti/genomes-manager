package org.genomesmanager.services.impl.species;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.genomesmanager.services.species.SpeciesBrowser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SpeciesBrowser")
public class SpeciesBrowserImpl implements SpeciesBrowser, Serializable {
	private static final long serialVersionUID = -6384957192110079568L;
	@Autowired
	private SpeciesRepository speciesRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#getAll()
	 */
	@Override
	public List<Species> getAll() {
		return speciesRepo.findAll();
	}

	@Override
	public List<Species> getAll(boolean greedy) {
		List<Species> sps = getAll();
		if (! greedy) 
			return sps;
		for (Species s:sps) {
			s.getChromosomes().size();
			s.getVarieties().size();
		}
		return sps;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genomesmanager.services.impl.species.SpeciesBrowser#getRice()
	 */
	@Override
	public List<Species> getRice() {
		List<Species> rice = new ArrayList<Species>(); 
		for (Species s:	getAll()) {
			if ( s.getGenus().equals("Oryza") )
					rice.add(s);
		}
		return rice;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.genomesmanager.services.impl.species.SpeciesBrowser#getChromosomes
	 * (org.genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public List<Chromosome> getChromosomes(String genus, String species, String subspecies) {
		Species speciesEntity = speciesRepo.findByGenusAndSpeciesAndSubspecies(genus, species, subspecies);
		return speciesEntity.getChromosomes();
	}

}
