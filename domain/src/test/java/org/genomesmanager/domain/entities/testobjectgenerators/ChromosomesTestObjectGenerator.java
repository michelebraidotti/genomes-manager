package org.genomesmanager.domain.entities.testobjectgenerators;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Species;

public class ChromosomesTestObjectGenerator {
	
	public static List<Chromosome> Generate(int amount, Species species) {
		List<Chromosome> res = new ArrayList<Chromosome>();
		for (int i = 0; i < amount ; i++) {
			Chromosome chr = new Chromosome();
			chr.setNumber("CN" + i);
			chr.setSpecies(species);
			res.add(chr);
		}
		return res;
	}

}
