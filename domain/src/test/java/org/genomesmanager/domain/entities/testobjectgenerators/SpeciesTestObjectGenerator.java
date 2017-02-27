package org.genomesmanager.domain.entities.testobjectgenerators;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Species;

public class SpeciesTestObjectGenerator {
	public static List<Species> Generate(int amount) {
		List<Species> res = new ArrayList<Species>();
		for (int i = 0; i < amount ; i++) {
			Species s = new Species();
			s.setGenus("Genus" + i);
			s.setSpecies("Species" + i);
			s.setSubspecies("SubSpecies" +i);
			s.setCommonName("CommonName" + i);
			s.setGenomeType("GenomeType" +i);
			res.add(s);
		}
		return res;
	}
}
