package org.genomesmanager.bioprograms.execute;


import org.genomesmanager.bioprograms.Configuration;

import java.io.IOException;

/**
 *
 * @author Kristofer
 */
public class Crossmatch extends Execute {
	private String sequence;
	private String vector;

    public Crossmatch() {
        setProgram(Configuration.getCrossMatchExecutablePath());
    }

    public Crossmatch(String Sequence, String Vector) {
    	setProgram("cross_match");
    	setSequence(Sequence);
    	setVector(Vector);
    }

    public void setSequence(String Sequence) {
    	sequence = Sequence;
    }

    public void setVector(String Vector) {
    	vector = Vector;
    }

    public void run() throws ExecuteException {
    	String tempParams = new String("");
    	if (sequence == null) {
			throw new ExecuteException("sequence is null");
    	}
		else {
    		tempParams = tempParams+"\""+sequence+"\" ";
    	}
    	if (vector == null) {
			throw new ExecuteException("vector is null");
    	}
		else {
    		tempParams = tempParams+"\""+vector+"\" ";
    	}
    	tempParams = tempParams+"-screen";
    	this.parameters += " " + tempParams;
    	
    	runProgram();
    }
}
