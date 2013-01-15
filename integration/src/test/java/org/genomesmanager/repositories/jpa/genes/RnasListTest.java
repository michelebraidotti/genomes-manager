package org.genomesmanager.repositories.jpa.genes;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.RnasOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.genes.RnaRepo;
import org.genomesmanager.repositories.genes.RnasList;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RnasListTest extends AbstractIntegrationTest {
	@Autowired
	private RnasList rnasList;
	@Autowired
	private RnaRepo rnaRepo;
	@Autowired
	private SpeciesRepo speciesRepo;
	@Autowired
	private ChromosomeRepo chromosomeRepo;
	@Autowired
	private SequenceRepo sequenceRepo;
	

	@Test
	public void test() throws Exception {
		int nOfRnas = 3;
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.insert(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chromosomeRepo.insert(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		sequenceRepo.insert(seq);
		for (Rna rna:RnasOM.Generate(nOfRnas, seq)) {
			rnaRepo.insert(rna);
		}
		assertEquals(nOfRnas, rnasList.getAllBySpecies(sp.getId()).size());
		assertEquals(nOfRnas, rnasList.getAllByChromosome(chr.getId()).size());
	}
}
