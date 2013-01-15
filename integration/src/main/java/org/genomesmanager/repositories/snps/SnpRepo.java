package org.genomesmanager.repositories.snps;

import org.genomesmanager.domain.entities.Snp;

public interface SnpRepo {

	public abstract Snp get(int id);

	public abstract void insert(Snp newSnp);

	public abstract void update(Snp snp);

	public abstract void delete(int id);

	public abstract void delete(Snp snp);

}