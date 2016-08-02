//Kristofer Christakos
// 2009-2010
package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.File;
import java.net.URL;

public class BlastAllTest {

    // The following runtime configuration is needed
    // if operating behind a proxy:
    //    -Dhttp.proxyHost=<your proxy>
    //    -Dhttp.proxyPort=<your proxy port>
    @Test
    public void testRun() throws Exception {
        FormatdbTest formatdbTest = new FormatdbTest();
        // If this fails please check formatdbtest test
        Formatdb formatdb = formatdbTest.runInTempDir();
        String databaseName = formatdb.getBaseName();


        Blastall blastall = new Blastall();
        blastall.setDatabase(formatdb.getWorkingDirectory() + File.separator + databaseName);
        URL url = Thread.currentThread().getContextClassLoader().getResource("blastTest/oneSequence.fa");
        blastall.setInputFile(url.getPath());
        blastall.setParameters(" -e 1e-10 ");
        blastall.setWorkingDirectory(System.getProperty("java.io.tmpdir"));
        blastall.run();
        if (blastall.getLastExitValue() != 0) {
            System.out.println("=== Error: A successful blast returns 0. getLastExitValue()==" + blastall.getLastExitValue());
            System.out.print("=== Gathered output:\n" + blastall.getLastRunOutput() + "\n\n");
            System.out.print("=== Gathered error:\n" + blastall.getLastRunError() + "\n\n");
            assertTrue(false);
        }
        System.out.println("=== Blast program: " + blastall.getProgram());
        System.out.println("=== Blast params: " + blastall.getParameters());
        System.out.println("==== Exit value: " + blastall.getLastExitValue());
        System.out.println("=== XML Result:");
        System.out.println(blastall.getXmlResults());
        System.out.println("=== HTML Result:");
        System.out.println(blastall.getXmlResultsAsHtml());
    }
}
