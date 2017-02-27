package org.genomesmanager.domain.entities.testobjectgenerators;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.Variety;

public class VarietiesTestObjectGenerator {
//	public static List<Variety> Generate(int amount) {
//		List<Species> species = SpeciesTestObjectGenerator.Generate(1);
//		return Generate(amount, species.get(0));
//	}
	
	public static List<Variety> Generate(int amount, Species species) {
		List<Variety> res = new ArrayList<Variety>();
		for (int i = 0; i < amount ; i++) {
			Variety var = new Variety();
			var.setAlias("VarAlias" + i);
			var.setName("VarName" + i);
			var.setSpecies(species);
			res.add(var);
		}
		return res;
	}

}
