package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class PhredTest {

    @Ignore("phred not installed")
    @Test
    public void testRun() throws ExecuteException {
        String chromatogram = "hjb0001aa01.b1";
        String sequence = "hjb0001aa01.b1.seq";
        String quality = "hjb0001aa01.b1.qual";
        String phd = "hjb0001aa01.b1.phd";
        Phred phred = new Phred(chromatogram, sequence, quality, phd);
        phred.setWorkingDirectory(System.getProperty("java.io.tmpdir"));
        phred.run();
        if (phred.getLastExitValue() != 0) {
            String message = "Phred output: " + phred.getLastRunOutput() + "\n"
                    + "Phred error: " + phred.getLastRunError() + "\n"
                    + "Phred code: " + phred.getLastExitValue();
            assertTrue(message, false);
        }
    }
}
