package org.genomesmanager.domain.entities.objectmothers;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsClassificationPK;

public class RepeatsClassificationOM {
	public static RepeatsClassification Generate(String classificationCommaSeparated) throws Exception {
		String [] pieces = classificationCommaSeparated.split(",\\s*");
		if ( pieces.length != 5 ) 
			throw new Exception("Error parsing classification definition");
		RepeatsClassification repClass = new RepeatsClassification();
		RepeatsClassificationPK pk = new RepeatsClassificationPK();
		int i = 0;
		pk.setRepClass(pieces[i++]);
		pk.setSubclass(pieces[i++]);
		pk.setOrder(pieces[i++]);
		pk.setSuperfamily(pieces[i++]);
		pk.setFamily(pieces[i++]);
		repClass.setId(pk);
		return repClass;
	}
}
