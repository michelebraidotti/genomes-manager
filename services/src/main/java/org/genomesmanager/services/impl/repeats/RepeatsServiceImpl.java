package org.genomesmanager.services.impl.repeats;

import org.genomesmanager.domain.entities.*;
import org.genomesmanager.repositories.repeats.RepeatRepository;
import org.genomesmanager.services.repeats.RepeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by michele on 9/25/15.
 */
@Service
public class RepeatsServiceImpl implements RepeatsService {
    @Autowired
    private RepeatRepository repeatRepository;

    @Override
    public Repeat save(Repeat repeat) throws RepeatException, OutOfBoundsException, IntervalFeatureException {
        validate(repeat);
        if ( repeat.getId() != 0 ) { // we are updating
            repeatRepository.validateUpdate(repeat);
            repeatRepository.updateContainedElementsCount(repeat);
        }
        return repeatRepository.save(repeat);
    }

    private void validate(Repeat repeat) throws IntervalFeatureException, OutOfBoundsException, RepeatException {
        repeat.validate();
        if (repeat instanceof LtrRepeat) {
            ((LtrRepeat) repeat).checkIsSolo();
        }

    }

    @Override
    public void delete(Repeat repeat) {
        repeatRepository.delete(repeat);
    }
    
    @Override
    public void deleteByKey(int repId) {
        repeatRepository.delete(repId);
    }
    
    @Override
    public Repeat get(int repId) {
        return repeatRepository.findOne(repId);
    }
    
    @Override
    public DnaTeRepeat getDnaTe(int dnaTeId) {
        return repeatRepository.findDnaTeRepeat(dnaTeId);
    }
    
    @Override
    public HelitronRepeat getHelitron(int helitronId) {
        return repeatRepository.findHelitronRepeat(helitronId);
    }
    
    @Override
    public LineRepeat getLine(int lineId) {
        return repeatRepository.findLineRepeat(lineId);
    }
    
    @Override
    public LtrRepeat getLtr(int lrtId) {
        return repeatRepository.findLtrRepeat(lrtId);
    }
    
    @Override
    public MiteRepeat getMite(int miteId) {
        return repeatRepository.findMiteRepeat(miteId);
    }
    
    @Override
    public SineRepeat getSine(int sineId) {
        return repeatRepository.findSineRepeat(sineId);
    }
    
    @Override
    public UnknownRepeat getUnkn(int unknId) {
        return repeatRepository.findUnknRepeat(unknId);
    }
    
    @Override
    public void validatePosition(Repeat repeat) throws RepeatException {
        repeatRepository.validatePosition(repeat);
    }
    
    @Override
    public void validateUpdate(Repeat rep) throws RepeatException {
        repeatRepository.validateUpdate(rep);
    }
    
    @Override
    public Long countChildren(int repId)  {
        return repeatRepository.countChildren(repId);
    }
    
    @Override
    public Repeat getParent(int repId) {
        return repeatRepository.getParent(repId);
    }
    
    @Override
    public List<Repeat> getAllBySequence(int seqId) {
        return repeatRepository.findAllRepeatsBySequence(seqId);
    }
    
    @Override
    public List<Repeat> getAllBySequence(int seqId, RepeatsOrder repOrder) {
        return repeatRepository.findAllRepeatsBySequence(seqId, repOrder);
    }
    
    @Override
    public List<Repeat> getAllByChromosome(int chrId, RepeatsOrder repOrder) {
        return repeatRepository.findAllRepeatsByChromosome(chrId, repOrder);
    }
    
    @Override
    public List<Repeat> getAllBySequence(int seqId,
                                         RepeatsClassification repClass) {
        return repeatRepository.findAllRepeatsBySequence(seqId, repClass);
    }
    
    @Override
    public List<Repeat> getAllInRange(int seqId, int start, int end) {
        return repeatRepository.findAllRepeatsInRange(seqId, start, end);
    }
    
    @Override
    public List<LtrRepeat> getAllLtr(int seqId) {
        return repeatRepository.findAllLtrRepeats(seqId);
    }
    
    @Override
    public List<LtrRepeat> getAllLtrInRange(int seqId, int start, int end) {
        return repeatRepository.findAllLtrRepeatsInRange(seqId, start, end);
    }
    
    @Override
    public List<Repeat> getAllBySequence(int seqId, RepeatsOrder repType,
                                         String superFamily) {
        return repeatRepository.findAllRepeatsBySequence(seqId, repType, superFamily);
    }
    
    @Override
    public List<Repeat> getAllBySpecies(int id) {
        return repeatRepository.findAllRepeatsBySpecies(id);
    }
    
    @Override
    public List<Repeat> getAllByChromosome(int chrId) {
        return repeatRepository.findAllRepeatsByChromosome(chrId);
    }
    
    @Override
    public List<Repeat> getAllByChromosome(int chrId,
                                           RepeatsClassification repClass) {
        return repeatRepository.findAllRepeatsByChromosome(chrId, repClass);
    }
    
    @Override
    public List<Repeat> getAllByChromosome(int chrId, RepeatsOrder repOrd,
                                           String superFamily) {
        return repeatRepository.findAllRepeatsByChromosome(chrId, repOrd, superFamily);
    }


}
