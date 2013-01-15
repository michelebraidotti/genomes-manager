package org.genomesmanager.services.sequences;

import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.repositories.sequences.SequenceRepoException;

public interface PseudomoleculeExporter {

	public abstract Pseudomolecule get(int id, boolean masked)
			throws SequenceSliceException, SequenceRepoException;

}