package org.genomesmanager.services.sequences;

import org.genomesmanager.bioprograms.formats.SimpleFasta;
import org.genomesmanager.domain.dtos.CannotParseSpeciesDefinitionException;
import org.genomesmanager.domain.entities.SequenceSliceException;

import java.util.List;

public interface ScaffoldsExporter {

	public abstract List<SimpleFasta> getAllSequencesBySpecies(
			String speciesDefinition, Boolean maskSequence)
			throws SequenceSliceException, CannotParseSpeciesDefinitionException;

	public abstract List<SimpleFasta> getAllSequencesByChromosome(int chrId,
			Boolean maskSequence) throws SequenceSliceException;

}