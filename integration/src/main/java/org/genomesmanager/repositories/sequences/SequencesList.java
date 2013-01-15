package org.genomesmanager.repositories.sequences;

import java.util.List;

import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.SequenceName;

public interface SequencesList {
	public List<Sequence> getAllByChromosome(int chrId);
	public List<SequenceName> getAllNamesByChromosome(int chrId);
}