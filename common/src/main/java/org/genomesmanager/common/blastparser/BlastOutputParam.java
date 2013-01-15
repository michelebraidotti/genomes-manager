/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.blastparser;

/**
 *
 * @author Kristofer
 */
public class BlastOutputParam extends XmlTag {

    public Parameters Parameters;

    protected void setTagInfo() {
        openTag = new String("<BlastOutput_param>");
        closeTag = new String("</BlastOutput_param>");
    }

    public BlastOutputParam(String tagContent) {
        data = tagContent;
    }

    public void fillFields() {
        Parameters = new Parameters(parseDataBetweenTagName("Parameters"));
        data = null;
        Parameters.fillFields();
    }
}
