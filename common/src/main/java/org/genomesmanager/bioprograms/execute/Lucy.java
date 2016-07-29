package org.genomesmanager.bioprograms.execute;

import org.genomesmanager.bioprograms.Configuration;

public class Lucy extends Execute {

    private String seqFileOut;
    private String qualFileOut;
    private String seqFile;
    private String qualFile;

    public Lucy() {
        setProgram(Configuration.getLucyExecutablePath());
    }

    public String getSeqFile() {
        return seqFile;
    }

    public void setSeqFile(String seqFile) {
        seqFileOut = seqFile + ".lucy";
        this.seqFile = seqFile;
    }

    public String getQualFile() {
        return qualFile;
    }

    public void setQualFile(String qualFile) {
        qualFileOut = qualFile + ".lucy";
        this.qualFile = qualFile;
    }

    public String getSeqFileOut() {
        return seqFileOut;
    }

    public String getQualFileOut() {
        return qualFileOut;
    }

    public Boolean run() {
        String tempParams = new String("");
        tempParams = tempParams + "-output " + seqFileOut + " ";
        tempParams = tempParams + " " + qualFileOut + " ";

        if (seqFile == null) {
            return false;
        } else {
            tempParams = tempParams + " " + seqFile + " ";
        }
        if (qualFile == null) {
            return false;
        } else {
            tempParams = tempParams + " " + qualFile + " ";
        }
        this.parameters += " " + tempParams;
        return runProgram();
    }
}
