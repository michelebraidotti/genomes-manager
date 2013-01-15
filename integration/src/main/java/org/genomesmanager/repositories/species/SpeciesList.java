package org.genomesmanager.repositories.species;

import java.util.List;

import org.genomesmanager.domain.entities.Species;

public interface SpeciesList {

	public abstract List<Species> getAll();

	public abstract List<Species> getRice();

}