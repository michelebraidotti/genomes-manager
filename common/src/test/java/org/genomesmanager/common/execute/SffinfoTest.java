package org.genomesmanager.common.execute;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SffinfoTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSffinfo() {
        System.out.println("testSffinfo");
        Sffinfo instance = new Sffinfo();
        if (instance == null) {
            fail();
        }
    }

    @Test
    public void testSffinfoStringString() {
        System.out.println("testSffinfoStringString");
        String sff = "D_2009_10_05_23_35_55_mme_signalProcessing/MID_split/GLA08-PoolB-MID2.sff";
        String fasta = "D_2009_10_05_23_35_55_mme_signalProcessing/MID_split/GLA08-PoolB-MID2.fna";
        Sffinfo instance = new Sffinfo(sff, fasta);
        if (instance == null) {
            fail();
        }
    }

    @Test
    public void testSetSff() {
        System.out.println("testSetSff");
        String sff = "D_2009_10_05_23_35_55_mme_signalProcessing/MID_split/GLA08-PoolB-MID2.sff";
        Sffinfo instance = new Sffinfo();
        instance.setSff(sff);
    }

    @Test
    public void testSetFasta() {
        System.out.println("testSetFasta");
        String fasta = "D_2009_10_05_23_35_55_mme_signalProcessing/MID_split/GLA08-PoolB-MID2.fna";
        Sffinfo instance = new Sffinfo();
        instance.setFasta(fasta);
    }

    @Test
    public void testRun() {
        System.out.println("testRun");
        String sff = "D_2009_10_05_23_35_55_mme_signalProcessing/MID_split/GLA08-PoolB-MID2.sff";
        String fasta = "D_2009_10_05_23_35_55_mme_signalProcessing/MID_split/GLA08-PoolB-MID2.fna";
        Sffinfo instance = new Sffinfo(sff, fasta);
        Boolean ret = instance.run();
        if (ret == false) {
            fail();
        }
    }
}
