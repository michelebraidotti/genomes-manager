package org.genomesmanager.repositories.jpa.genes;

import static org.junit.Assert.assertEquals;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Exon;
import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Mrna;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.ExonsOM;
import org.genomesmanager.domain.entities.objectmothers.GenesOM;
import org.genomesmanager.domain.entities.objectmothers.MrnasOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.genes.ExonRepo;
import org.genomesmanager.repositories.genes.GeneRepo;
import org.genomesmanager.repositories.genes.MrnaRepo;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepo;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.species.SpeciesRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ExonRepoTest extends AbstractIntegrationTest {
	@Autowired
	private ExonRepo exonRepo;
	@Autowired
	private MrnaRepo mrnaRepo;
	@Autowired
	private GeneRepo geneRepo;
	@Autowired
	private SpeciesRepo speciesRepo;
	@Autowired
	private ChromosomeRepo chromosomeRepo;
	@Autowired
	private SequenceRepo sequenceRepo;
	

	@Test
	public void test() throws Exception {
		Species sp = SpeciesOM.Generate(1).get(0);
		speciesRepo.insert(sp);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chromosomeRepo.insert(chr);
		Sequence seq = SequencesOM.Generate(1, chr).get(0);
		sequenceRepo.insert(seq);
		Gene gene = GenesOM.Generate(1, seq).get(0);
		geneRepo.insert(gene);
		Mrna mrna = MrnasOM.Generate(1, gene).get(0);
		mrnaRepo.insert(mrna);
		Exon exon = ExonsOM.Generate(1, mrna).get(0);
		exonRepo.insert(exon);
		Exon postExon = exonRepo.get(exon.getId());
		assertEquals(exon, postExon);
	}

}
