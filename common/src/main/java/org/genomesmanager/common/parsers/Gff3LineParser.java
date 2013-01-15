package org.genomesmanager.common.parsers;

import java.util.Properties;

import org.genomesmanager.common.formats.Gff3Line;

public class Gff3LineParser {

    private Gff3Line gff3Line = new Gff3Line();

    public Gff3LineParser() {
    }

    public String getSeqId() {
        return gff3Line.getSeqId();
    }

    public String getSource() {
        return gff3Line.getSource();
    }

    public String getType() {
        return gff3Line.getType();
    }

    public int getStart() {
        return gff3Line.getStart();
    }

    public int getEnd() {
        return gff3Line.getEnd();
    }

    public String getScore() {
        return gff3Line.getScore();
    }

    public String getStrand() {
        return gff3Line.getStrand();
    }

    public String getPhase() {
        return gff3Line.getPhase();
    }

    public Properties getAttributes() {
        return gff3Line.getAttributes();
    }

    public String getAttributesString() {
        return gff3Line.getAttributesString();
    }

    public String getAttribId() {
        return gff3Line.getAttribId();
    }

    public String getAttribParent() {
        return gff3Line.getAttribParent();
    }

    public void parse(String line) throws Gff3LineParserException {

        String elems[] = line.split("\t");
        if (elems.length < 9) {
            throw new Gff3LineParserException("parsing error: gff3 line has less than 9 values");
        }
        int i = 0;
        gff3Line.setSeqId(elems[i++]);
        gff3Line.setSource(elems[i++]);
        gff3Line.setType(elems[i++]);
        gff3Line.setStart(0);
        gff3Line.setEnd(0);
        try {
            gff3Line.setStart(Integer.parseInt(elems[i++]));
            gff3Line.setEnd(Integer.parseInt(elems[i++]));
        } catch (NumberFormatException ex) {
            throw new Gff3LineParserException("NumberFormatException during gff3 parsing: "
                    + ex.getMessage());
        }
        gff3Line.setScore(elems[i++]);
        gff3Line.setStrand(elems[i++]);
        gff3Line.setPhase(elems[i++]);
        String attributesString = elems[i++];
        String[] attribs = attributesString.split(";");
        Properties attributes = new Properties();
        for (String attrib : attribs) {
            String[] keyVal = attrib.split("=");
            attributes.setProperty(keyVal[0], keyVal[1]);
            if (keyVal[0].equals("ID")) {
                gff3Line.setAttribId(keyVal[1]);
            }
            if (keyVal[0].equals("Parent")) {
                gff3Line.setAttribParent(keyVal[1]);
            }
        }
        gff3Line.setAttributes(attributes);
    }
}
