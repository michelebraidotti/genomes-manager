package org.genomesmanager.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * The persistent class for the snps database table.
 * 
 */
@Entity
@Table(name = "snps", schema = "annotation")
@NamedQueries({
		@NamedQuery(name = "Snp.findAllBySpecies", query = "SELECT s FROM Snp s JOIN s.sequence seq JOIN seq.chromosome c "
				+ "JOIN c.species sp " + "WHERE sp.id.species = :speciesId "),
		@NamedQuery(name = "Snp.findAllByChromosome", query = "SELECT s FROM Snp s JOIN s.sequence seq JOIN seq.chromosome c "
				+ "WHERE c.id = :chrId") })
public class Snp extends PointFeature implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int pos;
	private String reference;
	private String reseq;
	private Sequence sequence;
	private Individual individual;

	public Snp() {
	}

	@Id
	@SequenceGenerator(name = "SNPS_ID_GENERATOR", sequenceName = "snps_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SNPS_ID_GENERATOR")
	@Column(insertable = false, updatable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPos() {
		return this.pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	@Column(name = "reference", columnDefinition = "bpchar(1)")
	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Column(name = "reseq", columnDefinition = "bpchar(1)")
	public String getReseq() {
		return this.reseq;
	}

	public void setReseq(String reseq) {
		this.reseq = reseq;
	}

	@ManyToOne
	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	@ManyToOne
	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Snp == false) {
			return false;
		}

		if (this == obj) {
			return true;
		}
		Snp other = (Snp) obj;
		return new EqualsBuilder().append(this.getPos(), other.getPos())
				.append(this.getSequence(), other.getSequence()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getPos())
				.append(this.getSequence()).hashCode();
	}
}