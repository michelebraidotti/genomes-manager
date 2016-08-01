package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.net.URL;


public class FormatdbTest {

    // This is need by blast tests.
    public Formatdb runInTempDir() throws ExecuteException {
        Formatdb formatdb = new Formatdb();
        formatdb.setTitle("My_Awesome_Database");
        URL url = Thread.currentThread().getContextClassLoader().getResource("formatdbTest/someSequences.fa");
        formatdb.setInputFile(url.getPath());
        formatdb.setBaseName("MADDB");
        formatdb.setWorkingDirectory(System.getProperty("java.io.tmpdir"));
        formatdb.run();
        return formatdb;
    }

    @Test
    public void testRun() throws ExecuteException {
        Formatdb formatdb = runInTempDir();
        if (formatdb.getLastExitValue() != 0) {
            String errorMessage = "Command: " + formatdb.getExecutedCommand() + "\n"
                    + "Return: " + formatdb.getLastExitValue() + "\n"
                    + "Output: " + formatdb.getLastRunOutput() + "\n"
                    + "Error: " + formatdb.getLastRunError() + "\n"
                    + "Formatdb test should return 0. Returned " + formatdb.getLastExitValue();
            assertTrue(errorMessage, false);
        }
    }
}
