package org.genomesmanager.repositories.sequences;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.SequenceName;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class SequenceRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	private SpeciesRepository speciesRepo;
	@Autowired
	private ChromosomeRepository chromosomeRepo;
	@Autowired
	private SequenceRepository sequenceRepo;
	
	@Test
	public void basicTest() {
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq = sequenceRepo.save(seq);
		Sequence seqPost = sequenceRepo.findOne(seq.getId());
		assertEquals(seq, seqPost);
	}
	
//	public List<Sequence> findByName(String name);
	@Test
	public void findByNameTest() {
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq = sequenceRepo.save(seq);
		Sequence seqPost = sequenceRepo.findByName(seq.getName()).get(0);
		assertEquals(seq, seqPost);
	}

//	public List<Sequence> findByNameAndVersion(String name, String version);
	@Test
	public void findByNameAndVersionTest() {
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq = sequenceRepo.save(seq);
		Sequence seqPost = sequenceRepo.findByNameAndVersion(seq.getName(), seq.getVersion()).get(0);
		assertEquals(seq, seqPost);
	}
	
//	public List<Sequence> findByChromosome(Chromosome chromosome);
	@Test
	public void findByChromosomeTest() {
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq = sequenceRepo.save(seq);
		Sequence seqPost = sequenceRepo.findByChromosome(chr).get(0);
		assertEquals(seq, seqPost);
	}
	
//	public abstract List<Sequence> findByChromosomeSpecies(Species species);
	@Test
	public void findByChromosomeSpeciesTest() {
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq = sequenceRepo.save(seq);
		Sequence seqPost = sequenceRepo.findByChromosomeSpecies(sp).get(0);
		assertEquals(seq, seqPost);
	}
	
//	public abstract int getLength(int seqId);
	@Test
	public void getLengthTest() {
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		seq = sequenceRepo.save(seq);
		int lengthPost = sequenceRepo.getLength(seq.getId());
		assertEquals(seq.getLength(), lengthPost);
	}
	
//	public abstract Sequence findLatest(String seqName);
	@Test
	public void findLatestTest() throws InterruptedException {
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		List<Sequence> seqs = new ArrayList<Sequence>();
		String seqName = "SeqName";
		for (Sequence seq:SequencesOM.Generate(3, chr)) {
			seq.setName(seqName);
			seqs.add(sequenceRepo.save(seq));
			Thread.sleep(1000);
		}
		Sequence seqPost = sequenceRepo.findLatest(seqName);
		assertEquals(seqs.get(seqs.size() - 1), seqPost);
	}
	
//	public abstract List<SequenceName> findNamesByChromosome(Chromosome chromosome);
	@Test
	public void findNamesByChromosomeTest() throws InterruptedException {
		Species sp = SpeciesOM.Generate(1).get(0);
		sp = speciesRepo.save(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr = chromosomeRepo.save(chr);
		List<Sequence> seqs = new ArrayList<Sequence>();
		for (Sequence seq:SequencesOM.Generate(3, chr)) {
			seqs.add(sequenceRepo.save(seq));
		}
		List<SequenceName> namesPost = sequenceRepo.findNamesByChromosome(chr);
		assertEquals(seqs.size(), namesPost.size());
	}
}
