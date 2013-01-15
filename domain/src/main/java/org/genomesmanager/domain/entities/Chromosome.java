package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the chromosomes database table.
 * 
 */
@Entity
@Table(name="chromosomes", schema="sequence")
@NamedQueries({
	@NamedQuery(name = "Chromosome.findAll", query = "SELECT c FROM Chromosome c"),
    @NamedQuery(name = "Chromosome.findAllNames", 
    		query = "SELECT c.id, c.number, s.id " +
    				"FROM Chromosome c LEFT JOIN c.species s"),
    @NamedQuery(name = "Chromosome.byNumAndSpecies", query = "SELECT c FROM Chromosome c " +
    		"WHERE number = :number AND species = :species"),
})
public class Chromosome implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String number;
	private Species species;
	private List<Sequence> sequences;

    public Chromosome() {
    }


    @Id
	@SequenceGenerator(name="CHRS_ID_GENERATOR", sequenceName="sequence.chromosomes_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHRS_ID_GENERATOR")
	@Column(insertable=false, updatable=false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="number")
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	//bi-directional many-to-one association to Species
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="species_genus", referencedColumnName="genus"),
		@JoinColumn(name="species_species", referencedColumnName="species"),
		@JoinColumn(name="species_subspecies", referencedColumnName="subspecies"),
	})
	public Species getSpecies() {
		return this.species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	
	@OneToMany(mappedBy="chromosome")
	public List<Sequence> getSequences() {
		return sequences;
	}

	public void setSequences(List<Sequence> sequences) {
		this.sequences = sequences;
	}
	
	@Transient
	public String descString() {
		SpeciesPK spk = species.getId();
		String abbrGenus = spk.getGenus().substring(0, 1);
		abbrGenus = abbrGenus.toUpperCase() + ".";
		String abbrSpecies = spk.getSpecies();
		if ( abbrSpecies.length() > 4 ) {
			abbrSpecies = spk.getSpecies().substring(0, 4) + ".";
		}
		String abbrSubsp = spk.getSubspecies();
		if ( abbrSubsp.length() > 4 ) {
			abbrSubsp = spk.getSubspecies().substring(0, 4) + ".";
		}
		String res =  abbrGenus + " " +  abbrSpecies + " "  + abbrSubsp + 
			" Chr" + number;
		return res;
	}
	
	@Transient
	public String speciesString() {
		SpeciesPK spk = species.getId();
		return spk.getGenus() + " " + spk.getSpecies() + " " + spk.getSubspecies();
	}
	
	@Override
	public String toString() {
		return number;
	}
	
}