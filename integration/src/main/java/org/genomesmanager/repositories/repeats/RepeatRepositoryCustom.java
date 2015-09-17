package org.genomesmanager.repositories.repeats;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.*;

public interface RepeatRepositoryCustom {
	public abstract DnaTeRepeat getDnaTe(int dnaTeId)
			throws RepeatRepoException;

	public abstract HelitronRepeat getHelitron(int helitronId)
			throws RepeatRepoException;

	public abstract LineRepeat getLine(int lineId) throws RepeatRepoException;

	public abstract LtrRepeat getLtr(int lrtId) throws RepeatRepoException;

	public abstract MiteRepeat getMite(int miteId) throws RepeatRepoException;

	public abstract SineRepeat getSine(int sineId) throws RepeatRepoException;

	public abstract UnknownRepeat getUnkn(int unknId)
			throws RepeatRepoException;

	public abstract List<Repeat> getAllBySequence(int seqId);

	public abstract List<Repeat> getAllBySequence(int seqId,
			RepeatsOrder repOrder);

	public abstract List<Repeat> getAllByChromosome(int chrId,
			RepeatsOrder repOrder);

	public abstract List<Repeat> getAllBySequence(int seqId,
			RepeatsClassification repClass);

	public abstract List<Repeat> getAllInRange(int seqId, int start, int end);

	public abstract List<LtrRepeat> getAllLtr(int seqId);

	public abstract List<LtrRepeat> getAllLtrInRange(int seqId, int start,
			int end);

	public abstract List<Repeat> getAllBySequence(int seqId,
			RepeatsOrder repType, String superFamily);

	public abstract List<Repeat> getAllBySpecies(Species sp);

	public abstract List<Repeat> getAllByChromosome(int chrId);

	public abstract List<Repeat> getAllByChromosome(int chrId,
			RepeatsClassification repClass);

	public abstract List<Repeat> getAllByChromosome(int chrId,
			RepeatsOrder repOrd, String superFamily);

	public Long countChildren(int repId) throws RepeatRepoException;

	public Repeat getParent(int repId);

	public void validatePosition(Repeat repeat) throws RepeatRepoException;

	public void validateUpdate(Repeat rep) throws RepeatRepoException;
}
