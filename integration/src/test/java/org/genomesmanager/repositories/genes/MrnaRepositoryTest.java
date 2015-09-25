package org.genomesmanager.repositories.genes;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Mrna;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.GenesOM;
import org.genomesmanager.domain.entities.objectmothers.MrnasOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
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
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		sequenceRepo.save(seq);
		Gene gene = GenesOM.Generate(1, seq).get(0);
		geneRepo.save(gene);
		Mrna mrna = MrnasOM.Generate(1, gene).get(0);
		mrna = mrnaRepo.save(mrna);
		Mrna postMrna = mrnaRepo.findOne(mrna.getId());
		assertEquals(mrna, postMrna);
	}
}
