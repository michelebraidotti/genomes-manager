package org.genomesmanager.repositories.jpa.repeats;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.objectmothers.RepeatsClassificationOM;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepeatsClassificationRepoTest extends AbstractIntegrationTest {
	@Autowired
	private RepeatsClassificationRepo repeatsClassificationRepo;
	
	
	@Test
	public void test() throws Exception {
		String [] repClassDefinitions = {
				 "I, I, LINE, test, test",
				 "II, II, Helitron, test, test",
				 "II, III, MITE, test, test",
				 "II, I, DNA_TE, test, test",
				 "I, I, LTR, test, test",
				 "UNKNOWN, UNKNOWN, UNKNOWN, test, test",
				 "I, I, SINE, test, test"
		};
		for (String repClassDefinition:repClassDefinitions) {
			RepeatsClassification repClass = RepeatsClassificationOM.Generate(repClassDefinition);
			repeatsClassificationRepo.insert(repClass);
			RepeatsClassification repClassPost = repeatsClassificationRepo.get(repClass.getId());
			assertEquals(repClassPost,repClass);
		}
	}
}
