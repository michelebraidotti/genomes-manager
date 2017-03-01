package org.genomesmanager.repositories.repeats;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.genomesmanager.domain.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

public class RepeatRepositoryImpl implements RepeatRepositoryCustom {
	@PersistenceContext
	private EntityManager em;
	private Query q;

	@Override
	public Repeat saveAndValidate(Repeat repeat) throws RepeatException, OutOfBoundsException, IntervalFeatureException {
		validate(repeat);
		if ( repeat.getId() != 0 ) { // we are updating
			validateUpdate(repeat);
			updateContainedElementsCount(repeat);
		}
		em.persist(repeat);
		return repeat;
	}

	private void validate(Repeat repeat) throws IntervalFeatureException, OutOfBoundsException, RepeatException {
		repeat.validate();
		if (repeat instanceof LtrRepeat) {
			((LtrRepeat) repeat).checkIsSolo();
		}

	}

	@Override
	public DnaTeRepeat findDnaTeRepeat(int dnaTeId) {
		return em.find(DnaTeRepeat.class, dnaTeId);
	}

	@Override
	public HelitronRepeat findHelitronRepeat(int helitronId) {
		return em.find(HelitronRepeat.class, helitronId);
	}

	@Override
	public LineRepeat findLineRepeat(int lineId) {
		return em.find(LineRepeat.class, lineId);
	}

	@Override
	public LtrRepeat findLtrRepeat(int lrtId) {
		return em.find(LtrRepeat.class, lrtId);
	}
	@Override
	public MiteRepeat findMiteRepeat(int miteId) {
		return em.find(MiteRepeat.class, miteId);
	}

	@Override
	public SineRepeat findSineRepeat(int sineId) {
		return em.find(SineRepeat.class, sineId);
	}

	@Override
	public UnknownRepeat findUnknRepeat(int unknId) {
		return em.find(UnknownRepeat.class, unknId);
	}

	@Override
	public List<Repeat> findAllRepeatsBySequence(int seqId) {
		q = em.createNamedQuery("Repeat.findAllBySequence");
		q.setParameter("seqId", seqId);
		List<Repeat> repats = q.getResultList();
		return repats;
	}

	@Override
	public List<Repeat> findAllRepeatsBySequence(int seqId, RepeatsOrder repOrder) {
		String qName = "";
		if (repOrder.equals(RepeatsOrder.ANY)) {
			qName = "Repeat.findAllBySequence";
		} else if (repOrder.equals(RepeatsOrder.LTR)) {
			qName = "LtrRepeat.findAllBySequence";
		} else if (repOrder.equals(RepeatsOrder.LINE)) {
			qName = "LineRepeat.findAllBySequence";
		} else if (repOrder.equals(RepeatsOrder.SINE)) {
			qName = "SineRepeat.findAllBySequence";
		} else if (repOrder.equals(RepeatsOrder.DNATE)) {
			qName = "DnaTeRepeat.findAllBySequence";
		} else if (repOrder.equals(RepeatsOrder.HEL)) {
			qName = "HelitronRepeat.findAllBySequence";
		} else if (repOrder.equals(RepeatsOrder.MITE)) {
			qName = "MiteRepeat.findAllBySequence";
		} else if (repOrder.equals(RepeatsOrder.UNKN)) {
			qName = "UnknownRepeat.findAllBySequence";
		}
		q = em.createNamedQuery(qName);
		q.setParameter("seqId", seqId);
		return q.getResultList();
	}

	@Override
	public List<Repeat> findAllRepeatsByChromosome(int chrId, RepeatsOrder repOrder) {
		String qName = "";
		if (repOrder.equals(RepeatsOrder.ANY)) {
			qName = "Repeat.findAllByChromosome";
		} else if (repOrder.equals(RepeatsOrder.LTR)) {
			qName = "LtrRepeat.findAllByChromosome";
		} else if (repOrder.equals(RepeatsOrder.LINE)) {
			qName = "LineRepeat.findAllByChromosome";
		} else if (repOrder.equals(RepeatsOrder.SINE)) {
			qName = "SineRepeat.findAllByChromosome";
		} else if (repOrder.equals(RepeatsOrder.DNATE)) {
			qName = "DnaTeRepeat.findAllByChromosome";
		} else if (repOrder.equals(RepeatsOrder.HEL)) {
			qName = "HelitronRepeat.findAllByChromosome";
		} else if (repOrder.equals(RepeatsOrder.MITE)) {
			qName = "MiteRepeat.findAllByChromosome";
		} else if (repOrder.equals(RepeatsOrder.UNKN)) {
			qName = "UnknownRepeat.findAllByChromosome";
		}
		q = em.createNamedQuery(qName);
		q.setParameter("chrId", chrId);
		return q.getResultList();
	}

	@Override
	public List<Repeat> findAllRepeatsBySequence(int seqId,
												 RepeatsClassification repClass) {
		q = em.createNamedQuery("Repeat.findByClassAndSequence");
		q.setParameter("seqId", seqId);
		q.setParameter("repClassId", repClass.getId());
		@SuppressWarnings("unchecked")
		List<Repeat> repats = q.getResultList();
		return repats;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Repeat> findAllRepeatsInRange(int seqId, int start, int end) {
		q = em.createNamedQuery("Repeat.findInRange");
		q.setParameter("seqId", seqId);
		q.setParameter("start", start);
		q.setParameter("end", end);
		List<Repeat> repats = q.getResultList();
		return repats;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LtrRepeat> findAllLtrRepeats(int seqId) {
		q = em.createNamedQuery("LtrRepeat.findAllBySequence");
		q.setParameter("seqId", seqId);
		List<LtrRepeat> ltrs = q.getResultList();
		return ltrs;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LtrRepeat> findAllLtrRepeatsInRange(int seqId, int start, int end) {
		q = em.createNamedQuery("LtrRepeat.findInRange");
		q.setParameter("seqId", seqId);
		q.setParameter("start", start);
		q.setParameter("end", end);
		List<LtrRepeat> ltrs = q.getResultList();
		return ltrs;
	}

	@Override
	public List<Repeat> findAllRepeatsBySequence(int seqId, RepeatsOrder repType,
												 String superFamily) {
		// TODO check performance, may need a direct jpa query
		List<Repeat> reps = new ArrayList<Repeat>();
		for (Repeat r : findAllRepeatsBySequence(seqId, repType)) {
			if (r.getRepeatsClassification().getSuperfamily()
					.equals(superFamily)) {
				reps.add(r);
			}
		}
		return reps;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Repeat> findAllRepeatsBySpecies(Species sp) {
		q = em.createNamedQuery("Repeat.findAllBySpecies");
		q.setParameter("speciesId", sp.getId());
		List<Repeat> repeats = q.getResultList();
		return repeats;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Repeat> findAllRepeatsBySpecies(int speciesId) {
		q = em.createNamedQuery("Repeat.findAllBySpecies");
		q.setParameter("speciesId", speciesId);
		List<Repeat> repeats = q.getResultList();
		return repeats;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Repeat> findAllRepeatsByChromosome(int chrId) {
		q = em.createNamedQuery("Repeat.findAllByChromosome");
		q.setParameter("chrId", chrId);
		List<Repeat> repeats = q.getResultList();
		return repeats;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Repeat> findAllRepeatsByChromosome(int chrId,
												   RepeatsClassification repClass) {
		q = em.createNamedQuery("Repeat.findByClassAndChromosome");
		q.setParameter("chrId", chrId);
		q.setParameter("repClassId", repClass.getId());
		List<Repeat> repeats = q.getResultList();
		return repeats;
	}

	@Override
	public List<Repeat> findAllRepeatsByChromosome(int chrId, RepeatsOrder repOrd,
												   String superFamily) {
		// TODO check performance, may need a direct jpa query
		List<Repeat> reps = new ArrayList<Repeat>();
		for (Repeat r : findAllRepeatsByChromosome(chrId, repOrd)) {
			if (r.getRepeatsClassification().getSuperfamily()
					.equals(superFamily)) {
				reps.add(r);
			}
		}
		return reps;
	}

	@Override
	public Long countChildren(int repId) {
		q = em.createNamedQuery("Repeat.countChildren");
		q.setParameter("id", repId);
		List<Object[]> results = q.getResultList();
		if (results.size() == 0)
			return new Long(0);
		if (results.size() > 1)
			throw new NonUniqueResultException(
					"Error, Repeat.countChildren query should return only one result");
		return (Long) results.get(0)[2];
	}

	@Override
	public Repeat getParent(int repId) {
		q = em.createNamedQuery("Repeat.findParent");
		q.setParameter("id", repId);
		List<Repeat> results = q.getResultList();
		if (results.size() == 0)
			return null;
		return results.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void validatePosition(Repeat repeat) throws RepeatException {
		q = em.createNamedQuery("Repeat.findByCandidateKey");
		q.setParameter("seqId", repeat.getSequence().getId());
		q.setParameter("start", repeat.getX());
		q.setParameter("end", repeat.getY());
		List<Repeat> reps = new ArrayList<Repeat>();
		reps = q.getResultList();
		if (reps.size() > 0) {
			Repeat existingRepeat = reps.get(0);
			throw new RepeatException("Repeat position conflicts with "
					+ "repeat " + existingRepeat.getId());
		}
	}

	@Override
	public void validateUpdate(Repeat rep) throws RepeatException {
		Repeat currentRepeat = em.find(Repeat.class, rep.getId());
		if (currentRepeat == null) {
			throw new NoResultException("Cannot update repeat " + rep.getId()
					+ ": repeat not found");
		}
		if (currentRepeat.getRepeatsClassification().getId() != rep
				.getRepeatsClassification().getId()) {
			throw new RepeatException("Cannot update repeat " + rep.getId()
					+ ": repeat classification can't be modified");
		}

	}

	@Override
	public void updateContainedElementsCount(Repeat repeat) {
		Repeat currentRepeat = em.find(Repeat.class, repeat.getId());
		if (currentRepeat.getParent() == null
				&& repeat.getParent() != null) {
			repeat.getParent().setContainedElementsCount(
					repeat.getParent().getContainedElementsCount() + 1);
		}
	}

	@Override
	public List<Object[]> findAllRepeatsWithParents() {
		q = em.createNamedQuery("Repeat.findAllWithParents");
		@SuppressWarnings("unchecked")
		List<Object[]> results = q.getResultList();
		return results;
	}
}
