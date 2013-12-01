package org.genomesmanager.repositories.sequences;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Scaffold;

public class ScaffoldRepositoryImpl implements ScaffoldRepositoryCustom {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Scaffold> findAllPlacedByChromosome(Chromosome chromosome) {
		q = em.createNamedQuery("Scaffold.findAllPlacedByChromosome");
		q.setParameter("chrId", chromosome.getId());
		return q.getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Scaffold> findAllUnplacedByChromosome(Chromosome chromosome) {
		q = em.createNamedQuery("Scaffold.findAllUnplacedByChromosome");
		q.setParameter("chrId", chromosome.getId());
		return q.getResultList();
	}
	

}
