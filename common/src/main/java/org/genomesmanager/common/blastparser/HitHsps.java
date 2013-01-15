/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.blastparser;

import java.util.ArrayList;

/**
 *
 * @author Kristofer
 */
public class HitHsps extends XmlTag {

    public ArrayList<Hsp> Hsp;
    public int HspCount;

    public HitHsps(String tagContent) {
        data = new String(tagContent);
    }

    protected void setTagInfo() {
        setTagInfoHelper("Hit_hsps");
    }

    protected void fillFields() {
        Hsp = new ArrayList<Hsp>(1);
        String HspContent;
        HspContent = parseDataBetweenTagName("Hsp");
        while (HspContent != null) {
            Hsp.add(new Hsp(HspContent));
            HspContent = parseDataBetweenTagName("Hsp");
            HspCount++;
            //System.out.println("HspCount "+HspCount+" FromIndex="+fromIndex+"  HspContent=" + HspContent);
        }
        data = null;
        //HspCount = Hsp.size();
        for (int x = 0; x < HspCount; x++) {
            Hsp.get(x).fillFields();
        }
    }
}
