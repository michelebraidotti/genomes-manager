package org.genomesmanager.domain.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the species database table.
 * 
 */
@Embeddable
public class SpeciesPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String genus;
	private String species;
	private String subspecies;

    public SpeciesPK() {
    }

	public String getGenus() {
		return this.genus;
	}
	public void setGenus(String genus) {
		this.genus = genus;
	}

	public String getSpecies() {
		return this.species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}

	public String getSubspecies() {
		return this.subspecies;
	}
	public void setSubspecies(String subspecies) {
		this.subspecies = subspecies;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SpeciesPK)) {
			return false;
		}
		SpeciesPK castOther = (SpeciesPK)other;
		return 
			this.genus.equals(castOther.genus)
			&& this.species.equals(castOther.species)
			&& this.subspecies.equals(castOther.subspecies);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.genus.hashCode();
		hash = hash * prime + this.species.hashCode();
		hash = hash * prime + this.subspecies.hashCode();
		
		return hash;
    }
}