package org.genomesmanager.repositories.repeats;

import java.util.List;

import org.genomesmanager.domain.dtos.LtrRepeatsPresenceInSativaStats;
import org.genomesmanager.domain.dtos.RepeatsStatsByClassification;

public interface RepeatsStatsRepo {

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countRepeatsBasesByChromosome(int)
	 */
	public abstract List<RepeatsStatsByClassification> countRepeatsBasesByChromosome(
			int chrId);

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countRepeatsAndBases()
	 */
	public abstract List<RepeatsStatsByClassification> countRepeatsAndBases();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllLtrs()
	 */
	public abstract Long countAllLtrs();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllLtrNucleotides()
	 */
	public abstract Long countAllLtrNucleotides();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countCompleteLtrs()
	 */
	public abstract Long countCompleteLtrs();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countSoloLtrs()
	 */
	public abstract Long countSoloLtrs();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countTruncatedLtrs()
	 */
	public abstract Long countTruncatedLtrs();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#presenceInSativaStatsCompleteLtrs()
	 */
	public abstract List<LtrRepeatsPresenceInSativaStats> presenceInSativaStatsCompleteLtrs();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#presenceInSativaStatsSoloLtrs()
	 */
	public abstract List<LtrRepeatsPresenceInSativaStats> presenceInSativaStatsSoloLtrs();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllDnaTes()
	 */
	public abstract Long countAllDnaTes();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllDnaTeNucleotides()
	 */
	public abstract Long countAllDnaTeNucleotides();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllCompleteHelitrons()
	 */
	public abstract Long countAllCompleteHelitrons();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllCompleteHelitronsNucleotides()
	 */
	public abstract Long countAllCompleteHelitronsNucleotides();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllPartialHelitrons()
	 */
	public abstract Long countAllPartialHelitrons();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllPartialHelitronsNucleotides()
	 */
	public abstract Long countAllPartialHelitronsNucleotides();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countPotAutonHelitrons()
	 */
	public abstract Long countPotAutonHelitrons();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countPotCdsCountHelitrons()
	 */
	public abstract Long countPotCdsCountHelitrons();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countOrfCountHelitrons()
	 */
	public abstract Long countOrfCountHelitrons();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllLines()
	 */
	public abstract Long countAllLines();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllLinesNucleotides()
	 */
	public abstract Long countAllLinesNucleotides();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllMites()
	 */
	public abstract Long countAllMites();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllMitesNucleotides()
	 */
	public abstract Long countAllMitesNucleotides();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllSines()
	 */
	public abstract Long countAllSines();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllSinesNucleotides()
	 */
	public abstract Long countAllSinesNucleotides();

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllUnkns()
	 */
	public abstract Long countAllUnkns();

}