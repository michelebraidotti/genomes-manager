package org.genomesmanager.services.impl.repeats;

import org.genomesmanager.domain.entities.*;
import org.genomesmanager.repositories.repeats.RepeatRepository;
import org.genomesmanager.services.repeats.RepeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
