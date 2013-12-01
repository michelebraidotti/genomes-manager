package org.genomesmanager.repositories.jpa.repeats;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.objectmothers.RepeatsClassificationOM;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.repeats.RepeatsClassificationException;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepository;
import org.genomesmanager.repositories.repeats.RepeatsClassificationsList;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepeatsClassificationsListTest extends AbstractIntegrationTest {
	@Autowired
	private RepeatsClassificationsList repeatsClassificationsList;
	@Autowired
	private RepeatsClassificationRepository repeatsClassificationRepo;
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
		preCount = repeatsClassificationsList.getAll().size();
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
			repeatsClassificationRepo.insert(repClass);
			repClassList.add(repeatsClassificationRepo.get(repClass.getId()));
		}
	}

	@Test
	public void getAllTest() {
		assertEquals(preCount + repClassDefinitions.size(), repeatsClassificationsList.getAll().size());
	}

	@Test
	public void getAllClassSubClassOrderTest() {
		int ordersCount = 7;
		assertEquals(ordersCount, repeatsClassificationsList.getAllClassSubClassOrder().size());
	}

	@Test
	public void getAllSuperfamiliesTest() throws RepeatsClassificationException {
		assertEquals(2, repeatsClassificationsList.getAllSuperfamilies(unknClass).size());
	}

	@Test
	public void getAllSuperfamiliesTest2() {
		for (RepeatsClassification repClass:repClassList) {
			assertTrue(repeatsClassificationsList
				.getAllSuperfamilies(repClass.getId().getRepClass(), repClass.getId().getSubclass(), repClass.getId().getOrder())
				.size() > 0);
		}
	}

	@Test
	public void getAllFamiliesTest() throws RepeatsClassificationException {
		for (String classifDefinition:repClassDefinitions)
			assertEquals(1, repeatsClassificationsList.getAllFamilies(classifDefinition).size());
	}

	public void getAllFamiliesTest2() {
		for (RepeatsClassification repClass:repClassList)
			assertEquals(1, repeatsClassificationsList.getAllFamilies(repClass.getId().getRepClass(), 
					repClass.getId().getSubclass(), repClass.getId().getOrder(), repClass.getId().getSuperfamily()).size());
	}

	@Test
	public void getAllClassesTest() {
		assertTrue(repeatsClassificationsList.getAllClasses().size() > 0);
	}

	@Test
	public void getAllSubClasses() {
		for (RepeatsClassification repClass:repClassList)
		 assertTrue(repeatsClassificationsList
				 .getAllSubClasses(repClass.getId().getRepClass()).size() > 0);
	}

	@Test
	public void getAllOrders() {
		for (RepeatsClassification repClass:repClassList)
			 assertTrue(repeatsClassificationsList
					 .getAllOrders(repClass.getId().getRepClass(), repClass.getId().getSubclass()).size() > 0);
	}

	@Test
	public void getAllOrders2() {
		assertTrue(repeatsClassificationsList.getAllOrders().size() > 0);
	}
		
}
