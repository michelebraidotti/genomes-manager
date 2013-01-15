package org.genomesmanager.repositories.sequences;

import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.SequenceSliceException;

public interface PseudomoleculeRepo {

	public abstract Pseudomolecule get(int pseudomolId)
			throws SequenceRepoException;
	public abstract StringBuilder getFromChromosome(int chrId, boolean masked) 
			throws SequenceSliceException;
	public abstract StringBuilder getFromChromosomeUnplaced(int chrId, boolean masked) 
			throws SequenceSliceException;
	
}
