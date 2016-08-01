package org.genomesmanager.bioprograms.execute;

import org.genomesmanager.bioprograms.Configuration;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getBaseName() { return baseName; }

    public void run() throws ExecuteException {
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
            throw new ExecuteException(program + ".run InputFile is null!");
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

        runProgram();
    }

}
