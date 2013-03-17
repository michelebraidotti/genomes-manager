package org.genomesmanager.presentation.species;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.species.SpeciesNotFound;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.species.SpeciesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("speciesManager")
@Scope("session")
public class SpeciesManagerController {
	@Autowired
	private SpeciesManager speciesManager;
	private List<String> selectedChromosomes = new ArrayList<String>();
	private String newChromosome;
	private Species species;
	private String speciesDefinition;
	private boolean editMode = false;

	public SpeciesManagerController() {
	}
	
	public String getSpeciesDefinition() {
		return speciesDefinition;
	}

	public void setSpeciesDefinition(String speciesDefinition) {
		this.speciesDefinition = speciesDefinition;
	}

	public String getNewChromosome() {
		return newChromosome;
	}

	public void setNewChromosome(String newChromosome) {
		this.newChromosome = newChromosome;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	public List<String> getSelectedChromosomes() {
		return selectedChromosomes;
	}

	public void setSelectedChromosomes(List<String> selectedChromosomes) {
		this.selectedChromosomes = selectedChromosomes;
	}

	public String speciesDetails() throws SpeciesRepoException {
		try {
			species = speciesManager.get(speciesDefinition);
		} catch (SpeciesNotFound e) {
			reportError("/species/home.xhtml", "Species '" + speciesDefinition
					+ "' not found.");
		}
		return "show";
	}

	private void reportError(String pageId, String message) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(pageId, new FacesMessage(message));
	}

	public String edit() throws SpeciesRepoException {
		try {
			species = speciesManager.get(speciesDefinition);
			editMode = true;
		} catch (SpeciesNotFound e) {
			reportError("/species/home.xhtml", "Species '" + speciesDefinition
					+ "' not found.");
		}
		species.chromosomesList(", ");
		return "edit";
	}

	public String create() {
		SpeciesPK id = new SpeciesPK();
		species = new Species();
		species.setId(id);
		return "new";
	}

	public String addChromosome() {
		if (newChromosome != null && !newChromosome.equals("")) {
			Chromosome chromosome = new Chromosome();
			chromosome.setNumber(newChromosome);
			chromosome.setSpecies(species);
			species.getChromosomes().add(chromosome);
		}
		return "speciesEdit.xhtml";
	}

	public String removeChromosomes() {
		List<Chromosome> chromosomesToRemove = new ArrayList<Chromosome>();
		for (String toRemove : selectedChromosomes) {
			for (Chromosome c : species.getChromosomes()) {
				if (c.getNumber().equals(toRemove))
					chromosomesToRemove.add(c);
			}
		}
		for (Chromosome c : chromosomesToRemove) {
			species.getChromosomes().remove(c);
		}
		selectedChromosomes = new ArrayList<String>();
		return "speciesEdit.xhtml";
	}

	public String save() throws SpeciesNotFound, SpeciesRepoException {
		if (editMode) {
			Species oldSpecies = speciesManager.get(speciesDefinition);
			speciesManager.update(oldSpecies, species);
			editMode = false;
		} else
			speciesManager.insert(species);
		return "/species/home.xhtml?faces-redirect=true";
	}

	public String delete() throws SpeciesNotFound, SpeciesRepoException {
		speciesManager.delete(speciesDefinition);
		return "/species/home.xhtml?faces-redirect=true";
	}
	
	public String cancel() {
		return "/species/home.xhtml?faces-redirect=true";
	}

}
