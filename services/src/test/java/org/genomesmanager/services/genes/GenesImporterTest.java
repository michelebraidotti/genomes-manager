package org.genomesmanager.services.genes;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Gene;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.genes.GeneRepo;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.services.impl.genes.GenesImporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GenesImporterTest {
	
	@Mock
	private SequenceRepo sequenceRepo;
	@Mock
	private GeneRepo geneRepo;
	@InjectMocks
	private GenesImporter genesImporter = new GenesImporterImpl();
	private Random generator;
	private Sequence seq;
	private List<String> gff3Content = new ArrayList<String>();
	
	@Before 
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		generator = new Random();
		Species sp = SpeciesOM.Generate(1).get(0);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr.setId(generator.nextInt());
		seq = SequencesOM.Generate(1, chr).get(0);
		seq.setId(generator.nextInt());
		
		gff3Content.add("##gff-version 3");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	gene	99	178	.	+	.	ID=Gene0");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	34	60	.	+	.	ID=name0;Parent=Gene0;Description=description0");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	exon	8	13	.	+	.	ID=name0;Parent=name0");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	exon	3	4	.	-	.	ID=name1;Parent=name0");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	60	77	.	-	.	ID=name1;Parent=Gene0;Description=description1");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	exon	8	7	.	+	.	ID=name0;Parent=name1");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	exon	3	14	.	-	.	ID=name1;Parent=name1");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	58	57	.	+	.	ID=name2;Parent=Gene0;Description=description2");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	gene	141	175	.	-	.	ID=Gene1");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	10	16	.	+	.	ID=name0;Parent=Gene1;Description=description0");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	19	25	.	-	.	ID=name1;Parent=Gene1;Description=description1");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	27	30	.	+	.	ID=name2;Parent=Gene1;Description=description2");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	gene	95	201	.	+	.	ID=Gene2");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	15	102	.	+	.	ID=name0;Parent=Gene2;Description=description0");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	exon	50	71	.	+	.	ID=name0;Parent=name0");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	exon	57	86	.	-	.	ID=name1;Parent=name0");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	77	87	.	-	.	ID=name1;Parent=Gene2;Description=description1");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	exon	1	0	.	+	.	ID=name0;Parent=name1");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	exon	10	9	.	-	.	ID=name1;Parent=name1");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	14	63	.	+	.	ID=name2;Parent=Gene2;Description=description2");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	exon	7	27	.	+	.	ID=name0;Parent=name2");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	exon	28	46	.	-	.	ID=name1;Parent=name2");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	gene	165	176	.	-	.	ID=Gene3");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	9	8	.	+	.	ID=name0;Parent=Gene3;Description=description0");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	7	10	.	-	.	ID=name1;Parent=Gene3;Description=description1");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	3	9	.	+	.	ID=name2;Parent=Gene3;Description=description2");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	gene	162	177	.	+	.	ID=Gene4");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	2	2	.	+	.	ID=name0;Parent=Gene4;Description=description0");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	11	10	.	-	.	ID=name1;Parent=Gene4;Description=description1");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	mRNA	3	12	.	+	.	ID=name2;Parent=Gene4;Description=description2");
	}
	
	@Test
	public void testImport() throws SequenceRepoException {
		when(sequenceRepo.getLatest(anyString())).thenReturn(seq);
		//for (String s:ggf3Content) System.out.println(s);
		genesImporter.parseMipsGenePredictionGff3(gff3Content);
		genesImporter.save();
		assertTrue(genesImporter.getErrors().size() == 1);
		assertTrue(genesImporter.getWarnings().size() == 0);
		assertTrue(genesImporter.getGenes().size() > 0);
		verify(geneRepo, times(genesImporter.getGenes().size())).insert((Gene) anyObject());
	}
}
