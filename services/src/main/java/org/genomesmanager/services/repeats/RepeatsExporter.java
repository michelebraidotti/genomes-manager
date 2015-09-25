package org.genomesmanager.services.repeats;

import java.util.List;

import org.genomesmanager.common.formats.AgiExportType;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsOrder;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.repositories.species.SpeciesRepoException;

public interface RepeatsExporter {

	public abstract List<String> getFileContent();

	public abstract void setRepeatsListBySpecies(String speciesDefinition)
			throws SpeciesRepoException;

	public abstract List<Repeat> getRepeatsList();

	public abstract void setRepeatsList(Chromosome chr);

	public abstract void setRepeatsList(Chromosome chr, RepeatsOrder repOrd);

	public abstract void setRepeatsList(Chromosome chr,
			RepeatsClassification repClass);

	public abstract void setRepeatsList(Chromosome chr, RepeatsOrder repOrd,
			String superFamily);

	public abstract void setRepeatsList(Sequence seq);

	public abstract void setRepeatsList(List<Integer> repIds)
			throws RepeatRepoException;

	public abstract void setRepeatsList(Sequence seq,
			RepeatsClassification repClass);

	public abstract void setRepeatsList(Sequence seq, RepeatsOrder repOrd,
			String superFamily);

	public abstract void setRepeatsList(Sequence seq, RepeatsOrder repOrd);

	public abstract int getNOfRepeats();

	public abstract void setFileContent(AgiExportType expType)
			throws RepeatsExporterException;

	public abstract void setFileContent(AgiExportType expType,
			Boolean usingPseudomolCoordinates) throws RepeatsExporterException,
			SequenceRepoException;

	public abstract void exportFlankingRegions(int flankingLength);

}