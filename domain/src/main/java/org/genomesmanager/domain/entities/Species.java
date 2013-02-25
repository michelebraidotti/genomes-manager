package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the species database table.
 * 
 */
@Entity
@Table(name="species", schema="sequence")
@NamedQueries({
    @NamedQuery(name = "Species.findAll", query = "SELECT s FROM Species s " +
    		"ORDER BY s.id.genus, s.id.species, s.id.subspecies"),
    @NamedQuery(name = "Species.findRice", query = "SELECT s FROM Species s " +
    		"WHERE id.genus = 'Oryza' ORDER BY s.id.genus, s.id.species, s.id.subspecies")
})
public class Species implements Serializable {
	private static final long serialVersionUID = 1L;
	private SpeciesPK id;
	private String commonName;
	private String genomeType;
	private List<Chromosome> chromosomes = new ArrayList<Chromosome>();
	private List<Variety> varieties = new ArrayList<Variety>();

    public Species() {
    }


	@EmbeddedId
	public SpeciesPK getId() {
		return this.id;
	}

	public void setId(SpeciesPK id) {
		this.id = id;
	}
	

	@Column(name="common_name")
	public String getCommonName() {
		return this.commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}


	@Column(name="genome_type")
	public String getGenomeType() {
		return this.genomeType;
	}

	public void setGenomeType(String genomeType) {
		this.genomeType = genomeType;
	}


	//bi-directional many-to-one association to Chromosomes
	@OneToMany(mappedBy="species")
	@OrderBy(value="number")
	public List<Chromosome> getChromosomes() {
		return chromosomes;
	}

	public void setChromosomes(List<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
	}
	
	//bi-directional many-to-one association to Variety
	@OneToMany(mappedBy="species")
	public List<Variety> getVarieties() {
		return varieties;
	}

	public void setVarieties(List<Variety> varieties) {
		this.varieties = varieties;
	}
	
	@Override
	public String toString() {
		return  id.getGenus() + " " +  id.getSpecies() + " "  + id.getSubspecies();
	}
	
	@Transient
	public String toString(String separator) {
		String out =  id.getGenus() + separator +  id.getSpecies();
		if ( ! id.getSubspecies().equals("") ) out += separator + id.getSubspecies();
		return out;
	}

	@Transient
	public String chromosomesList() {
		return chromosomesList(", ");
	}
	
	@Transient
	public String chromosomesList(String separator) {
		StringBuilder out = new StringBuilder();
		Collections.sort(getChromosomes(), new ChromosomesComparable());
		for (Chromosome c:getChromosomes()) {
			out.append(c.getNumber() + separator);
		}
		if ( out.length() > separator.length() )
			out.setLength(out.length() - separator.length());
		return out.toString();
	}
	
	public class ChromosomesComparable implements Comparator<Chromosome>{
		 
	    @Override
	    public int compare(Chromosome c1, Chromosome c2) {
	    	return (c1.getId()<c2.getId() ? -1 : (c1.getId()==c2.getId() ? 0 : 1));
	    }
	}
	
}