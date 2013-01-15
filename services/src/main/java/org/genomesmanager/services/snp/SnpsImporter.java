package org.genomesmanager.services.snp;

import java.util.List;

import org.genomesmanager.domain.entities.Individual;
import org.genomesmanager.domain.entities.Snp;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.repositories.species.VarietyRepoException;

public interface SnpsImporter {

	public abstract List<Snp> getSnps();

	public abstract List<Individual> getIndividuals();

	public abstract void setIndividuals(List<Integer> ids);

	public abstract List<String> getErrors();

	public abstract List<String> getWarnings();

	public abstract void reset();

	public abstract void buildIndividuals(List<String> varieties, String descr)
			throws VarietyRepoException;

	public abstract void parseMipsSnps(List<String> lines)
			throws SequenceRepoException;

	public abstract void save();

}