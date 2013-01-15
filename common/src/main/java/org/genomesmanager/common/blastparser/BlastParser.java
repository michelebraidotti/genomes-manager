/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.blastparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BlastParser {

    public BlastOutput blastOutput;
    private BufferedReader file;
    private String data;

    public BlastParser() {
        data = new String("");
    }

    public BlastOutput parseString(String XMLString) {
        if (XMLString == null) {
            System.out.print("No XML string given.\n");
            return null;
        }
        data = XMLString;
        return parseData();
    }

    public BlastOutput parseFile(String fileName) {
        try {
            file = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException ex) {
            System.out.print("File \"" + fileName + "\" does not exist.\n");
            //ex.printStackTrace();
            data = null;
            return null;
        }
        String line = new String("");
        try {
            line = file.readLine();
            do {
                data = data.concat(line);
                line = file.readLine();
            } while (line != null);
            file.close();
            file = null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return parseData();
    }

    private BlastOutput parseData() {
        System.out.print("Parsing blast xml...");   //Somewhat pointless since
        //Java doesn't output it immediately before moving to the next line >_<
        blastOutput = new BlastOutput(
                //new blastOutput(data).parseDataForTag(
                //new blastOutput().openTag,
                //new blastOutput().closeTag)
                new BlastOutput(data).parseDataBetweenTagName("BlastOutput"));  //A little complicated for root class
        blastOutput.fillFields();
        data = null;
        System.out.print(" complete.\n");
        return blastOutput;
    }
}
