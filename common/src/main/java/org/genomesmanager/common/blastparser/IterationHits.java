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
public class IterationHits extends XmlTag {

    public ArrayList<Hit> Hit;
    public int HitCount;

    public IterationHits(String tagContent) {
        data = new String(tagContent);
    }

    protected void setTagInfo() {
        setTagInfoHelper("Iteration_hits");
    }

    protected void fillFields() {
        Hit = new ArrayList<Hit>();
        String HitContent;
        HitContent = parseDataBetweenTagName("Hit");
        while (HitContent != null) {
            Hit.add(new Hit(HitContent));
            HitContent = parseDataBetweenTagName("Hit");
        }
        data = null;
        HitCount = Hit.size();
        for (int x = 0; x < HitCount; x++) {
            Hit.get(x).fillFields();
        }
    }
}
