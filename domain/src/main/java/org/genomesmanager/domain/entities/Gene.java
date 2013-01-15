package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

import org.genomesmanager.common.formats.Gff3Line;


/**
 * The persistent class for the genes database table.
 * 
 */
@Entity
@Table(name="genes", schema="annotation")
@NamedQueries({
	@NamedQuery(name = "Gene.findByName", 
    		query = "SELECT g FROM Gene g " +
    				"WHERE g.name = :name"),
	@NamedQuery(name = "Gene.findAllBySpecies", 
    		query = "SELECT g FROM Gene g JOIN g.sequence s JOIN s.chromosome c " +
    				"JOIN c.species sp " +
    				"WHERE sp.id.species = :species " +
    				"AND sp.id.subspecies = :subspecies " +
    				"AND sp.id.genus = :genus "),
	@NamedQuery(name = "Gene.findAllByChromosome", 
    		query = "SELECT g FROM Gene g JOIN g.sequence s JOIN s.chromosome c " +
    				"WHERE c.id = :chrId")
})
public class Gene extends IntervalFeature implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Calendar dateCreated;
	private Calendar dateModified;
	private BigDecimal gcContent;
	private String name;
	private Sequence sequence;
	private String strandness;
	private String type;
	private int x;
	private int y;
	private List<Mrna> mrnas = new ArrayList<Mrna>();

    public Gene() {
    }

    @Id
	@SequenceGenerator(name="GENES_ID_GENERATOR", sequenceName="annotation.genes_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GENES_ID_GENERATOR")
	@Column(insertable=false, updatable=false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	
	//bi-directional many-to-one association to Chromosome
    @ManyToOne
	public Sequence getSequence() {
		return this.sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
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


	//bi-directional many-to-one association to Exon
	@OneToMany(mappedBy="gene", cascade={CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval=true)
	public List<Mrna> getMrnas() {
		return this.mrnas;
	}

	public void setMrnas(List<Mrna> mrnas) {
		this.mrnas = mrnas;
	}
	
	@Transient
	public String toGff3Line() {
		return this.buildGff3Annotation().toString();
	}
	
	@Transient
	public String toGff3WithPseudomolCoordinatesLine(String chrName, Long offset) {
		Gff3Line gff3 = buildGff3Annotation();
		gff3.toPseudomolCoords(chrName, Integer.parseInt(offset + ""));
		return gff3.toString();
	}
	
	private Gff3Line buildGff3Annotation() {
		Gff3Line gff3 = new Gff3Line();
		gff3.setSeqId(sequence.humanName());
		gff3.setSource("agi_genomes_db");
		gff3.setType("gene");
		gff3.setStart(x);
		gff3.setEnd(y);
		gff3.setScore(".");
		gff3.setStrand(strandness);
		gff3.setPhase(".");
		gff3.setAttribId(name + "");
		return gff3;
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