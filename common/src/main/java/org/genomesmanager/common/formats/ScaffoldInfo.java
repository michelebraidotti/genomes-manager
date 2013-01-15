package org.genomesmanager.common.formats;

public class ScaffoldInfo {

    private String name;
    private String chr;
    private int order;

    public ScaffoldInfo() {
    }

    public ScaffoldInfo(String desc) {
        String[] values = desc.split("\\s+");
        this.name = values[0];
        this.order = Integer.parseInt(values[1]);
        this.chr = values[2];
    }

    public ScaffoldInfo(String name, String chr, int order) {
        super();
        this.name = name;
        this.chr = chr;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
