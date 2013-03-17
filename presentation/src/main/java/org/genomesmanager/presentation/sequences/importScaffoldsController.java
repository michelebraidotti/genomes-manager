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
import org.genomesmanager.common.formats.ScaffoldInfo;
import org.genomesmanager.common.formats.SimpleFasta;
import org.genomesmanager.common.parsers.FastaLinesToSimpleFasta;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.presentation.FileUpload;
import org.genomesmanager.repositories.species.SpeciesNotFound;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.sequences.PseudomoleculeImporterException;
import org.genomesmanager.services.sequences.ScaffoldsImporter;
import org.genomesmanager.services.sequences.ScaffoldsImporterException;
import org.genomesmanager.services.species.SpeciesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component("importScaffolds")
@Scope("session")
public class importScaffoldsController extends FileUpload {
	@Autowired
	private ScaffoldsImporter scaffoldsImporter;
	@Autowired
	private SpeciesManager speciesManager;
	private String speciesDefinition = "";
	private Species speciesSeleced;
	private List<ScaffoldInfo> scaffoldsInfo = new ArrayList<ScaffoldInfo>();
	private List<SimpleFasta> fastas = new ArrayList<SimpleFasta>();

	public String getSpeciesDefinition() {
		return speciesDefinition;
	}

	public void setSpeciesDefinition(String speciesDefinition) {
		this.speciesDefinition = speciesDefinition;
	}
	
	public Species getSpeciesSeleced() {
		return speciesSeleced;
	}

	public void setSpeciesSeleced(Species speciesSeleced) {
		this.speciesSeleced = speciesSeleced;
	}

	public List<ScaffoldInfo> getScaffoldsInfo() {
		return scaffoldsInfo;
	}

	public void setScaffoldsInfo(List<ScaffoldInfo> scaffoldsInfo) {
		this.scaffoldsInfo = scaffoldsInfo;
	}

	public List<SimpleFasta> getFastas() {
		return fastas;
	}

	public void setFastas(List<SimpleFasta> fastas) {
		this.fastas = fastas;
	}

	public void speciesChanged(ValueChangeEvent e) throws SpeciesNotFound,
			SpeciesRepoException {
		speciesDefinition = e.getNewValue().toString();
		speciesSeleced = speciesManager.get(speciesDefinition);
	}

	public void importScaffolds() {
		try {
			scaffoldsImporter.importScaffoldsWithInfo(scaffoldsInfo, fastas,
					speciesSeleced);
		} catch (ScaffoldsImporterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String doUpload() {
		try {
			if (scaffoldsInfo.size() == 0) {
				// load manifest and reset fasta
				UploadedFile file = getFile();
				FileInputStream fstream = new FileInputStream(
						file.getFilename());
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));

				List<String> lines = new ArrayList<String>();
				String line;
				while ((line = br.readLine()) != null)
					scaffoldsInfo.add(new ScaffoldInfo(line));
				in.close();
				fastas = new ArrayList<SimpleFasta>();
			} else if (fastas.size() == 0) {
				// load fasta
				UploadedFile file = getFile();
				FileInputStream fstream = new FileInputStream(
						file.getFilename());
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));

				List<String> lines = new ArrayList<String>();
				String line;
				while ((line = br.readLine()) != null)
					lines.add(line);
				in.close();

				fastas = FastaLinesToSimpleFasta.GetSimpleFastas(lines);
			} else {
				// do nothing, should be unreachable
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String importSequences() {
//		if ( version.equals("")) {
//			FacesMessage fm = new FacesMessage("Version cannot be empty");
//            FacesContext.getCurrentInstance().addMessage("Error saving pseudomolecules:", fm);
//			return "/sequences/importByChromosome.xhtml";
//		}
//		try {
//			Chromosome chromosome = chromosomeRepo.get(chromosomeSelected, speciesSeleced);
//			pseudomoleculeImporter.importPseudomolecule(chromosome.getId(), getSimpleFasta(), getVersion());
//			cleanUp();
//			return "/sequences/home.xhtml";
//		} catch (DataIntegrityViolationException e) {
//			FacesMessage fm = new FacesMessage(e.getMessage());
//            FacesContext.getCurrentInstance().addMessage("Error saving pseudomolecules:", fm);
//			return "/sequences/importByChromosome.xhtml";
//		} catch (PseudomoleculeImporterException e) {
//			FacesMessage fm = new FacesMessage(e.getMessage());
//            FacesContext.getCurrentInstance().addMessage("Error saving pseudomolecules:", fm);
//			return "/sequences/importByChromosome.xhtml";
//		}
		return "";
	}
	
	public String cancel() {
		cleanUp();
		return "/sequences/home.xhtml";
	}
	
	private void cleanUp() {
		scaffoldsInfo = new ArrayList<ScaffoldInfo>();
		fastas = new ArrayList<SimpleFasta>();
	}
}
