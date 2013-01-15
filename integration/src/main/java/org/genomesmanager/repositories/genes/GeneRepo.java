package org.genomesmanager.repositories.genes;

import org.genomesmanager.domain.entities.Gene;

public interface GeneRepo {

	public abstract Gene get(int id);

	public abstract Gene get(String name);

	public abstract Gene getEager(int id);

	public abstract Gene setRelationsEagerly(Gene g);

	public abstract void insert(Gene newGene);

	public abstract void update(Gene gene);

	public abstract void delete(int id);

	public abstract void delete(Gene gene);

}