package org.genomesmanager.repositories.sequences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.SequenceName;
import org.genomesmanager.domain.entities.Species;

public class SequenceRepositoryImpl implements SequenceRepositoryCustom {
	@PersistenceContext
	private EntityManager em;
	private Query q;

	@Override
	public int getLength(int seqId) {
		// TODO use criteria query ...
		// CriteriaBuilder cb = em.getCriteriaBuilder();
		// CriteriaQuery<Sequence> q = cb.createQuery(Sequence.class);
		// Root<Sequence> c = q.from(Sequence.class);
		// q.where(cb.gt(c.get(Sequence_.id), seqId));
		// q.multiselect(c.get(Sequence_.length));
		Sequence seq = em.find(Sequence.class, seqId);
		if (seq == null) {
			return 0;
		}
		return seq.getLength();
	}

	@Override
	public Sequence findLatest(String seqName) {
		q = em.createNamedQuery("Sequence.findByNameOrdByDate");
		q.setParameter("name", seqName);
		@SuppressWarnings("unchecked")
		List<Sequence> seqs = q.getResultList();
		if (seqs != null && seqs.size() > 0) {
			return seqs.get(0);
		}
		return null;
	}

	@Override
	public List<SequenceName> findNamesByChromosome(Chromosome chromosome) {
		q = em.createNamedQuery("Sequence.findAllByChr");
		q.setParameter("chrId", chromosome.getId());
		@SuppressWarnings("unchecked")
		List<Sequence> seqs = q.getResultList();
		List<SequenceName> seqNames = new ArrayList<SequenceName>();
		for (Sequence s : seqs) {
			SequenceName seqName = new SequenceName(s);
			seqNames.add(seqName);
		}
		Collections.sort(seqNames);
		return seqNames;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sequence> findBySpecies(Species species) {
		q = em.createNamedQuery("Sequence.findAllBySpecies");
		q.setParameter("speciesId", species.getId());
		return q.getResultList();
	}

}
