package org.genomesmanager.repositories.sequences;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;

public interface ChromosomeRepo {

	public abstract void delete(Chromosome chr);

	public abstract void deleteByKey(int chrId);

	public abstract Chromosome get(int chrId) throws ChromosomeRepoException;

	public abstract Chromosome getGreedy(int chrId)
			throws ChromosomeRepoException;

	public abstract Chromosome get(String number, Species sp);

	public abstract void insert(Chromosome chr);

	public abstract void update(Chromosome chr);

}