package org.genomesmanager.services.repeats;

import org.genomesmanager.domain.dtos.LtrRepeatsPresenceInSativaStats;
import org.genomesmanager.domain.dtos.RepeatsStatsByClassification;

import java.util.List;

public interface RepeatsStats {

	public abstract List<RepeatsStatsByClassification> countRepeatsBasesByChromosome(
			int chrId);

	public abstract List<RepeatsStatsByClassification> countRepeatsAndBases();

	public abstract Long countAllLtrs();

	public abstract Long countAllLtrNucleotides();

	public abstract Long countCompleteLtrs();

	public abstract Long countSoloLtrs();

	public abstract Long countTruncatedLtrs();

	public abstract List<LtrRepeatsPresenceInSativaStats> presenceInSativaStatsCompleteLtrs();

	public abstract List<LtrRepeatsPresenceInSativaStats> presenceInSativaStatsSoloLtrs();

	public abstract Long countAllDnaTes();

	public abstract Long countAllDnaTeNucleotides();

	public abstract Long countAllCompleteHelitrons();

	public abstract Long countAllCompleteHelitronsNucleotides();

	public abstract Long countAllPartialHelitrons();

	public abstract Long countAllPartialHelitronsNucleotides();

	public abstract Long countPotAutonHelitrons();

	public abstract Long countPotCdsCountHelitrons();

	public abstract Long countOrfCountHelitrons();

	public abstract Long countAllLines();

	public abstract Long countAllLinesNucleotides();

	public abstract Long countAllMites();

	public abstract Long countAllMitesNucleotides();

	public abstract Long countAllSines();

	public abstract Long countAllSinesNucleotides();

	public abstract Long countAllUnkns();

}