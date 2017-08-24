package org.genomesmanager.formats;

import java.util.Properties;

public class Gff3Line {

    private String seqId;
    private String source;
    private String type;
    private int start = 0;
    private int end = 0;
    private String score;
    private String strand;
    private String phase;
    private String attribId;
    private String attribParent;
    private Properties attributes;

    public Gff3Line() {
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStrand() {
        return strand;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getAttribId() {
        return attribId;
    }

    public void setAttribId(String attribId) {
        this.attribId = attribId;
    }

    public String getAttribParent() {
        return attribParent;
    }

    public void setAttribParent(String attribParent) {
        this.attribParent = attribParent;
    }

    public Properties getAttributes() {
        return attributes;
    }

    public void setAttributes(Properties attributes) {
        this.attributes = attributes;
    }

    public String getAttributesString() {
        String out = "";
        for (Object keyO : attributes.keySet()) {
            String key = (String) keyO;
            String value = attributes.getProperty(key);
            out += ";" + key + "=" + value;
        }
        return out;
    }

    public void toPseudomolCoords(String pseudomolName, int offset) {
        seqId = pseudomolName;
        start += offset;
        end += offset;
    }

    @Override
    public String toString() {
        String out = "";
        out = seqId + "\t";
        out += source + "\t";
        out += type + "\t";
        out += start + "\t";
        out += end + "\t";
        out += score + "\t";
        out += strand + "\t";
        out += phase + "\t";
        if (attribId != null && !attribId.isEmpty()) {
            out += "ID=" + attribId;
        }
        if (attribParent != null && !attribParent.isEmpty()) {
            out += ";Parent=" + attribParent;
        }
        return out;
    }
}
