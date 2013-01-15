package org.genomesmanager.repositories.species;

import org.genomesmanager.domain.entities.Individual;

public interface IndividualRepo {

	public abstract Individual get(int id);

	public abstract void insert(Individual i);

	public abstract void update(Individual i);

	public abstract void delete(Individual i);

	public abstract void delete(int id);

}