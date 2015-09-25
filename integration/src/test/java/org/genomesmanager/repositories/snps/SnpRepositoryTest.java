package org.genomesmanager.repositories.snps;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Individual;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Snp;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.Variety;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.IndividualsOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SnpsOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.domain.entities.objectmothers.VarietiesOM;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.IndividualRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.genomesmanager.repositories.species.VarietyRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SnpRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private SpeciesRepository speciesRepo;
	@Autowired
	private ChromosomeRepository chromosomeRepo;
	@Autowired
	private SequenceRepository sequenceRepo;
	@Autowired
	private VarietyRepository varietyRepo;
	@Autowired
	private IndividualRepository individualRepo;
	@Autowired
	private SnpRepository snpRepo;
	
	@Test
	public void test() {
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		sequenceRepo.save(seq);
		Variety variety = VarietiesOM.Generate(1, sp).get(0);
		varietyRepo.save(variety);
		Individual individual = IndividualsOM.Generate(1, variety).get(0);
		individualRepo.save(individual);
		Snp snp = SnpsOM.Generate(1, individual, seq).get(0);
		snp = snpRepo.save(snp);
		Snp postSnp = snpRepo.findOne(snp.getId());
		assertEquals(snp, postSnp);
	}
	
	@Test
	public void testGetAll() {
		int nOfSnps = 7;
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq = sequenceRepo.save(seq);
		Variety variety = VarietiesOM.Generate(1, sp).get(0);
		variety = varietyRepo.save(variety);
		Individual individual = IndividualsOM.Generate(1, variety).get(0);
		individual = individualRepo.save(individual);
		for (Snp snp:SnpsOM.Generate(nOfSnps, individual, seq) ) {
			snp = snpRepo.save(snp);
		}
		assertEquals(nOfSnps, snpRepo.getAllByChromosome(chr).size());
		assertEquals(nOfSnps, snpRepo.getAllBySpecies(sp).size());
	}
	
}
