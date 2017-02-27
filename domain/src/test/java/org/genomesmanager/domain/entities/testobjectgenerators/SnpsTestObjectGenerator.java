package org.genomesmanager.domain.entities.testobjectgenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.Individual;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Snp;

public class SnpsTestObjectGenerator {
	public static List<Snp> Generate(int amount, Individual individual, Sequence sequence) {
		List<Snp> snps = new ArrayList<Snp>();
		Random generator = new Random();
		for (int i = 0; i < amount ; i++) {
			Snp snp = new Snp();
			snp.setIndividual(individual);
			snp.setPos(generator.nextInt(sequence.getLength()));
			snp.setReference("A");
			snp.setReseq("C");
			snp.setSequence(sequence);
			snps.add(snp);
		}
		return snps;
	}
}
