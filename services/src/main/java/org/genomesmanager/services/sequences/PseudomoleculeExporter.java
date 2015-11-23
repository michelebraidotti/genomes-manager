package org.genomesmanager.services.sequences;

import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.SequenceSliceException;

public interface PseudomoleculeExporter {

	public abstract Pseudomolecule get(int id, boolean masked)
			throws SequenceSliceException;

}