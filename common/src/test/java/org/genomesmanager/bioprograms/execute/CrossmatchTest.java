package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.*;//import org.junit.Before;

import org.junit.Ignore;
import org.junit.Test;

public class CrossmatchTest {

    @Ignore("cross_match not installed")
    @Test
    public void testRun() throws ExecuteException {
        String sequence = "hjb0001aa01.b1.seq";
        String vector = "pBluescriptIIKS_plus.seq";
        Crossmatch crossmatch = new Crossmatch(sequence, vector);
        crossmatch.setWorkingDirectory(System.getProperty("java.io.tmpdir"));
        crossmatch.run();
        if (crossmatch.getLastExitValue() != 0) {
            String errorMessage = "Crossmatch output: " + crossmatch.getLastRunOutput() + "\n"
                    + "Crossmatch error: " + crossmatch.getLastRunError() + "\n"
                    + "Crossmatch code: " + crossmatch.getLastExitValue();
            assertTrue(errorMessage, false);
        }
    }
}
