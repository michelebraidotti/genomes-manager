package org.genomesmanager.services.genes;

import java.util.List;

import org.genomesmanager.domain.entities.Rna;

public interface RnasImporter {

	public abstract List<Rna> getRnas();

	public abstract List<String> getErrors();

	public abstract List<String> getWarnings();

	public abstract void reset();

	public abstract void parseMipsGenePredictionGff3(List<String> lines);

	public abstract void save();

}