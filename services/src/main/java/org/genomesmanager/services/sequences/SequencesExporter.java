package org.genomesmanager.services.sequences;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.SequenceSliceException;

public interface SequencesExporter {

	public List<String> getFastaContent(Chromosome chr);
	public List<String> getMaskedFastaContent(Chromosome chr) throws SequenceSliceException;

}