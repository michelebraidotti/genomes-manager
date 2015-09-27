package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.*;
import org.genomesmanager.domain.entities.objectmothers.*;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.repeats.RepeatRepository;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepository;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 9/25/15.
 */
public class RepeatsRepositoryFindAllRepeatsWithParentsTest extends AbstractIntegrationTest {
    @Autowired
    private RepeatsClassificationRepository repeatsClassificationRepo;
    @Autowired
    private RepeatRepository repeatRepo;
    @Autowired
    private SpeciesRepository speciesRepo;
    @Autowired
    private ChromosomeRepository chromosomeRepo;
    @Autowired
    private SequenceRepository sequenceRepo;

    @Test
    public void test() throws Exception {
        Species sp = SpeciesOM.Generate(1).get(0);
        sp = speciesRepo.save(sp);
        Chromosome chr = ChromosomesOM.Generate(1, sp).get(0);
        chr = chromosomeRepo.save(chr);
        Sequence seq = SequencesOM.Generate(1, chr).get(0);
        seq = sequenceRepo.save(seq);
        RepeatsClassification repClass = RepeatsClassificationOM.Generate("I, I, LTR, test, test");
        repClass = repeatsClassificationRepo.save(repClass);
        LtrRepeat parentLtr = RepeatsOM.GenerateLtrs(1, repClass, seq).get(0);
        repeatRepo.save(parentLtr);
        LtrRepeat nestedLtr = RepeatsOM.GenerateLtrs(1, repClass, seq).get(0);
        nestedLtr.setX(parentLtr.getX() + 1);
        nestedLtr.setY(parentLtr.getY() - 1);
        nestedLtr.setPbsX(nestedLtr.getX() + 1);
        nestedLtr.setPbsY(nestedLtr.getY() - 1);
        nestedLtr.setPptX(nestedLtr.getX() + 1);
        nestedLtr.setPptY(nestedLtr.getY() - 1);
        nestedLtr = repeatRepo.save(nestedLtr);

        List<Object[]> result = repeatRepo.findAllRepeatsWithParents();
        assertEquals((Integer) nestedLtr.getId(), result.get(0)[0]);
        assertEquals(new Long(1), result.get(0)[1]);
    }
}
