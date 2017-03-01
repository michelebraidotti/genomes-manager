package org.genomesmanager.services.repeats;

import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.repositories.repeats.RepeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("RepeatsBatchUpdater")
public class RepeatsBatchUpdater {
	// TODO! Replace print on screen with log
	@Autowired
	private RepeatRepository repeatRepo;

    public void updateRepatsParent() {
		List<Object[]> results = repeatRepo.findAllRepeatsWithParents();
		int total = results.size();
		System.out.println("Fetched " + total + " repeats");
		int count = 0;
		for (Object[] result:results) {
			int repId = (Integer) result[0];
			Repeat parent = repeatRepo.getParent(repId);
			if ( parent != null ) {
				Repeat r = repeatRepo.findOne(repId);
				r.setParent(parent);
				repeatRepo.save(r);
			}
			count++;
			if ( count % 10 == 0 ) {
				int perc = (count/total)*100;
				System.out.println("Done " + count + " of " + total + " " + perc + " %");
			}
		}
    }
    
}
