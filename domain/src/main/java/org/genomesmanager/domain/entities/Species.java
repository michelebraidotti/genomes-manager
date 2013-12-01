package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
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
	private Integer id;
	private String genus;
	private String species;
	private String subspecies;
	private String commonName;
	private String genomeType;
	private List<Chromosome> chromosomes = new ArrayList<Chromosome>();
	private List<Variety> varieties = new ArrayList<Variety>();

    public Species() {
    }
    
	@Id
	@SequenceGenerator(name="SPECIES_ID_GENERATOR", sequenceName="species_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPECIES_ID_GENERATOR")
	@Column(insertable=false, updatable=false)
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGenus() {
		return genus;
	}

	public void setGenus(String genus) {
		this.genus = genus;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getSubspecies() {
		return subspecies;
	}

	public void setSubspecies(String subspecies) {
		this.subspecies = subspecies;
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
	@OneToMany(mappedBy="species", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
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
		return  getGenus() + " " +  getSpecies() + " "  + getSubspecies();
	}
	
	@Transient
	public String toString(String separator) {
		String out =  getGenus() + separator +  getSpecies();
		if ( ! getSubspecies().equals("") ) out += separator + getSubspecies();
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
	
	@Transient
	public Chromosome getChromosome(int chrId) {
		for (Chromosome c:chromosomes) {
			if (c.getId() == chrId)
				return c;
		}
		return null;
	}
	
	// TODO is this the right place for this one?
	public class ChromosomesComparable implements Comparator<Chromosome> {
		 
	    @Override
	    public int compare(Chromosome c1, Chromosome c2) {
	    	return (c1.getId()<c2.getId() ? -1 : (c1.getId()==c2.getId() ? 0 : 1));
	    }
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Species)) {
			return false;
		}
		Species castOther = (Species)other;
		return 
			this.getGenus().equals(castOther.getGenus())
			&& this.getSpecies().equals(castOther.getSpecies())
			&& this.getSubspecies().equals(castOther.getSubspecies());

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.getGenus().hashCode();
		hash = hash * prime + this.getSpecies().hashCode();
		hash = hash * prime + this.getSubspecies().hashCode();
		
		return hash;
    }
	
}