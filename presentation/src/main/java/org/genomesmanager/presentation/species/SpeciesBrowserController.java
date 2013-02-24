package org.genomesmanager.presentation.species;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.species.SpeciesBrowser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("speciesBrowser")
@Scope("request")
public class SpeciesBrowserController {
	@Autowired
	private SpeciesBrowser speciesBrowser;
	private List<Species> species = new ArrayList<Species>();
	private String selectedSpecies;
	private Species currentSpecies;

	public SpeciesBrowserController() {
	}

	public String getSelectedSpecies() {
		return selectedSpecies;
	}

	public void setSelectedSpecies(String selectedSpecies) {
		this.selectedSpecies = selectedSpecies;
	}

	public List<Species> getSpecies() {
		if (species.size() == 0)
			species = speciesBrowser.getAll(true);
		return species;
	}

	public Species getCurrentSpecies() {
		return currentSpecies;
	}

	public void setCurrentSpecies(Species currentSpecies) {
		this.currentSpecies = currentSpecies;
	}

	public String speciesDetails() throws SpeciesRepoException {
		currentSpecies = speciesBrowser.get(selectedSpecies);
		return "success";
	}

}
