//Kristofer Christakos
// 2009-2010
package org.genomesmanager.common.execute;

import static org.junit.Assert.fail;

import org.genomesmanager.common.blastparser.BlastOutput;
import org.genomesmanager.common.blastparser.TestVariables;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Kristofer
 */
public class BlastAllTest {

    TestVariables staticVars;

    public BlastAllTest() {
    }

    @Before
    public void setUp() {
        staticVars = new TestVariables();
    }

    @Test
    public void testRun() throws Exception {
        System.out.println("Run");
        Blastall instance = new Blastall();
        instance.setBlastProgram("blastn");
        instance.setDatabase(staticVars.getDatabaseName());
        instance.setInputFile(staticVars.getQueryFileName());
        instance.setParameters(" -e 1e-10 ");
        //instance.setOutputFile(staticVars.getOutputFileName());
        // ^ Optional
        instance.run();
        System.out.println("=== Blast program: " + instance.getProgram());
        System.out.println("=== Blast params: " + instance.getParameters());
        if (instance.getLastExitValue() != 0) {
            System.out.println("=== Error: A successful blast returns 0. getLastExitValue()==" + instance.getLastExitValue());
            System.out.print("=== Gathered output:\n" + instance.getLastRunOutput() + "\n\n");
            System.out.print("=== Gathered error:\n" + instance.getLastRunError() + "\n\n");
            fail();
        }
        System.out.println("==== Exit value: " + instance.getLastExitValue());
        System.out.println("=== XML Result:");
        System.out.println(instance.getXmlResults());
        System.out.println("=== HTML Result:");
        System.out.println(instance.getXmlResultsAsHtml());
        System.out.println("=== Parsed Result:");
        BlastOutput bo = instance.getParsedResults();
        System.out.println(bo.BlastOutput_db);
        System.out.println(bo.BlastOutput_program);
    }
}
