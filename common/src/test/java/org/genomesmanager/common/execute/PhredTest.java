package org.genomesmanager.common.execute;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kristofer
 */
public class PhredTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testPhred() {
        System.out.println("testPhred");
        Phred instance = new Phred();
        if (instance == null) {
            fail();
        }
    }

    @Test
    public void testPhredStringStringStringString() {
        System.out.println("testPhredStringStringStringString");
        String chromatogram = "hjb0001aa01.b1";
        String sequence = "hjb0001aa01.b1.seq";
        String quality = "hjb0001aa01.b1.qual";
        String phd = "hjb0001aa01.b1.phd";
        Phred instance = new Phred(chromatogram, sequence, quality, phd);
        if (instance == null) {
            fail();
        }
    }

    @Test
    public void testSetChromatogram() {
        System.out.println("testSetChromatogram");
        String chromatogram = "";
        Phred instance = new Phred();
        instance.setChromatogram(chromatogram);
    }

    @Test
    public void testSetSequence() {
        System.out.println("testSetSequence");
        String sequence = "";
        Phred instance = new Phred();
        instance.setSequence(sequence);
    }

    @Test
    public void testSetQuality() {
        System.out.println("testSetQuality");
        String quality = "";
        Phred instance = new Phred();
        instance.setQuality(quality);
    }

    @Test
    public void testSetPhd() {
        System.out.println("testSetPhd");
        String phd = "";
        Phred instance = new Phred();
        instance.setPhd(phd);
    }

    @Test
    public void testRun() {
        System.out.println("testRun");
        String chromatogram = "hjb0001aa01.b1";
        String sequence = "hjb0001aa01.b1.seq";
        String quality = "hjb0001aa01.b1.qual";
        String phd = "hjb0001aa01.b1.phd";
        Phred instance = new Phred(chromatogram, sequence, quality, phd);
        Boolean ret = instance.run();
        if (ret == false) {
            fail();
        }
        System.out.println("Phred output: " + instance.getLastRunOutput());
        System.out.println("Phred error: " + instance.getLastRunError());
        System.out.println("Phred code: " + instance.getLastExitValue());
    }
}
