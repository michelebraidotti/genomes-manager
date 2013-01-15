package org.genomesmanager.repositories.jpa.snps;

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
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.ChromosomeRepoException;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.repositories.snps.SnpRepo;
import org.genomesmanager.repositories.species.IndividualRepo;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.genomesmanager.repositories.species.SpeciesRepoException;
import org.genomesmanager.repositories.species.VarietyRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SnpsListTest extends AbstractIntegrationTest {
	@Autowired
	private SpeciesRepo speciesRepo;
	@Autowired
	private ChromosomeRepo chromosomeRepo;
	@Autowired
	private SequenceRepo sequenceRepo;
	@Autowired
	private VarietyRepo varietyRepo;
	@Autowired
	private IndividualRepo individualRepo;
	@Autowired
	private SnpRepo snpRepo;
	
	@Test
	public void test() throws SpeciesRepoException, ChromosomeRepoException, SequenceRepoException {
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.insert(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chromosomeRepo.insert(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		sequenceRepo.insert(seq);
		Variety variety = VarietiesOM.Generate(1, sp).get(0);
		varietyRepo.insert(variety);
		Individual individual = IndividualsOM.Generate(1, variety).get(0);
		individualRepo.insert(individual);
		Snp snp = SnpsOM.Generate(1, individual, seq).get(0);
		snpRepo.insert(snp);
		Snp postSnp = snpRepo.get(snp.getId());
		assertEquals(snp, postSnp);
	}
	
}
