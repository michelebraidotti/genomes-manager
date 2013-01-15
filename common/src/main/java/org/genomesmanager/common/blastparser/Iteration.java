/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.blastparser;

/**
 *
 * @author Kristofer
 */
public class Iteration extends XmlTag {

    String Iteration_iter__num;
    String Iteration_query__ID;
    String Iteration_query__def;
    String Iteration_query__len;
    IterationHits Iteration_hits;
    IterationStat Iteration_stat;

    public Iteration(String tagContent) {
        data = tagContent;
    }

    protected void setTagInfo() {
        setTagInfoHelper("Iteration");
    }

    protected void fillFields() {
        Iteration_iter__num = parseDataBetweenTagName("Iteration_iter-num");
        Iteration_query__ID = parseDataBetweenTagName("Iteration_query-ID");
        Iteration_query__def = parseDataBetweenTagName("Iteration_query-def");
        Iteration_query__len = parseDataBetweenTagName("Iteration_query-len");
        Iteration_hits = new IterationHits(parseDataBetweenTagName("Iteration_hits"));
        Iteration_stat = new IterationStat(parseDataBetweenTagName("Iteration_stat"));
        data = null;
        Iteration_hits.fillFields();
        Iteration_stat.fillFields();
    }
}
