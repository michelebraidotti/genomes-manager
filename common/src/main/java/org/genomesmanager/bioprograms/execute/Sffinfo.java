package org.genomesmanager.bioprograms.execute;

import org.genomesmanager.bioprograms.Configuration;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Sffinfo extends Execute {

    private String sff;
    private String fasta;

    public Sffinfo() {
        setProgram(Configuration.getSffinfoExecutablePath());
    }

    public Sffinfo(String Sff, String Fasta) {
        this();
        setSff(Sff);
        setFasta(Fasta);
    }

    public void setSff(String Sff) {
        sff = Sff;
    }

    public void setFasta(String Fasta) {
        fasta = Fasta;
    }

    public void run() throws ExecuteException {
        String tempParams = new String("");
        File fastaOutput;

        if (sff == null) {
            throw new ExecuteException("sff is null");
        } else {
            tempParams = tempParams + "-s \"" + sff + "\"";
        }

        if (fasta == null) {
            throw new ExecuteException("fasta is null");
        } else {
            fastaOutput = new File(fasta);
            if ((fastaOutput.isFile() == false)
                    || (fastaOutput.canWrite() == false)
                    || (fastaOutput.exists() == false)) {
                throw new ExecuteException("Can't write to fasta.");
            }
        }

        this.parameters += " " + tempParams;
        runProgram();

        String fastaOutputString = this.getLastRunOutput();
        if ((fastaOutputString != null) && (fastaOutputString.length() > 0)) {
            BufferedWriter out;
            try {
                out = new BufferedWriter(new FileWriter(fastaOutput));
                out.write(fastaOutputString);
            } catch (IOException ex) {
                throw  new ExecuteException("Failed writing out FASTA file.", ex);
            }
        }
    }
}
