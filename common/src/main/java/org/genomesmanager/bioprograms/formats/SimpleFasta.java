package org.genomesmanager.bioprograms.formats;

import java.io.Serializable;

public class SimpleFasta implements Serializable {

    private static final long serialVersionUID = 1L;
    private String filename = "";
    private String id = "";
    private String sequence = "";

    public SimpleFasta() {
    }

    public SimpleFasta(String filename, String id, String sequence) {
        this.filename = filename;
        this.id = id;
        this.sequence = sequence;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
