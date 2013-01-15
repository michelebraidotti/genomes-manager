package org.genomesmanager.services.sequences;

import java.util.List;

import org.genomesmanager.common.formats.SimpleFasta;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.repositories.species.SpeciesRepoException;

public interface ScaffoldsExporter {

	public abstract List<SimpleFasta> getAllSequencesBySpecies(
			String speciesDefinition, Boolean maskSequence)
			throws SpeciesRepoException, SequenceSliceException;

	public abstract List<SimpleFasta> getAllSequencesByChromosome(int chrId,
			Boolean maskSequence) throws SpeciesRepoException,
			SequenceSliceException;

}