package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.*;
import org.genomesmanager.domain.entities.testobjectgenerators.*;
import org.genomesmanager.repositories.AbstractIntegrationTest;
import org.genomesmanager.repositories.sequences.ChromosomeRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.repositories.species.SpeciesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

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

    private Sequence getSequence() {
        Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
        sp = speciesRepository.save(sp);
        Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
        chr = chromosomeRepository.save(chr);
        Sequence seq = SequencesTestObjectGenerator.Generate(1, chr).get(0);
        seq = sequenceRepository.save(seq);
        return  seq;
    }

    @Test
    public void testFind() throws Exception {
        Sequence seq = getSequence();
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

    @Test
    public void testFindAllRepeatsBySequence() throws Exception {
        int nOfRepeats = 7;
        Sequence seq = getSequence();

        String repClassDefinition = "I, I, LTR, test, test";
        RepeatsClassification repClass = RepeatsClassificationTestObjectGenerator
                .Generate(repClassDefinition);
        repClass = repeatsClassificationRepository.save(repClass);

        for (LtrRepeat ltr : RepeatsTestObjectGenerator.GenerateLtrs(nOfRepeats, repClass, seq)) {
            repeatRepo.save(ltr);
        }
        assertEquals(nOfRepeats, repeatRepo.findAllRepeatsBySequence(seq.getId()).size());
    }

    @Test
    public void testFindAllRepeatsBySequenceAndRepeatClassificationOrRepeatOrder() throws Exception {
        int nOfLtrRepeats = 7;
        int nOfDnaTeRepeats = 5;
        Sequence seq = getSequence();

        String repClassDefinitionLtr = "I, I, LTR, test, test";
        RepeatsClassification repClassLtr = RepeatsClassificationTestObjectGenerator
                .Generate(repClassDefinitionLtr);
        repClassLtr = repeatsClassificationRepository.save(repClassLtr);

        String repClassDefinitionDnaTe = "II, I, DNA_TE, test, test";
        RepeatsClassification repClassDnaTe = RepeatsClassificationTestObjectGenerator
                .Generate(repClassDefinitionDnaTe);
        repClassDnaTe = repeatsClassificationRepository.save(repClassDnaTe);

        for (LtrRepeat ltr : RepeatsTestObjectGenerator.GenerateLtrs(nOfLtrRepeats, repClassLtr, seq)) {
            repeatRepo.save(ltr);
        }
        for (DnaTeRepeat dnaTeRepeat : RepeatsTestObjectGenerator.GenerateDnaTes(nOfDnaTeRepeats, repClassDnaTe, seq)) {
            repeatRepo.save(dnaTeRepeat);
        }
        assertEquals( nOfLtrRepeats, repeatRepo.findAllRepeatsBySequence(seq.getId(), repClassLtr).size() );
        assertEquals( nOfLtrRepeats, repeatRepo.findAllRepeatsBySequence(seq.getId(), RepeatsOrder.LTR).size() );
        assertEquals( nOfDnaTeRepeats, repeatRepo.findAllRepeatsBySequence(seq.getId(), repClassDnaTe).size() );
        assertEquals( nOfDnaTeRepeats, repeatRepo.findAllRepeatsBySequence(seq.getId(), RepeatsOrder.DNATE).size() );
    }

    @Test
    public void testFindAllRepeatsByChromosomeAndRepeatOrder() {
    }

    @Test
    public void testFindAllRepeatsInRange() {
    }

    @Test
    public void testFindAllLtrRepeats() {
    }

    @Test
    public void testFindAllLtrRepeatsInRange() {
    }

    @Test
    public void testFindAllRepeatsBySequenceAndRepeatsOrderAndSuperFamily() {
    }

    @Test
    public void testFindAllRepeatsBySpeciesClass() {
    }

    @Test
    public void testFindAllRepeatsBySpeciesId() {
    }

    @Test
    public void testFindAllRepeatsByChromosomeId() {
    }

    @Test
    public void testFindAllRepeatsByChromosomeAndRepeatClassfication() {
    }

    @Test
    public void testFindAllRepeatsByChromosomeAndRepeatOrderAndSuperfamily() {
    }

    @Test
    public void testFindAllRepeatsWithParents() throws Exception {
        Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
        sp = speciesRepository.save(sp);
        Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
        chr = chromosomeRepository.save(chr);
        Sequence seq = SequencesTestObjectGenerator.Generate(1, chr).get(0);
        seq = sequenceRepository.save(seq);
        RepeatsClassification repClass = RepeatsClassificationTestObjectGenerator.Generate("I, I, LTR, test, test");
        repClass = repeatsClassificationRepository.save(repClass);
        LtrRepeat parentLtr = RepeatsTestObjectGenerator.GenerateLtrs(1, repClass, seq).get(0);
        repeatRepo.save(parentLtr);
        LtrRepeat nestedLtr = RepeatsTestObjectGenerator.GenerateLtrs(1, repClass, seq).get(0);
        nestedLtr.setX(parentLtr.getX() + 1);
        nestedLtr.setY(parentLtr.getY() - 1);
        nestedLtr.setPbsX(nestedLtr.getX() + 1);
        nestedLtr.setPbsY(nestedLtr.getY() - 1);
        nestedLtr.setPptX(nestedLtr.getX() + 1);
        nestedLtr.setPptY(nestedLtr.getY() - 1);
        nestedLtr = repeatRepo.saveAndValidate(nestedLtr);

        List<Object[]> result = repeatRepo.findAllRepeatsWithParents();
        assertEquals((Integer) nestedLtr.getId(), result.get(0)[0]);
        assertEquals(new Long(1), result.get(0)[1]);
    }

    @Test
    public void testCountChildren() {
    }

    @Test
    public void testGetParent() {
    }

    @Test
    public void testValidatePosition() throws RepeatException {
    }

    @Test
    public void testValidateUpdate() throws RepeatException {
    }

    @Test
    public void testUpdateContainedElementsCount() {
    }

    @Test
    public void saveAndValidateNewRepeatTest() throws Exception {
        Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
        Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
        Sequence seq = SequencesTestObjectGenerator.Generate(1,chr).get(0);
        String repClassDefinition = "TEST, TEST, TEST, test, test";
        RepeatsClassification repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
        Repeat repeat = RepeatsTestObjectGenerator.Generate(1, repClass, seq).get(0);
        repeatRepo.saveAndValidate(repeat);
        assertTrue(repeat.getId() != 0);
    }

    @Test
    public void saveAndValidateUpdateRepeatTest() throws Exception {
        Species sp = SpeciesTestObjectGenerator.Generate(1).get(0);
        Chromosome chr = ChromosomesTestObjectGenerator.Generate(1, sp).get(0);
        Sequence seq = SequencesTestObjectGenerator.Generate(1,chr).get(0);
        String repClassDefinition = "TEST, TEST, TEST, test, test";
        RepeatsClassification repClass = RepeatsClassificationTestObjectGenerator.Generate(repClassDefinition);
        Repeat repeat = RepeatsTestObjectGenerator.Generate(1, repClass, seq).get(0);
        // First we need to save the repeat
        repeatRepo.saveAndValidate(repeat);
        // And now we try the update
        String theNote = repeat.getNotes() + " This is an update";
        repeat.setNotes(theNote);
        repeatRepo.saveAndValidate(repeat);
        Repeat repeatPost = repeatRepo.findOne(repeat.getId());
        assertEquals(theNote, repeatPost.getNotes());
    }

}
