//Kristofer Christakos
//Jan 2010
package org.genomesmanager.bioprograms.blastparser;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Kristofer
 */
public class TestVariables {

    private Properties prop;
    private String classPath;
    private String slash;
    private String commonDirectory;
    //BlastnTest variables:
    public String databaseName;
    public String queryFileName;
    public String outputFileName;
    //ExecuteTest variables:
    public String programString;
    public String parametersString;
    //BlastParserTest variables:
    public String blastOutputXmlFileName;
    public String blastOutputXmlString;

    public TestVariables() {
        prop = System.getProperties();
        classPath = new String(prop.getProperty("java.class.path", null));
        classPath.replaceFirst("src", "test");
        slash = File.separator;
        commonDirectory = new String("edu" + slash + "arizona" + slash + "genome" + slash + "blastparser");

        //Set public strings to null, so only strings used will be created
        databaseName = null;
        queryFileName = null;
        outputFileName = null;
        programString = null;
        parametersString = null;
        blastOutputXmlFileName = null;
        blastOutputXmlString = null;
    }

    public String getDatabaseName() {
        if (databaseName == null) {
            databaseName = getFileNameFromClassPath(commonDirectory + slash + "TestDatabase.nhr");
            if (databaseName == null) {
                return null;
            }
            databaseName = new String(databaseName.substring(0, (databaseName.length() - 4))); //Cuts off ".nhr"
        }
        return databaseName;
    }

    public String getQueryFileName() {
        if (queryFileName == null) {
            queryFileName = getFileNameFromClassPath(commonDirectory + slash + "unknown_sequence.fasta");
        }
        return queryFileName;
    }

    public String getOutputFileName() {
        if (outputFileName == null) {
            outputFileName = getFileNameFromClassPath(commonDirectory + slash + "TestOutput.xml");
        }
        return outputFileName;
    }

    public String getProgramString() {
        if (programString == null) {
            programString = new String("formatdb");
        }
        return programString;
    }

    public String getParametersString() {
        if (parametersString == null) {
            parametersString = new String("--help");
        }
        return parametersString;
    }

    public String getBlastOutputXmlFileName() {
        if (blastOutputXmlFileName == null) {
            blastOutputXmlFileName = getFileNameFromClassPath(commonDirectory + slash + "BlastOutput.xml");
        }
        return blastOutputXmlFileName;
    }

    public String getBlastOutputXmlString() {
        if (blastOutputXmlFileName == null) {
            getBlastOutputXmlFileName();
        }
        if (blastOutputXmlFileName == null) {
            return null;
        }
        if (blastOutputXmlString == null) {
            blastOutputXmlString = readFile(blastOutputXmlFileName);//Read contents of file into String
        }
        return blastOutputXmlString;
    }

    private static String readFile(String fileName) {
        if (fileName == null) {
            return null;
        }
        File file = new File(fileName);
        if (!file.canRead()) {
            return null;
        }
        byte[] buffer = new byte[(int) (file.length())];
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(fileName));
            inputStream.read(buffer);
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
        	try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return new String(buffer);
    }

    private String getFileNameFromClassPath(String fileToFind) {
        if ((prop == null) || (classPath == null)) {
            return null;
        }
        int pos = 0;
        int prevPos = 0;
        pos = classPath.indexOf(File.pathSeparatorChar, prevPos);
        //System.out.println(classPath);
        do {
            String possiblePath = new String();
            possiblePath = classPath.substring(prevPos, pos);
            String possibleFileName = new String(possiblePath + slash + fileToFind);
            File possibleFile = new File(possibleFileName);
            if (possibleFile.exists()) {
                return possibleFileName;
            }
            prevPos = pos + 1;
            pos = classPath.indexOf(File.pathSeparatorChar, prevPos);
        } while (pos != -1);
        return null;
    }
}
