package org.genomesmanager.repositories.genes;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.testobjectgenerators.ChromosomesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.GenesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SequencesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class GeneRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private GeneRepository geneRepo;
	@Autowired
	private SpeciesRepository speciesRepo;
	@Autowired
	private ChromosomeRepository chromosomeRepo;
	@Autowired
	private SequenceRepository sequenceRepo;
	

	@Test
	public void basicTest() throws Exception {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Sequence seq = SequencesTestObjectGenerator.Generate(1, chr).get(0);
		seq = sequenceRepo.save(seq);
		Gene gene = GenesTestObjectGenerator.Generate(1, seq).get(0);
		gene = geneRepo.save(gene);
		Gene postGene = geneRepo.findOne(gene.getId());
		assertEquals(gene, postGene);
	}
	
	@Test
	public void findByTest() throws Exception {
		int nOfGenes = 5;
		int nOfSequences = 2;
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		List<Sequence> sequences = new ArrayList<>();
		for (Sequence seq: SequencesTestObjectGenerator.Generate(nOfSequences, chr)) {
			sequences.add(sequenceRepo.save(seq));
		}
		for (Gene gene : GenesTestObjectGenerator.Generate(nOfGenes*nOfSequences, sequences)) {
			gene = geneRepo.save(gene);
		}
		assertEquals(nOfGenes*nOfSequences, geneRepo.findBySequenceChromosome(chr).size());
		assertEquals(nOfGenes*nOfSequences, geneRepo.findBySequenceChromosomeSpecies(sp).size());
	}
}
