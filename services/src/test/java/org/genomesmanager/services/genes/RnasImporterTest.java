package org.genomesmanager.services.genes;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.testobjectgenerators.ChromosomesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SequencesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.repositories.genes.RnaRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RnasImporterTest {
	
	@Mock
	private SequenceRepository sequenceRepository;
	@Mock
	private RnaRepository rnaRepository;
	@InjectMocks
	private RnasImporter rnasImporter = new RnasImporter();
	private Random generator;
	private Sequence seq;
	private List<String> gff3Content = new ArrayList<String>();
	
	@Before 
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		generator = new Random();
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr.setId(generator.nextInt());
		seq = SequencesTestObjectGenerator.Generate(1, chr).get(0);
		seq.setId(generator.nextInt());
		
		gff3Content.add(seq.getId() +"\t##gff-version 3");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	RNA	80	96	.	+	.	ID=Name0;Name=RnaName0");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	RNA	31	32	.	-	.	ID=Name1;Name=RnaName1");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	RNA	90	90	.	+	.	ID=Name2;Name=RnaName2");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	RNA	10	17	.	-	.	ID=Name3;Name=RnaName3");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	RNA	21	25	.	+	.	ID=Name4;Name=RnaName4");
	}
	
	@Test
	public void testImport() {
		when(sequenceRepository.findLatest(anyString())).thenReturn(seq);
		//for (String s:ggf3Content) System.out.println(s);
		rnasImporter.parseMipsGenePredictionGff3(gff3Content);
		rnasImporter.save();
		assertTrue(rnasImporter.getErrors().size() == 1);
		assertTrue(rnasImporter.getWarnings().size() == 0);
		assertTrue(rnasImporter.getRnas().size() > 0);
		verify(rnaRepository, times(rnasImporter.getRnas().size())).save((Rna) anyObject());
	}
}
