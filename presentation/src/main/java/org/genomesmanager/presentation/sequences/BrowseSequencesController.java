package org.genomesmanager.presentation.sequences;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequencesList;
import org.genomesmanager.repositories.species.SpeciesNotFound;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.species.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("browseSequences")
@Scope("session")
public class BrowseSequencesController {
	private static String NO_SELECTION = "--";
	@Autowired
	private SpeciesService speciesManager;
	@Autowired
	private ChromosomeRepository chromosomeRepo;
	private String speciesDefinition = "";
	private String chromosomeSelected = "";
	private Species speciesSeleced = null;
	@SuppressWarnings("unused")
	private List<Chromosome> chromosomes = new ArrayList<Chromosome>();
	@Autowired
	private SequencesList sequencesList;
	private List<Sequence> sequences = new ArrayList<Sequence>();

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

	public List<Sequence> getSequences() {
		return sequences;
	}

	public void setSequences(List<Sequence> sequences) {
		this.sequences = sequences;
	}

	public void speciesChanged(ValueChangeEvent e) throws SpeciesNotFound,
			SpeciesRepoException {
		speciesDefinition = e.getNewValue().toString();
		speciesSeleced = speciesManager.get(speciesDefinition);
		chromosomeSelected = NO_SELECTION;
	}

	public void search() {
		sequences = new ArrayList<Sequence>();
		if (!chromosomeSelected.equals("")
				&& !chromosomeSelected.equals(NO_SELECTION)) {
			Chromosome c = chromosomeRepo.get(chromosomeSelected, speciesSeleced);
			sequences = sequencesList.getAllByChromosome(c.getId());
		}
		else if (speciesSeleced != null) {
			sequences = sequencesList.getAllBySpecies(speciesSeleced.getId());
		}
	}

	public void export() throws IOException {
		String filename = "export.fasta";

		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) fc
				.getExternalContext().getResponse();

		response.reset();
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ filename + "\"");

		OutputStream output = response.getOutputStream();

		for (Sequence s : sequences) {
			StringBuilder sb = new StringBuilder();
			sb.append(s.getFastaHeader() + "\n");
			sb.append(s.getSequenceText() + "\n");
			output.write(sb.toString().getBytes());
		}

		output.flush();
		output.close();

		fc.responseComplete();
	}

	public void cleanUp() {
		speciesDefinition = "";
		chromosomeSelected = "";
		speciesSeleced = null;
		chromosomes = new ArrayList<Chromosome>();
		sequences = new ArrayList<Sequence>();
	}
}
