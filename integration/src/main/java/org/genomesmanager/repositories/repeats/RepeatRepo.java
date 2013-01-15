package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.DnaTeRepeat;
import org.genomesmanager.domain.entities.HelitronRepeat;
import org.genomesmanager.domain.entities.LineRepeat;
import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.MiteRepeat;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.SineRepeat;
import org.genomesmanager.domain.entities.UnknownRepeat;

public interface RepeatRepo {

	public abstract void delete(Repeat repeat);

	public abstract void deleteByKey(int repId);

	public abstract Repeat get(int repId) throws RepeatRepoException;

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

	public abstract Repeat getNew(RepeatsClassification repClass)
			throws RepeatRepoException;

	public abstract void insert(Repeat repeat) throws RepeatRepoException;

	public abstract void update(Repeat repeat) throws RepeatRepoException;

	public abstract void validatePosition(Repeat repeat)
			throws RepeatRepoException;

	public abstract void validateUpdate(Repeat rep) throws RepeatRepoException;

	public abstract Long countChildren(int repId) throws RepeatRepoException;

	public abstract Repeat getParent(int repId);

}