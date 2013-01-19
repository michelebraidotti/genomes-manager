package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsClassificationPK;
import org.genomesmanager.domain.entities.RepeatsOrder;

public interface RepeatsClassificationRepo {

	public abstract RepeatsClassification generate(String name, String agiDesc)
			throws RepeatsClassificationException;

	public abstract void delete(RepeatsClassification repeatsClass);

	public abstract RepeatsClassification get(String repClassPKDefinition)
			throws RepeatsClassificationException;

	public abstract RepeatsClassification get(RepeatsClassificationPK repClassPK);

	public abstract void insert(RepeatsClassification repeatsClass)
			throws RepeatsClassificationException;

	public abstract void insertIfNotExists(RepeatsClassification repeatsClass)
			throws RepeatsClassificationException;

	public abstract RepeatsOrder getRepeatOrder(String classifDefinition)
			throws RepeatsClassificationException;

}