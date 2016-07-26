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

    public Boolean run() {
        String tempParams = new String("");
        File fastaOutput;

        if (sff == null) {
            System.out.println("sff is null");
            return false;
        } else {
            tempParams = tempParams + "-s \"" + sff + "\"";
        }

        if (fasta == null) {
            System.out.println("fasta is null");
            return false;
        } else {
            fastaOutput = new File(fasta);
            if ((fastaOutput.isFile() == false)
                    || (fastaOutput.canWrite() == false)
                    || (fastaOutput.exists() == false)) {
                System.out.println("Can't write to fasta.");
                return false;
            }
        }

        this.parameters += " " + tempParams;
        Boolean ret = runProgram();

        if (ret == false) {
            return false;
        }

        String fastaOutputString = this.getLastRunOutput();

        if ((fastaOutputString != null) && (fastaOutputString.length() > 0)) {
            BufferedWriter out;
            try {
                out = new BufferedWriter(new FileWriter(fastaOutput));
                out.write(fastaOutputString);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return true;
    }
}
