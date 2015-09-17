package org.genomesmanager.repositories.repeats;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.*;

public class RepeatRepositoryCustomImpl implements RepeatRepositoryCustom {
	@PersistenceContext
	private EntityManager em;
	private Query q;

	private Repeat get(int repId) throws RepeatRepoException {
		Repeat rep = em.find(Repeat.class, repId);
		if (rep == null) {
			throw new RepeatRepoException("Repeat id " + repId + " not found");
		}
		return rep;
	}

	@Override
	public DnaTeRepeat getDnaTe(int dnaTeId) throws RepeatRepoException {
		DnaTeRepeat rep = (DnaTeRepeat) get(dnaTeId);
		return rep;
	}

	@Override
	public HelitronRepeat getHelitron(int helitronId)
			throws RepeatRepoException {
		HelitronRepeat rep = (HelitronRepeat) get(helitronId);
		return rep;
	}

	@Override
	public LineRepeat getLine(int lineId) throws RepeatRepoException {
		LineRepeat rep = (LineRepeat) get(lineId);
		return rep;
	}

	@Override
	public LtrRepeat getLtr(int lrtId) throws RepeatRepoException {
		LtrRepeat rep = (LtrRepeat) get(lrtId);
		return rep;
	}
	@Override
	public MiteRepeat getMite(int miteId) throws RepeatRepoException {
		MiteRepeat rep = (MiteRepeat) get(miteId);
		return rep;
	}

	@Override
	public SineRepeat getSine(int sineId) throws RepeatRepoException {
		SineRepeat rep = (SineRepeat) get(sineId);
		return rep;
	}

	@Override
	public UnknownRepeat getUnkn(int unknId) throws RepeatRepoException {
		UnknownRepeat rep = (UnknownRepeat) get(unknId);
		return rep;
	}

	@Override
	public List<Repeat> getAllBySequence(int seqId) {
		q = em.createNamedQuery("Repeat.findAllBySequence");
		q.setParameter("seqId", seqId);
		List<Repeat> repats = q.getResultList();
		return repats;
	}

	@Override
	public List<Repeat> getAllBySequence(int seqId, RepeatsOrder repOrder) {
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
	public List<Repeat> getAllByChromosome(int chrId, RepeatsOrder repOrder) {
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
	public List<Repeat> getAllBySequence(int seqId,
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
	public List<Repeat> getAllInRange(int seqId, int start, int end) {
		q = em.createNamedQuery("Repeat.findInRange");
		q.setParameter("seqId", seqId);
		q.setParameter("start", start);
		q.setParameter("end", end);
		List<Repeat> repats = q.getResultList();
		return repats;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LtrRepeat> getAllLtr(int seqId) {
		q = em.createNamedQuery("LtrRepeat.findAllBySequence");
		q.setParameter("seqId", seqId);
		List<LtrRepeat> ltrs = q.getResultList();
		return ltrs;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LtrRepeat> getAllLtrInRange(int seqId, int start, int end) {
		q = em.createNamedQuery("LtrRepeat.findInRange");
		q.setParameter("seqId", seqId);
		q.setParameter("start", start);
		q.setParameter("end", end);
		List<LtrRepeat> ltrs = q.getResultList();
		return ltrs;
	}

	@Override
	public List<Repeat> getAllBySequence(int seqId, RepeatsOrder repType,
			String superFamily) {
		// TODO check performance, may need a direct jpa query
		List<Repeat> reps = new ArrayList<Repeat>();
		for (Repeat r : getAllBySequence(seqId, repType)) {
			if (r.getRepeatsClassification().getSuperfamily()
					.equals(superFamily)) {
				reps.add(r);
			}
		}
		return reps;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Repeat> getAllBySpecies(Species sp) {
		q = em.createNamedQuery("Repeat.findAllBySpecies");
		q.setParameter("speciesId", sp.getId());
		List<Repeat> repeats = q.getResultList();
		return repeats;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Repeat> getAllByChromosome(int chrId) {
		q = em.createNamedQuery("Repeat.findAllByChromosome");
		q.setParameter("chrId", chrId);
		List<Repeat> repeats = q.getResultList();
		return repeats;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Repeat> getAllByChromosome(int chrId,
			RepeatsClassification repClass) {
		q = em.createNamedQuery("Repeat.findByClassAndChromosome");
		q.setParameter("chrId", chrId);
		q.setParameter("repClassId", repClass.getId());
		List<Repeat> repeats = q.getResultList();
		return repeats;
	}

	@Override
	public List<Repeat> getAllByChromosome(int chrId, RepeatsOrder repOrd,
			String superFamily) {
		// TODO check performance, may need a direct jpa query
		List<Repeat> reps = new ArrayList<Repeat>();
		for (Repeat r : getAllByChromosome(chrId, repOrd)) {
			if (r.getRepeatsClassification().getSuperfamily()
					.equals(superFamily)) {
				reps.add(r);
			}
		}
		return reps;
	}

	@Override
	public Long countChildren(int repId) throws RepeatRepoException {
		q = em.createNamedQuery("Repeat.countChildren");
		q.setParameter("id", repId);
		List<Object[]> results = q.getResultList();
		if (results.size() == 0)
			return new Long(0);
		if (results.size() > 1)
			throw new RepeatRepoException(
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
	public void validatePosition(Repeat repeat) throws RepeatRepoException {
		q = em.createNamedQuery("Repeat.findByCandidateKey");
		q.setParameter("seqId", repeat.getSequence().getId());
		q.setParameter("start", repeat.getX());
		q.setParameter("end", repeat.getY());
		List<Repeat> reps = new ArrayList<Repeat>();
		reps = q.getResultList();
		if (reps.size() > 0) {
			Repeat existingRepeat = reps.get(0);
			throw new RepeatRepoException("Repeat position conflicts with "
					+ "repeat " + existingRepeat.getId());
		}
	}

	@Override
	public void validateUpdate(Repeat rep) throws RepeatRepoException {
		Repeat currentRepeat = em.find(Repeat.class, rep.getId());
		if (currentRepeat == null) {
			throw new RepeatRepoException("Cannot update repeat " + rep.getId()
					+ ": repeat not found");
		}
		if (currentRepeat.getRepeatsClassification().getId() != rep
				.getRepeatsClassification().getId()) {
			throw new RepeatRepoException("Cannot update repeat " + rep.getId()
					+ ": repeat classification can't be modified");
		}
	}

}
