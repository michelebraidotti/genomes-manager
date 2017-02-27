package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.*;
import org.genomesmanager.domain.entities.testobjectgenerators.*;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 9/16/15.
 */
public class RepeatRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private RepeatRepository repeatRepo;
    @Autowired
    private SpeciesRepository speciesRepository;
    @Autowired
    private ChromosomeRepository chromosomeRepository;
    @Autowired
    private SequenceRepository sequenceRepository;
    @Autowired
    private
    RepeatsClassificationRepository repeatsClassificationRepository;

    @Test
    public void test() throws Exception {
        Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
        speciesRepository.save(sp);
        Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
        chromosomeRepository.save(chr);
        Sequence seq = SequencesTestObjectGenerator.Generate(1, chr).get(0);
        sequenceRepository.save(seq);
        String[] repClassDefinitions = { "I, I, LINE, test, test",
                "II, II, Helitron, test, test", "II, III, MITE, test, test",
                "II, I, DNA_TE, test, test", "I, I, LTR, test, test",
                "UNKNOWN, UNKNOWN, UNKNOWN, test, test",
                "I, I, SINE, test, test" };
		/* 1. LINE */
        String repClassDefinition = repClassDefinitions[0];
        RepeatsClassification repClass = RepeatsClassificationTestObjectGenerator
                .Generate(repClassDefinition);
        repeatsClassificationRepository.save(repClass);
        LineRepeat line = RepeatsTestObjectGenerator.GenerateLines(1, repClass, seq).get(0);
        line = repeatRepo.save(line);
        LineRepeat postLine = repeatRepo.findLineRepeat(line.getId());
        assertEquals(line, postLine);
		/* 2. Helitron */
        repClassDefinition = repClassDefinitions[1];
        repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
        repeatsClassificationRepository.save(repClass);
        HelitronRepeat hel = RepeatsTestObjectGenerator.GenerateHelitrons(1, repClass, seq).get(0);
        hel = repeatRepo.save(hel);
        HelitronRepeat postHel = repeatRepo.findHelitronRepeat(hel.getId());
        assertEquals(hel, postHel);
		/* 3. Mite */
        repClassDefinition = repClassDefinitions[2];
        repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
        repeatsClassificationRepository.save(repClass);
        MiteRepeat mite = RepeatsTestObjectGenerator.GenerateMites(1, repClass, seq).get(0);
        mite = repeatRepo.save(mite);
        MiteRepeat postMite = repeatRepo.findMiteRepeat(mite.getId());
        assertEquals(mite, postMite);
		/* 3. DNATE */
        repClassDefinition = repClassDefinitions[3];
        repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
        repeatsClassificationRepository.save(repClass);
        DnaTeRepeat dnate = RepeatsTestObjectGenerator.GenerateDnaTes(1, repClass, seq).get(0);
        dnate = repeatRepo.save(dnate);
        DnaTeRepeat postDnate = repeatRepo.findDnaTeRepeat(dnate.getId());
        assertEquals(dnate, postDnate);
		/* 4. LTR */
        repClassDefinition = repClassDefinitions[4];
        repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
        repeatsClassificationRepository.save(repClass);
        LtrRepeat ltr = RepeatsTestObjectGenerator.GenerateLtrs(1, repClass, seq).get(0);
        ltr = repeatRepo.save(ltr);
        LtrRepeat postLtr = repeatRepo.findLtrRepeat(ltr.getId());
        assertEquals(ltr, postLtr);
		/* 5. UNKN */
        repClassDefinition = repClassDefinitions[5];
        repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
        repeatsClassificationRepository.save(repClass);
        UnknownRepeat unkn = RepeatsTestObjectGenerator.GenerateUnknowns(1, repClass, seq)
                .get(0);
        unkn = repeatRepo.save(unkn);
        UnknownRepeat postUnkn = repeatRepo.findUnknRepeat(unkn.getId());
        assertEquals(unkn, postUnkn);
		/* 6. Sine */
        repClassDefinition = repClassDefinitions[6];
        repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
        repeatsClassificationRepository.save(repClass);
        SineRepeat sine = RepeatsTestObjectGenerator.GenerateSines(1, repClass, seq).get(0);
        sine = repeatRepo.save(sine);
        SineRepeat postSine = repeatRepo.findSineRepeat(sine.getId());
        assertEquals(sine, postSine);
    }
}
