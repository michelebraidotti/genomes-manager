package org.genomesmanager.services.repeats;

import org.genomesmanager.domain.entities.*;

import java.util.List;

/**
 * Created by michele on 9/25/15.
 */
public interface RepeatsService {

    public Repeat save(Repeat repeat) throws RepeatException, OutOfBoundsException, IntervalFeatureException;

    public abstract void delete(Repeat repeat);

    public abstract void deleteByKey(int repId);

    public abstract Repeat get(int repId);

    public abstract DnaTeRepeat getDnaTe(int dnaTeId);

    public abstract HelitronRepeat getHelitron(int helitronId);

    public abstract LineRepeat getLine(int lineId);

    public abstract LtrRepeat getLtr(int lrtId);

    public abstract MiteRepeat getMite(int miteId);

    public abstract SineRepeat getSine(int sineId);

    public abstract UnknownRepeat getUnkn(int unknId);

    public abstract void validatePosition(Repeat repeat) throws RepeatException;

    public abstract void validateUpdate(Repeat rep) throws RepeatException;

    public abstract Long countChildren(int repId);

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

    public abstract List<Repeat> getAllBySpecies(int id);

    public abstract List<Repeat> getAllByChromosome(int chrId);

    public abstract List<Repeat> getAllByChromosome(int chrId,
                                                    RepeatsClassification repClass);

    public abstract List<Repeat> getAllByChromosome(int chrId,
                                                    RepeatsOrder repOrd, String superFamily);
}
