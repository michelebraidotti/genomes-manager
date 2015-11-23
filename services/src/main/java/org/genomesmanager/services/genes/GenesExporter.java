package org.genomesmanager.services.genes;

import org.genomesmanager.common.formats.AgiExportType;
import org.genomesmanager.domain.dtos.CannotParseSpeciesDefinitionException;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;

import java.util.List;

public interface GenesExporter {

	public abstract List<String> getFileContent();

	public abstract void setGenesList(Chromosome chr);

	public abstract void setGenesList(Species sp);

	public abstract void setGenesList(String speciesDefinition)
			throws CannotParseSpeciesDefinitionException;

	public abstract void setFileContent(AgiExportType expType,
			Boolean usingPseudomolCoordinates) throws GenesExporterException;

}