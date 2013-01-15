package org.genomesmanager.repositories.snps;

import java.util.List;

import org.genomesmanager.domain.entities.Snp;
import org.genomesmanager.domain.entities.SpeciesPK;

public interface SnpsList {

	public abstract List<Snp> getAllBySpecies(SpeciesPK id);

	public abstract List<Snp> getAllByChromosome(int chrId);

}