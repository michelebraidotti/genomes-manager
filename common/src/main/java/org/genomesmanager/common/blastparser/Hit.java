/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.blastparser;

/**
 *
 * @author Kristofer
 */
public class Hit extends XmlTag {

    public String Hit_num;
    public String Hit_id;
    public String Hit_def;
    public String Hit_accession;
    public String Hit_len;
    public HitHsps Hit_hsps;

    public Hit(String tagContent) {
        data = tagContent;
    }

    protected void setTagInfo() {
        setTagInfoHelper("Hit");
    }

    protected void fillFields() {
        Hit_num = parseDataBetweenTagName("Hit_num");
        Hit_id = parseDataBetweenTagName("Hit_id");
        Hit_def = parseDataBetweenTagName("Hit_def");
        Hit_accession = parseDataBetweenTagName("Hit_accession");
        Hit_len = parseDataBetweenTagName("Hit_len");
        Hit_hsps = new HitHsps(parseDataBetweenTagName("Hit_hsps"));
        data = null;
        Hit_hsps.fillFields();
    }
}
