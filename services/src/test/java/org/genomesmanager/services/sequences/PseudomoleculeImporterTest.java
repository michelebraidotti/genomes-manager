package org.genomesmanager.services.sequences;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.formats.SimpleFasta;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.testobjectgenerators.ChromosomesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SequencesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PseudomoleculeImporterTest {
	@Mock
	private ChromosomeRepository chrRepo;
	@Mock
	private SequenceRepository seqRepo;
	@InjectMocks
	private PseudomoleculeImporter pseudomoleculeImporter = new PseudomoleculeImporter();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test() throws PseudomoleculeImporterException {

		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		when(chrRepo.findOne(chr.getId())).thenReturn(chr);

		pseudomoleculeImporter
				.importPseudomolecule(chr.getId(),
						SequencesTestObjectGenerator.GenererateSequence(1000000),
						"NewPseudommol", "1.0");
		verify(seqRepo).save((Pseudomolecule) anyObject());
	}
	
//	public abstract void importPseudomolecule(int chrId,
//			List<SimpleFasta> fastas, String version)
//			throws PseudomoleculeImporterException;
	
	@Test
	public void importPseudomoleculeFasta() throws PseudomoleculeImporterException {

		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		when(chrRepo.findOne(chr.getId())).thenReturn(chr);

		List<SimpleFasta> fastas = new ArrayList<SimpleFasta>();
		for (int i = 1; i < 3 ; i++) 
			fastas.add( new SimpleFasta("fileName", "NewPseudomol" + i, SequencesTestObjectGenerator.GenererateSequence(10*i).toString()) );
		
		
		pseudomoleculeImporter
				.importPseudomolecule(chr.getId(), fastas, "1.0");
		verify(seqRepo, times(fastas.size())).save((Pseudomolecule) anyObject());
	}
}