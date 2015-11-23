package org.genomesmanager.services.repeats;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsClassificationException;

import java.util.List;

public interface RepeatsClassificationService {

	public abstract List<RepeatsClassification> getAll();

	public abstract List<String> getAllClassSubClassOrder();

	public abstract List<String> getAllSuperfamilies(String classifDefinition)
			throws RepeatsClassificationException;

	public abstract List<String> getAllSuperfamilies(String repClass,
			String subclass, String order);

	public abstract List<String> getAllFamilies(String classifDefinition)
			throws RepeatsClassificationException;

	public abstract List<String> getAllFamilies(String repClass,
			String subclass, String order, String superfamily);

	public abstract List<String> getAllClasses();

	public abstract List<String> getAllSubClasses(String repClass);

	public abstract List<String> getAllOrders(String repClass, String subclass);

	public abstract List<String> getAllOrders();

}