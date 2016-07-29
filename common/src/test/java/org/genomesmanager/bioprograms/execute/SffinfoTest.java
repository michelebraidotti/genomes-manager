package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.*;
import org.junit.Test;

public class SffinfoTest {

    @Test
    public void testRun() {
        System.out.println("testRun");
        String sff = "D_2009_10_05_23_35_55_mme_signalProcessing/MID_split/GLA08-PoolB-MID2.sff";
        String fasta = "D_2009_10_05_23_35_55_mme_signalProcessing/MID_split/GLA08-PoolB-MID2.fna";
        Sffinfo sffinfo = new Sffinfo(sff, fasta);
        sffinfo.setWorkingDirectory(System.getProperty("java.io.tmpdir"));
        Boolean ret = sffinfo.run();
        if (ret == false) {
            assertTrue(false);
        }
    }
}
