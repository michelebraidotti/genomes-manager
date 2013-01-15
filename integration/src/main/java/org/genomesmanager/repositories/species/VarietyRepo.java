package org.genomesmanager.repositories.species;

import org.genomesmanager.domain.entities.Variety;

public interface VarietyRepo {

	public abstract Variety get(String name) throws VarietyRepoException;

	public abstract void insert(Variety v);

	public abstract void update(Variety v);

	public abstract void delete(Variety v);

	public abstract void deleteByKey(String name);

}