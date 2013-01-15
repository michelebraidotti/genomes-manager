package org.genomesmanager.services.genes;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Random;

import org.genomesmanager.common.formats.AgiExportType;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.RnasOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.genes.RnasList;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.impl.genes.RnasExporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RnasExporterTest {
	@Mock
	private RnasList rnasList;
	@Mock
	private SpeciesRepo speciesRepo;
	@Mock
	private SequenceRepo seqRepo;
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
		speciesRepo.insert(sp);
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
	public void testExportByChr() throws SpeciesRepoException, RnasExporterException {
		when(speciesRepo.get(sp.toString())).thenReturn(sp);
		when(rnasList.getAllByChromosome(chr.getId())).thenReturn(rnas);
		
		rnasExporter.setRnasList(chr);
		rnasExporter.setFileContent(AgiExportType.GFF3PLUS, false);
		for (String s:rnasExporter.getFileContent()) System.out.println(s);
		assertTrue(rnasExporter.getFileContent().size() > 0);
	}
	
	@Test
	public void testExportByChrNormalGff3() throws SpeciesRepoException, RnasExporterException {
		when(speciesRepo.get(sp.toString())).thenReturn(sp);
		when(rnasList.getAllByChromosome(chr.getId())).thenReturn(rnas);
		
		rnasExporter.setRnasList(chr);
		rnasExporter.setFileContent(AgiExportType.GFF3, false);
		assertTrue(rnasExporter.getFileContent().size() > 0);
	} 
	
	@Test
	public void testExportBySpecies() throws SpeciesRepoException, RnasExporterException {
		when(speciesRepo.get(sp.toString())).thenReturn(sp);
		when(rnasList.getAllBySpecies(sp.getId())).thenReturn(rnas);
		
		rnasExporter.setRnasList(sp);
		rnasExporter.setFileContent(AgiExportType.GFF3PLUS, false);
		assertTrue(rnasExporter.getFileContent().size() > 0);
	}
	
	@Test
	public void testExportBySpeciesDefinition() throws SpeciesRepoException, RnasExporterException {
		String speciesDefinition = "dummy";
		when(speciesRepo.get(sp.toString())).thenReturn(sp);
		when(speciesRepo.get(speciesDefinition)).thenReturn(sp);
		when(rnasList.getAllBySpecies(sp.getId())).thenReturn(rnas);
		
		rnasExporter.setRnasList(speciesDefinition);
		rnasExporter.setFileContent(AgiExportType.GFF3PLUS, false);
		assertTrue(rnasExporter.getFileContent().size() > 0);
	}
	
}
