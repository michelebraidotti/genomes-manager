package org.genomesmanager.services.sequences;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.testobjectgenerators.ChromosomesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.ScaffoldRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ScaffoldsImporterTest {
	@Mock
	private ChromosomeRepository chromosomeRepo;
	@Mock
	private ScaffoldRepository scaffoldRepository;
	@InjectMocks
	private ScaffoldsImporter scaffoldsImporter = new ScaffoldsImporter();
	private Chromosome chr;
	private List<String> manifest;
	private List<String> fastaContent;
	private Species sp;
	
	@Before 
	public void initMocks() {
		MockitoAnnotations.initMocks(this); 
		
		sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		manifest = ScaffoldsImporterTestObjectGenerator.GenerateManifest(20, chr);
		fastaContent = ScaffoldsImporterTestObjectGenerator.GenerateFasta(manifest);
		
		when(chromosomeRepo.findByChromosomeNumberAndSpecies(chr.getNumber(), sp)).thenReturn(chr);
		when(scaffoldRepository.findByName(anyString())).thenReturn(new ArrayList<Scaffold>());
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
		when(scaffoldRepository.save((Scaffold) anyObject())).thenReturn(new Scaffold());

		scaffoldsImporter.importScaffolds(manifest, fastaContent, sp);
		scaffoldsImporter.save();
		int nOfScaffolds = scaffoldsImporter.countScaffolds();
		verify(scaffoldRepository, times(nOfScaffolds)).save((Scaffold) anyObject());
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
