package org.genomesmanager.domain.entities.objectmothers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.Exon;
import org.genomesmanager.domain.entities.Mrna;

public class ExonsOM {
	public static List<Exon> Generate(int amount, Mrna mrna) {
		List<Exon> exons = new ArrayList<Exon>();
		Random generator = new Random();
		for (int i = 0; i < amount ; i++) {
			Exon exon = new Exon();
			exon.setMrna(mrna);
			exon.setName("name" + i);
			String strandness = ( i%2 ==0 ? "+" : "-");
			exon.setStrandness(strandness);
			exon.setX(generator.nextInt(mrna.length()));
			int offset = mrna.length() - exon.getX();
			exon.setY(exon.getX() + generator.nextInt(offset) - 1);
			exons.add(exon);
		}
		return exons;
	}
}
