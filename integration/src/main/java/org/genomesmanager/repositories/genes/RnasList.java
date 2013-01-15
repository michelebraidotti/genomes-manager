package org.genomesmanager.repositories.genes;

import java.util.List;

import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.domain.entities.SpeciesPK;

public interface RnasList {

	public abstract List<Rna> getAllBySpecies(SpeciesPK id);

	public abstract List<Rna> getAllByChromosome(int chrId);

}