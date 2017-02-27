package org.genomesmanager.services.snp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Individual;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Snp;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.domain.entities.Variety;
import org.genomesmanager.domain.entities.testobjectgenerators.ChromosomesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SequencesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.SpeciesTestObjectGenerator;
import org.genomesmanager.domain.entities.testobjectgenerators.VarietiesTestObjectGenerator;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.snps.SnpRepository;
import org.genomesmanager.repositories.species.IndividualRepository;
import org.genomesmanager.repositories.species.VarietyRepository;
import org.genomesmanager.services.impl.snps.SnpsImporterImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SnpsImporterTest {
	@Mock
	private VarietyRepository varietyRepository;
	@Mock
	private SequenceRepository sequenceRepository;
	@Mock
	private IndividualRepository individualRepository;
	@Mock
	private SnpRepository snpRepository;
	@InjectMocks
	private SnpsImporter snpsImporter = new SnpsImporterImpl();
	private List<Variety> varieties;
	private static final int nOfSnps = 7;
	private Random generator;
	private Scaffold scaffold;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		generator = new Random();
		Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
		Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
		chr.setId(generator.nextInt(1000));
		scaffold = SequencesTestObjectGenerator.GenerateScaffold(1, chr).get(0);
		scaffold.setId(generator.nextInt(1000));
		varieties = VarietiesTestObjectGenerator.Generate(nOfSnps, sp);

		when(sequenceRepository.findLatest(anyString())).thenReturn(scaffold);
		when(varietyRepository.findByName(isA(String.class))).thenReturn(varieties);
	}

	@Test
	public void test() {
		List<String> varietiesNames = new ArrayList<String>();
		for (Variety v:varieties) {
			varietiesNames.add(v.getName());
		}
		snpsImporter.buildIndividuals(varietiesNames, "IndividualsDescr");
		assertEquals(varietiesNames.size(), snpsImporter.getIndividuals().size());
		
		List<String> lines = new ArrayList<String>();
		String [] nucleotides = {"A","C","T","G"};
		for (int i = 0; i < nOfSnps; i++) {
			lines.add(scaffold.getName() + "\t" + generator.nextInt(scaffold.getLength()) 
					+ "\t" + nucleotides[1] + "\t" + nucleotides[generator.nextInt(4)]);
		}
		snpsImporter.parseMipsSnps(lines);
		assertEquals(nOfSnps, snpsImporter.getSnps().size());
		assertEquals(0, snpsImporter.getWarnings().size());
		snpsImporter.save();
		verify(individualRepository, times(nOfSnps)).save((Individual) anyObject());
		verify(snpRepository, times(nOfSnps)).save((Snp) anyObject());
	}
}
