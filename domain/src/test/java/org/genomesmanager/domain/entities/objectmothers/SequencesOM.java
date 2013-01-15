package org.genomesmanager.domain.entities.objectmothers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Sequence;

public class SequencesOM {
	public static List<Sequence> Generate(int amount, Chromosome chr) {
		List<Sequence> sequences = new ArrayList<Sequence>();
		for (int i = 0; i < amount ; i++) {
			Sequence seq = new Sequence();
			seq.setChromosome(chr);
			String seqText = GenerateSequeceString();
			seq.setSequence(seqText);
			seq.setLength(seqText.length());
			sequences.add(seq);
		}
		return sequences;
	}
	
	public static StringBuilder GenererateSequence(int length) {
		String [] nuclotides = {"A","C","T","G"};
		Random generator = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < generator.nextInt(length); i++) {
			sb.append(nuclotides[generator.nextInt(4)]);
		}
		if (sb.length() < 10) 
			return new StringBuilder("AACTGGACTC");
		return sb;
	}
	
	private static String GenerateSequeceString() {
		return GenererateSequence(10000).toString();
	}
	
	public static List<Pseudomolecule> GeneratePseudomolecule(int amount, Chromosome chr) {
		List<Pseudomolecule> sequences = new ArrayList<Pseudomolecule>();
		for (int i = 0; i < amount ; i++) {
			Pseudomolecule seq = new Pseudomolecule();
			seq.setChromosome(chr);
			String seqText = GenerateSequeceString();
			seq.setSequence(seqText);
			seq.setLength(seqText.length());
			seq.setVersion("Version" + i);
			seq.setName("PseudomolName" + i);
			seq.setIsScaffoldDerived(true);
			seq.setIsUnplaced(false);
			sequences.add(seq);
		}
		return sequences;
	}
	
	public static List<Scaffold> GenerateScaffold(int amount, Chromosome chr) {
		List<Scaffold> sequences = new ArrayList<Scaffold>();
		for (int i = 0; i < amount ; i++) {
			Scaffold seq = new Scaffold();
			seq.setChromosome(chr);
			String seqText = GenerateSequeceString();
			seq.setSequence(seqText);
			seq.setLength(seqText.length());
			seq.setName("ScaffoldName" + i);
			seq.setScaffVersion("ScaffVersion" + i);
			seq.setOrder(i);
			seq.setPseudomolOffset((long) (1000 * i));
			seq.setIsUnplaced(false);
			sequences.add(seq);
		}
		return sequences;
	}
}
