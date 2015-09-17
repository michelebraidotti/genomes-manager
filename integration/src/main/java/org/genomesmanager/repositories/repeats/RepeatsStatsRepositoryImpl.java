package org.genomesmanager.repositories.repeats;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.dtos.LtrRepeatsPresenceInSativaStats;
import org.genomesmanager.domain.dtos.RepeatsStatsByClassification;
import org.genomesmanager.repositories.repeats.RepeatsStatsRepository;
import org.springframework.stereotype.Repository;

@Repository("RepeatsStatsRepository")
public class RepeatsStatsRepositoryImpl implements RepeatsStatsRepository {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    /* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countRepeatsBasesByChromosome(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RepeatsStatsByClassification> countRepeatsBasesByChromosome(int chrId) {
		q = em.createNamedQuery("Repeat.countRepeatsAndBasesByChromosome").setParameter(1, chrId);
		List<Object[]> results = q.getResultList();
		List<RepeatsStatsByClassification> out = new ArrayList<RepeatsStatsByClassification>();
		for (Object[] result : results) {
			out.add(new RepeatsStatsByClassification(
						(String) result[0],(String) result[1],
						(String) result[2],(String) result[3], 
						(BigInteger) result[4], (BigInteger) result[5]));
		}
    	return out;
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countRepeatsAndBases()
	 */
    @SuppressWarnings("unchecked")
	@Override
	public List<RepeatsStatsByClassification> countRepeatsAndBases() {
		q = em.createNamedQuery("Repeat.countRepeatsAndBases");
		List<Object[]> results = q.getResultList();
		List<RepeatsStatsByClassification> out = new ArrayList<RepeatsStatsByClassification>();
		for (Object[] result : results) {
			out.add(new RepeatsStatsByClassification(
						(String) result[0],(String) result[1],
						(String) result[2],(String) result[3], 
						(BigInteger) result[4], (BigInteger) result[5]));
		}
    	return out;
    }
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllLtrs()
	 */
	@Override
	public Long countAllLtrs() {
    	q = em.createNamedQuery("LtrRepeat.count");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllLtrNucleotides()
	 */
	@Override
	public Long countAllLtrNucleotides() {
		q = em.createNamedQuery("LtrRepeat.countNucl");
    	return (Long) q.getSingleResult();
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countCompleteLtrs()
	 */
	@Override
	public Long countCompleteLtrs() {
		q = em.createNamedQuery("LtrRepeat.countComplete");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countSoloLtrs()
	 */
	@Override
	public Long countSoloLtrs() {
		q = em.createNamedQuery("LtrRepeat.countSolo");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countTruncatedLtrs()
	 */
	@Override
	public Long countTruncatedLtrs() {
		q = em.createNamedQuery("LtrRepeat.countTruncated");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#presenceInSativaStatsCompleteLtrs()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LtrRepeatsPresenceInSativaStats> presenceInSativaStatsCompleteLtrs() {
		q = em.createNamedQuery("LtrRepeat.presenceInSativaIsComplete");
		List<Object[]> results = q.getResultList();
		List<LtrRepeatsPresenceInSativaStats> out = 
			new ArrayList<LtrRepeatsPresenceInSativaStats>();
		for (Object[] result : results) {
			out.add(new LtrRepeatsPresenceInSativaStats((String) result[0], (Long) result[1]));
		}
		return out;
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#presenceInSativaStatsSoloLtrs()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LtrRepeatsPresenceInSativaStats> presenceInSativaStatsSoloLtrs() {
		q = em.createNamedQuery("LtrRepeat.presenceInSativaAndIsSolo");
		List<Object[]> results = q.getResultList();
		List<LtrRepeatsPresenceInSativaStats> out = 
			new ArrayList<LtrRepeatsPresenceInSativaStats>();
		for (Object[] result : results) {
			out.add(new LtrRepeatsPresenceInSativaStats((String) result[0], (Long) result[1]));
		}
		return out;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsStats#countAllDnaTes()
	 */
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllDnaTes()
	 */
	@Override
	public Long countAllDnaTes() {
    	q = em.createNamedQuery("DnaTeRepeat.count");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllDnaTeNucleotides()
	 */
	@Override
	public Long countAllDnaTeNucleotides() {
		q = em.createNamedQuery("DnaTeRepeat.countNucl");
    	return (Long) q.getSingleResult();
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllCompleteHelitrons()
	 */
	@Override
	public Long countAllCompleteHelitrons() {
		q = em.createNamedQuery("HelitronRepeat.count");
		@SuppressWarnings("unchecked")
		List<Object[]> results = q.getResultList();
		Long res = new Long(0);
		for (Object[] result : results) {
			String end3 = (String) result[0];
			String end5 = (String) result[1];
			Long count = (Long) result[2];
			if ( end3 != null && end5 != null) {
				res += count;
			}
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllCompleteHelitronsNucleotides()
	 */
	@Override
	public Long countAllCompleteHelitronsNucleotides() {
		q = em.createNamedQuery("HelitronRepeat.countNulc");
		@SuppressWarnings("unchecked")
		List<Object[]> results = q.getResultList();
		Long res = new Long(0);
		for (Object[] result : results) {
			String end3 = (String) result[0];
			String end5 = (String) result[1];
			Long count = (Long) result[2];
			if ( end3 != null && end5 != null) {
				res += count;
			}
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllPartialHelitrons()
	 */
	@Override
	public Long countAllPartialHelitrons() {
		q = em.createNamedQuery("HelitronRepeat.count");
		@SuppressWarnings("unchecked")
		List<Object[]> results = q.getResultList();
		Long res = new Long(0);
		for (Object[] result : results) {
			String end3 = (String) result[0];
			String end5 = (String) result[1];
			Long count = (Long) result[2];
			if ( end3 != null && end5 == null) {
				res += count;
			}
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllPartialHelitronsNucleotides()
	 */
	@Override
	public Long countAllPartialHelitronsNucleotides() {
		q = em.createNamedQuery("HelitronRepeat.countNulc");
		@SuppressWarnings("unchecked")
		List<Object[]> results = q.getResultList();
		Long res = new Long(0);
		for (Object[] result : results) {
			String end3 = (String) result[0];
			String end5 = (String) result[1];
			Long count = (Long) result[2];
			if ( end3 != null && end5 == null) {
				res += count;
			}
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countPotAutonHelitrons()
	 */
	@Override
	public Long countPotAutonHelitrons() {
		q = em.createNamedQuery("HelitronRepeat.countPotAuton");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countPotCdsCountHelitrons()
	 */
	@Override
	public Long countPotCdsCountHelitrons() {
		q = em.createNamedQuery("HelitronRepeat.countPotCdsCount");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countOrfCountHelitrons()
	 */
	@Override
	public Long countOrfCountHelitrons() {
		q = em.createNamedQuery("HelitronRepeat.countOrfCount");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllLines()
	 */
	@Override
	public Long countAllLines() {
		q = em.createNamedQuery("LineRepeat.count");
    	return (Long) q.getSingleResult();	
    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllLinesNucleotides()
	 */
	@Override
	public Long countAllLinesNucleotides() {
		q = em.createNamedQuery("LineRepeat.countNucl");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllMites()
	 */
	@Override
	public Long countAllMites() {
		q = em.createNamedQuery("MiteRepeat.count");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllMitesNucleotides()
	 */
	@Override
	public Long countAllMitesNucleotides() {
		q = em.createNamedQuery("MiteRepeat.countNucl");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllSines()
	 */
	@Override
	public Long countAllSines() {
		q = em.createNamedQuery("SineRepeat.count");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllSinesNucleotides()
	 */
	@Override
	public Long countAllSinesNucleotides() {
		q = em.createNamedQuery("SineRepeat.countNucl");
    	return (Long) q.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsStatsRepository#countAllUnkns()
	 */
	@Override
	public Long countAllUnkns() {
		q = em.createNamedQuery("UnknownRepeat.count");
    	return (Long) q.getSingleResult();
    }
	
}
