/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.blastparser;

/**
 *
 * @author Kristofer
 */
public class Statistics extends XmlTag {

    public String Statistics_db__num;
    public String Statistics_db__len;
    public String Statistics_hsp__len;
    public String Statistics_eff__space;
    public String Statistics_kappa;
    public String Statistics_lambda;
    public String Statistics_entropy;

    public Statistics(String tagContent) {
        data = tagContent;
    }

    protected void setTagInfo() {
        setTagInfoHelper("Statistics");
    }

    protected void fillFields() {
        Statistics_db__num = parseDataBetweenTagName("Statistics_db-num");
        Statistics_db__len = parseDataBetweenTagName("Statistics_db-len");
        Statistics_hsp__len = parseDataBetweenTagName("Statistics_hsp-len");
        Statistics_eff__space = parseDataBetweenTagName("Statistics_eff-space");
        Statistics_kappa = parseDataBetweenTagName("Statistics_kappa");
        Statistics_lambda = parseDataBetweenTagName("Statistics_lambda");
        Statistics_entropy = parseDataBetweenTagName("Statistics_entropy");
        data = null;
    }
}
