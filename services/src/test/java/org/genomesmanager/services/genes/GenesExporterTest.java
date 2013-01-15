package org.genomesmanager.services.genes;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.common.formats.AgiExportType;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Exon;
import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Mrna;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.ExonsOM;
import org.genomesmanager.domain.entities.objectmothers.GenesOM;
import org.genomesmanager.domain.entities.objectmothers.MrnasOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.genes.GenesList;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.services.impl.genes.GenesExporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GenesExporterTest {
	@Mock
	private GenesList genesList;
	@Mock
	private SpeciesRepo speciesRepo;
	@Mock
	private SequenceRepo seqRepo;
	@InjectMocks
	private GenesExporter genesExporter = new GenesExporterImpl();
	private Species sp;
	private Chromosome chr;
	private Random generator;
	private ArrayList<Gene> genes;
	private boolean greedyness;

	@Before 
	public void initMocks() {
		MockitoAnnotations.initMocks(this); 
		
		generator = new Random();
		int nOfGenes = 5;
		int nOfMRnas = 3;
		int nOfExons = 2;
		sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.insert(sp);
		chr = ChromosomesOM.Generate(1, sp).get(0);
		chr.setId(generator.nextInt());
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq.setId(generator.nextInt());
		genes = new ArrayList<Gene>();
		int geneId = generator.nextInt();
		int mrnaId = generator.nextInt();
		int exonsId = generator.nextInt();
		for (Gene gene:GenesOM.Generate(nOfGenes, seq)) {
			gene.setId(geneId++);
			List<Mrna> mrnas = MrnasOM.Generate(nOfMRnas, gene);
			for (Mrna mrna:mrnas) {
				mrna.setId(mrnaId++);
				if ( mrna.length() > 10 ) {
					List<Exon> exons = ExonsOM.Generate(nOfExons, mrna);
					for (Exon exon:exons) {
						exon.setId(exonsId++);
						mrna.getExons().add(exon);
					}
				}
				gene.getMrnas().add(mrna);
			}
			genes.add(gene);
		}
		greedyness = false;
	}
	
	@Test
	public void testExportByChr() throws GenesExporterException, SpeciesRepoException {
		when(speciesRepo.get(sp.toString())).thenReturn(sp);
		when(genesList.getAllByChromosome(chr.getId(), greedyness)).thenReturn(genes);
		
		genesExporter.setGreedyLoad(greedyness);
		genesExporter.setGenesList(chr);
		genesExporter.setFileContent(AgiExportType.GFF3PLUS, false);
		assertTrue(genesExporter.getFileContent().size() > 0);
	}
	
	@Test
	public void testExportByChrNormalGff3() throws GenesExporterException, SpeciesRepoException {
		when(speciesRepo.get(sp.toString())).thenReturn(sp);
		when(genesList.getAllByChromosome(chr.getId(), greedyness)).thenReturn(genes);
		
		genesExporter.setGreedyLoad(greedyness);
		genesExporter.setGenesList(chr);
		genesExporter.setFileContent(AgiExportType.GFF3, false);
		assertTrue(genesExporter.getFileContent().size() > 0);
	} 
	
	@Test
	public void testExportBySpecies() throws GenesExporterException, SpeciesRepoException {
		when(speciesRepo.get(sp.toString())).thenReturn(sp);
		when(genesList.getAllBySpecies(sp.getId(), greedyness)).thenReturn(genes);
		
		genesExporter.setGreedyLoad(greedyness);
		genesExporter.setGenesList(sp);
		genesExporter.setFileContent(AgiExportType.GFF3PLUS, false);
		assertTrue(genesExporter.getFileContent().size() > 0);
	}
	
	@Test
	public void testExportBySpeciesDefinition() throws GenesExporterException, SpeciesRepoException {
		String speciesDefinition = "dummy";
		when(speciesRepo.get(sp.toString())).thenReturn(sp);
		when(speciesRepo.get(speciesDefinition)).thenReturn(sp);
		when(genesList.getAllBySpecies(sp.getId(), greedyness)).thenReturn(genes);
		
		genesExporter.setGreedyLoad(greedyness);
		genesExporter.setGenesList(speciesDefinition);
		genesExporter.setFileContent(AgiExportType.GFF3PLUS, false);
		assertTrue(genesExporter.getFileContent().size() > 0);
	}
	
}
