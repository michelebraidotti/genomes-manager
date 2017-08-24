package org.genomesmanager.formats;

public class ScaffoldInfo {

    private String name;
    private String chr;
    private int order;

    public ScaffoldInfo() {
    }

    public ScaffoldInfo(String desc) throws ScaffoldInfoException {
        String[] values = desc.split("\\s+");
        if ( values.length != 3 ) 
        	throw new ScaffoldInfoException("Error parsing string: needs to be 3 elements space sparated");
        this.name = values[0];
        try {
        	this.order = Integer.parseInt(values[1]);
        }
        catch (NumberFormatException e) {
        	throw new ScaffoldInfoException("Error parsing string: order must be a number");
        }
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
