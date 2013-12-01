package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the rnas database table.
 * 
 */
@Entity
@Table(name="rnas", schema="annotation")
@NamedQueries({
	@NamedQuery(name = "Rna.findByName", 
    		query = "SELECT r FROM Rna r " +
    				"WHERE r.name = :name"),
	@NamedQuery(name = "Rna.findAllBySpecies", 
    		query = "SELECT r FROM Rna r JOIN r.sequence s JOIN s.chromosome c " +
    				"JOIN c.species sp " +
    				"WHERE sp.id = :speciesId "),
	@NamedQuery(name = "Rna.findAllByChromosome", 
    		query = "SELECT r FROM Rna r JOIN r.sequence s JOIN s.chromosome c " +
    				"WHERE c.id = :chrId")  
})
public class Rna extends IntervalFeature implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Calendar dateCreated;
	private Calendar dateModified;
	private BigDecimal gcContent;
	private String name;
	private String rnaName;
	private String strandness;
	private String type;
	private int x;
	private int y;
	private Sequence sequence;
	private Rna parent;
	private List<Rna> children;

    public Rna() { }

	@Id
	@SequenceGenerator(name="RNAS_ID_GENERATOR", sequenceName="annotation.rnas_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RNAS_ID_GENERATOR")
	@Column(insertable=false, updatable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="date_created")
	public Calendar getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="date_modified")
	public Calendar getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Calendar dateModified) {
		this.dateModified = dateModified;
	}


	@Column(name="gc_content")
	public BigDecimal getGcContent() {
		return this.gcContent;
	}

	public void setGcContent(BigDecimal gcContent) {
		this.gcContent = gcContent;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(name="rna_name")
	public String getRnaName() {
		return this.rnaName;
	}

	public void setRnaName(String rnaName) {
		this.rnaName = rnaName;
	}

	
	@Column(name = "strandness", columnDefinition = "bpchar(1)")
	public String getStrandness() {
		return this.strandness;
	}

	public void setStrandness(String strandness) {
		this.strandness = strandness;
	}


	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@ManyToOne
	public Sequence getSequence() {
		return this.sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}
	
	@ManyToOne
	@JoinColumn(name="parent_rna_id")
	public Rna getParent() {
		return this.parent;
	}

	public void setParent(Rna parent) {
		this.parent = parent;
	}
	
	@OneToMany(mappedBy="parent")
	public List<Rna> getChildren() {
		return this.children;
	}

	public void setChildren(List<Rna> children) {
		this.children = children;
	}
	
	@Transient
	public String toGff3Line() {
		String line = sequence.humanName() + "\t";
		line += "agi_genomes_db\t";
		line += type + "\t";
		line += x + "\t";
		line += y + "\t";
		line += ".\t";
		line += strandness + "\t";
		line += ".\t";
		line += "ID=" + name;
		if ( rnaName != null ) {
			line += ";Name=" + rnaName;
		}
		if ( parent != null ) {
			line += ";Parent=" + parent.getName();
		}
		return line;
	}
	
	@Transient
	public String toGff3WithPseudomolCoordinatesLine(String chrName, Long offset) {
		Long newX = offset + x;
		Long newY = offset + y;
		String line = chrName + "\t";
		line += "agi_genomes_db\t";
		line += type + "\t";
		line += newX + "\t";
		line += newY + "\t";
		line += ".\t";
		line += strandness + "\t";
		line += ".\t";
		line += "ID=" + name;
		if ( rnaName != null ) {
			line += ";Name=" + rnaName;
		}
		if ( parent != null ) {
			line += ";Parent=" + parent.getName();
		}
		return line;
	}
	
	@Transient
	public String extraAnnot() {
		String annot = "";
		return annot;
	}
	
	@PrePersist
	public void setCreateDefaults() {
		if ( this.dateCreated == null ) {
			this.dateCreated = Calendar.getInstance();
			this.dateModified = Calendar.getInstance();
		}
	}
	
	@PreUpdate
	public void setUpdateDefaults() {
		this.dateModified = Calendar.getInstance();
	}
	
}