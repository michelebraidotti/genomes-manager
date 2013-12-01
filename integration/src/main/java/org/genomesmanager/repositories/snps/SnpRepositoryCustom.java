package org.genomesmanager.repositories.snps;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Snp;
import org.genomesmanager.domain.entities.Species;

public interface SnpRepositoryCustom {
	public abstract List<Snp> getAllBySpecies(Species species);
	public abstract List<Snp> getAllByChromosome(Chromosome chromosome);
}