package org.genomesmanager.repositories.genes;

import org.genomesmanager.domain.entities.Exon;

public interface ExonRepo {

	public abstract Exon get(int id);

	public abstract Exon get(String name);

	public abstract void insert(Exon exon);

	public abstract void update(Exon exon);

	public abstract void delete(int id);

	public abstract void delete(Exon exon);

}