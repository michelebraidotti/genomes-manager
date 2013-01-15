package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsClassificationPK;
import org.genomesmanager.domain.entities.RepeatsOrder;

public interface RepeatsClassificationRepo {

	public abstract RepeatsClassification generate(String name, String agiDesc)
			throws RepeatsClassificationRepoException;

	public abstract void delete(RepeatsClassification repeatsClass);

	public abstract RepeatsClassification get(String repClassPKDefinition)
			throws RepeatsClassificationRepoException;

	public abstract RepeatsClassification get(RepeatsClassificationPK repClassPK);

	public abstract void insert(RepeatsClassification repeatsClass)
			throws RepeatsClassificationRepoException;

	public abstract void insertIfNotExists(RepeatsClassification repeatsClass)
			throws RepeatsClassificationRepoException;

	public abstract RepeatsOrder getRepeatOrder(String classifDefinition)
			throws RepeatsClassificationRepoException;

}