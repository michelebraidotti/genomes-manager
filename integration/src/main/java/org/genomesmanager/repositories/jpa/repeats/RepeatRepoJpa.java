package org.genomesmanager.repositories.jpa.repeats;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.DnaTeRepeat;
import org.genomesmanager.domain.entities.HelitronRepeat;
import org.genomesmanager.domain.entities.IntervalFeatureException;
import org.genomesmanager.domain.entities.LineRepeat;
import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.MiteRepeat;
import org.genomesmanager.domain.entities.OutOfBoundsException;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatException;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsOrder;
import org.genomesmanager.domain.entities.SineRepeat;
import org.genomesmanager.domain.entities.UnknownRepeat;
import org.genomesmanager.repositories.repeats.RepeatRepo;
import org.genomesmanager.repositories.repeats.RepeatRepoException;
import org.springframework.stereotype.Repository;

@Repository("RepeatRepo")
public class RepeatRepoJpa implements RepeatRepo  {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public RepeatRepoJpa() {}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#delete(org.genomesmanager.domain.entities.Repeat)
	 */
	@Override
	public void delete(Repeat repeat) {
		em.remove(repeat);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#deleteByKey(int)
	 */
	@Override
	public void deleteByKey(int repId) {
		em.remove(em.find(Repeat.class, repId));	
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#get(int)
	 */
	@Override
	public Repeat get(int repId) throws RepeatRepoException {
		Repeat rep = em.find(Repeat.class, repId);
		if ( rep == null ) {
			throw new RepeatRepoException("Repeat id " + repId + " not found");
		}
		return rep;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#getDnaTe(int)
	 */
	@Override
	public DnaTeRepeat getDnaTe(int dnaTeId) throws RepeatRepoException {
		DnaTeRepeat rep = (DnaTeRepeat)get(dnaTeId);
		return rep;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#getHelitron(int)
	 */
	@Override
	public HelitronRepeat getHelitron(int helitronId) throws RepeatRepoException {
		HelitronRepeat rep = (HelitronRepeat)get(helitronId);
		return rep;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#getLine(int)
	 */
	@Override
	public LineRepeat getLine(int lineId) throws RepeatRepoException {
		LineRepeat rep = (LineRepeat)get(lineId);
		return rep;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#getLtr(int)
	 */
	@Override
	public LtrRepeat getLtr(int lrtId) throws RepeatRepoException {
		LtrRepeat rep = (LtrRepeat)get(lrtId);
		return rep;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#getMite(int)
	 */
	@Override
	public MiteRepeat getMite(int miteId) throws RepeatRepoException {
		MiteRepeat rep = (MiteRepeat)get(miteId);
		return rep;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#getSine(int)
	 */
	@Override
	public SineRepeat getSine(int sineId) throws RepeatRepoException {
		SineRepeat rep = (SineRepeat)get(sineId);
		return rep;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#getUnkn(int)
	 */
	@Override
	public UnknownRepeat getUnkn(int unknId) throws RepeatRepoException {
		UnknownRepeat rep = (UnknownRepeat)get(unknId);
		return rep;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#getNew(org.genomesmanager.domain.entities.RepeatsClassification)
	 */
	@Override
	public Repeat getNew(RepeatsClassification repClass) throws RepeatRepoException {
		Repeat rep = new Repeat();
		if ( repClass == null || repClass.getId() == null ) {
			throw new RepeatRepoException("Repeat classification null on repeat creation"); 
		}
		String repOrder = repClass.getId().getOrder();
		if ( repOrder == null ) {
			throw new RepeatRepoException("Repeat classification order null on repeat creation"); 
		}
		if ( repOrder.equals(RepeatsOrder.LTR.getLabel()) ) {
			LtrRepeat ltr = new LtrRepeat();
			rep = ltr;
		}
		else if ( repOrder.equals(RepeatsOrder.LINE.getLabel()) ) {
			LineRepeat line = new LineRepeat();
			rep = line;
		}
		else if ( repOrder.equals(RepeatsOrder.SINE.getLabel()) ) {
			SineRepeat sine = new SineRepeat();
			rep = sine;
		}
		else if ( repOrder.equals(RepeatsOrder.HEL.getLabel()) ) {
			HelitronRepeat hel = new HelitronRepeat();
			rep = hel;
		}
		else if ( repOrder.equals(RepeatsOrder.MITE.getLabel()) ) {
			MiteRepeat mite = new MiteRepeat();
			rep = mite;
		}
		else if ( repOrder.equals(RepeatsOrder.DNATE.getLabel()) ) {
			DnaTeRepeat dnate = new DnaTeRepeat();
			rep = dnate;
		}
		else if ( repOrder.equals(RepeatsOrder.SINE.getLabel()) ) {
			SineRepeat sine = new SineRepeat();
			rep = sine;
		}
		else if ( repOrder.equals(RepeatsOrder.UNKN.getLabel()) ) {
			UnknownRepeat unkn = new UnknownRepeat();
			rep = unkn;
		}
		else {
			throw new RepeatRepoException("Repeat order " + repOrder + " not available");
		}
		rep.setRepeatsClassification(repClass);
		return rep;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#insert(org.genomesmanager.domain.entities.Repeat)
	 */
	@Override
	public void insert(Repeat repeat) throws RepeatRepoException {
		validate(repeat);
		try {
			em.persist(repeat);
		}
		catch (PersistenceException ex) {
			throw new RepeatRepoException("Error writing repeat to database." +
					" Sequence: " + repeat.getSequence().toString() + 
					" start: " + repeat.getX() + 
					" end: " + repeat.getY());
		}
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#update(org.genomesmanager.domain.entities.Repeat)
	 */
	@Override
	public void update(Repeat repeat) throws RepeatRepoException {
		validateUpdate(repeat);
		validate(repeat);
		Repeat existingRepeat = get(repeat.getId());
		if ( existingRepeat != null ) {
			if ( existingRepeat.getDateModified().after(repeat.getDateModified() ) ) {
				throw new RepeatRepoException("Conflict while editing repeat " + 
						repeat.getId());
			}
			if (  existingRepeat.getParent() == null && repeat.getParent() != null) {
				repeat.getParent().setContainedElementsCount(
						repeat.getParent().getContainedElementsCount() + 1);
			}
		}
		em.merge(repeat);
	}
	
	private void validate(Repeat repeat) throws RepeatRepoException{
		try {
			repeat.validate();
			if (repeat instanceof LtrRepeat) {
				((LtrRepeat)repeat).checkIsSolo();
			}
		}
		catch (OutOfBoundsException e) {
			throw new RepeatRepoException(e.getMessage());
		}
		catch (IntervalFeatureException e) {
			throw new RepeatRepoException(e.getMessage());
		} 
		catch (RepeatException e) {
			throw new RepeatRepoException(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#validatePosition(org.genomesmanager.domain.entities.Repeat)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void validatePosition(Repeat repeat) throws RepeatRepoException {
		q = em.createNamedQuery("Repeat.findByCandidateKey");
		q.setParameter("seqId", repeat.getSequence().getId());
		q.setParameter("start", repeat.getX());
    	q.setParameter("end", repeat.getY());
    	List<Repeat> reps = new ArrayList<Repeat>();
    	reps = q.getResultList();
		if ( reps.size() > 0 ) {
			Repeat existingRepeat = reps.get(0); 
			throw new RepeatRepoException("Repeat position conflicts with " +
					"repeat " + existingRepeat.getId());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#validateUpdate(org.genomesmanager.domain.entities.Repeat)
	 */
	@Override
	public void validateUpdate(Repeat rep) throws RepeatRepoException {
		Repeat currentRepeat = em.find(Repeat.class, rep.getId());
		if ( currentRepeat == null ) {
			throw new RepeatRepoException("Cannot update repeat " + rep.getId() + ": repeat not found");
		}
		if ( ! currentRepeat.getRepeatsClassification().getId().equals(rep.getRepeatsClassification().getId()) ) {
			throw new RepeatRepoException("Cannot update repeat " + rep.getId() + 
					": repeat classification can't be modified");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#countChildren(int)
	 */
	@Override
	public Long countChildren(int repId) throws RepeatRepoException {
		q = em.createNamedQuery("Repeat.countChildren");
		q.setParameter("id", repId);
		List<Object[]> results = q.getResultList();
		if ( results.size() == 0 ) return new Long(0);
		if ( results.size() > 1) throw new 
			RepeatRepoException("Error, Repeat.countChildren query should return only one result");
		return (Long) results.get(0)[2];
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#getParent(int)
	 */
	@Override
	public Repeat getParent(int repId) {
		q = em.createNamedQuery("Repeat.findParent");
		q.setParameter("id", repId);
		List<Repeat> results = q.getResultList();
		if ( results.size() == 0 ) return null;
		return results.get(0);
	}

}
