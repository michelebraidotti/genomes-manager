package org.genomesmanager.services.sequences;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.ChromosomeRepoException;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.services.impl.sequences.PseudomoleculeImporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PseudomoleculeImporterTest {
	@Mock
	private ChromosomeRepo chrRepo;
	@Mock
	private SequenceRepo seqRepo;
	@InjectMocks
	private PseudomoleculeImporter pseudomoleculeImporter = new PseudomoleculeImporterImpl();
	
	@Before 
	public void initMocks() {
		MockitoAnnotations.initMocks(this); 
	}
	
	@Test
	public void test() throws PseudomoleculeImporterException, ChromosomeRepoException {
		
		Species sp = SpeciesOM.Generate(1).get(0);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		when(chrRepo.get(chr.getId())).thenReturn(chr);
		
		pseudomoleculeImporter.importPseudomolecule(chr.getId(), SequencesOM.GenererateSequence(1000000), 
				"NewPseudommol", "1.0");
		verify(seqRepo).insert((Pseudomolecule) anyObject());
	}
}
