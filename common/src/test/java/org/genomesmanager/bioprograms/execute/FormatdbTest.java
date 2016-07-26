package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.fail;

import org.genomesmanager.bioprograms.blastparser.TestVariables;
import org.junit.Before;
import org.junit.Test;

//import java.io.FileOutputStream;
//import java.io.IOException;
public class FormatdbTest {

    private TestVariables staticVars;

    @Before
    public void setUp() {
        staticVars = new TestVariables();
    }

    @Test
    public void testFormatdb() {
        System.out.println("testFormatdb");
        Formatdb instance = new Formatdb();
        if (instance == null) {
            fail();
        }
    }

    @Test
    public void testFormatdbString() {
        System.out.println("testFormatdbString");
        String InputName = staticVars.getQueryFileName();//Just a fasta file to use to make a database
        Formatdb instance = new Formatdb(InputName);
        if (instance == null) {
            fail();
        }
    }

    @Test
    public void testFormatdbStringString() {
        System.out.println("testFormatdbStringString");
        String inputFileName = staticVars.getQueryFileName();//Just a fasta file to use to make a database
        String title = new String("My_Awesome_Database");
        Formatdb instance = new Formatdb(inputFileName, title);
        if (instance == null) {
            fail();
        }
    }

    @Test
    public void testSetTitle() {
        System.out.println("testSetTitle");
        Formatdb instance = new Formatdb();
        String title = new String("My_Awesome_Database");
        instance.setTitle(title);
        //Optional
        System.out.println("\t" + title);
    }

    @Test
    public void testSetInputFile() {
        System.out.println("testSetInputFile");
        Formatdb instance = new Formatdb();
        String inputFileName = staticVars.getQueryFileName();//Just a fasta file to use to make a database
        instance.setInputFile(inputFileName);
        System.out.println("\t" + inputFileName);
    }

    @Test
    public void testSetBaseName() {
        System.out.println("testSetBaseName");
        Formatdb instance = new Formatdb();
        String baseName = new String("ricechro3db");
        instance.setBaseName(baseName);
        //This was useful for me, so I added it, optional though
        System.out.println("\t" + baseName);
    }

    @Test
    public void testRun() {
        System.out.println("testRun");
        Formatdb instance = new Formatdb();
        String title = new String("My_Awesome_Database");
        String inputFileName = staticVars.getQueryFileName();//Just a fasta file to use to make a database
        String baseName = new String("MADDB");
        instance.setTitle(title);			//Optional
        instance.setInputFile(inputFileName);//Required
        instance.setBaseName(baseName);		//Optional
        instance.run();
        /*try {
        FileOutputStream fout = new FileOutputStream("problem.txt");

        fout.write("dfsujdsjfkdkj".getBytes());
        } catch (IOException ex) {
        //
        }*/
        if (instance.getLastExitValue() != 0) {
            System.out.println("Return: " + instance.getLastExitValue());
            System.out.println("Output: " + instance.getLastRunOutput());
            System.out.println("Error: " + instance.getLastRunError());
            System.out.println("Formatdb test should return 0. Returned " + instance.getLastExitValue());
            fail();
        } else {
            System.out.println("\tSuccess!");
        }
    }
}
