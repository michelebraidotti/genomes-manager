package org.genomesmanager.repositories.sequences;

import java.util.List;

import org.genomesmanager.domain.dtos.SequenceName;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;

public interface SequenceRepositoryCustom {
	public abstract int getLength(int seqId);

	public abstract Sequence findLatest(String seqName);

	public abstract List<SequenceName> findNamesByChromosome(
			Chromosome chromosome);

	public abstract List<Sequence> findBySpecies(Species species);
}
