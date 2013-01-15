package org.genomesmanager.common.execute;

public class Sfffile extends Execute {

    public Sfffile() {
        setProgram("sfffile");
    }

    public Boolean run() {
        String tempParams = new String("");

        this.parameters += " " + tempParams;
        return runProgram();
    }
}
