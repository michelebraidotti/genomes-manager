package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class SffinfoTest {

    @Ignore("sff tools not installed")
    @Test
    public void testRun() throws ExecuteException {
        String sff = "D_2009_10_05_23_35_55_mme_signalProcessing/MID_split/GLA08-PoolB-MID2.sff";
        String fasta = "D_2009_10_05_23_35_55_mme_signalProcessing/MID_split/GLA08-PoolB-MID2.fna";
        Sffinfo sffinfo = new Sffinfo(sff, fasta);
        sffinfo.setWorkingDirectory(System.getProperty("java.io.tmpdir"));
        sffinfo.run();
        if ( sffinfo.getLastExitValue() != 0 ) {
            assertTrue(false);
        }
    }
}
