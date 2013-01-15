/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.execute;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.genomesmanager.common.blastparser.BlastOutput;
import org.genomesmanager.common.blastparser.BlastParser;

/**
 *
 * @author Kristofer
 */
public class Blastall extends Execute {
    // parameters is inherited

    private String database;
    private File inputFile;
    private String blastProgram = "";
    private String xmlResults = "";

    public Blastall() {
        program = "blastall";
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getInputFile() {
        return inputFile.getAbsolutePath();
    }

    public void setInputFile(String inputFilePath) throws BlastError {
        this.inputFile = new File(inputFilePath);
        if (!inputFile.canRead()) {
            throw new BlastError("Can't read input file: " + inputFilePath);
        }
    }

    public String getBlastProgram() {
        return blastProgram;
    }

    public void setBlastProgram(String blastProgram) {
        this.blastProgram = blastProgram;
    }

    public String getXmlResults() {
        return xmlResults;
    }

    @Override
    public void setParameters(String blastParams) throws BlastError {
        if (blastParams.contains("-m ")) {
            throw new BlastError("");
        }
        this.parameters = blastParams;
    }

    public void run() throws BlastError, FileNotFoundException {
        // Create input file if needed
        if (inputFile == null) {
            throw new BlastError("Blast input file null");
        }
        if (!inputFile.canRead()) {
            throw new BlastError("Blast input file not readable");
        }
        if (database.length() <= 0) {
            throw new BlastError("Database parameter is empty.");
        }
        parameters += " -p " + blastProgram
                + " -d " + database + " -i " + inputFile.getAbsolutePath() + " -m 7";
        runProgram();
        xmlResults = getLastRunOutput();
    }

    public String getXmlResultsAsHtml() throws TransformerException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(
                "edu/arizona/genome/execute/blast.xsl")));
        String xsl = "";
        try {
            String line = "";
            while ((line = reader.readLine()) != null) {
                xsl += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return transform(xmlResults, xsl);
    }

    public BlastOutput getParsedResults() {
        BlastParser bp = new BlastParser();
        bp.parseString(xmlResults);
        return bp.blastOutput;
    }

    public String transform(String xml, String xsl) throws TransformerException {
        Source xmlSource = new StreamSource(new StringReader(xml));
        Source xsltSource = new StreamSource(new StringReader(xsl));
        TransformerFactory transFact = TransformerFactory.newInstance();
        Transformer trans = transFact.newTransformer(xsltSource);
        StringWriter html = new StringWriter();
        trans.transform(xmlSource, new StreamResult(html));
        return html.toString();
    }
}
