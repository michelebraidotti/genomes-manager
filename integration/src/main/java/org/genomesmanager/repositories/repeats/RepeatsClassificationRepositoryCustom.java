package org.genomesmanager.repositories.repeats;

import java.util.List;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsClassificationException;

public interface RepeatsClassificationRepositoryCustom {
	public List<RepeatsClassification> getAll();

	public List<String> getAllClassSubClassOrder();

	public List<String> getAllSuperfamilies(String classifDefinition)
			throws RepeatsClassificationException;

	public List<String> getAllSuperfamilies(String repClass, String subclass, String order);

	public List<String> getAllFamilies(String classifDefinition)
			throws RepeatsClassificationException;

	public List<String> getAllFamilies(String repClass, String subclass, String order, String superfamily);

	public List<String> getAllClasses();

	public List<String> getAllSubClasses(String repClass);

	public List<String> getAllOrders(String repClass, String subclass);

	public List<String> getAllOrders();
}
