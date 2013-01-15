/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.blastparser;

/**
 *
 * @author Kristofer
 */
public class Hsp extends XmlTag {

    public String Hsp_num;
    public String Hsp_bit__score;
    public String Hsp_score;
    public Float Hsp_evalue;
    public String Hsp_query__from;
    public String Hsp_query__to;
    public String Hsp_hit__from;
    public String Hsp_hit__to;
    public String Hsp_query__frame;
    public String Hsp_hit__frame;
    public String Hsp_identity;
    public String Hsp_positive;
    public String Hsp_gaps;
    public String Hsp_align__len;
    public String Hsp_qseq;
    public String Hsp_hseq;
    public String Hsp_midline;

    public Hsp(String tagContent) {
        data = tagContent;
    }

    protected void setTagInfo() {
        setTagInfoHelper("Hsp");
    }

    protected void fillFields() {
        Hsp_num = parseDataBetweenTagName("Hsp_num");
        Hsp_bit__score = parseDataBetweenTagName("Hsp_bit-score");
        Hsp_score = parseDataBetweenTagName("Hsp_score");
        Hsp_evalue = new Float(parseDataBetweenTagName("Hsp_evalue"));
        Hsp_query__from = parseDataBetweenTagName("Hsp_query-from");
        Hsp_query__to = parseDataBetweenTagName("Hsp_query-to");
        Hsp_hit__from = parseDataBetweenTagName("Hsp_hit-from");
        Hsp_hit__to = parseDataBetweenTagName("Hsp_hit-to");
        Hsp_query__frame = parseDataBetweenTagName("Hsp_query-frame");
        Hsp_hit__frame = parseDataBetweenTagName("Hsp_hit-frame");
        Hsp_identity = parseDataBetweenTagName("Hsp_identity");
        Hsp_positive = parseDataBetweenTagName("Hsp_positive");
        Hsp_gaps = parseDataBetweenTagName("Hsp_gaps");
        Hsp_align__len = parseDataBetweenTagName("Hsp_align-len");
        Hsp_qseq = parseDataBetweenTagName("Hsp_qseq");
        Hsp_hseq = parseDataBetweenTagName("Hsp_hseq");
        Hsp_midline = parseDataBetweenTagName("Hsp_midline");
        data = null;
    }
}
