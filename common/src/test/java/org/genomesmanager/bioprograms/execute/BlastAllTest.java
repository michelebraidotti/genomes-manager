//Kristofer Christakos
// 2009-2010
package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.fail;

import org.genomesmanager.bioprograms.blast.BlastOutput;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Kristofer
 */
public class BlastAllTest {

    @Test
    public void testRun() throws Exception {
        System.out.println("Run");
        Blastall blastall = new Blastall();
        //blastall.setDatabase(staticVars.getDatabaseName());
        //blastall.setInputFile(staticVars.getQueryFileName());
        blastall.setParameters(" -e 1e-10 ");
        //instance.setOutputFile(staticVars.getOutputFileName());
        // ^ Optional
        blastall.run();
        System.out.println("=== Blast program: " + blastall.getProgram());
        System.out.println("=== Blast params: " + blastall.getParameters());
        if (blastall.getLastExitValue() != 0) {
            System.out.println("=== Error: A successful blast returns 0. getLastExitValue()==" + blastall.getLastExitValue());
            System.out.print("=== Gathered output:\n" + blastall.getLastRunOutput() + "\n\n");
            System.out.print("=== Gathered error:\n" + blastall.getLastRunError() + "\n\n");
            fail();
        }
        System.out.println("==== Exit value: " + blastall.getLastExitValue());
        System.out.println("=== XML Result:");
        System.out.println(blastall.getXmlResults());
        System.out.println("=== HTML Result:");
        System.out.println(blastall.getXmlResultsAsHtml());
        System.out.println("=== Parsed Result:");
        BlastOutput bo = blastall.getParsedResults();
        System.out.println(bo.getBlastOutputDb());
        System.out.println(bo.getBlastOutputProgram());
    }
}
