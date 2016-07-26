package org.genomesmanager.bioprograms.execute;

import org.genomesmanager.bioprograms.Configuration;

public class Sfffile extends Execute {

    public Sfffile() {
        setProgram(Configuration.getSfffileExecutablePath());
    }

    public Boolean run() {
        String tempParams = new String("");

        this.parameters += " " + tempParams;
        return runProgram();
    }
}
