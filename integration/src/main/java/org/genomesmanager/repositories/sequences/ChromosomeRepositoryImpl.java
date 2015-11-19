package org.genomesmanager.repositories.sequences;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by michele on 11/19/15.
 */
public class ChromosomeRepositoryImpl implements ChromosomeRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    private Query q;

    @Override
    public Chromosome findByChromosomeNumberAndSpecies(String chrNumber, Species sp) {
        q = em.createNamedQuery("Chromosome.byNumAndSpecies");
        q.setParameter("number", chrNumber);
        q.setParameter("species", sp);
        return (Chromosome) q.getSingleResult();
    }
}
