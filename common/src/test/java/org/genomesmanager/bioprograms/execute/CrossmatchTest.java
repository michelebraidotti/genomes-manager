package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.*;
//import org.junit.Before;
import org.junit.Test;

public class CrossmatchTest {

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
