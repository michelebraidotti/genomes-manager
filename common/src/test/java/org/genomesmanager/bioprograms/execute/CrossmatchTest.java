package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.*;
//import org.junit.Before;
import org.junit.Test;

public class CrossmatchTest {

    @Test
    public void testCrossmatch() {
        System.out.println("testCrossmatch");
        Crossmatch instance = new Crossmatch();
        if (instance == null) {
            fail();
        }
    }

    @Test
    public void testCrossmatchStringString() {
        System.out.println("testCrossmatchStringString");
        String seqeunce = "hjb0001aa01.b1.seq";
        String vector = "pBluescriptIIKS_plus.seq";
        Crossmatch instance = new Crossmatch(seqeunce, vector);
        if (instance == null) {
            fail();
        }
    }

    @Test
    public void testSetSequence() {
        System.out.println("testSetSequence");
        String sequence = "hjb0001aa01.b1.seq";
        Crossmatch instance = new Crossmatch();
        instance.setSequence(sequence);
    }

    @Test
    public void testSetVector() {
        System.out.println("testSetVector");
        String vector = "pBluescriptIIKS_plus.seq";
        Crossmatch instance = new Crossmatch();
        instance.setVector(vector);
    }

    @Test
    public void testRun() {
        System.out.println("testRun");
        String seqeunce = "hjb0001aa01.b1.seq";
        String vector = "pBluescriptIIKS_plus.seq";
        Crossmatch instance = new Crossmatch(seqeunce, vector);
        Boolean ret = instance.run();
        if (ret == false) {
            fail();
        }
        System.out.println("Crossmatch output: " + instance.getLastRunOutput());
        System.out.println("Crossmatch error: " + instance.getLastRunError());
        System.out.println("Crossmatch code: " + instance.getLastExitValue());
    }
}
