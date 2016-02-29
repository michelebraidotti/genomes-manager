/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.blastparser;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kristofer
 */
public class BlastParserTest {

    TestVariables staticVars;

    public BlastParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        staticVars = new TestVariables();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parseString method, of class BlastParser.
     */
    @Test
    public void testParseString() {
        System.out.println("parseString");
        String xmlString = staticVars.getBlastOutputXmlString();
        BlastParser blastParser = new BlastParser();

        BlastOutput blastOutput = blastParser.parseString(xmlString);

        assertEquals("blastn", blastOutput.BlastOutput_program);
    }

    /**
     * Test of parseFile method, of class BlastParser.
     */
    @Test
    public void testParseFile() {
        System.out.println("parseFile");
        String fileName = staticVars.getBlastOutputXmlFileName();
        BlastParser instance = new BlastParser();

        BlastOutput result = instance.parseFile(fileName);

        assertEquals("blastn", result.BlastOutput_program);
    }
}
