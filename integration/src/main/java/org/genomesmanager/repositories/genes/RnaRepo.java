package org.genomesmanager.repositories.genes;

import org.genomesmanager.domain.entities.Rna;

public interface RnaRepo {

	public abstract Rna get(int id);

	public abstract Rna get(String name);

	public abstract void insert(Rna newRna);

	public abstract void update(Rna rna);

	public abstract void delete(int id);

	public abstract void delete(Rna rna);

}