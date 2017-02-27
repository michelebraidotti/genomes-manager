package org.genomesmanager.services.repeats;

import org.genomesmanager.domain.entities.*;
import org.genomesmanager.domain.entities.testobjectgenerators.*;
import org.genomesmanager.repositories.repeats.RepeatRepository;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.services.impl.repeats.RepeatsImporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class RepeatsImporterTest {
	@Mock
	private RepeatRepository repeatRepository;
	@Mock
	private RepeatsService repeatsService;
	@Mock
	private RepeatsClassificationRepository repeatsClassRepo;
	@Mock
	private SequenceRepository sequenceRepository;
	@InjectMocks
	private RepeatsImporter repeatsImporter = new RepeatsImporterImpl();
	private List<String> gff3Content = new ArrayList<String>();
	private Random generator;
	private Sequence seq;
	private LtrRepeat ltrRepeat;

	@Before
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);

		generator = new Random();
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr.setId(generator.nextInt());
		int seqLength = 150;
		seq = SequencesTestObjectGenerator.Generate(1, chr).get(0);
		seq.setId(generator.nextInt());
		seq.setSequenceText(SequencesTestObjectGenerator.GenererateSequence(seqLength).toString());
		seq.setLength(seqLength);
		
		String repClassDefinition = "I, I, LTR, test, test";		
		RepeatsClassification repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
		int repeatId = generator.nextInt();
		ltrRepeat = RepeatsTestObjectGenerator.GenerateLtrs(1, repClass, seq).get(0);
		ltrRepeat.setId(repeatId);
		
		gff3Content.add("##gff-version 3");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	LTR_retrotransposon	22	60	.	-	.	ID="+ repeatId +";rclass=I;subclass=I;rorder=LTR;superfamily=test;family=test;notes=genreatedRepeat0;is_complete=true;ltr_5_length=3;ltr_3_length=2;pbs_x=23;pbs_y=59;ppt_x=23;ppt_y=59;rt_presence=true;rt_sequence=CCAATTGG;int_presence=true;int_sequence=AACCTTGG;ltr_comparison_similarity=7;ltr_comparison_nuc_distance=6;ltr_comparison_mutation_ca=2;ltr_comparison_mutation_ct=4;ltr_comparison_mutation_at=1;ltr_comparison_mutation_cg=3;ltr_comparison_insertion_time=4;tsd_sequence=GGAATTCC;gc_content_5=0;gc_content_3=0;rt_stop_codons_count=8;int_stop_codons_count=1;presence_in_sativa=PresenceInSativa0");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	LTR_retrotransposon	60	82	.	-	.	ID=0;rclass=I;subclass=I;rorder=LTR;superfamily=test;family=test;notes=genreatedRepeat1;is_complete=true;ltr_5_length=4;ltr_3_length=3;pbs_x=61;pbs_y=61;ppt_x=61;ppt_y=61;rt_presence=true;rt_sequence=CCAATTGG;int_presence=true;int_sequence=AACCTTGG;ltr_comparison_similarity=8;ltr_comparison_nuc_distance=7;ltr_comparison_mutation_ca=3;ltr_comparison_mutation_ct=5;ltr_comparison_mutation_at=2;ltr_comparison_mutation_cg=4;ltr_comparison_insertion_time=5;tsd_sequence=GGAATTCC;gc_content_5=1;gc_content_3=1;rt_stop_codons_count=9;int_stop_codons_count=2;presence_in_sativa=PresenceInSativa1");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	LTR_retrotransposon	47	98	.	-	.	ID=0;rclass=I;subclass=I;rorder=LTR;superfamily=test;family=test;notes=genreatedRepeat2;is_complete=true;ltr_5_length=5;ltr_3_length=4;pbs_x=48;pbs_y=57;ppt_x=48;ppt_y=57;rt_presence=true;rt_sequence=CCAATTGG;int_presence=true;int_sequence=AACCTTGG;ltr_comparison_similarity=9;ltr_comparison_nuc_distance=8;ltr_comparison_mutation_ca=4;ltr_comparison_mutation_ct=6;ltr_comparison_mutation_at=3;ltr_comparison_mutation_cg=5;ltr_comparison_insertion_time=6;tsd_sequence=GGAATTCC;gc_content_5=2;gc_content_3=2;rt_stop_codons_count=10;int_stop_codons_count=3;presence_in_sativa=PresenceInSativa2");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	LTR_retrotransposon	31	60	.	-	.	ID=0;rclass=I;subclass=I;rorder=LTR;superfamily=test;family=test;notes=genreatedRepeat3;is_complete=true;ltr_5_length=6;ltr_3_length=5;pbs_x=32;pbs_y=59;ppt_x=32;ppt_y=59;rt_presence=true;rt_sequence=CCAATTGG;int_presence=true;int_sequence=AACCTTGG;ltr_comparison_similarity=10;ltr_comparison_nuc_distance=9;ltr_comparison_mutation_ca=5;ltr_comparison_mutation_ct=7;ltr_comparison_mutation_at=4;ltr_comparison_mutation_cg=6;ltr_comparison_insertion_time=7;tsd_sequence=GGAATTCC;gc_content_5=3;gc_content_3=3;rt_stop_codons_count=11;int_stop_codons_count=4;presence_in_sativa=PresenceInSativa3");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	LTR_retrotransposon	38	55	.	-	.	ID=0;rclass=I;subclass=I;rorder=LTR;superfamily=test;family=test;notes=genreatedRepeat4;is_complete=true;ltr_5_length=7;ltr_3_length=6;pbs_x=39;pbs_y=54;ppt_x=39;ppt_y=54;rt_presence=true;rt_sequence=CCAATTGG;int_presence=true;int_sequence=AACCTTGG;ltr_comparison_similarity=11;ltr_comparison_nuc_distance=10;ltr_comparison_mutation_ca=6;ltr_comparison_mutation_ct=8;ltr_comparison_mutation_at=5;ltr_comparison_mutation_cg=7;ltr_comparison_insertion_time=8;tsd_sequence=GGAATTCC;gc_content_5=4;gc_content_3=4;rt_stop_codons_count=12;int_stop_codons_count=5;presence_in_sativa=PresenceInSativa4");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	LTR_retrotransposon	2	22	.	-	.	ID=0;rclass=I;subclass=I;rorder=LTR;superfamily=test;family=test;notes=genreatedRepeat5;is_complete=true;ltr_5_length=8;ltr_3_length=7;pbs_x=3;pbs_y=21;ppt_x=3;ppt_y=21;rt_presence=true;rt_sequence=CCAATTGG;int_presence=true;int_sequence=AACCTTGG;ltr_comparison_similarity=12;ltr_comparison_nuc_distance=11;ltr_comparison_mutation_ca=7;ltr_comparison_mutation_ct=9;ltr_comparison_mutation_at=6;ltr_comparison_mutation_cg=8;ltr_comparison_insertion_time=9;tsd_sequence=GGAATTCC;gc_content_5=5;gc_content_3=5;rt_stop_codons_count=13;int_stop_codons_count=6;presence_in_sativa=PresenceInSativa5");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	LTR_retrotransposon	16	25	.	+	.	ID=0;rclass=I;subclass=I;rorder=LTR;superfamily=test;family=test;notes=genreatedRepeat6;is_complete=true;ltr_5_length=9;ltr_3_length=8;pbs_x=17;pbs_y=24;ppt_x=17;ppt_y=24;rt_presence=true;rt_sequence=CCAATTGG;int_presence=true;int_sequence=AACCTTGG;ltr_comparison_similarity=13;ltr_comparison_nuc_distance=12;ltr_comparison_mutation_ca=8;ltr_comparison_mutation_ct=10;ltr_comparison_mutation_at=7;ltr_comparison_mutation_cg=9;ltr_comparison_insertion_time=10;tsd_sequence=GGAATTCC;gc_content_5=6;gc_content_3=6;rt_stop_codons_count=14;int_stop_codons_count=7;presence_in_sativa=PresenceInSativa6");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	HELITRON	21	50	.	+	.	ID=0;rclass=II;subclass=II;rorder=Helitron;superfamily=test;family=test;notes=genreatedRepeat0;orf_greater_than_50aa=1;is_potentially_autonomus=true;end_3=TT;end_5=GG");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	HELITRON	54	85	.	-	.	ID=0;rclass=II;subclass=II;rorder=Helitron;superfamily=test;family=test;notes=genreatedRepeat1;potential_cds_count=1;orf_greater_than_50aa=2;is_potentially_autonomus=true;end_3=TT;end_5=GG");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	HELITRON	36	97	.	-	.	ID=0;rclass=II;subclass=II;rorder=Helitron;superfamily=test;family=test;notes=genreatedRepeat2;potential_cds_count=2;orf_greater_than_50aa=3;is_potentially_autonomus=true;end_3=TT;end_5=GG");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	HELITRON	54	60	.	-	.	ID=0;rclass=II;subclass=II;rorder=Helitron;superfamily=test;family=test;notes=genreatedRepeat3;potential_cds_count=3;orf_greater_than_50aa=4;is_potentially_autonomus=true;end_3=TT;end_5=GG");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	HELITRON	60	64	.	-	.	ID=0;rclass=II;subclass=II;rorder=Helitron;superfamily=test;family=test;notes=genreatedRepeat4;potential_cds_count=4;orf_greater_than_50aa=5;is_potentially_autonomus=true;end_3=TT;end_5=GG");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	HELITRON	37	41	.	-	.	ID=0;rclass=II;subclass=II;rorder=Helitron;superfamily=test;family=test;notes=genreatedRepeat5;potential_cds_count=5;orf_greater_than_50aa=6;is_potentially_autonomus=true;end_3=TT;end_5=GG");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	HELITRON	50	80	.	+	.	ID=0;rclass=II;subclass=II;rorder=Helitron;superfamily=test;family=test;notes=genreatedRepeat6;potential_cds_count=6;orf_greater_than_50aa=7;is_potentially_autonomus=true;end_3=TT;end_5=GG");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	terminal_inverted_repeat_element	42	75	.	-	.	ID=0;rclass=II;subclass=I;rorder=DNA_TE;superfamily=test;family=test;notes=genreatedRepeat0;tir_x=43;tir_y=44;trans_presence=true;trans_sequence=AA;tsd_sequence=CC");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	terminal_inverted_repeat_element	40	65	.	+	.	ID=0;rclass=II;subclass=I;rorder=DNA_TE;superfamily=test;family=test;notes=genreatedRepeat1;tir_x=41;tir_y=54;trans_presence=true;trans_sequence=AA;tsd_sequence=CC");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	terminal_inverted_repeat_element	44	66	.	+	.	ID=0;rclass=II;subclass=I;rorder=DNA_TE;superfamily=test;family=test;notes=genreatedRepeat2;tir_x=45;tir_y=65;trans_presence=true;trans_sequence=AA;tsd_sequence=CC");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	terminal_inverted_repeat_element	26	41	.	-	.	ID=0;rclass=II;subclass=I;rorder=DNA_TE;superfamily=test;family=test;notes=genreatedRepeat3;tir_x=27;tir_y=40;trans_presence=true;trans_sequence=AA;tsd_sequence=CC");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	terminal_inverted_repeat_element	28	50	.	-	.	ID=0;rclass=II;subclass=I;rorder=DNA_TE;superfamily=test;family=test;notes=genreatedRepeat4;tir_x=29;tir_y=49;trans_presence=true;trans_sequence=AA;tsd_sequence=CC");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	terminal_inverted_repeat_element	41	51	.	-	.	ID=0;rclass=II;subclass=I;rorder=DNA_TE;superfamily=test;family=test;notes=genreatedRepeat5;tir_x=42;tir_y=50;trans_presence=true;trans_sequence=AA;tsd_sequence=CC");
		gff3Content.add(seq.getId() +"\tagi_genomes_db	terminal_inverted_repeat_element	63	93	.	-	.	ID=0;rclass=II;subclass=I;rorder=DNA_TE;superfamily=test;family=test;notes=genreatedRepeat6;tir_x=65;tir_y=63;trans_presence=true;trans_sequence=AA;tsd_sequence=CC");
	}
	
	@Test
	public void testImport() throws RepeatsImporterException, RepeatException, OutOfBoundsException, IntervalFeatureException {
		when(sequenceRepository.findLatest(anyString())).thenReturn(seq);
		when(repeatRepository.findOne(ltrRepeat.getId())).thenReturn(ltrRepeat);
		when(repeatsService.save(any(Repeat.class))).thenReturn(ltrRepeat);
		
		repeatsImporter.parseAgiGff3(gff3Content);
		assertEquals(3, repeatsImporter.getWarningLines().size());
		assertEquals(1, repeatsImporter.getWrongLines().size());
		assertEquals(gff3Content.size() - 1, repeatsImporter.getRepeatsSize());
		repeatsImporter.saveList();
		verify(repeatsService, times(gff3Content.size() - 1)).save((Repeat) anyObject());
		verify(repeatRepository, atLeastOnce()).validatePosition((Repeat) anyObject());
	}
	
}
