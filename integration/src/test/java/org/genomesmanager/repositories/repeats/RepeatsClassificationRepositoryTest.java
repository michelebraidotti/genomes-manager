package org.genomesmanager.repositories.repeats;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsClassificationException;
import org.genomesmanager.domain.entities.objectmothers.RepeatsClassificationOM;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepeatsClassificationRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private RepeatsClassificationRepository repeatsClassificationRepository;
	private String lineClass;
	private String helClass;
	private String miteClass;
	private String dnaTeClass;
	private String ltrClass;
	private String unknClass;
	private String sineClass;
	private List<String> repClassDefinitions;
	private List<RepeatsClassification> repClassList;
	private int preCount = 0;

	@Before
	public void createRepeatsClassifications() throws Exception {
		preCount = repeatsClassificationRepository.getAll().size();
		repClassDefinitions = new ArrayList<String>();
		lineClass = "I, I, LINE, test, test";
		repClassDefinitions.add(lineClass);
		helClass = "II, II, Helitron, test, test";
		repClassDefinitions.add(helClass);
		miteClass = "II, III, MITE, test, test";
		repClassDefinitions.add(miteClass);
		dnaTeClass = "II, I, DNA_TE, test, test";
		repClassDefinitions.add(dnaTeClass);
		ltrClass = "I, I, LTR, test, test";
		repClassDefinitions.add(ltrClass);
		unknClass = "UNKNOWN, UNKNOWN, UNKNOWN, test, test";
		repClassDefinitions.add(unknClass);
		sineClass = "I, I, SINE, test, test";
		repClassDefinitions.add(sineClass);
		repClassList = new ArrayList<RepeatsClassification>();
		for (String repClassDefinition:repClassDefinitions) {
			RepeatsClassification repClass = RepeatsClassificationOM.Generate(repClassDefinition);
			repeatsClassificationRepository.save(repClass);
			repClassList.add(repeatsClassificationRepository.findOne(repClass.getId()));
		}
	}

	@Test
	public void saveAndFindOne() throws Exception {
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

	@Test
	public void getAllTest() {
		assertEquals(preCount + repClassDefinitions.size(), repeatsClassificationRepository.getAll().size());
	}

	@Test
	public void getAllClassSubClassOrderTest() {
		int ordersCount = 7;
		assertEquals(ordersCount, repeatsClassificationRepository.getAllClassSubClassOrder().size());
	}

	@Test
	public void getAllSuperfamiliesTest() throws RepeatsClassificationException {
		assertEquals(2, repeatsClassificationRepository.getAllSuperfamilies(unknClass).size());
	}

	@Test
	public void getAllSuperfamiliesTest2() {
		for (RepeatsClassification repClass:repClassList) {
			assertTrue(repeatsClassificationRepository
				.getAllSuperfamilies(repClass.getRepClass(), repClass.getSubclass(), repClass.getOrder())
				.size() > 0);
		}
	}

	@Test
	public void getAllFamiliesTest() throws RepeatsClassificationException {
		for (String classifDefinition:repClassDefinitions)
			assertEquals(1, repeatsClassificationRepository.getAllFamilies(classifDefinition).size());
	}

	public void getAllFamiliesTest2() {
		for (RepeatsClassification repClass:repClassList)
			assertEquals(1, repeatsClassificationRepository.getAllFamilies(repClass.getRepClass(),
					repClass.getSubclass(), repClass.getOrder(), repClass.getSuperfamily()).size());
	}

	@Test
	public void getAllClassesTest() {
		assertTrue(repeatsClassificationRepository.getAllClasses().size() > 0);
	}

	@Test
	public void getAllSubClasses() {
		for (RepeatsClassification repClass:repClassList)
		 assertTrue(repeatsClassificationRepository
				 .getAllSubClasses(repClass.getRepClass()).size() > 0);
	}

	@Test
	public void getAllOrders() {
		for (RepeatsClassification repClass:repClassList)
			 assertTrue(repeatsClassificationRepository
					 .getAllOrders(repClass.getRepClass(), repClass.getSubclass()).size() > 0);
	}

	@Test
	public void getAllOrders2() {
		assertTrue(repeatsClassificationRepository.getAllOrders().size() > 0);
	}
		
}
