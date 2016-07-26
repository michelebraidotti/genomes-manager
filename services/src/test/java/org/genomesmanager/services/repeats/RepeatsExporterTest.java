package org.genomesmanager.services.repeats;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.bioprograms.formats.AgiExportType;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.DnaTeRepeat;
import org.genomesmanager.domain.entities.HelitronRepeat;
import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.RepeatsClassificationOM;
import org.genomesmanager.domain.entities.objectmothers.RepeatsOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.genomesmanager.services.impl.repeats.RepeatsExporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RepeatsExporterTest {
	private List<Repeat> repeats = new ArrayList<Repeat>();
	@Mock
	private RepeatsService repeatsService;
	@Mock
	private SpeciesRepository speciesRepository;
	@Mock
	private SequenceRepository sequenceRepository;
	@InjectMocks
	private RepeatsExporter repeatsExporter = new RepeatsExporterImpl();
	private Species sp;
	private Chromosome chr;
	private Sequence seq;
	
	@Before 
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this); 
		
		Random generator = new Random();
		int nOfRepeats = 7;
		sp = SpeciesOM.Generate(1).get(0);
		chr = ChromosomesOM.Generate(1, sp).get(0);
		seq = SequencesOM.Generate(1, chr).get(0);
		seq.setId(generator.nextInt());
		
		String repClassDefinition = "I, I, LTR, test, test";		
		RepeatsClassification repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		int id = generator.nextInt();
		for (LtrRepeat ltr:RepeatsOM.GenerateLtrs(nOfRepeats, repClass, seq) ) {
			ltr.setId(id++);
			repeats.add(ltr);
		}
		
		repClassDefinition = "II, II, Helitron, test, test";
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		for (HelitronRepeat hel:RepeatsOM.GenerateHelitrons(nOfRepeats, repClass, seq) ) {
			hel.setId(id++);
			repeats.add(hel);
		}
		
		repClassDefinition = "II, I, DNA_TE, test, test";
		repClass = RepeatsClassificationOM.Generate(repClassDefinition);
		for (DnaTeRepeat dnaTe:RepeatsOM.GenerateDnaTes(nOfRepeats, repClass, seq) ) {
			dnaTe.setId(id++);
			repeats.add(dnaTe);
		}
	}
	
	@Test
	public void testExportByChrGff3() throws RepeatsExporterException {
		when(repeatsService.getAllByChromosome(chr.getId())).thenReturn(repeats);
		
		repeatsExporter.loadRepeatsList(chr);
		repeatsExporter.setFileContent(AgiExportType.GFF3);
		assertTrue(repeatsExporter.getFileContent().size() > 0);
		assertEquals(repeatsExporter.getNOfRepeats(), repeats.size());
	}
	
	@Test
	public void testExporByChrtGff3Plus() throws RepeatsExporterException {
		when(repeatsService.getAllByChromosome(chr.getId())).thenReturn(repeats);
		
		repeatsExporter.loadRepeatsList(chr);
		repeatsExporter.setFileContent(AgiExportType.GFF3PLUS);
		assertTrue(repeatsExporter.getFileContent().size() > 0);
		assertEquals(repeatsExporter.getNOfRepeats(), repeats.size());
	}
	
	@Test
	public void testExportBySeqGff3() throws RepeatsExporterException {
		when(repeatsService.getAllBySequence(seq.getId())).thenReturn(repeats);
		
		repeatsExporter.loadRepeatsList(seq);
		repeatsExporter.setFileContent(AgiExportType.GFF3);
		assertTrue(repeatsExporter.getFileContent().size() > 0);
		assertEquals(repeatsExporter.getNOfRepeats(), repeats.size());
	}
	
	
}
