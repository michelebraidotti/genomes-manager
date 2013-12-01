package org.genomesmanager.repositories.snps;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Snp;
import org.genomesmanager.domain.entities.Species;

public class SnpRepositoryImpl implements SnpRepositoryCustom {
	@PersistenceContext
	private EntityManager em;
	private Query q;

	public SnpRepositoryImpl() {
	}

	@Override
	public List<Snp> getAllBySpecies(Species species) {
		q = em.createNamedQuery("Snp.findAllBySpecies");
		q.setParameter("speciesId", species.getId());
		@SuppressWarnings("unchecked")
		List<Snp> snps = q.getResultList();
		return snps;
	}

	@Override
	public List<Snp> getAllByChromosome(Chromosome choromosome) {
		q = em.createNamedQuery("Snp.findAllByChromosome");
		q.setParameter("chrId", choromosome.getId());
		@SuppressWarnings("unchecked")
		List<Snp> snp = q.getResultList();
		return snp;
	}

}
