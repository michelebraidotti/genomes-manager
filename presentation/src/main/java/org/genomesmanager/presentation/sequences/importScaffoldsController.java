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
import org.genomesmanager.common.formats.ScaffoldInfoException;
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
	private List<ExtendedScaffoldInfo> scaffoldsInfo = new ArrayList<ExtendedScaffoldInfo>();
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

	public List<ExtendedScaffoldInfo> getScaffoldsInfo() {
		return scaffoldsInfo;
	}

	public void setScaffoldsInfo(List<ExtendedScaffoldInfo> scaffoldsInfo) {
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
		cleanUp();
	}

	public void importScaffolds() {
		try {
			List<ScaffoldInfo> infos = new ArrayList<ScaffoldInfo>();
			for (ExtendedScaffoldInfo es : scaffoldsInfo) {
				infos.add(new ScaffoldInfo(es.getName(), es.getChr(), es
						.getOrder()));
			}
			scaffoldsImporter.importScaffoldsWithInfo(infos, fastas,
					speciesSeleced);
		} catch (ScaffoldsImporterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String doUpload() {
		BufferedReader br = null;
		try {
			UploadedFile file = getFile();
			FileInputStream fstream = new FileInputStream(file.getFilename());
			DataInputStream in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			if (scaffoldsInfo.size() == 0) {
				// load manifest and reset fasta
				String line;
				while ((line = br.readLine()) != null) {
					try {
						ExtendedScaffoldInfo esi = new ExtendedScaffoldInfo(
								line);
						int chrId = Integer.parseInt(esi.getChr());
						String chrDescr = speciesSeleced + ", chromosome "
								+ speciesSeleced.getChromosome(chrId);
						esi.setChrDescr(chrDescr);
						scaffoldsInfo.add(esi);
					} catch (ScaffoldInfoException e) {
						FacesMessage fm = new FacesMessage(e.getMessage());
						FacesContext.getCurrentInstance().addMessage(
								"Error parsing scaffod info:", fm);
						return "/sequences/importScaffolds.xhtml";
					}
				}
				fastas = new ArrayList<SimpleFasta>();
			} else if (fastas.size() == 0) {
				List<String> lines = new ArrayList<String>();
				String line;
				while ((line = br.readLine()) != null)
					lines.add(line);
				in.close();

				fastas = FastaLinesToSimpleFasta.GetSimpleFastas(lines);
			} else {
				// do nothing, should be unreachable
			}
			in.close();
		} catch (FileNotFoundException e) {
			FacesMessage fm = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("Error:", fm);
			return "/sequences/importScaffolds.xhtml";
		} catch (IOException e) {
			FacesMessage fm = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("Error:", fm);
			return "/sequences/importScaffolds.xhtml";
		}
		return "/sequences/importScaffolds.xhtml";
	}

	public String importSequences() {
		importScaffolds();
		return "/sequences/home.xhtml";
	}

	public String clear() {
		cleanUp();
		return "/sequences/importScaffolds.xhtml";
	}

	public String cancel() {
		cleanUp();
		return "/sequences/home.xhtml";
	}

	private void cleanUp() {
		setFile(null);
		scaffoldsInfo = new ArrayList<ExtendedScaffoldInfo>();
		fastas = new ArrayList<SimpleFasta>();
	}
}
