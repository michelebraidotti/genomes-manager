package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.*;
import org.genomesmanager.domain.entities.objectmothers.*;
import org.genomesmanager.repositories.jpa.AbstractIntegrationTest;
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
    RepeatsClassificationRepository repeatsClassificationRepo;

    @Test
    public void test() throws Exception {
        Species sp = SpeciesOM.Generate(1).get(0);
        speciesRepository.save(sp);
        Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
        chromosomeRepository.save(chr);
        Sequence seq = SequencesOM.Generate(1, chr).get(0);
        sequenceRepository.save(seq);
        String[] repClassDefinitions = { "I, I, LINE, test, test",
                "II, II, Helitron, test, test", "II, III, MITE, test, test",
                "II, I, DNA_TE, test, test", "I, I, LTR, test, test",
                "UNKNOWN, UNKNOWN, UNKNOWN, test, test",
                "I, I, SINE, test, test" };
		/* 1. LINE */
        String repClassDefinition = repClassDefinitions[0];
        RepeatsClassification repClass = RepeatsClassificationOM
                .Generate(repClassDefinition);
        repeatsClassificationRepo.save(repClass);
        LineRepeat line = RepeatsOM.GenerateLines(1, repClass, seq).get(0);
        repeatRepo.save(line);
        LineRepeat postLine = repeatRepo.getLine(line.getId());
        assertEquals(line, postLine);
		/* 2. Helitron */
        repClassDefinition = repClassDefinitions[1];
        repClass = RepeatsClassificationOM.Generate(repClassDefinition);
        repeatsClassificationRepo.save(repClass);
        HelitronRepeat hel = RepeatsOM.GenerateHelitrons(1, repClass, seq).get(0);
        repeatRepo.save(hel);
        HelitronRepeat postHel = repeatRepo.getHelitron(hel.getId());
        assertEquals(hel, postHel);
		/* 3. Mite */
        repClassDefinition = repClassDefinitions[2];
        repClass = RepeatsClassificationOM.Generate(repClassDefinition);
        repeatsClassificationRepo.save(repClass);
        MiteRepeat mite = RepeatsOM.GenerateMites(1, repClass, seq).get(0);
        repeatRepo.save(mite);
        MiteRepeat postMite = repeatRepo.getMite(mite.getId());
        assertEquals(mite, postMite);
		/* 3. DNATE */
        repClassDefinition = repClassDefinitions[3];
        repClass = RepeatsClassificationOM.Generate(repClassDefinition);
        repeatsClassificationRepo.save(repClass);
        DnaTeRepeat dnate = RepeatsOM.GenerateDnaTes(1, repClass, seq).get(0);
        repeatRepo.save(dnate);
        DnaTeRepeat postDnate = repeatRepo.getDnaTe(dnate.getId());
        assertEquals(dnate, postDnate);
		/* 4. LTR */
        repClassDefinition = repClassDefinitions[4];
        repClass = RepeatsClassificationOM.Generate(repClassDefinition);
        repeatsClassificationRepo.save(repClass);
        LtrRepeat ltr = RepeatsOM.GenerateLtrs(1, repClass, seq).get(0);
        repeatRepo.save(ltr);
        LtrRepeat postLtr = repeatRepo.getLtr(ltr.getId());
        assertEquals(ltr, postLtr);
		/* 5. UNKN */
        repClassDefinition = repClassDefinitions[5];
        repClass = RepeatsClassificationOM.Generate(repClassDefinition);
        repeatsClassificationRepo.save(repClass);
        UnknownRepeat unkn = RepeatsOM.GenerateUnknowns(1, repClass, seq)
                .get(0);
        repeatRepo.save(unkn);
        UnknownRepeat postUnkn = repeatRepo.getUnkn(unkn.getId());
        assertEquals(unkn, postUnkn);
		/* 6. Sine */
        repClassDefinition = repClassDefinitions[6];
        repClass = RepeatsClassificationOM.Generate(repClassDefinition);
        repeatsClassificationRepo.save(repClass);
        SineRepeat sine = RepeatsOM.GenerateSines(1, repClass, seq).get(0);
        repeatRepo.save(sine);
        SineRepeat postSine = repeatRepo.getSine(sine.getId());
        assertEquals(sine, postSine);
    }
}