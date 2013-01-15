/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.blastparser;

/**
 *
 * @author Kristofer
 */
public class Parameters extends XmlTag {

    public String Parameters_expect;
    public String Parameters_sc__match;
    public String Parameters_sc__mismatch;
    public String Parameters_gap__open;
    public String Parameters_gap__extend;
    public String Parameters_filter;

    public Parameters(String tagInfo) {
        data = tagInfo;
    }

    protected void setTagInfo() {
        setTagInfoHelper("Parameters");
    }

    protected void fillFields() {
        Parameters_expect = parseDataBetweenTagName("Parameters_expect");
        Parameters_sc__match = parseDataBetweenTagName("Parameters_sc-match");
        Parameters_sc__mismatch = parseDataBetweenTagName("Parameters_sc-mismatch");
        Parameters_gap__open = parseDataBetweenTagName("Parameters_gap-open");
        Parameters_gap__extend = parseDataBetweenTagName("Parameters_gap-extend");
        Parameters_filter = parseDataBetweenTagName("Parameters_filter");
        data = null;
    }
}
