package org.genomesmanager.domain.entities.testobjectgenerators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.domain.entities.Sequence;

public class RnasTestObjectGenerator {
	public static List<Rna> Generate(int amount, Sequence sequence) {
		List<Rna> rnas = new ArrayList<Rna>();
		Random generator = new Random();
		for (int i = 0; i < amount ; i++) {	
			Rna rna = new Rna();
			rna.setGcContent(new BigDecimal(i));
			rna.setName("Name" + i);
			rna.setRnaName("RnaName" + i);
			rna.setSequence(sequence);
			String strandness = ( i%2 ==0 ? "+" : "-");
			rna.setStrandness(strandness);
			rna.setType("Type" + i);
			rna.setX(generator.nextInt(sequence.getLength()));
			int offset = sequence.getLength() - rna.getX();
			rna.setY(rna.getX() + generator.nextInt(offset) - 1);
			rnas.add(rna);
		}
		return rnas;
	}
}
