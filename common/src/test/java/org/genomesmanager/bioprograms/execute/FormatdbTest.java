package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.fail;

import org.junit.Test;

import java.net.URL;

//import java.io.FileOutputStream;
//import java.io.IOException;
public class FormatdbTest {

    @Test
    public void testRun() {
        System.out.println("testRun");
        Formatdb formatdb = new Formatdb();
        formatdb.setTitle("My_Awesome_Database");
        URL url = Thread.currentThread().getContextClassLoader().getResource("formatdbTest/someSequences.fa");
        formatdb.setInputFile(url.getPath());
        formatdb.setBaseName("MADDB");
        url = Thread.currentThread().getContextClassLoader().getResource("formatdbTest/");
        formatdb.setWorkingDirectory(url.getPath());
        formatdb.run();
        /*try {
        FileOutputStream fout = new FileOutputStream("problem.txt");

        fout.write("dfsujdsjfkdkj".getBytes());
        } catch (IOException ex) {
        //
        }*/
        if (formatdb.getLastExitValue() != 0) {
            System.out.println("Command: " + formatdb.getExecutedCommand());
            System.out.println("Return: " + formatdb.getLastExitValue());
            System.out.println("Output: " + formatdb.getLastRunOutput());
            System.out.println("Error: " + formatdb.getLastRunError());
            System.out.println("Formatdb test should return 0. Returned " + formatdb.getLastExitValue());
            fail();
        } else {
            System.out.println("\tSuccess!");
        }
    }
}
