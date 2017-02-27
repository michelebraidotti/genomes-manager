package org.genomesmanager.repositories.sequences;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.testobjectgenerators.ChromosomesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SequencesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ScaffoldRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private SpeciesRepository speciesRepo;
	@Autowired
	private ChromosomeRepository chromosomeRepo;
	@Autowired
	private ScaffoldRepository scaffoldRepo;
	
	@Test
	public void basicTest() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Scaffold scaffold = SequencesTestObjectGenerator.GenerateScaffold(1, chr).get(0);
		scaffold = scaffoldRepo.save(scaffold);
		Scaffold scaffoldPost = scaffoldRepo.findOne(scaffold.getId());
		assertEquals(scaffold, scaffoldPost);
	}
	
//	List<Scaffold> findByName(String name);
	@Test
	public void findByNameTest() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Scaffold scaffold = SequencesTestObjectGenerator.GenerateScaffold(1, chr).get(0);
		scaffold = scaffoldRepo.save(scaffold);
		Scaffold scaffoldPost = scaffoldRepo.findByName(scaffold.getName()).get(0);
		assertEquals(scaffold, scaffoldPost);
	}
	
//	List<Scaffold> findByNameAndVersion(String name, String version);
	@Test
	public void findByNameAndVersionTest() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Scaffold scaffold = SequencesTestObjectGenerator.GenerateScaffold(1, chr).get(0);
		scaffold = scaffoldRepo.save(scaffold);
		Scaffold scaffoldPost = scaffoldRepo.findByNameAndVersion(scaffold.getName(), scaffold.getVersion()).get(0);
		assertEquals(scaffold, scaffoldPost);
	}
	
//	List<Scaffold> findByChromosome(Chromosome chromosome);
	@Test
	public void findByChromosomeTest() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Scaffold scaffold = SequencesTestObjectGenerator.GenerateScaffold(1, chr).get(0);
		scaffold = scaffoldRepo.save(scaffold);
		Scaffold scaffoldPost = scaffoldRepo.findByChromosome(chr).get(0);
		assertEquals(scaffold, scaffoldPost);
	}
	
//	List<Scaffold> findByChromosomeSpecies(Species species);
	@Test
	public void findByChromosomeSpeciesTest() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Scaffold scaffold = SequencesTestObjectGenerator.GenerateScaffold(1, chr).get(0);
		scaffold = scaffoldRepo.save(scaffold);
		Scaffold scaffoldPost = scaffoldRepo.findByChromosomeSpecies(sp).get(0);
		assertEquals(scaffold, scaffoldPost);
	}
	
//	public abstract List<Scaffold> findAllPlacedByChromosome(Chromosome chromosome);
	@Test
	public void findAllPlacedByChromosomeSpeciesTest() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		List<Scaffold> scaffolds = new ArrayList<Scaffold>();
		int i = 0;
		int nOfPlaced = 0;
		for ( Scaffold scaffold: SequencesTestObjectGenerator.GenerateScaffold(5, chr) ) {
			if ( i%2 == 0 ) {
				scaffold.setIsUnplaced(false);
				nOfPlaced++;
			}
			else {
				scaffold.setIsUnplaced(true);
			}
			scaffolds.add(scaffoldRepo.save(scaffold));
			i++;
		}
		List<Scaffold> scaffoldsPost = scaffoldRepo.findAllPlacedByChromosome(chr);
		assertEquals(nOfPlaced, scaffoldsPost.size());
	}
	
//	public abstract List<Scaffold> findAllUnplacedByChromosome(Chromosome chromosome);
	@Test
	public void findAllUnplacedByChromosomeTest() {
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		speciesRepo.save(sp);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		List<Scaffold> scaffolds = new ArrayList<Scaffold>();
		int i = 0;
		int nOfPlaced = 0;
		for ( Scaffold scaffold: SequencesTestObjectGenerator.GenerateScaffold(5, chr) ) {
			if ( i%2 == 0 ) {
				scaffold.setIsUnplaced(true);
				nOfPlaced++;
			}
			scaffolds.add(scaffoldRepo.save(scaffold));
			i++;
		}
		List<Scaffold> scaffoldsPost = scaffoldRepo.findAllUnplacedByChromosome(chr);
		assertEquals(nOfPlaced, scaffoldsPost.size());
	}
	
}