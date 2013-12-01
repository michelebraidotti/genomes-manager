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
import org.genomesmanager.domain.entities.objectmothers.ChromosomesOM;
import org.genomesmanager.domain.entities.objectmothers.SequencesOM;
import org.genomesmanager.domain.entities.objectmothers.SpeciesOM;
import org.genomesmanager.domain.entities.objectmothers.VarietiesOM;
import org.genomesmanager.repositories.sequences.SequenceRepo;
import org.genomesmanager.repositories.sequences.SequenceRepoException;
import org.genomesmanager.repositories.snps.SnpRepository;
import org.genomesmanager.repositories.species.IndividualRepository;
import org.genomesmanager.repositories.species.VarietyRepository;
import org.genomesmanager.repositories.species.VarietyRepoException;
import org.genomesmanager.services.impl.snps.SnpsImporterImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class SnpsImporterTest {
	@Mock
	private VarietyRepository varietyRepo;
	@Mock
	private SequenceRepo sequenceRepo;
	@Mock
	private IndividualRepository indRepo;
	@Mock
	private SnpRepository snpRepo;
	@InjectMocks
	private SnpsImporter snpsImporter = new SnpsImporterImpl();
	private List<Variety> varieties;
	
	
	@Test
	public void test() throws VarietyRepoException, SequenceRepoException {
		MockitoAnnotations.initMocks(this);
		int nOfSnps = 7;
		Random generator = new Random();
		Species sp = SpeciesOM.Generate(1).get(0);
		Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
		chr.setId(generator.nextInt(1000));
		Scaffold scaffold = SequencesOM.GenerateScaffold(1, chr).get(0);
		scaffold.setId(generator.nextInt(1000));
		varieties = VarietiesOM.Generate(nOfSnps, sp);
		
		when(sequenceRepo.getLatest(anyString())).thenReturn(scaffold);
		when(varietyRepo.get(isA(String.class))).thenAnswer(
				new Answer<Variety>() {

					@Override
					public Variety answer(InvocationOnMock invocation) throws Throwable {
						String name = (String) invocation.getArguments()[0];
						for (Variety v:varieties) {
							if ( v.getName().equals(name) ) return v;
						}
						return null;
					}
					
				}
		);
		
		List<String> varietiesNames = new ArrayList<String>();
		for (Variety v:varieties) varietiesNames.add(v.getName());
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
		verify(indRepo, times(nOfSnps)).insert((Individual) anyObject());
		verify(snpRepo, times(nOfSnps)).insert((Snp) anyObject());
	}
}
