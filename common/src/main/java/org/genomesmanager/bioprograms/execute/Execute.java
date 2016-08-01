package org.genomesmanager.bioprograms.execute;

import java.io.*;

/**
 *
 * @author Kristofer
 */
public abstract class Execute {

    private Process process;
    protected String program;
    protected String parameters = "";
    protected String lastRunOutput;
    protected String lastRunError;
    private int lastExitValue;
    private String workingDirectory;
    private String executedCommand;

    public Execute() {
        program = new String("");
        parameters = new String("");
        lastRunOutput = new String("");
        lastRunError = new String("");
        lastExitValue = -1;
        workingDirectory = null;
    }

    public void setParameters(String parameters) { this.parameters = parameters; }

    public String getParameters() {
        return this.parameters;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getProgram() {
        return this.program;
    }

    public String getWorkingDirectory() { return workingDirectory; }

    public void setWorkingDirectory(String workingDirectory) { this.workingDirectory = workingDirectory; }

    public String getLastRunError() {
        return lastRunError;
    }

    public String getLastRunOutput() {
        return lastRunOutput;
    }

    public int getLastExitValue() { return lastExitValue; }

    public String getExecutedCommand() {
        return this.executedCommand;
    }

    public abstract void run() throws ExecuteException;

    protected void runProgram() throws ExecuteException {
        lastRunOutput = new String("");
        lastRunError = new String("");
        lastExitValue = -1;

        String command;
        if (program == null) {
            throw new java.lang.NullPointerException();
        }
        if (parameters.length() > 0) {
            command = new String(program + " " + parameters);
        } else {
            command = program;
        }
        File workingDirObject = null;
        if ((workingDirectory != null) && (workingDirectory.length() > 0)) {
            workingDirObject = new File(workingDirectory);
        }
        this.executedCommand = command;
        try {
            process = Runtime.getRuntime().exec(command, null, workingDirObject);
            process.waitFor();
        } catch (IOException e) {
            throw new ExecuteException(e);
        } catch (InterruptedException e) {
            throw new ExecuteException(e);
        }

        lastRunOutput = readStream(process.getInputStream());
        lastRunError = readStream(process.getErrorStream());
        //                      process.getOutputStream()
        lastExitValue = process.exitValue();
    }

    private String readStream(InputStream in) {
        BufferedReader std = new BufferedReader(new InputStreamReader(in));
        String output = new String("");
        String line = new String("");
        do {
            try {
                line = std.readLine();
                if (line != null) {
                    line = line.concat("\n");
                    output = output.concat(line);
                }
            } catch (java.io.IOException ex) {
                ex.printStackTrace();
            }
        } while (line != null);
        return output;
    }

}
