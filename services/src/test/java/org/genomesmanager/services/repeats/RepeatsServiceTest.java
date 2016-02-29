package org.genomesmanager.services.repeats;

import org.genomesmanager.domain.entities.*;
import org.genomesmanager.domain.entities.objectmothers.*;
import org.genomesmanager.repositories.repeats.RepeatRepository;
import org.genomesmanager.services.impl.repeats.RepeatsServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RepeatsServiceTest {
	@Mock
	private RepeatRepository repeatRepository;
	@InjectMocks
	private RepeatsService repeatsService = new RepeatsServiceImpl();
	private Repeat repeat;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		Species sp = SpeciesOM.Generate(1).get(0);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		Sequence seq = SequencesOM.Generate(1,chr).get(0);
		String repClassDefinition = "TEST, TEST, TEST, test, test";
		RepeatsClassification repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		repeat = RepeatsOM.Generate(1, repClass, seq).get(0);
	}

	@Test
	public void testSaveNew() throws Exception {
		repeatsService.save(repeat);

		verify(repeatRepository, times(0)).validateUpdate((Repeat) anyObject());
		verify(repeatRepository, times(0)).updateContainedElementsCount((Repeat) anyObject());
		verify(repeatRepository, times(1)).save((Repeat) anyObject());
	}

	@Test
	public void testSaveExisting() throws Exception {
		repeat.setId(99999999);
		repeatsService.save(repeat);

		verify(repeatRepository, times(1)).validateUpdate((Repeat) anyObject());
		verify(repeatRepository, times(1)).updateContainedElementsCount((Repeat) anyObject());
		verify(repeatRepository, times(1)).save((Repeat) anyObject());
	}
}
