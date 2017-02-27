package org.genomesmanager.repositories.genes;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Mrna;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.testobjectgenerators.*;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MrnaRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private MrnaRepository mrnaRepo;
	@Autowired
	private GeneRepository geneRepo;
	@Autowired
	private SpeciesRepository speciesRepo;
	@Autowired
	private ChromosomeRepository chromosomeRepo;
	@Autowired
	private SequenceRepository sequenceRepo;
	

	@Test
	public void test() throws Exception {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chromosomeRepo.save(chr);
		Sequence seq = SequencesTestObjectGenerator.Generate(1, chr).get(0);
		sequenceRepo.save(seq);
		Gene gene = GenesTestObjectGenerator.Generate(1, seq).get(0);
		geneRepo.save(gene);
		Mrna mrna = MrnasTestObjectGenerator.Generate(1, gene).get(0);
		mrna = mrnaRepo.save(mrna);
		Mrna postMrna = mrnaRepo.findOne(mrna.getId());
		assertEquals(mrna, postMrna);
	}
}
