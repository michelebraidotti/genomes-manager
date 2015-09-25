package org.genomesmanager.services.repeats;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.batch.repeats.RepeatsBatchUpdater;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.junit.Test;
import org.mockito.Mock;

public class RepeatsBatchUpdaterTest {
	@Mock
	private RepeatsBatchUpdater repeatsBatchUpdater;

	@Test
	public void test() throws Exception {
		repeatsBatchUpdater.updateRepatsParent();
		assertEquals(true, false);
	}
}
