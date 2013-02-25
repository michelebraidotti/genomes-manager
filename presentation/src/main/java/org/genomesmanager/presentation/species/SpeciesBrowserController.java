package org.genomesmanager.presentation.species;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.species.SpeciesBrowser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("speciesBrowser")
@Scope("session")
public class SpeciesBrowserController {
	@Autowired
	private SpeciesBrowser speciesBrowser;
	private List<Species> species = new ArrayList<Species>();
	private String selectedSpecies;
	private List<String> selectedChromosomes = new ArrayList<String>();;
	private String newChromosome;
	private Species currentSpecies;

	public SpeciesBrowserController() {
	}

	public String getSelectedSpecies() {
		return selectedSpecies;
	}

	public void setSelectedSpecies(String selectedSpecies) {
		this.selectedSpecies = selectedSpecies;
	}
	
	public List<String> getSelectedChromosomes() {
		return selectedChromosomes;
	}

	public void setSelectedChromosomes(List<String> selectedChromosomes) {
		this.selectedChromosomes = selectedChromosomes;
	}

	public String getNewChromosome() {
		return newChromosome;
	}

	public void setNewChromosome(String newChromosome) {
		this.newChromosome = newChromosome;
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
	
	public String speciesEdit() throws SpeciesRepoException {
		currentSpecies = speciesBrowser.get(selectedSpecies);
		currentSpecies.chromosomesList(", ");
		return "edit";
	}
	
	public String speciesNew() throws SpeciesRepoException {
		SpeciesPK id = new SpeciesPK();
		currentSpecies = new Species();
		currentSpecies.setId(id);
		return "new";
	}
	
	public String addChromosome() {
		if (newChromosome != null && ! newChromosome.equals("") ) {
			Chromosome chromosome = new Chromosome();
			chromosome.setNumber(newChromosome);
			chromosome.setSpecies(currentSpecies);
			currentSpecies.getChromosomes().add(chromosome);
		}
		return "speciesEdit.xhtml";
	}
	
	public String removeChromosomes() {
		List<Chromosome> chromosomesToRemove = new ArrayList<Chromosome>();
		for (String toRemove:selectedChromosomes) {
			for (Chromosome c:currentSpecies.getChromosomes()) {
				if (c.getNumber().equals(toRemove)) 
					chromosomesToRemove.add(c);
			}
		}
		for (Chromosome c:chromosomesToRemove) {
			currentSpecies.getChromosomes().remove(c);
		}
		selectedChromosomes = new ArrayList<String>();
		return "speciesEdit.xhtml";
	}
}
