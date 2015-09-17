package org.genomesmanager.services.impl.repeats;

import java.util.List;

import org.genomesmanager.domain.dtos.LtrRepeatsPresenceInSativaStats;
import org.genomesmanager.domain.dtos.RepeatsStatsByClassification;
import org.genomesmanager.repositories.repeats.RepeatsStatsRepository;
import org.genomesmanager.services.repeats.RepeatsStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("RepeatsStats")
public class RepeatsStatsImpl implements RepeatsStats {
	@Autowired
	private RepeatsStatsRepository repeatsStatsRepo;

	@Override
	public List<RepeatsStatsByClassification> countRepeatsBasesByChromosome(
			int chrId) {
		return repeatsStatsRepo.countRepeatsBasesByChromosome(chrId);
	}

	@Override
	public List<RepeatsStatsByClassification> countRepeatsAndBases() {
		return repeatsStatsRepo.countRepeatsAndBases();
	}

	@Override
	public Long countAllLtrs() {
		return repeatsStatsRepo.countAllLtrs();
	}
	
	@Override
	public Long countAllLtrNucleotides() {
		return repeatsStatsRepo.countAllLtrNucleotides();
	}

	@Override
	public Long countCompleteLtrs() {
		return repeatsStatsRepo.countCompleteLtrs();
	}

	@Override
	public Long countSoloLtrs() {
		return repeatsStatsRepo.countSoloLtrs();
	}

	@Override
	public Long countTruncatedLtrs() {
		return repeatsStatsRepo.countTruncatedLtrs();
	}

	@Override
	public List<LtrRepeatsPresenceInSativaStats> presenceInSativaStatsCompleteLtrs() {
		return repeatsStatsRepo.presenceInSativaStatsCompleteLtrs();
	}

	@Override
	public List<LtrRepeatsPresenceInSativaStats> presenceInSativaStatsSoloLtrs() {
		return repeatsStatsRepo.presenceInSativaStatsSoloLtrs();
	}

	@Override
	public Long countAllDnaTes() {
		return repeatsStatsRepo.countAllDnaTes();
	}

	@Override
	public Long countAllDnaTeNucleotides() {
		return repeatsStatsRepo.countAllDnaTeNucleotides();
	}

	@Override
	public Long countAllCompleteHelitrons() {
		return repeatsStatsRepo.countAllCompleteHelitrons();
	}

	@Override
	public Long countAllCompleteHelitronsNucleotides() {
		return repeatsStatsRepo.countAllCompleteHelitronsNucleotides();
	}

	@Override
	public Long countAllPartialHelitrons() {
		return repeatsStatsRepo.countAllPartialHelitrons();
	}

	@Override
	public Long countAllPartialHelitronsNucleotides() {
		return repeatsStatsRepo.countAllPartialHelitronsNucleotides();
	}

	@Override
	public Long countPotAutonHelitrons() {
		return repeatsStatsRepo.countPotAutonHelitrons();
	}

	@Override
	public Long countPotCdsCountHelitrons() {
		return repeatsStatsRepo.countPotCdsCountHelitrons();
	}

	@Override
	public Long countOrfCountHelitrons() {
		return repeatsStatsRepo.countOrfCountHelitrons();
	}

	@Override
	public Long countAllLines() {
		return repeatsStatsRepo.countAllLines();
	}

	@Override
	public Long countAllLinesNucleotides() {
		return repeatsStatsRepo.countAllLinesNucleotides();
	}

	@Override
	public Long countAllMites() {
		return repeatsStatsRepo.countAllMites();
	}

	@Override
	public Long countAllMitesNucleotides() {
		return repeatsStatsRepo.countAllMitesNucleotides();
	}

	@Override
	public Long countAllSines() {
		return repeatsStatsRepo.countAllSines();
	}

	@Override
	public Long countAllSinesNucleotides() {
		return repeatsStatsRepo.countAllSinesNucleotides();
	}

	@Override
	public Long countAllUnkns() {
		return repeatsStatsRepo.countAllUnkns();
	}
	
}
