package org.genomesmanager.domain.entities.objectmothers;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.SpeciesPK;

public class SpeciesOM {
	public static List<Species> Generate(int amount) {
		List<Species> res = new ArrayList<Species>();
		for (int i = 0; i < amount ; i++) {
			Species s = new Species();
			SpeciesPK pk = new SpeciesPK();
			pk.setGenus("Genus" + i);
			pk.setSpecies("Species" + i);
			pk.setSubspecies("SubSpecies" +i);
			s.setId(pk);
			s.setCommonName("CommonName" + i);
			s.setGenomeType("GenomeType" +i);
			res.add(s);
		}
		return res;
	}
}
