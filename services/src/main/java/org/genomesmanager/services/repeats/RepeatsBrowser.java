package org.genomesmanager.services.repeats;

import java.util.List;

import org.genomesmanager.domain.entities.DnaTeRepeat;
import org.genomesmanager.domain.entities.HelitronRepeat;
import org.genomesmanager.domain.entities.LineRepeat;
import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.MiteRepeat;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsOrder;
import org.genomesmanager.domain.entities.SineRepeat;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.domain.entities.UnknownRepeat;
import org.genomesmanager.repositories.repeats.RepeatRepoException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/repeatsBrowser")
public interface RepeatsBrowser extends RemoteService {

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

	public abstract List<Repeat> getAllBySpecies(SpeciesPK id);

	public abstract List<Repeat> getAllByChromosome(int chrId);

	public abstract List<Repeat> getAllByChromosome(int chrId,
			RepeatsClassification repClass);

	public abstract List<Repeat> getAllByChromosome(int chrId,
			RepeatsOrder repOrd, String superFamily);

}