package org.genomesmanager.repositories.genes;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Exon;
import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Mrna;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.testobjectgenerators.ChromosomesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.ExonsTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.GenesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.MrnasTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SequencesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ExonRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private ExonRepository exonRepo;
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
		mrnaRepo.save(mrna);
		Exon exon = ExonsTestObjectGenerator.Generate(1, mrna).get(0);
		exon = exonRepo.save(exon);
		Exon postExon = exonRepo.findOne(exon.getId());
		assertEquals(exon, postExon);
	}

}
