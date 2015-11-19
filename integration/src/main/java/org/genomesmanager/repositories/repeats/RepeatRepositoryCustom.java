package org.genomesmanager.repositories.repeats;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.*;

public interface RepeatRepositoryCustom {
	public abstract DnaTeRepeat findDnaTeRepeat(int dnaTeId);

	public abstract HelitronRepeat findHelitronRepeat(int helitronId);

	public abstract LineRepeat findLineRepeat(int lineId);

	public abstract LtrRepeat findLtrRepeat(int lrtId);

	public abstract MiteRepeat findMiteRepeat(int miteId);

	public abstract SineRepeat findSineRepeat(int sineId);

	public abstract UnknownRepeat findUnknRepeat(int unknId);

	public abstract List<Repeat> findAllRepeatsBySequence(int seqId);

	public abstract List<Repeat> findAllRepeatsBySequence(int seqId, RepeatsOrder repOrder);

	public abstract List<Repeat> findAllRepeatsByChromosome(int chrId, RepeatsOrder repOrder);

	public abstract List<Repeat> findAllRepeatsBySequence(int seqId, RepeatsClassification repClass);

	public abstract List<Repeat> findAllRepeatsInRange(int seqId, int start, int end);

	public abstract List<LtrRepeat> findAllLtrRepeats(int seqId);

	public abstract List<LtrRepeat> findAllLtrRepeatsInRange(int seqId, int start, int end);

	public abstract List<Repeat> findAllRepeatsBySequence(int seqId, RepeatsOrder repType, String superFamily);

	public abstract List<Repeat> findAllRepeatsBySpecies(Species sp);

	public abstract List<Repeat> findAllRepeatsBySpecies(int speciesId);

	public abstract List<Repeat> findAllRepeatsByChromosome(int chrId);

	public abstract List<Repeat> findAllRepeatsByChromosome(int chrId, RepeatsClassification repClass);

	public abstract List<Repeat> findAllRepeatsByChromosome(int chrId, RepeatsOrder repOrd, String superFamily);

	public abstract List<Object[]> findAllRepeatsWithParents();

	public Long countChildren(int repId);

	public Repeat getParent(int repId);

	public void validatePosition(Repeat repeat) throws RepeatException;

	public void validateUpdate(Repeat rep) throws RepeatException;

	public void updateContainedElementsCount(Repeat repeat);
}
