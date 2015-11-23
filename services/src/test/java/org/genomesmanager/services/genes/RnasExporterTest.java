package org.genomesmanager.services.genes;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Random;

import org.genomesmanager.common.formats.AgiExportType;
import org.genomesmanager.domain.dtos.CannotParseSpeciesDefinitionException;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.RnasOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.genes.RnaRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.services.impl.genes.RnasExporterImpl;
import org.genomesmanager.services.species.SpeciesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RnasExporterTest {
	@Mock
	private RnaRepository rnaRepository;
	@Mock
	private SpeciesService speciesService;
	@Mock
	private SequenceRepository sequenceRepository;
	@InjectMocks
	private RnasExporter rnasExporter = new RnasExporterImpl();
	private Species sp;
	private Chromosome chr;
	private Random generator;
	private ArrayList<Rna> rnas;

	@Before 
	public void initMocks() {
		MockitoAnnotations.initMocks(this); 
		
		generator = new Random();
		int nOfRnas = 5;
		sp = SpeciesOM.Generate(1).get(0);
		speciesService.save(sp);
		chr = ChromosomesOM.Generate(1, sp).get(0);
		chr.setId(generator.nextInt());
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq.setId(generator.nextInt());
		rnas = new ArrayList<Rna>();
		int rnaId = generator.nextInt();
		for (Rna rna:RnasOM.Generate(nOfRnas, seq)) {
			rna.setId(rnaId++);
			rnas.add(rna);
		}
	}
	
	@Test
	public void testExportByChr() throws RnasExporterException {
		when(rnaRepository.findBySequenceChromosome(chr)).thenReturn(rnas);
		
		rnasExporter.setRnasList(chr);
		rnasExporter.setFileContent(AgiExportType.GFF3PLUS, false);

		assertTrue(rnasExporter.getFileContent().size() > 0);
	}
	
	@Test
	public void testExportByChrNormalGff3() throws RnasExporterException {
		when(rnaRepository.findBySequenceChromosome(chr)).thenReturn(rnas);
		
		rnasExporter.setRnasList(chr);
		rnasExporter.setFileContent(AgiExportType.GFF3, false);
		assertTrue(rnasExporter.getFileContent().size() > 0);
	} 
	
	@Test
	public void testExportBySpecies() throws RnasExporterException {
		when(rnaRepository.findBySequenceChromosomeSpecies(sp)).thenReturn(rnas);
		
		rnasExporter.setRnasList(sp);
		rnasExporter.setFileContent(AgiExportType.GFF3PLUS, false);
		assertTrue(rnasExporter.getFileContent().size() > 0);
	}
	
	@Test
	public void testExportBySpeciesDefinition() throws RnasExporterException, CannotParseSpeciesDefinitionException {
		String speciesDefinition = "dummy";
		when(speciesService.get(speciesDefinition)).thenReturn(sp);
		when(rnaRepository.findBySequenceChromosomeSpecies(sp)).thenReturn(rnas);
		
		rnasExporter.setRnasList(speciesDefinition);
		rnasExporter.setFileContent(AgiExportType.GFF3PLUS, false);
		assertTrue(rnasExporter.getFileContent().size() > 0);
	}
	
}
