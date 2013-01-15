package org.genomesmanager.common.execute;

/**
 * @author Kristofer
 */
public class Formatdb extends Execute {

    private String title;		//Optional
    private String inputFile;	//Required
    private String baseName;	//Optional

    public Formatdb() {
        setProgram("formatdb");
    }

    public Formatdb(String InputFile) {
        setProgram("formatdb");
        if (InputFile == null) {
            System.out.println(program + " InputFile == null");
            return;
        }
        setInputFile(InputFile);
    }

    public Formatdb(String InputFile, String Title) {
        setProgram("formatdb");
        if (InputFile == null) {
            System.out.println(program + " InputFile == null");
            return;
        }
        setTitle(Title);
        setInputFile(InputFile);
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
            tempParams = tempParams + "-i \"" + inputFile + "\" ";
        } else {
            System.out.println(program + ".run InputFile is null!");
            return false;
        }
        if (baseName != null) {
            tempParams = tempParams + "-n \"" + baseName + "\" ";
        }
        tempParams = tempParams + "-p F";
        this.parameters += " " + tempParams;

        String OutputDirPath = getParentDirectory(inputFile);
        changeWorkingDirectory(OutputDirPath);
        //System.out.println("Output to: "+OutputDirPath);

        return runProgram();
    }
}
