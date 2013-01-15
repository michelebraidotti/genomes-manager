package org.genomesmanager.common.execute;


/**
 *
 * @author Kristofer
 */
public class Crossmatch extends Execute {
	private String sequence;
	private String vector;
    public Crossmatch() {
        setProgram("cross_match");
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
    public Boolean run() {
    	String tempParams = new String("");
    	if (sequence == null) {
    		System.out.println("sequence is null");
    		return false;
    	} else {
    		tempParams = tempParams+"\""+sequence+"\" ";
    	}
    	if (vector == null) {
    		System.out.println("vector is null");
    		return false;
    	} else {
    		tempParams = tempParams+"\""+vector+"\" ";
    	}
    	tempParams = tempParams+"-screen";
    	this.parameters += " " + tempParams;
    	
    	/*If there are problems with file output directory, easily change
    	 * the environment directory to the same directory as an input file
    	 * by un-commenting the next line.
    	 */
    	//changeWorkingDirectory(getParentDirectory(sequence));
    	
    	return runProgram();
    }
}
