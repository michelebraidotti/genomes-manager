package org.genomesmanager.services.repeats;

import org.genomesmanager.domain.entities.IntervalFeatureException;
import org.genomesmanager.domain.entities.OutOfBoundsException;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatException;

/**
 * Created by michele on 9/25/15.
 */
public interface RepeatsService {

    public Repeat save(Repeat repeat) throws RepeatException, OutOfBoundsException, IntervalFeatureException;
}
