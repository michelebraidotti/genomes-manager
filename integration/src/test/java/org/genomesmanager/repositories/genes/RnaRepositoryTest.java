package org.genomesmanager.repositories.genes;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.RnasOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.genes.RnaRepository;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RnaRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private RnaRepository rnaRepo;
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
		Rna rna = RnasOM.Generate(1, seq).get(0);
		rna = rnaRepo.save(rna);
		Rna postRna = rnaRepo.findOne(rna.getId());
		assertEquals(rna, postRna);
	}
	
	@Test
	public void findByTest() throws Exception {
		int nOfRnas = 3;
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq = sequenceRepo.save(seq);
		for (Rna rna:RnasOM.Generate(nOfRnas, seq)) {
			rna = rnaRepo.save(rna);
		}
		assertEquals(nOfRnas, rnaRepo.findBySequenceChromosomeSpecies(sp).size());
		assertEquals(nOfRnas, rnaRepo.findBySequenceChromosome(chr).size());
	}
}
