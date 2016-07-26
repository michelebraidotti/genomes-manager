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

    public Boolean run() {
        String tempParams = new String("");
        if (chromatogram == null) {
            System.out.println("chromatogram is null");
            return false;
        } else {
            tempParams = tempParams + "\"" + chromatogram + "\" ";
        }
        if (sequence == null) {
            System.out.println("sequence is null");
            return false;
        } else {
            tempParams = tempParams + "-s \"" + sequence + "\" ";
        }
        if (quality == null) {
            System.out.println("quality is null");
            return false;
        } else {
            tempParams = tempParams + "-q \"" + quality + "\" ";
        }
        if (phd == null) {
            System.out.println("phd is null");
            return false;
        } else {
            tempParams = tempParams + "-p \"" + phd + "\" ";
        }

        this.parameters += " " + tempParams;
        return runProgram();
    }
}
