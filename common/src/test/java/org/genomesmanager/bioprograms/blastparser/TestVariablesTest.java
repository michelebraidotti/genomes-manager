//Kristofer Christakos
//Jan 2010
package org.genomesmanager.bioprograms.blastparser;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kristofer
 */
public class TestVariablesTest {

    public TestVariablesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getDatabaseName method, of class TestVariables.
     */
    @Test
    public void testGetDatabaseName() {
        System.out.println("getDatabaseName");
        TestVariables instance = new TestVariables();
        String result = instance.getDatabaseName();
        if (result == null) {
            System.out.println("\treturn NULL;");
            fail();
        } else {
            System.out.println("\t" + result);
        }
    }

    /**
     * Test of getQueryFileName method, of class TestVariables.
     */
    @Test
    public void testGetQueryFileName() {
        System.out.println("getQueryFileName");
        TestVariables instance = new TestVariables();
        String result = instance.getQueryFileName();
        if (result == null) {
            System.out.println("\treturn NULL;");
            fail();
        } else {
            System.out.println("\t" + result);
        }
    }

    /**
     * Test of getOutputFileName method, of class TestVariables.
     */
    @Test
    public void testGetOutputFileName() {
        System.out.println("getOutputFileName");
        TestVariables instance = new TestVariables();
        String result = instance.getOutputFileName();
        if (result == null) {
            System.out.println("\treturn NULL;");
            fail();
        } else {
            System.out.println("\t" + result);
        }
    }

    /**
     * Test of getProgramString method, of class TestVariables.
     */
    @Test
    public void testGetProgramString() {
        System.out.println("getProgramString");
        TestVariables instance = new TestVariables();
        String result = instance.getProgramString();
        if (result == null) {
            System.out.println("\treturn NULL;");
            fail();
        } else {
            System.out.println("\t" + result);
        }
    }

    /**
     * Test of getParametersString method, of class TestVariables.
     */
    @Test
    public void testGetParametersString() {
        System.out.println("getParametersString");
        TestVariables instance = new TestVariables();
        String result = instance.getParametersString();
        if (result == null) {
            System.out.println("\treturn NULL;");
            fail();
        } else {
            System.out.println("\t" + result);
        }
    }

    /**
     * Test of getBlastOutputXmlFileName method, of class TestVariables.
     */
    @Test
    public void testGetBlastOutputXmlFileName() {
        System.out.println("getBlastOutputXmlFileName");
        TestVariables instance = new TestVariables();
        String result = instance.getBlastOutputXmlFileName();
        if (result == null) {
            System.out.println("\treturn NULL;");
            fail();
        } else {
            System.out.println("\t" + result);
        }
    }

    /**
     * Test of getBlastOutputXmlString method, of class TestVariables.
     */
    @Test
    public void testGetBlastOutputXmlString() {
        System.out.println("getBlastOutputXmlString");
        TestVariables instance = new TestVariables();
        String result = instance.getBlastOutputXmlString();
        if (result == null) {
            System.out.println("\treturn NULL;");
            fail();
        } else {
            System.out.println("\t" + result.substring(0, 128) + "...");
        }
    }
}
