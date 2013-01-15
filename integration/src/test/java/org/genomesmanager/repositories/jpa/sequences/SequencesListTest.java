package org.genomesmanager.repositories.jpa.sequences;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.ChromosomeRepoException;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.repositories.sequences.SequencesList;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SequencesListTest extends AbstractIntegrationTest {
	@Autowired
	private SpeciesRepo speciesRepo;
	@Autowired
	private ChromosomeRepo chromosomeRepo;
	@Autowired
	private SequenceRepo sequenceRepo;
	@Autowired
	private SequencesList sequencesList;
	
	@Test
	public void test() throws SpeciesRepoException, ChromosomeRepoException, SequenceRepoException {
		int nOfNewSequences = 7;
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.insert(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chromosomeRepo.insert(chr);
		for ( Sequence seq:SequencesOM.Generate(nOfNewSequences, chr) ) {
			sequenceRepo.insert(seq);
		}
		assertEquals( nOfNewSequences, sequencesList.getAllByChromosome(chr.getId()).size() );
		assertEquals( nOfNewSequences, sequencesList.getAllNamesByChromosome(chr.getId()).size() );
	}
	
}
