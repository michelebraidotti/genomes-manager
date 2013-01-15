package org.genomesmanager.repositories.jpa.sequences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.SequenceName;
import org.genomesmanager.repositories.sequences.SequencesList;
import org.springframework.stereotype.Repository;

/**
 * Session Bean implementation class SequencesList
 */
@Repository("SequencesList")
public class SequencesListJpa implements SequencesList {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public SequencesListJpa() {
    }
    
    @Override
    @SuppressWarnings("unchecked")
	public List<Sequence> getAllByChromosome(int chrId) {
		q = em.createNamedQuery("Sequence.findAllByChr");
		q.setParameter("chrId", chrId);		
		return q.getResultList();
    }
    
    @Override
	public List<SequenceName> getAllNamesByChromosome(int chrId) {
		List<Sequence> seqs = getAllByChromosome(chrId);
		List<SequenceName> seqNames = new ArrayList<SequenceName>();
		for (Sequence s:seqs) {
			SequenceName seqName = new SequenceName(s);
			seqNames.add(seqName);
		}
		Collections.sort(seqNames);
		return seqNames;
    }

}
