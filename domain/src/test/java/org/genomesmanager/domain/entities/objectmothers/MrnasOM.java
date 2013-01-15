package org.genomesmanager.domain.entities.objectmothers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Mrna;

public class MrnasOM {
	public static List<Mrna> Generate(int amount, Gene gene) {
		List<Mrna> mrnas = new ArrayList<Mrna>();
		Random generator = new Random();
		for (int i = 0; i < amount ; i++) {
			Mrna mrna = new Mrna();
			mrna.setDescription("description" + i);
			mrna.setGene(gene);
			mrna.setName("name" + i);
			String strandness = ( i%2 ==0 ? "+" : "-");
			mrna.setStrandness(strandness);
			mrna.setX(generator.nextInt(gene.length()));
			int offset = gene.length() - mrna.getX();
			mrna.setY(mrna.getX() + generator.nextInt(offset) - 1);
			mrnas.add(mrna);
		}
		return mrnas;
	}
}
