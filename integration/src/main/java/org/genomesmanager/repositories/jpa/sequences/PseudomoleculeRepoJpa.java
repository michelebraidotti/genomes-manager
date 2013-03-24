package org.genomesmanager.repositories.jpa.sequences;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.repositories.sequences.PseudomoleculeRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.springframework.stereotype.Repository;

@Repository("PseudomoleculeRepo")
public class PseudomoleculeRepoJpa implements PseudomoleculeRepo {
	@PersistenceContext
	private EntityManager em;
	private Query q;

	@Override
	public Pseudomolecule get(int pseudomolId) throws SequenceRepoException {
		return em.find(Pseudomolecule.class, pseudomolId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public StringBuilder getFromChromosome(int chrId, boolean masked)
			throws SequenceSliceException {
		q = em.createNamedQuery("Scaffold.findAllPlacedByChromosome");
		q.setParameter("chrId", chrId);
		List<Scaffold> scafs = q.getResultList();
		StringBuilder out = new StringBuilder();
		for (Scaffold scaf:scafs) {
			if (masked) {
				out.append(scaf.getMaskedSequence());
			}
			else {
				out.append(scaf.getSequenceText());
			}
			out.append(Pseudomolecule.SCAFFOLDS_SPACER());
		}
		out.delete(out.length() - 100, out.length() + 1);
		return out;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StringBuilder getFromChromosomeUnplaced(int chrId, boolean masked)
			throws SequenceSliceException {
		q = em.createNamedQuery("Scaffold.findAllUnplacedByChromosome");
		q.setParameter("chrId", chrId);
		List<Scaffold> scafs = q.getResultList();
		StringBuilder out = new StringBuilder();
		if ( scafs.size() == 0) 
			return out;
		for (Scaffold scaf:scafs) {
			if (masked) {
				out.append(scaf.getMaskedSequence());
			}
			else {
				out.append(scaf.getSequenceText());
			}
			out.append(Pseudomolecule.SCAFFOLDS_SPACER());
		}
		out.delete(out.length() - Pseudomolecule.SCAFFOLDS_SPACER_SIZE, out.length() + 1);
		return out;
	}

}
