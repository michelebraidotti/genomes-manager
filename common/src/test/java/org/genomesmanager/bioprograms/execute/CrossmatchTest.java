package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.*;//import org.junit.Before;
import org.junit.Test;

public class CrossmatchTest {

    @Test
    public void testRun() {
        System.out.println("testRun");
        String seqeunce = "hjb0001aa01.b1.seq";
        String vector = "pBluescriptIIKS_plus.seq";
        Crossmatch crossmatch = new Crossmatch(seqeunce, vector);
        crossmatch.setWorkingDirectory(System.getProperty("java.io.tmpdir"));
        Boolean ret = crossmatch.run();
        if (ret == false) {
            String errorMessage = "Crossmatch output: " + crossmatch.getLastRunOutput() + "\n"
                    + "Crossmatch error: " + crossmatch.getLastRunError() + "\n"
                    + "Crossmatch code: " + crossmatch.getLastExitValue();
            assertTrue(errorMessage, false);
        }
    }
}
