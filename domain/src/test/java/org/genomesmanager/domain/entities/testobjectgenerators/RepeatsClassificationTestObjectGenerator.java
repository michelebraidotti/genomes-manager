package org.genomesmanager.domain.entities.testobjectgenerators;

import org.genomesmanager.domain.entities.RepeatsClassification;

public class RepeatsClassificationTestObjectGenerator {
	public static RepeatsClassification Generate(
			String classificationCommaSeparated) throws Exception {
		String[] pieces = classificationCommaSeparated.split(",\\s*");
		if (pieces.length != 5)
			throw new Exception("Error parsing classification definition");
		RepeatsClassification repClass = new RepeatsClassification();
		int i = 0;
		repClass.setRepClass(pieces[i++]);
		repClass.setSubclass(pieces[i++]);
		repClass.setOrder(pieces[i++]);
		repClass.setSuperfamily(pieces[i++]);
		repClass.setFamily(pieces[i++]);
		return repClass;
	}
}
