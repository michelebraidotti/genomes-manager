package org.genomesmanager.batch.jpa.repeats;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.batch.repeats.RepeatsBatchUpdater;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.repositories.repeats.RepeatRepo;
import org.genomesmanager.repositories.repeats.RepeatRepoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("RepeatsBatchUpdater")
public class RepeatsBatchUpdaterJpa implements RepeatsBatchUpdater {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	@Autowired
	private RepeatRepo repeatRepo;
	
    public RepeatsBatchUpdaterJpa() {
    }
    
    @Override
    public void updateRepatsParent() {
		q = em.createNamedQuery("Repeat.findAllWithParents");
		@SuppressWarnings("unchecked")
		List<Object[]> results = q.getResultList();
		int total = results.size();
		System.out.println("Fetched " + total + " repeats");
		int count = 0;
		for (Object[] result:results) {
			int repId = (Integer) result[0];
			Repeat parent = repeatRepo.getParent(repId);
			try {
				if ( parent != null ) {
					Repeat r = repeatRepo.get(repId);
					r.setParent(parent);
					repeatRepo.update(r);
				}
			} 
			catch (RepeatRepoException e) {
				e.printStackTrace();
			}
			count++;
			if ( count % 10 == 0 ) {
				int perc = (count/total)*100;
				System.out.println("Done " + count + " of " + total + " " + perc + " %");
			}
		}
    }
    
}
