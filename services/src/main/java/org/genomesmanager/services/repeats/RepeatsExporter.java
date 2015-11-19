package org.genomesmanager.services.repeats;

import java.util.List;

import org.genomesmanager.common.formats.AgiExportType;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsOrder;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.dtos.CannotParseSpeciesDefinitionException;

public interface RepeatsExporter {

	public abstract List<String> getFileContent();

	public abstract void loadRepeatsListBySpecies(String speciesDefinition)
			throws CannotParseSpeciesDefinitionException;

	public abstract List<Repeat> getRepeats();

	public abstract void loadRepeatsList(Chromosome chr);

	public abstract void loadRepeatsList(Chromosome chr, RepeatsOrder repOrd);

	public abstract void loadRepeatsList(Chromosome chr,
										 RepeatsClassification repClass);

	public abstract void loadRepeatsList(Chromosome chr, RepeatsOrder repOrd,
										 String superFamily);

	public abstract void loadRepeatsList(Sequence seq);

	public abstract void loadRepeatsList(List<Integer> repIds);

	public abstract void loadRepeatsList(Sequence seq,
										 RepeatsClassification repClass);

	public abstract void loadRepeatsList(Sequence seq, RepeatsOrder repOrd,
										 String superFamily);

	public abstract void loadRepeatsList(Sequence seq, RepeatsOrder repOrd);

	public abstract int getNOfRepeats();

	public abstract void setFileContent(AgiExportType expType)
			throws RepeatsExporterException;

	public abstract void setFileContent(AgiExportType expType, Boolean usingPseudomolCoordinates)
			throws RepeatsExporterException;

	public abstract void exportFlankingRegions(int flankingLength);

}