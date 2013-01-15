package org.genomesmanager.repositories.jpa.sequences;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.springframework.stereotype.Repository;

@Repository("SequenceRepo")
public class SequenceRepoJpa implements SequenceRepo {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public SequenceRepoJpa() {
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.SequenceRepo#delete(org.genomesmanager.domain.entities.Sequence)
	 */
	@Override
	public void delete(Sequence seq) {
		em.remove(seq);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.SequenceRepo#deleteByKey(int)
	 */
	@Override
	public void deleteByKey(int seqId) {
		em.remove(em.find(Sequence.class, seqId));
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.SequenceRepo#get(int)
	 */
	@Override
	public Sequence get(int seqId) throws SequenceRepoException {
		Sequence seq = em.find(Sequence.class, seqId);
		if ( seq == null ) {
			throw new SequenceRepoException("Sequence id " + seqId  + " not found");
		}
		return seq;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.SequenceRepo#getScaffold(int)
	 */
	@Override
	public Scaffold getScaffold(int seqId) throws SequenceRepoException {
		Sequence seq = get(seqId);
		return getScaffold(seq);
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.SequenceRepo#getScaffold(org.genomesmanager.domain.entities.Sequence)
	 */
	@Override
	public Scaffold getScaffold(Sequence seq) throws SequenceRepoException {
		if ( seq instanceof Scaffold ) {
			Scaffold s = (Scaffold) seq;
			if ( s.getName().contains("unplaced") ) {
	    		q = em.createNamedQuery("Scaffold.getUnplacedOffset");
	    	}
	    	else {
	    		q = em.createNamedQuery("Scaffold.getPlacedOffset");
	    	}
			q.setParameter("chrId", s.getChromosome().getId());
			q.setParameter("scaffOrder", s.getOrder());
			q.setParameter("scaffVersion", s.getScaffVersion());
			Long offset = new Long(0);
			try { 
				offset = (Long) q.getSingleResult();
				if ( offset == null ) offset = new Long(0);
			}
			catch (NoResultException  nre) {
				offset = new Long(0);
			}
			s.setPseudomolOffset(offset);
			return s;
		}
		else {
			throw new SequenceRepoException("Sequence " + seq.getId() + " is not a scaffold");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.SequenceRepo#get(java.lang.String)
	 */
	@Override
	public Sequence get(String seqName) throws SequenceRepoException {
		String [] definition = seqName.split(Sequence.NAME_SEPARATOR);
		if ( definition.length != 2 ) {
			throw new SequenceRepoException("No sequence with name " + seqName + " found");
		}
		String name = definition[0];
		String version = definition[1];
		Scaffold s = getScaffoldByName(name, version);
		if ( s == null ) {
			Pseudomolecule p = getPseudomolByName(name, version);
			if ( p == null ) {
				throw new SequenceRepoException("No sequence with name " + seqName + " found");
			}
			else {
				return p;
			}
		}
		else {
			return s;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.SequenceRepo#getLatest(java.lang.String)
	 */
	@Override
	public Sequence getLatest(String seqName) throws SequenceRepoException  {
		Sequence s = null;
		s = getLatestScaffold(seqName);
		if ( s == null ) {
			s = getLatestPseudoMol(seqName);
		}
		if ( s == null ) {
			throw new SequenceRepoException("Sequence " + seqName + " not found");
		}
		return s;
	}
	
	@SuppressWarnings("unchecked")
	private Pseudomolecule getPseudomolByName(String name, String version) {
		q = em.createNamedQuery("Pseudomolecule.findByNameVersion");
		q.setParameter("name", name);
		q.setParameter("version", version);
		List<Pseudomolecule> pseudos = q.getResultList();
		if ( pseudos == null || pseudos.size() == 0) {
			return null;
		}
		return pseudos.get(0);
	}

	@SuppressWarnings("unchecked")
	private Scaffold getScaffoldByName(String name, String version) {
		q = em.createNamedQuery("Scaffold.findByNameVersion");
		q.setParameter("name", name);
		q.setParameter("scaffVersion", version);
		List<Scaffold> scaffs = q.getResultList();
		if (scaffs == null || scaffs.size() == 0) {
			return null;
		}
		return scaffs.get(0);
	}

	@SuppressWarnings("unchecked")
	private Scaffold getLatestScaffold(String name) {
		q = em.createNamedQuery("Scaffold.findByNameOrdByDate");
		q.setParameter("name", name);
		List<Scaffold> scaffs = q.getResultList();
		if ( scaffs != null && scaffs.size() > 0 ) {
			return scaffs.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Pseudomolecule getLatestPseudoMol(String name) {
		q = em.createNamedQuery("Pseudomolecule.findByNameOrdByDate");
		q.setParameter("name", name);
		List<Pseudomolecule> pseudos = q.getResultList();
		if ( pseudos != null && pseudos.size() > 0 ) {
			return pseudos.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.SequenceRepo#insert(org.genomesmanager.domain.entities.Sequence)
	 */
	@Override
	public void insert(Sequence seq) {
		em.persist(seq);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.SequenceRepo#update(org.genomesmanager.domain.entities.Sequence)
	 */
	@Override
	public void update(Sequence seq) {
		em.merge(seq);	
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.sequences.SequenceRepo#getLength(int)
	 */
	@Override
	public int getLength(int seqId) throws SequenceRepoException {
		Sequence seq = em.find(Sequence.class, seqId);
		if ( seq == null ) {
			throw new SequenceRepoException("Sequence id " + seqId  + " not found");
		}
		return seq.getLength();
	}
	
}
