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
	@NamedQuery(name = "Scaffold.findAllPlacedByChromosome", query = "SELECT s FROM " +
		"Scaffold s JOIN s.chromosome c WHERE c.id = :chrId AND s.isUnplaced = false " +
		"ORDER BY s.order "),
	@NamedQuery(name = "Scaffold.findAllUnplacedByChromosome", query = "SELECT s FROM " +
		"Scaffold s JOIN s.chromosome c WHERE c.id = :chrId AND s.isUnplaced = true " +
		"ORDER BY s.order "),
	@NamedQuery(name = "Scaffold.getPlacedOffset", query = "SELECT SUM(s.length + 100) FROM " +
		"Scaffold s JOIN s.chromosome c WHERE c.id = :chrId " +
		"AND s.isUnplaced = false AND s.order < :scaffOrder " +
		"AND s.version = :scaffVersion"),
	@NamedQuery(name = "Scaffold.getUnplacedOffset", query = "SELECT SUM(s.length + 100) FROM " +
		"Scaffold s JOIN s.chromosome c WHERE c.id = :chrId " +
		"AND s.isUnplaced = true AND s.order < :scaffOrder " +
		"AND s.version = :scaffVersion")
})
public class Scaffold extends Sequence implements Serializable {
	private static final long serialVersionUID = 1L;
	private int order;
	private Long pseudomolOffset;
	private Pseudomolecule pseudomolecule;
	private Boolean isUnplaced = false;
	
    public Scaffold() {
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


	@Transient
	public String getType() {
		return "Scaffold";
	}
	
	@Transient
	public boolean isUnplaced() {
		if ( getName().contains("unplaced")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transient
	@Override
	public String getFastaHeader() {
		return super.getFastaHeader() + "|Scaffold|" + getName() + "|version " + getVersion();
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