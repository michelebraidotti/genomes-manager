package org.genomesmanager.services.species;

import org.genomesmanager.domain.dtos.CannotParseSpeciesDefinitionException;
import org.genomesmanager.domain.dtos.SpeciesDefinition;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SpeciesService")
public class SpeciesService {
	@Autowired
	private SpeciesRepository speciesRepo;

	public void delete(Species sp) {
		speciesRepo.delete(sp);
	}

	public void delete(String speciesDefinition) throws CannotParseSpeciesDefinitionException {
		SpeciesDefinition sd = new SpeciesDefinition(speciesDefinition);
		Species speciesEntity = speciesRepo.findByGenusAndSpeciesAndSubspecies(sd.genus, sd.species, sd.subspecies);
		speciesRepo.delete(speciesEntity);
	}

	public void delete(String genus, String species, String subspecies) {
		Species speciesEntity = speciesRepo.findByGenusAndSpeciesAndSubspecies(genus, species, subspecies);
		speciesRepo.delete(speciesEntity);
	}

	public Species get(String genus, String species, String subspecies) {
		return speciesRepo.findByGenusAndSpeciesAndSubspecies(genus, species, subspecies);
	}

	public Species get(String speciesDefinition) throws CannotParseSpeciesDefinitionException {
		SpeciesDefinition sd = new SpeciesDefinition(speciesDefinition);
		return speciesRepo.findByGenusAndSpeciesAndSubspecies(sd.genus, sd.species, sd.subspecies);
	}

	public void save(Species sp) {
		speciesRepo.save(sp);
	}

	public List<Species> getAll() {
		return speciesRepo.findAll();
	}

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

	public List<Species> getRice() {
		List<Species> rice = new ArrayList<Species>();
		for (Species s:	getAll()) {
			if ( s.getGenus().equals("Oryza") )
				rice.add(s);
		}
		return rice;
	}

	public List<Chromosome> getChromosomes(String genus, String species, String subspecies) {
		Species speciesEntity = speciesRepo.findByGenusAndSpeciesAndSubspecies(genus, species, subspecies);
		return speciesEntity.getChromosomes();
	}
	
}
