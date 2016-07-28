package org.genomesmanager.bioprograms.execute;

import org.genomesmanager.bioprograms.Configuration;

import java.io.File;

/**
 * @author Kristofer
 */
public class Formatdb extends Execute {

    private String title;
    private String inputFile;
    private String baseName;

    public Formatdb() {
        setProgram(Configuration.getFormatdbExecutablePath());
    }

    public Formatdb(String InputFile) {
        this();
        if (InputFile == null) {
            System.out.println(program + " InputFile == null");
            return;
        }
        setInputFile(InputFile);
    }

    public Formatdb(String InputFile, String Title) {
        this(InputFile);
        setTitle(Title);
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    public void setInputFile(String InputFile) {
        this.inputFile = InputFile;
    }

    public void setBaseName(String BaseName) {
        this.baseName = BaseName;
    }

    public Boolean run() {
        String tempParams = new String("");
        if (title != null) {
            tempParams = "-t " + title + " ";
        }
        if (inputFile != null) {
            if ( inputFile.contains(" ") ) {
                tempParams = tempParams + "-i '" + inputFile + "' ";
            }
            else {
                tempParams = tempParams + "-i " + inputFile + " ";
            }
        } else {
            System.out.println(program + ".run InputFile is null!");
            return false;
        }
        if (baseName != null) {
            if (baseName.contains(" ")) {
                tempParams = tempParams + "-n '" + baseName + "' ";
            }
            else {
                tempParams = tempParams + "-n " + baseName + " ";
            }
        }
        tempParams = tempParams + "-p F";
        this.parameters += " " + tempParams;

        return runProgram();
    }

}
