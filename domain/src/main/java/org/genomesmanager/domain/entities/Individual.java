package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * The persistent class for the individuals database table.
 * 
 */
@Entity
@Table(name = "individuals", schema = "sequence")
public class Individual implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String description;
	private Variety variety;
	private List<Snp> snps;

	public Individual() {
	}

	@Id
	@SequenceGenerator(name = "INDIVIDUALS_ID_GENERATOR", sequenceName = "individuals_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INDIVIDUALS_ID_GENERATOR")
	@Column(insertable = false, updatable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "variety_id")
	public Variety getVariety() {
		return this.variety;
	}

	public void setVariety(Variety variety) {
		this.variety = variety;
	}

	@OneToMany(mappedBy = "individual")
	public List<Snp> getSnps() {
		return snps;
	}

	public void setSnps(List<Snp> snps) {
		this.snps = snps;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Individual == false) {
			return false;
		}

		if (this == obj) {
			return true;
		}
		Individual other = (Individual) obj;
		return new EqualsBuilder().append(this.getDescription(),
				other.getDescription()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getDescription()).hashCode();
	}

}