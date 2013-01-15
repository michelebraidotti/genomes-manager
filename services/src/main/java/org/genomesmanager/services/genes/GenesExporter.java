package org.genomesmanager.services.genes;

import java.util.List;

import org.genomesmanager.common.formats.AgiExportType;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.species.SpeciesRepoException;

public interface GenesExporter {

	public abstract List<String> getFileContent();

	public abstract void setGreedyLoad(Boolean greedyLoad);

	public abstract void setGenesList(Chromosome chr);

	public abstract void setGenesList(Species sp);

	public abstract void setGenesList(String speciesDefinition)
			throws SpeciesRepoException;

	public abstract void setFileContent(AgiExportType expType,
			Boolean usingPseudomolCoordinates) throws GenesExporterException;

}