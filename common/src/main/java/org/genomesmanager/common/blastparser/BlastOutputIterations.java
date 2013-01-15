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
public class BlastOutputIterations extends XmlTag {

    public ArrayList<Iteration> Iteration;
    public int IterationCount;

    public BlastOutputIterations(String tagContents) {
        data = tagContents;
    }

    protected void setTagInfo() {
        setTagInfoHelper("BlastOutput_iterations");
    }

    protected void fillFields() {
        Iteration = new ArrayList<Iteration>(1);
        String IterationContent;
        IterationContent = parseDataBetweenTagName("Iteration");
        while (IterationContent != null) {
            Iteration.add(new Iteration(IterationContent));
            IterationContent = parseDataBetweenTagName("Iteration");
        }
        data = null;
        IterationCount = Iteration.size();
        for (int x = 0; x < IterationCount; x++) {
            Iteration.get(x).fillFields();
        }
    }
}
