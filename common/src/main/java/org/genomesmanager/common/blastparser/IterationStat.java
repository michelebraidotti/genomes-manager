/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.blastparser;

/**
 *
 * @author Kristofer
 */
public class IterationStat extends XmlTag {

    public Statistics Statistics;

    public IterationStat(String tagContent) {
        data = tagContent;
    }

    protected void setTagInfo() {
        setTagInfoHelper("Iteration_stat");
    }

    protected void fillFields() {
        Statistics = new Statistics(parseDataBetweenTagName("Statistics"));
        data = null;
        Statistics.fillFields();
    }
}
