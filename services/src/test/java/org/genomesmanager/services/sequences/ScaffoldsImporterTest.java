package org.genomesmanager.services.sequences;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.services.impl.sequences.ScaffoldsImporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ScaffoldsImporterTest {
	@Mock
	private ChromosomeRepo chromosomeRepo;
	@Mock
	private SequenceRepo sequenceRepo;
	@InjectMocks
	private ScaffoldsImporter scaffoldsImporter = new ScaffoldsImporterImpl();	
	private Chromosome chr;
	private List<String> manifest;
	private List<String> fastaContent;
	private Species sp;
	private Scaffold dummySequence;
	
	
	@Before 
	public void initMocks() throws SequenceRepoException {
		MockitoAnnotations.initMocks(this); 
		
		sp = SpeciesOM.Generate(1).get(0);
		chr = ChromosomesOM.Generate(1, sp).get(0);
		dummySequence = SequencesOM.GenerateScaffold(1, chr).get(0);
		manifest = ScaffoldsImporterOM.GenerateManifest(20, chr);
		fastaContent = ScaffoldsImporterOM.GenerateFasta(manifest);
		
		when(chromosomeRepo.get(chr.getNumber(), sp)).thenReturn(chr);
		when(sequenceRepo.get(anyString())).thenReturn(dummySequence);
	}

	@Test
	public void getScaffoldsTest() throws ScaffoldsImporterException {
		scaffoldsImporter.importScaffolds(manifest, fastaContent, sp);
		assertEquals(manifest.size(), scaffoldsImporter.getScaffolds().size());
	}
	
	@Test
	public void countScaffoldsTest() throws ScaffoldsImporterException {
		scaffoldsImporter.importScaffolds(manifest, fastaContent, sp);
		assertEquals(manifest.size(), scaffoldsImporter.countScaffolds());
		assertEquals(0, scaffoldsImporter.getWrongLines().size());
		assertEquals(0, scaffoldsImporter.getWarningLines().size());
	}
	
	@Test
	public void resetImporterTest() throws ScaffoldsImporterException {
		scaffoldsImporter.importScaffolds(manifest, fastaContent, sp);
		scaffoldsImporter.reset();
		assertEquals(0, scaffoldsImporter.countScaffolds());
	}
	
	@Test
	public void saveImporterTest() throws ScaffoldsImporterException {
		scaffoldsImporter.importScaffolds(manifest, fastaContent, sp);
		scaffoldsImporter.save();
		int nOfScaffolds = scaffoldsImporter.countScaffolds();
		verify(sequenceRepo, times(nOfScaffolds)).insert((Scaffold) anyObject());
	}	
	
	@Test
	public void warningsTest() throws ScaffoldsImporterException {
		fastaContent.add(">emptyone");
		fastaContent.add("");
		manifest.add("emptyone\t21\tCN0");
		scaffoldsImporter.importScaffolds(manifest, fastaContent, sp);
		assertEquals(1, scaffoldsImporter.getWarningLines().size());
	}
	
	
}
