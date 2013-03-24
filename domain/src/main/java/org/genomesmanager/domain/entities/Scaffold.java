package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="scaffolds", schema="sequence")
@DiscriminatorValue(value="SCAFF")
@PrimaryKeyJoinColumn(name = "sequence_id")
@NamedQueries({
	@NamedQuery(name = "Scaffold.findByNameOrdByDate", query = "SELECT s FROM Scaffold s " +
		"WHERE name = :name ORDER BY s.dateCreated DESC"),
	@NamedQuery(name = "Scaffold.findByNameVersion", query = "SELECT s FROM Scaffold s " +
		"WHERE name = :name AND scaffVersion = :scaffVersion"),
	@NamedQuery(name = "Scaffold.findAllBySpecies", query = "SELECT s FROM " +
		"Scaffold s JOIN s.chromosome c JOIN c.species sp " +
		"WHERE sp.id.genus = :genus AND sp.id.species = :species AND sp.id.subspecies = :subspecies"),
	@NamedQuery(name = "Scaffold.findAllByChromosome", query = "SELECT s FROM " +
		"Scaffold s JOIN s.chromosome c WHERE c.id = :chrId ORDER BY s.order "),
	@NamedQuery(name = "Scaffold.findAllPlacedByChromosome", query = "SELECT s FROM " +
		"Scaffold s JOIN s.chromosome c WHERE c.id = :chrId AND s.isUnplaced = false " +
		"ORDER BY s.order "),
	@NamedQuery(name = "Scaffold.findAllUnplacedByChromosome", query = "SELECT s FROM " +
		"Scaffold s JOIN s.chromosome c WHERE c.id = :chrId AND s.isUnplaced = true " +
		"ORDER BY s.order "),
	@NamedQuery(name = "Scaffold.getPlacedOffset", query = "SELECT SUM(s.length + 100) FROM " +
		"Scaffold s JOIN s.chromosome c WHERE c.id = :chrId " +
		"AND s.isUnplaced = false AND s.order < :scaffOrder " +
		"AND s.scaffVersion = :scaffVersion"),
	@NamedQuery(name = "Scaffold.getUnplacedOffset", query = "SELECT SUM(s.length + 100) FROM " +
		"Scaffold s JOIN s.chromosome c WHERE c.id = :chrId " +
		"AND s.isUnplaced = true AND s.order < :scaffOrder " +
		"AND s.scaffVersion = :scaffVersion")
})
public class Scaffold extends Sequence implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String scaffVersion;
	private int order;
	private Long pseudomolOffset;
	private Pseudomolecule pseudomolecule;
	private Boolean isUnplaced = false;
	
    public Scaffold() {
    }
        
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="scaff_version")
	public String getScaffVersion() {
		return scaffVersion;
	}

	public void setScaffVersion(String scaffVersion) {
		this.scaffVersion = scaffVersion;
	}

	@Column(name="scaff_order")
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	@Column(name="is_unplaced")
	public Boolean getIsUnplaced() {
		return isUnplaced;
	}

	public void setIsUnplaced(Boolean isUnplaced) {
		this.isUnplaced = isUnplaced;
	}
	
	@ManyToOne
	@JoinColumn(name="pseudomolecule_id", nullable=true)
	public Pseudomolecule getPseudomolecule() {
		return pseudomolecule;
	}

	public void setPseudomolecule(Pseudomolecule pseudomolecule) {
		this.pseudomolecule = pseudomolecule;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((scaffVersion == null) ? 0 : scaffVersion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		//TODO test me!!!!
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Scaffold other = (Scaffold) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (scaffVersion == null) {
			if (other.scaffVersion != null)
				return false;
		} else if (!scaffVersion.equals(other.scaffVersion))
			return false;
		return true;
	}

	@Transient
	public String getType() {
		return "Scaffold";
	}
	
	@Transient
	public String descr() {
		return name + Sequence.NAME_SEPARATOR + scaffVersion;
	}
	
	@Transient
	public boolean isUnplaced() {
		if ( name.contains("unplaced")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transient
	@Override
	public String humanName() {
		return name;
	}
	
	@Transient
	@Override
	public String getFastaHeader() {
		return super.getFastaHeader() + "|Scaffold|" + name + "|version " + scaffVersion ;
	}
	
	@Transient
	public Long getPseudomolOffset() {
		return pseudomolOffset;
	}
	
	@Transient
	public void setPseudomolOffset(Long pseudomolOffset) {
		this.pseudomolOffset = pseudomolOffset;
	}

	@Transient 
	public String toAGPLine() {
		String out = "";
		Pattern p = Pattern.compile("N{6,}+");
		Matcher m = p.matcher(this.getSequenceText());
		while ( m.find() ) {			
			System.out.println("String: " + m.group());
			System.out.println("Start: " + m.start());
			System.out.println("End: " + m.end());
		}
		return out;
	}
}