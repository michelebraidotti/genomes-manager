package org.genomesmanager.services.sequences;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.SequenceSliceException;

import java.util.List;

public interface SequencesExporter {

	public List<String> getFastaContent(Chromosome chr);
	public List<String> getMaskedFastaContent(Chromosome chr) throws SequenceSliceException;

}