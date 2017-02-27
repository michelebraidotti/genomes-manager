package org.genomesmanager.services.sequences;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.Chromosome;

public class ScaffoldsImporterTestObjectGenerator {

	public static List<String> GenerateManifest(int nOfScaffolds, Chromosome chr) {
		List<String> res = new ArrayList<String>();
		for (int i = 1; i <= nOfScaffolds; i++) {
			res.add("Scaffold" + i + "\t" + i + "\t" + chr.getNumber());
		}
		return res;
	}
	
	public static List<String> GenerateFasta(List<String> manifest) {
		List<String> res = new ArrayList<String>();
		for (String line:manifest) {
			String scaffName = line.split("\\s+")[0];
			res.add(">" + scaffName);
			res.add(GenererateSequence().toString());
		}
		return res;
	}
	
	private static StringBuilder GenererateSequence() {
		String [] nuclotides = {"A","C","T","G"};
		Random generator = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < generator.nextInt(10000); i++) {
			sb.append(nuclotides[generator.nextInt(4)]);
		}
		if (sb.length() < 10) 
			return new StringBuilder("AACTGGACTC");
		return sb;
	}
}
