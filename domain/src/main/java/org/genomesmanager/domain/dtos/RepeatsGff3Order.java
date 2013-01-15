package org.genomesmanager.domain.dtos;

public enum RepeatsGff3Order {
	LINE("LINE_element"),
	LTR("LTR_retrotransposon"),
	SOLO_LTR("solo_LTR"),
	SINE("SINE_element"),
	HEL("helitron"),
	MITE("MITE"),
	DNATE("terminal_inverted_repeat_element"),
	UNKN("dispersed_repeat");
  	
  	private String label;
  	
  	RepeatsGff3Order(String label) {
  		this.label = label;
  	}
  	
  	public String getLabel() {
  		return this.label;
  	}
  	
  	public String toString(){
  	     return label;
  	}
  	
}
