package org.genomesmanager.services.sequences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.SequenceSliceException;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.sequences.PseudomoleculeRepository;
import org.genomesmanager.repositories.sequences.ScaffoldRepository;
import org.genomesmanager.services.impl.sequences.PseudomoleculeExporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PseudomoleculeExporterTest {
	@Mock
	private PseudomoleculeRepository pseudomoleculeRepo;
	@Mock
	private ScaffoldRepository scaffoldRepository;
	@InjectMocks
	private PseudomoleculeExporter pseudomoleculeExporter = new PseudomoleculeExporterImpl();
	private Random generator;
	
	
	@Before 
	public void initMocks() {
		MockitoAnnotations.initMocks(this); 
		generator = new Random();
		
	}
	
	@Test
	public void exportPseudomolTest() throws SequenceSliceException {
		Species sp = SpeciesOM.Generate(1).get(0);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		Pseudomolecule pseudomol = SequencesOM.GeneratePseudomolecule(1,chr).get(0);
		pseudomol.setId(generator.nextInt());
		pseudomol.setScaffoldDerived(false);
		pseudomol.setUnplaced(false);
		when(pseudomoleculeRepo.findOne(pseudomol.getId())).thenReturn(pseudomol);
		
		Pseudomolecule p = pseudomoleculeExporter.get(pseudomol.getId(), false);
		assertEquals(pseudomol,p);
	}
	
	@Test
	public void exportPseudomolTestScaffoldDerived() throws SequenceSliceException {
		Species sp = SpeciesOM.Generate(1).get(0);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		Pseudomolecule pseudomol = SequencesOM.GeneratePseudomolecule(1,chr).get(0);
		pseudomol.setId(generator.nextInt());
		pseudomol.setScaffoldDerived(true);
		pseudomol.setUnplaced(true);
		int i = 1;
		List<Scaffold> scaffoldsPlaced = new ArrayList<Scaffold>();
		List<Scaffold> scaffoldsUnplaced = new ArrayList<Scaffold>();
		int lastId = generator.nextInt();
		for (Scaffold scaffold:SequencesOM.GenerateScaffold(5, chr)) {
			scaffold.setId(lastId++);
			scaffold.setOrder(i++);
			scaffold.setIsUnplaced(false);
			scaffoldsPlaced.add(scaffold);
		}
		for (Scaffold scaffold:SequencesOM.GenerateScaffold(4, chr)) {
			scaffold.setId(lastId++);
			scaffold.setOrder(0);
			scaffold.setIsUnplaced(true);
			scaffoldsUnplaced.add(scaffold);
		}
		pseudomol.setScaffolds(scaffoldsPlaced);
		pseudomol.getScaffolds().addAll(scaffoldsUnplaced);
		boolean masked = false;
		when(pseudomoleculeRepo.findOne(pseudomol.getId())).thenReturn(pseudomol);
		when(scaffoldRepository.findAllUnplacedByChromosome(chr)).thenReturn(pseudomol.getScaffolds());
		Pseudomolecule p = pseudomoleculeExporter.get(pseudomol.getId(), masked);
		
		assertTrue(p.getSequenceText().length() > 0);
		for (Scaffold scaffold:p.getScaffolds()) {
			for (Scaffold scaffoldOrig:scaffoldsUnplaced) {
				if ( scaffoldOrig.getId() == scaffold.getId() ) {
					assertEquals(scaffoldOrig, scaffold);
				}
			}
			for (Scaffold scaffoldOrig:scaffoldsPlaced) {
				if ( scaffoldOrig.getId() == scaffold.getId() ) {
					assertEquals(scaffoldOrig, scaffold);
				}
			}

		}
	}
}
