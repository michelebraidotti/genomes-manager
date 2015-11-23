package org.genomesmanager.services.snp;

import org.genomesmanager.domain.entities.Individual;
import org.genomesmanager.domain.entities.Snp;

import java.util.List;

public interface SnpsImporter {

	public abstract List<Snp> getSnps();

	public abstract List<Individual> getIndividuals();

	public abstract void setIndividuals(List<Integer> ids);

	public abstract List<String> getErrors();

	public abstract List<String> getWarnings();

	public abstract void reset();

	public abstract void buildIndividuals(List<String> varieties, String descr);

	public abstract void parseMipsSnps(List<String> lines);

	public abstract void save();

}