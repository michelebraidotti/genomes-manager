package org.genomesmanager.domain.entities;

public enum RepeatsOrder {
	// Reflects the database definition for repeats classification 
	// 'roder' field which acts as discriminator for repeats types 
	// (of course with the exception of 'ANY' ... )
	ANY("Any"),
	LINE("LINE"),
	LTR("LTR"),
	SINE("SINE"),
	HEL("Helitron"),
	MITE("MITE"),
	DNATE("DNA_TE"),
	UNKN("UNKNOWN");
  	
  	private String label;
  	
  	RepeatsOrder(String label) {
  		this.label = label;
  	}
  	
  	public String getLabel() {
  		return this.label;
  	}
  	
  	public String toString(){
  	     return label;
  	}
  	
}
