package org.genomesmanager.common.execute;

public class Lucy extends Execute {

    private String output1;
    private String output2;
    private String input1;
    private String input2;

    public Lucy() {
        setProgram("lucy");
    }

    public Lucy(String Output1, String Output2, String Input1, String Input2) {
        setProgram("lucy");
        setOutput1(Output1);
        setOutput2(Output2);
        setInput1(Input1);
        setInput2(Input2);
    }

    public void setOutput1(String Output1) {
        output1 = Output1;
    }

    public void setOutput2(String Output2) {
        output2 = Output2;
    }

    public void setInput1(String Input1) {
        input1 = Input1;
    }

    public void setInput2(String Input2) {
        input2 = Input2;
    }

    public Boolean run() {
        String tempParams = new String("");
        if (output1 == null) {
            System.out.println("output1 is null");
            return false;
        } else {
            tempParams = tempParams + "-output \"" + output1 + "\" ";
        }
        if (output2 == null) {
            System.out.println("output2 is null");
            return false;
        } else {
            tempParams = tempParams + "\"" + output2 + "\" ";
        }
        if (input1 == null) {
            System.out.println("input1 is null");
            return false;
        } else {
            tempParams = tempParams + "\"" + input1 + "\" ";
        }
        if (input2 == null) {
            System.out.println("input2 is null");
            return false;
        } else {
            tempParams = tempParams + "\"" + input2 + "\" ";
        }

        this.parameters += " " + tempParams;

        //changeWorkingDirectory(getParentDirectory(output1));

        return runProgram();
    }
}
