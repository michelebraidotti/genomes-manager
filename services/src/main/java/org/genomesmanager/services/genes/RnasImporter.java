package org.genomesmanager.services.genes;

import org.genomesmanager.domain.entities.Rna;

import java.util.List;

public interface RnasImporter {

	public abstract List<Rna> getRnas();

	public abstract List<String> getErrors();

	public abstract List<String> getWarnings();

	public abstract void reset();

	public abstract void parseMipsGenePredictionGff3(List<String> lines);

	public abstract void save();

}