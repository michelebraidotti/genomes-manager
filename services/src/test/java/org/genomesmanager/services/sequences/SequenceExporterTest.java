package org.genomesmanager.services.sequences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.testobjectgenerators.ChromosomesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.RepeatsClassificationTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.RepeatsTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SequencesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.services.impl.sequences.SequencesExporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SequenceExporterTest {
	@Mock
	private SequenceRepository sequenceRepository;
	@InjectMocks
	private SequencesExporter sequencesExporter = new SequencesExporterImpl();	
	private Random generator;
	private Chromosome chr;
	private ArrayList<Sequence> seqs;
	
	
	@Before 
	public void initMocks() {
		MockitoAnnotations.initMocks(this); 
		generator = new Random();
		
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		seqs = new ArrayList<>();
		
		Pseudomolecule pseudomol = SequencesTestObjectGenerator.GeneratePseudomolecule(1,chr).get(0);
		pseudomol.setId(generator.nextInt());
		pseudomol.setScaffoldDerived(false);
		pseudomol.setUnplaced(false);
		seqs.add(pseudomol);
		int i = 1;
		int lastId = generator.nextInt();
		for (Scaffold scaffold: SequencesTestObjectGenerator.GenerateScaffold(5, chr)) {
			scaffold.setId(lastId++);
			scaffold.setOrder(i++);
			scaffold.setIsUnplaced(false);
			seqs.add(scaffold);
		}
		for (Scaffold scaffold: SequencesTestObjectGenerator.GenerateScaffold(4, chr)) {
			scaffold.setId(lastId++);
			scaffold.setOrder(0);
			scaffold.setIsUnplaced(true);
			seqs.add(scaffold);
		}
	}
	
	@Test
	public void getFastaTest() throws SequenceSliceException {
		when(sequenceRepository.findByChromosome(chr)).thenReturn(seqs);
		
		List<String> fastaContent = sequencesExporter.getFastaContent(chr);
		assertEquals(seqs.size(), fastaContent.size()/2);
	}
	
	@Test
	public void getFastaMasked() throws Exception {
		for (Sequence seq:seqs) {
			RepeatsClassification repClass = RepeatsClassificationTestObjectGenerator.Generate("I, I, LINE, test, test");
			Repeat repeat = RepeatsTestObjectGenerator.Generate(1, repClass, seq).get(0);
			seq.getRepeats().add(repeat);
		}
		when(sequenceRepository.findByChromosome(chr)).thenReturn(seqs);
		
		List<String> fastaContent = sequencesExporter.getMaskedFastaContent(chr);
		assertEquals(seqs.size(), fastaContent.size()/2);
		for (String maskedFasta:fastaContent) {
			assertTrue(maskedFasta.contains("N"));
		}
	}
}
