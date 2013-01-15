/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.blastparser;

/**
 *
 * @author Kristofer
 */
public class BlastOutput extends XmlTag {

    public String BlastOutput_program;
    public String BlastOutput_version;
    public String BlastOutput_reference;
    public String BlastOutput_db;
    public String BlastOutput_query__ID;
    public String BlastOutput_query__def;
    public String BlastOutput_query__len;
    public BlastOutputParam BlastOutput_param;
    public BlastOutputIterations BlastOutput_iterations;

    public BlastOutput() {
    }

    public BlastOutput(String tagContent) {
        data = tagContent;
    }

    protected void setTagInfo() {
        openTag = new String("<BlastOutput>");
        closeTag = new String("</BlastOutput>");
    }

    public void fillFields() {
        BlastOutput_program = parseDataBetweenTagName("BlastOutput_program");
        BlastOutput_version = parseDataBetweenTagName("BlastOutput_version");
        BlastOutput_reference = parseDataBetweenTagName("BlastOutput_reference");
        BlastOutput_db = parseDataBetweenTagName("BlastOutput_db");
        BlastOutput_query__ID = parseDataBetweenTagName("BlastOutput_query-ID");
        BlastOutput_query__def = parseDataBetweenTagName("BlastOutput_query-def");
        BlastOutput_query__len = parseDataBetweenTagName("BlastOutput_query-len");
        BlastOutput_param = new BlastOutputParam(parseDataBetweenTagName("BlastOutput_param"));
        BlastOutput_iterations = new BlastOutputIterations(parseDataBetweenTagName("BlastOutput_iterations"));
        data = null;
        BlastOutput_param.fillFields();
        BlastOutput_iterations.fillFields();
    }
}
