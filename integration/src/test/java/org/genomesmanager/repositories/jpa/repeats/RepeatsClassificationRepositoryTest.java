package org.genomesmanager.repositories.jpa.repeats;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.objectmothers.RepeatsClassificationOM;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepeatsClassificationRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private RepeatsClassificationRepository repeatsClassificationRepository;

	@Test
	public void test() throws Exception {
		String[] repClassDefinitions = { "I, I, LINE, test, test",
				"II, II, Helitron, test, test", "II, III, MITE, test, test",
				"II, I, DNA_TE, test, test", "I, I, LTR, test, test",
				"UNKNOWN, UNKNOWN, UNKNOWN, test, test",
				"I, I, SINE, test, test" };
		for (String repClassDefinition : repClassDefinitions) {
			RepeatsClassification repClass = RepeatsClassificationOM
					.Generate(repClassDefinition);
			repeatsClassificationRepository.save(repClass);
			RepeatsClassification repClassPost = repeatsClassificationRepository
					.findOne(repClass.getId());
			assertEquals(repClassPost, repClass);
		}
	}
}
