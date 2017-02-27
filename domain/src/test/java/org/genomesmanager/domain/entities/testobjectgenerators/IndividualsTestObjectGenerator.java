package org.genomesmanager.domain.entities.testobjectgenerators;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Individual;
import org.genomesmanager.domain.entities.Variety;

public class IndividualsTestObjectGenerator {
	public static List<Individual> Generate(int amount, Variety variety) {
		List<Individual> res = new ArrayList<Individual>();
		for (int i = 0; i < amount ; i++) {
			Individual ind = new Individual();
			ind.setDescription("Description" + i);
			ind.setVariety(variety);
			res.add(ind);
		}
		return res;
		
	}
}
