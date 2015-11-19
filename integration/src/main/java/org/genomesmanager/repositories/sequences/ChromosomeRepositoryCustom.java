package org.genomesmanager.repositories.sequences;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;

/**
 * Created by michele on 11/19/15.
 */
public interface ChromosomeRepositoryCustom {
    public Chromosome findByChromosomeNumberAndSpecies(String chrNumber, Species sp);
}
