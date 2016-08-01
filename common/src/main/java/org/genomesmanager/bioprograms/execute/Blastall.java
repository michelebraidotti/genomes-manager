/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.bioprograms.execute;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.genomesmanager.bioprograms.Configuration;
import org.genomesmanager.bioprograms.blast.BlastOutput;

/**
 *
 * @author Kristofer
 */
public class Blastall extends Execute {
    private String database;
    private File inputFile;
    private String xmlResults;
    private String blastProgram = "blastn";

    public Blastall() {
        setProgram(Configuration.getBlastallExecutablePath());
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getBlastProgram() {
        return blastProgram;
    }

    public void setBlastProgram(String blastProgram) {
        this.blastProgram = blastProgram;
    }

    public String getInputFile() {
        return inputFile.getAbsolutePath();
    }

    public void setInputFile(String inputFilePath) throws ExecuteException {
        this.inputFile = new File(inputFilePath);
        if (!inputFile.canRead()) {
            throw new ExecuteException("Can't read input file: " + inputFilePath);
        }
    }

    public String getXmlResults() {
        return xmlResults;
    }

    public void run() throws ExecuteException {
        if (inputFile == null) {
            throw new ExecuteException("Blast input file null");
        }
        if (!inputFile.canRead()) {
            throw new ExecuteException("Blast input file not readable");
        }
        if (database == null || database.length() == 0) {
            throw new ExecuteException("Database parameter is empty.");
        }
        parameters += " -p " + blastProgram
                + " -d " + database
                + " -i " + inputFile.getAbsolutePath()
                + " -m 7";
        runProgram();
        xmlResults = getLastRunOutput();
    }

    public String getXmlResultsAsHtml() throws TransformerException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("blast/blast.xsl");
        String xsl = "";
//        BufferedReader reader = new BufferedReader(new InputStreamReader(
//                getClass().getClassLoader().getResourceAsStream(url.getPath())));
//        String xsl = "";
//        try {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                xsl += line + "\n";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(url.getPath()));
            xsl = new String(encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transform(xmlResults, xsl);
    }

    public BlastOutput getParsedResults() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance("org.genomesmanager.bioprograms.blast");
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xmlResults);
        BlastOutput blastOutput = (BlastOutput) unmarshaller.unmarshal(reader);
        return blastOutput;
    }

    private String transform(String xml, String xsl) throws TransformerException {
        Source xmlSource = new StreamSource(new StringReader(xml));
        Source xsltSource = new StreamSource(new StringReader(xsl));
        TransformerFactory transFact = TransformerFactory.newInstance();
        Transformer trans = transFact.newTransformer(xsltSource);
        StringWriter html = new StringWriter();
        trans.transform(xmlSource, new StreamResult(html));
        return html.toString();
    }
}
