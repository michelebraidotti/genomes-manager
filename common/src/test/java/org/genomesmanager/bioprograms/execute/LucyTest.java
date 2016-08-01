package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;

public class LucyTest {
    private String seqFile;
    private String qualFile;

    @Before
    public void setUp() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("lucyTest/ARMTM40TR.seq");
        seqFile = url.getPath();
        url = Thread.currentThread().getContextClassLoader().getResource("lucyTest/ARMTM40TR.qul");
        qualFile = url.getPath();
    }

    @Test
    public void testParams() {
        Lucy lucy = new Lucy();
        lucy.setSeqFile(seqFile);
        lucy.setQualFile(qualFile);
        assertEquals(lucy.getQualFileOut(), lucy.getQualFile() + ".lucy");
        assertEquals(lucy.getSeqFileOut(), lucy.getSeqFile() + ".lucy");
    }
    @Test
    public void testRun() throws ExecuteException {
        Lucy lucy = new Lucy();
        lucy.setSeqFile(seqFile);
        lucy.setQualFile(qualFile);
        lucy.run();
        if (lucy.getLastExitValue() != 0) {
            String errorMessage = "Lucy output:" + lucy.getLastRunOutput() + "\n"
                    + "Lucy error:" + lucy.getLastRunError() + "\n"
                    + "lucy exit value: " + lucy.getLastExitValue();
            assertTrue(errorMessage, false);
        }
    }
}
