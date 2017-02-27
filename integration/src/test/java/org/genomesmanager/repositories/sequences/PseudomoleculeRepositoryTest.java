package org.genomesmanager.repositories.sequences;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Pseudomolecule;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.testobjectgenerators.ChromosomesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SequencesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PseudomoleculeRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private SpeciesRepository speciesRepo;
	@Autowired
	private ChromosomeRepository chromosomeRepo;
	@Autowired
	private PseudomoleculeRepository pseudomoleculeRepo;
	
	@Test
	public void basicTest() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Pseudomolecule pseudomolecule = SequencesTestObjectGenerator.GeneratePseudomolecule(1, chr).get(0);
		pseudomolecule = pseudomoleculeRepo.save(pseudomolecule);
		Pseudomolecule pseudomoleculePost= pseudomoleculeRepo.findOne(pseudomolecule.getId());
		assertEquals(pseudomolecule, pseudomoleculePost);
	}

//	List<Pseudomolecule> findByName(String name);
	@Test
	public void findByNameTest() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Pseudomolecule pseudomolecule = SequencesTestObjectGenerator.GeneratePseudomolecule(1, chr).get(0);
		pseudomolecule = pseudomoleculeRepo.save(pseudomolecule);
		Pseudomolecule pseudomoleculePost= pseudomoleculeRepo.findByName(pseudomolecule.getName()).get(0);
		assertEquals(pseudomolecule, pseudomoleculePost);
	}
	
//	List<Pseudomolecule> findByNameAndVersion(String name, String version);
	@Test
	public void findByNameAndVersionTest() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Pseudomolecule pseudomolecule = SequencesTestObjectGenerator.GeneratePseudomolecule(1, chr).get(0);
		pseudomolecule = pseudomoleculeRepo.save(pseudomolecule);
		Pseudomolecule pseudomoleculePost= pseudomoleculeRepo.findByNameAndVersion(pseudomolecule.getName(), pseudomolecule.getVersion()).get(0);
		assertEquals(pseudomolecule, pseudomoleculePost);
	}
	
//	List<Pseudomolecule> findByChromosome(Chromosome chromosome);
	@Test
	public void findByChromosomeTest() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Pseudomolecule pseudomolecule = SequencesTestObjectGenerator.GeneratePseudomolecule(1, chr).get(0);
		pseudomolecule = pseudomoleculeRepo.save(pseudomolecule);
		Pseudomolecule pseudomoleculePost= pseudomoleculeRepo.findByChromosome(chr).get(0);
		assertEquals(pseudomolecule, pseudomoleculePost);
	}
	
//	List<Pseudomolecule> findByChromosomeSpecies(Species species);
	@Test
	public void findByChromosomeSpeciesTest() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Pseudomolecule pseudomolecule = SequencesTestObjectGenerator.GeneratePseudomolecule(1, chr).get(0);
		pseudomolecule = pseudomoleculeRepo.save(pseudomolecule);
		Pseudomolecule pseudomoleculePost= pseudomoleculeRepo.findByChromosomeSpecies(sp).get(0);
		assertEquals(pseudomolecule, pseudomoleculePost);
	}

}
