package org.genomesmanager.services.genes;

import java.util.List;

import org.genomesmanager.domain.entities.Gene;

public interface GenesImporter {

	public abstract List<Gene> getGenes();

	public abstract List<String> getErrors();

	public abstract List<String> getWarnings();

	public abstract void reset();

	public abstract void parseMipsGenePredictionGff3(List<String> lines);

	public abstract void save();

}