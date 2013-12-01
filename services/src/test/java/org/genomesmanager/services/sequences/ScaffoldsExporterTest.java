package org.genomesmanager.services.sequences;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.common.formats.SimpleFasta;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.RepeatsClassificationOM;
import org.genomesmanager.domain.entities.objectmothers.RepeatsOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.sequences.ScaffoldsList;
import org.genomesmanager.repositories.species.SpeciesRepositoryCustom;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.impl.sequences.ScaffoldsExporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ScaffoldsExporterTest {
	@Mock
	private ScaffoldsList scaffoldsList;
	// Species s = speciesRepo.get(speciesDefinition);
	@Mock
	private SpeciesRepositoryCustom speciesRepo;
	@InjectMocks
	private ScaffoldsExporter scaffoldsExporter = new ScaffoldsExporterImpl();	
	private Random generator;
	private Chromosome chr;
	private ArrayList<Scaffold> seqs;
	private Species sp;
	
	
	@Before 
	public void initMocks() {
		MockitoAnnotations.initMocks(this); 
		generator = new Random();
		
		sp = SpeciesOM.Generate(1).get(0);
		chr = ChromosomesOM.Generate(1, sp).get(0);
		seqs = new ArrayList<Scaffold>();
		int i = 1;
		int lastId = generator.nextInt();
		for (Scaffold scaffold:SequencesOM.GenerateScaffold(5, chr)) {
			scaffold.setId(lastId++);
			scaffold.setOrder(i++);
			scaffold.setIsUnplaced(false);
			seqs.add(scaffold);
		}
		for (Scaffold scaffold:SequencesOM.GenerateScaffold(4, chr)) {
			scaffold.setId(lastId++);
			scaffold.setOrder(0);
			scaffold.setIsUnplaced(true);
			seqs.add(scaffold);
		}
	}
	
	@Test
	public void getAllScaffoldsTest() throws SpeciesRepoException, SequenceSliceException {
		when(scaffoldsList.getAllByChromosome(chr.getId())).thenReturn(seqs);
		when(scaffoldsList.getAllBySpecies(sp.getId())).thenReturn(seqs);
		when(speciesRepo.get(sp.toString())).thenReturn(sp);
		
		Boolean maskSequence = false;
		List<SimpleFasta> fastaContent = scaffoldsExporter
				.getAllSequencesByChromosome(chr.getId(), maskSequence );
		assertEquals(seqs.size(), fastaContent.size());
		fastaContent = scaffoldsExporter.getAllSequencesBySpecies(sp.toString(), maskSequence );
		assertEquals(seqs.size(), fastaContent.size());
	}
	
	@Test
	public void getAllScaffoldsMaskedTest() throws Exception {
		for (Sequence seq:seqs) {
			RepeatsClassification repClass = RepeatsClassificationOM.Generate("I, I, LINE, test, test");
			Repeat repeat = RepeatsOM.Generate(1, repClass, seq).get(0);
			seq.getRepeats().add(repeat);
		}
		when(scaffoldsList.getAllByChromosome(chr.getId())).thenReturn(seqs);
		when(scaffoldsList.getAllBySpecies(sp.getId())).thenReturn(seqs);
		when(speciesRepo.get(sp.toString())).thenReturn(sp);
		
		Boolean maskSequence = true;
		List<SimpleFasta> fastaContent = scaffoldsExporter
				.getAllSequencesByChromosome(chr.getId(), maskSequence );
		assertEquals(seqs.size(), fastaContent.size());
		fastaContent = scaffoldsExporter.getAllSequencesBySpecies(sp.toString(), maskSequence );
		assertEquals(seqs.size(), fastaContent.size());
	}
}
