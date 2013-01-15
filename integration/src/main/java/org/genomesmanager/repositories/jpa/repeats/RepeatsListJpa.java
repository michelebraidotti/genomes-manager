package org.genomesmanager.repositories.jpa.repeats;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsOrder;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.repositories.repeats.RepeatsList;
import org.springframework.stereotype.Repository;

@Repository("RepeatsList")
public class RepeatsListJpa implements RepeatsList  {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public RepeatsListJpa() {
    }

    /* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsList#getAllBySequence(int)
	 */
    @Override
	@SuppressWarnings("unchecked")
	public List<Repeat> getAllBySequence(int seqId) {
		q = em.createNamedQuery("Repeat.findAllBySequence");
		q.setParameter("seqId", seqId);
		List<Repeat> repats = q.getResultList();
		return repats;
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsList#getAllBySequence(int, org.genomesmanager.domain.entities.RepeatsOrder)
	 */
    @Override
	@SuppressWarnings("unchecked")
    public List<Repeat> getAllBySequence(int seqId, RepeatsOrder repOrder) {
    	String qName = "";
    	if ( repOrder.equals(RepeatsOrder.ANY ) ) {
    		qName = "Repeat.findAllBySequence";
    	}
    	else if ( repOrder.equals(RepeatsOrder.LTR) ) {
			qName = "LtrRepeat.findAllBySequence";
		}
		else if ( repOrder.equals(RepeatsOrder.LINE) ) {
			qName = "LineRepeat.findAllBySequence";
		}
		else if ( repOrder.equals(RepeatsOrder.SINE) ) {
			qName = "SineRepeat.findAllBySequence";
		}
		else if ( repOrder.equals(RepeatsOrder.DNATE) ) {
			qName = "DnaTeRepeat.findAllBySequence";
		}
		else if ( repOrder.equals(RepeatsOrder.HEL) ) {
			qName = "HelitronRepeat.findAllBySequence";
		}
		else if ( repOrder.equals(RepeatsOrder.MITE) ) {
			qName = "MiteRepeat.findAllBySequence";
		}
		else if ( repOrder.equals(RepeatsOrder.UNKN) ) {
			qName = "UnknownRepeat.findAllBySequence";
		}
		q = em.createNamedQuery(qName);
		q.setParameter("seqId", seqId);
		return q.getResultList(); 
	}
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsList#getAllByChromosome(int, org.genomesmanager.domain.entities.RepeatsOrder)
	 */
    @Override
	@SuppressWarnings("unchecked")
    public List<Repeat> getAllByChromosome(int chrId, RepeatsOrder repOrder) {
    	String qName = "";
    	if ( repOrder.equals(RepeatsOrder.ANY ) ) {
    		qName = "Repeat.findAllByChromosome";
    	}
    	else if ( repOrder.equals(RepeatsOrder.LTR) ) {
			qName = "LtrRepeat.findAllByChromosome";
		}
		else if ( repOrder.equals(RepeatsOrder.LINE) ) {
			qName = "LineRepeat.findAllByChromosome";
		}
		else if ( repOrder.equals(RepeatsOrder.SINE) ) {
			qName = "SineRepeat.findAllByChromosome";
		}
		else if ( repOrder.equals(RepeatsOrder.DNATE) ) {
			qName = "DnaTeRepeat.findAllByChromosome";
		}
		else if ( repOrder.equals(RepeatsOrder.HEL) ) {
			qName = "HelitronRepeat.findAllByChromosome";
		}
		else if ( repOrder.equals(RepeatsOrder.MITE) ) {
			qName = "MiteRepeat.findAllByChromosome";
		}
		else if ( repOrder.equals(RepeatsOrder.UNKN) ) {
			qName = "UnknownRepeat.findAllByChromosome";
		}
		q = em.createNamedQuery(qName);
		q.setParameter("chrId", chrId);
		return q.getResultList(); 
	}
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsList#getAllBySequence(int, org.genomesmanager.domain.entities.RepeatsClassification)
	 */
    @Override
	public List<Repeat> getAllBySequence(int seqId, RepeatsClassification repClass) {
    	q = em.createNamedQuery("Repeat.findByClassAndSequence");
    	q.setParameter("seqId", seqId);
		q.setParameter("repClass", repClass.getId().getRepClass());
		q.setParameter("subclass", repClass.getId().getSubclass());
		q.setParameter("order", repClass.getId().getOrder());
		q.setParameter("superfamily", repClass.getId().getSuperfamily());
		q.setParameter("family", repClass.getId().getFamily());
		@SuppressWarnings("unchecked")
		List<Repeat> repats = q.getResultList();
		return repats;
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsList#getAllInRange(int, int, int)
	 */
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
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsList#getAllLtr(int)
	 */
    @Override
	@SuppressWarnings("unchecked")
	public List<LtrRepeat> getAllLtr(int seqId) {
    	q = em.createNamedQuery("LtrRepeat.findAllBySequence");
    	q.setParameter("seqId", seqId);
		List<LtrRepeat> ltrs = q.getResultList();
		return ltrs;
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsList#getAllLtrInRange(int, int, int)
	 */
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
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsList#getAllBySequence(int, org.genomesmanager.domain.entities.RepeatsOrder, java.lang.String)
	 */
    @Override
	public List<Repeat> getAllBySequence(int seqId, RepeatsOrder repType, String superFamily) {
    	// TODO check performance, may need a direct jpa query
    	List<Repeat> reps = new ArrayList<Repeat>();
    	for (Repeat r:getAllBySequence(seqId, repType)) {
    		if ( r.getRepeatsClassification().getId().getSuperfamily().equals(superFamily) ) {
    			reps.add(r);
    		}
    	}
    	return reps;
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsList#getAllBySpecies(org.genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Repeat> getAllBySpecies(SpeciesPK id) {
		q = em.createNamedQuery("Repeat.findAllBySpecies");
		q.setParameter("genus", id.getGenus());
		q.setParameter("species", id.getSpecies());
		q.setParameter("subspecies", id.getSubspecies());
		List<Repeat> repeats = q.getResultList();
		return repeats;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsList#getAllByChromosome(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Repeat> getAllByChromosome(int chrId) {
		q = em.createNamedQuery("Repeat.findAllByChromosome");
		q.setParameter("chrId", chrId);
		List<Repeat> repeats = q.getResultList();
		return repeats;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsList#getAllByChromosome(int, org.genomesmanager.domain.entities.RepeatsClassification)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Repeat> getAllByChromosome(int chrId, RepeatsClassification repClass) {
    	q = em.createNamedQuery("Repeat.findByClassAndChromosome");
    	q.setParameter("chrId", chrId);
		q.setParameter("repClass", repClass.getId().getRepClass());
		q.setParameter("subclass", repClass.getId().getSubclass());
		q.setParameter("order", repClass.getId().getOrder());
		q.setParameter("superfamily", repClass.getId().getSuperfamily());
		q.setParameter("family", repClass.getId().getFamily());
		List<Repeat> repeats = q.getResultList();
		return repeats;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsList#getAllByChromosome(int, org.genomesmanager.domain.entities.RepeatsOrder, java.lang.String)
	 */
	@Override
	public List<Repeat> getAllByChromosome(int chrId, RepeatsOrder repOrd, String superFamily) {
		// TODO check performance, may need a direct jpa query
    	List<Repeat> reps = new ArrayList<Repeat>();
    	for (Repeat r:getAllByChromosome(chrId, repOrd)) {
    		if ( r.getRepeatsClassification().getId().getSuperfamily().equals(superFamily) ) {
    			reps.add(r);
    		}
    	}
    	return reps;
	}
		
}
