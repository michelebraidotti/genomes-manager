package org.genomesmanager.presentation.species;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.species.SpeciesNotFound;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.species.SpeciesBrowser;
import org.genomesmanager.services.species.SpeciesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("speciesBrowser")
@Scope("request")
public class SpeciesBrowserController {
	private static String NO_SELECTION = "--";
	@Autowired
	private SpeciesBrowser speciesBrowser;
	@Autowired
	private SpeciesManager speciesManager;
	@Autowired
	private ChromosomeRepository chromosomeRepo;
	private String speciesDefinition = "";
	private String chromosomeSelected = "";
	private Species speciesSeleced = null;
	private List<Species> species = new ArrayList<Species>();	
	@SuppressWarnings("unused")
	private List<Chromosome> chromosomes = new ArrayList<Chromosome>();
	

	public SpeciesBrowserController() {
	}
	
	public List<Species> getSpecies() {
		if (species.size() == 0)
			species = speciesBrowser.getAll(true);
		return species;
	}

	public String getSpeciesDefinition() {
		return speciesDefinition;
	}

	public void setSpeciesDefinition(String speciesDefinition) {
		this.speciesDefinition = speciesDefinition;
	}

	public String getChromosomeSelected() {
		return chromosomeSelected;
	}

	public void setChromosomeSelected(String chromosomeSelected) {
		this.chromosomeSelected = chromosomeSelected;
	}

	public Species getSpeciesSeleced() {
		return speciesSeleced;
	}

	public void setSpeciesSeleced(Species speciesSeleced) {
		this.speciesSeleced = speciesSeleced;
	}

	public List<Chromosome> getChromosomes() {
		List<Chromosome> list = new ArrayList<Chromosome>();
		Chromosome empty = new Chromosome();
		empty.setNumber(NO_SELECTION);
		list.add(empty);
		if (speciesSeleced != null)
			list.addAll(speciesSeleced.getChromosomes());
		return list;
	}
	
	public void speciesChanged(ValueChangeEvent e) throws SpeciesNotFound,
	SpeciesRepoException {
		speciesDefinition = e.getNewValue().toString();
		speciesSeleced = speciesManager.get(speciesDefinition);
		chromosomeSelected = NO_SELECTION;
	}
	
	public String cancel() {
		return "/species/home.xhtml";
	}

}
