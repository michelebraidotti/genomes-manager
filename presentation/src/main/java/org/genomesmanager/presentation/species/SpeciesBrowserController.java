package org.genomesmanager.presentation.species;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Species;
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

	public SpeciesBrowserController() {
	}

	public List<Species> getSpecies() {
		if (species.size() == 0)
			species = speciesBrowser.getAll(true);
		return species;
	}

	public String cancel() {
		return "/species/home.xhtml";
	}
}
