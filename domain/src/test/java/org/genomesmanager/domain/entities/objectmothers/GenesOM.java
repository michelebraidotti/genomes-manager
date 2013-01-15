package org.genomesmanager.domain.entities.objectmothers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Sequence;

public class GenesOM {
	public static List<Gene> Generate(int amount, Sequence sequence) {
		List<Gene> genes = new ArrayList<Gene>();
		Random generator = new Random();
		for (int i = 0; i < amount ; i++) {
			Gene gene = new Gene();
			gene.setGcContent(new BigDecimal(i));
			gene.setName("Gene" + i);
			gene.setSequence(sequence);
			String strandness = ( i%2 ==0 ? "+" : "-");
			gene.setStrandness(strandness);
			gene.setType("Type" + i);
			gene.setX(generator.nextInt(sequence.getLength()));
			int offset = sequence.getLength() - gene.getX();
			gene.setY(gene.getX() + generator.nextInt(offset) - 1);
			genes.add(gene);
		}
		return genes;
	}
}
