package org.genomesmanager.presentation.sequences;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.genomesmanager.bioprograms.formats.SimpleFasta;
import org.genomesmanager.bioprograms.parsers.FastaLinesToSimpleFasta;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.presentation.FileUpload;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.species.SpeciesNotFound;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.sequences.PseudomoleculeImporter;
import org.genomesmanager.services.sequences.PseudomoleculeImporterException;
import org.genomesmanager.services.species.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component("importPseudomolByChromosome")
@Scope("session")
public class ImportPseudomolByChromosomeController extends FileUpload {
	@Autowired
	private PseudomoleculeImporter pseudomoleculeImporter;
	@Autowired
	private SpeciesService speciesManager;
	@Autowired
	private ChromosomeRepository chromosomeRepo;
	private String speciesDefinition = "";
	private String chromosomeSelected = "";
	private Species speciesSeleced;
	private List<SimpleFasta> simpleFasta =  new ArrayList<SimpleFasta>();
	private String version = "";

	public ImportPseudomolByChromosomeController() {
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

	public List<Chromosome> getChromosomes() {
		if (speciesSeleced == null)
			return new ArrayList<Chromosome>();
		return this.speciesSeleced.getChromosomes();
	}

	public List<SimpleFasta> getSimpleFasta() {
		return simpleFasta;
	}

	public void setSimpleFasta(List<SimpleFasta> simpleFasta) {
		this.simpleFasta = simpleFasta;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void speciesChanged(ValueChangeEvent e) throws SpeciesNotFound, SpeciesRepoException {
		speciesDefinition = e.getNewValue().toString();
		speciesSeleced = speciesManager.get(speciesDefinition);
		chromosomeSelected = speciesSeleced.getChromosomes().size() > 0 ? 
			speciesSeleced.getChromosomes().get(0).getNumber() : "";
		simpleFasta =  new ArrayList<SimpleFasta>();
	}
	
	@Override
	public String doUpload() {
		try {
			UploadedFile file = getFile();
			FileInputStream fstream = new FileInputStream(file.getFilename());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			List<String> lines = new ArrayList<String>();
			String line;
			while ((line = br.readLine()) != null)
				lines.add(line);
			in.close();

			setSimpleFasta(FastaLinesToSimpleFasta.GetSimpleFastas(lines));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/sequences/importPseudomolByChromosome.xhtml";
	}
	
	public String importSequences() {
		if ( version.equals("")) {
			FacesMessage fm = new FacesMessage("Version cannot be empty");
            FacesContext.getCurrentInstance().addMessage("Error saving pseudomolecules:", fm);
			return "/sequences/importPseudomolByChromosome.xhtml";
		}
		try {
			Chromosome chromosome = chromosomeRepo.get(chromosomeSelected, speciesSeleced);
			pseudomoleculeImporter.importPseudomolecule(chromosome.getId(), getSimpleFasta(), getVersion());
			cleanUp();
			return "/sequences/home.xhtml";
		} catch (DataIntegrityViolationException e) {
			FacesMessage fm = new FacesMessage(e.getMessage());
            FacesContext.getCurrentInstance().addMessage("Error saving pseudomolecules:", fm);
			return "/sequences/importPseudomolByChromosome.xhtml";
		} catch (PseudomoleculeImporterException e) {
			FacesMessage fm = new FacesMessage(e.getMessage());
            FacesContext.getCurrentInstance().addMessage("Error saving pseudomolecules:", fm);
			return "/sequences/importPseudomolByChromosome.xhtml";
		}
	}
	
	public String cancel() {
		cleanUp();
		return "/sequences/home.xhtml";
	}
	
	private void cleanUp() {
		setSimpleFasta(new ArrayList<SimpleFasta>());
		setVersion("");
	}

}
