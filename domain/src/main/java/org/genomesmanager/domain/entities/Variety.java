package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the varieties database table.
 * 
 */
@Entity
@Table(name="varieties", schema="sequence")
public class Variety implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String alias;
	private Species species;
	private List<Individual> individuals;

    public Variety() {
    }
     
	@Id
	@SequenceGenerator(name="VARIETIES_ID_GENERATOR", sequenceName="varieties_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="VARIETIES_ID_GENERATOR")
	@Column(insertable=false, updatable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

    @ManyToOne
    @JoinColumn(name="species_id")
	public Species getSpecies() {
		return this.species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}


	@OneToMany(mappedBy="variety")
	public List<Individual> getIndividuals() {
		return individuals;
	}

	public void setIndividuals(List<Individual> individuals) {
		this.individuals = individuals;
	}

}