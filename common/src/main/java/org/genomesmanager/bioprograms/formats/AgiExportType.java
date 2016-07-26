package org.genomesmanager.bioprograms.formats;

public enum AgiExportType {
	FASTA("Fasta"),
	GFF3("Gff3"),
	GFF3PLUS("Gff3Plus"),
	FLANKING("Flanking");
  	
  	private String label;
  	
  	AgiExportType(String label) {
  		this.label = label;
  	}
  	
  	public String getLabel() {
  		return this.label;
  	}
  	
  	public String toString(){
 	     return label;
 	}
}
