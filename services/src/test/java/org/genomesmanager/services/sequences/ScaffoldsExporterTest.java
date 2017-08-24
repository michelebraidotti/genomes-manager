package org.genomesmanager.services.sequences;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.formats.SimpleFasta;
import org.genomesmanager.domain.dtos.CannotParseSpeciesDefinitionException;
import org.genomesmanager.domain.entities.Chromosome;
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
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.ScaffoldRepository;
import org.genomesmanager.services.species.SpeciesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ScaffoldsExporterTest {
	@Mock
	private ScaffoldRepository scaffoldRepository;
	@Mock
	private SpeciesService speciesService;
	@Mock
	private ChromosomeRepository chromosomeRepository;
	@InjectMocks
	private ScaffoldsExporter scaffoldsExporter = new ScaffoldsExporter();
	private Random generator;
	private Chromosome chr;
	private ArrayList<Scaffold> seqs;
	private Species sp;
	
	
	@Before 
	public void initMocks() {
		MockitoAnnotations.initMocks(this); 
		generator = new Random();
		
		sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		seqs = new ArrayList<Scaffold>();
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
	public void getAllScaffoldsTest() throws SequenceSliceException, CannotParseSpeciesDefinitionException {
		when(scaffoldRepository.findByChromosome(chr)).thenReturn(seqs);
		when(scaffoldRepository.findByChromosomeSpecies(sp)).thenReturn(seqs);
		when(chromosomeRepository.findOne(chr.getId())).thenReturn(chr);
		when(speciesService.get(sp.toString(" "))).thenReturn(sp);
		
		Boolean maskSequence = false;
		List<SimpleFasta> fastaContent = scaffoldsExporter
				.getAllSequencesByChromosome(chr.getId(), maskSequence );
		assertEquals(seqs.size(), fastaContent.size());
		fastaContent = scaffoldsExporter.getAllSequencesBySpecies(sp.toString(" "), maskSequence );
		assertEquals(seqs.size(), fastaContent.size());
	}
	
	@Test
	public void getAllScaffoldsMaskedTest() throws Exception {
		for (Sequence seq:seqs) {
			RepeatsClassification repClass = RepeatsClassificationTestObjectGenerator.Generate("I, I, LINE, test, test");
			Repeat repeat = RepeatsTestObjectGenerator.Generate(1, repClass, seq).get(0);
			seq.getRepeats().add(repeat);
		}
		when(scaffoldRepository.findByChromosome(chr)).thenReturn(seqs);
		when(scaffoldRepository.findByChromosomeSpecies(sp)).thenReturn(seqs);
		when(chromosomeRepository.findOne(chr.getId())).thenReturn(chr);
		when(speciesService.get(sp.toString(" "))).thenReturn(sp);
		
		Boolean maskSequence = true;
		List<SimpleFasta> fastaContent = scaffoldsExporter
				.getAllSequencesByChromosome(chr.getId(), maskSequence );
		assertEquals(seqs.size(), fastaContent.size());
		fastaContent = scaffoldsExporter.getAllSequencesBySpecies(sp.toString(), maskSequence );
		assertEquals(seqs.size(), fastaContent.size());
	}
}
