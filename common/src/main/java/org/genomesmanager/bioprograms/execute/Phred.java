package org.genomesmanager.bioprograms.execute;

import org.genomesmanager.bioprograms.Configuration;

public class Phred extends Execute {

    private String chromatogram;
    private String sequence;
    private String quality;
    private String phd;

    public Phred() {
        setProgram(Configuration.getPhredExecutablePath());
    }

    public Phred(String Chromatogram, String Sequence, String Quality, String Phd) {
        this();
        setChromatogram(Chromatogram);
        setSequence(Sequence);
        setQuality(Quality);
        setPhd(Phd);
    }

    public void setChromatogram(String Chromatogram) {
        chromatogram = Chromatogram;
    }

    public void setSequence(String Sequence) {
        sequence = Sequence;
    }

    public void setQuality(String Quality) {
        quality = Quality;
    }

    public void setPhd(String Phd) {
        phd = Phd;
    }

    public void run() throws ExecuteException {
        String tempParams = new String("");
        if (chromatogram == null) {
            throw new ExecuteException("chromatogram is null");
        } else {
            tempParams = tempParams + "\"" + chromatogram + "\" ";
        }
        if (sequence == null) {
            throw new ExecuteException("sequence is null");
        } else {
            tempParams = tempParams + "-s \"" + sequence + "\" ";
        }
        if (quality == null) {
            throw new ExecuteException("quality is null");
        } else {
            tempParams = tempParams + "-q \"" + quality + "\" ";
        }
        if (phd == null) {
            throw new ExecuteException("phd is null");
        } else {
            tempParams = tempParams + "-p \"" + phd + "\" ";
        }

        this.parameters += " " + tempParams;
        runProgram();
    }
}
