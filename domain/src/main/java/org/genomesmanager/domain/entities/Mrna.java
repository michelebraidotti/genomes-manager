package org.genomesmanager.domain.entities;

import java.io.Serializable;
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
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.genomesmanager.formats.Gff3Line;

/**
 * The persistent class for the mrnas database table.
 * 
 */
@Entity
@Table(name = "mrnas", schema = "annotation")
@NamedQueries({ @NamedQuery(name = "Mrna.findByName", query = "SELECT m FROM Mrna m "
		+ "WHERE m.name = :name") })
public class Mrna extends IntervalFeature implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String strandness;
	private int x;
	private int y;
	private Gene gene;
	private List<Exon> exons = new ArrayList<Exon>();
	private String description;
	private Calendar dateCreated;
	private Calendar dateModified;

	public Mrna() {
	}

	@Id
	@SequenceGenerator(name = "MRNAS_ID_GENERATOR", sequenceName = "annotation.mrnas_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MRNAS_ID_GENERATOR")
	@Column(insertable = false, updatable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "date_created")
	public Calendar getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Column(name = "date_modified")
	public Calendar getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Calendar dateModified) {
		this.dateModified = dateModified;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	// bi-directional many-to-one association to Gene
	@ManyToOne
	public Gene getGene() {
		return this.gene;
	}

	public void setGene(Gene gene) {
		this.gene = gene;
	}

	// bi-directional many-to-one association to Exon
	@OneToMany(mappedBy = "mrna", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, orphanRemoval = true)
	public List<Exon> getExons() {
		return this.exons;
	}

	public void setExons(List<Exon> exons) {
		this.exons = exons;
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

	@Transient
	public String extraAnnot() {
		String annot = "";
		if (description != null && !description.isEmpty()) {
			annot += ";Description=" + description;
		}
		return annot;
	}

	private Gff3Line buildGff3Annotation() {
		Gff3Line gff3 = new Gff3Line();
		gff3.setSeqId(gene.getSequence().humanName());
		gff3.setSource("agi_genomes_db");
		gff3.setType("mRNA");
		gff3.setStart(x);
		gff3.setEnd(y);
		gff3.setScore(".");
		gff3.setStrand(strandness);
		gff3.setPhase(".");
		gff3.setAttribId(name + "");
		gff3.setAttribParent(gene.getName());
		// if ( description != null && ! description.isEmpty() ) {
		// Properties attrs = new Properties();
		// attrs.setProperty("Description", description);
		// gff3.setAttributes(new Properties());
		// }
		return gff3;
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
		if (obj instanceof Mrna == false) {
			return false;
		}

		if (this == obj) {
			return true;
		}
		Mrna other = (Mrna) obj;
		return new EqualsBuilder().append(this.getName(), other.getName())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getName()).hashCode();
	}

}