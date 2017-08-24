package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.genomesmanager.formats.Gff3Line;

/**
 * The persistent class for the exons database table.
 * 
 */
@Entity
@Table(name = "exons", schema = "annotation")
@NamedQueries({ @NamedQuery(name = "Exon.findByName", query = "SELECT e FROM Exon e "
		+ "WHERE e.name = :name") })
public class Exon extends IntervalFeature implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String strandness;
	private int x;
	private int y;
	private Mrna mrna;
	private Calendar dateCreated;
	private Calendar dateModified;

	public Exon() {
	}

	@Id
	@SequenceGenerator(name = "EXONS_ID_GENERATOR", sequenceName = "annotation.exons_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXONS_ID_GENERATOR")
	@Column(insertable = false, updatable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	public Calendar getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified")
	public Calendar getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Calendar dateModified) {
		this.dateModified = dateModified;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "strandness", columnDefinition = "bpchar(1)")
	public String getStrandness() {
		return this.strandness;
	}

	public void setStrandness(String strandness) {
		this.strandness = strandness;
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

	// bi-directional many-to-one association to Mrna
	@ManyToOne
	public Mrna getMrna() {
		return this.mrna;
	}

	public void setMrna(Mrna mrna) {
		this.mrna = mrna;
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
		gff3.setSeqId(mrna.getGene().getSequence().humanName());
		gff3.setSource("agi_genomes_db");
		gff3.setType("exon");
		gff3.setStart(x);
		gff3.setEnd(y);
		gff3.setScore(".");
		gff3.setStrand(strandness);
		gff3.setPhase(".");
		gff3.setAttribId(name + "");
		gff3.setAttribParent(mrna.getName());
		return gff3;
	}

	@Transient
	public String extraAnnot() {
		String annot = "";
		return annot;
	}

	@PrePersist
	public void setCreateDefaults() {
		if (this.dateCreated == null) {
			this.dateCreated = Calendar.getInstance();
			this.dateModified = Calendar.getInstance();
		}
	}

	@PreUpdate
	public void setUpdateDefaults() {
		this.dateModified = Calendar.getInstance();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Exon == false) {
			return false;
		}

		if (this == obj) {
			return true;
		}
		Exon other = (Exon) obj;
		return new EqualsBuilder().append(this.getName(), other.getName())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getName()).hashCode();
	}

}