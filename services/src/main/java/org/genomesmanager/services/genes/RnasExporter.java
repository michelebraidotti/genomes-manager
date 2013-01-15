package org.genomesmanager.services.genes;

import java.util.List;

import org.genomesmanager.common.formats.AgiExportType;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.species.SpeciesRepoException;

public interface RnasExporter {

	public abstract List<String> getFileContent();

	public abstract void setRnasList(Chromosome chr);

	public abstract void setRnasList(Species sp);

	public abstract void setRnasList(String speciesDefinition)
			throws SpeciesRepoException;

	public abstract void setFileContent(AgiExportType expType,
			Boolean usingPseudomolCoordinates) throws RnasExporterException;

}