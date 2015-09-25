package org.genomesmanager.repositories.genes;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.GenesOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq = sequenceRepo.save(seq);
		Gene gene = GenesOM.Generate(1, seq).get(0);
		gene = geneRepo.save(gene);
		Gene postGene = geneRepo.findOne(gene.getId());
		assertEquals(gene, postGene);
	}
	
	@Test
	public void findByTest() throws Exception {
		int nOfGenes = 5;
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq = sequenceRepo.save(seq);
		for (Gene gene:GenesOM.Generate(nOfGenes, seq)) {
			gene = geneRepo.save(gene);
		}
		assertEquals(nOfGenes, geneRepo.findBySequenceChromosome(chr).size());
		assertEquals(nOfGenes, geneRepo.findBySequenceChromosomeSpecies(sp).size());
	}
}
