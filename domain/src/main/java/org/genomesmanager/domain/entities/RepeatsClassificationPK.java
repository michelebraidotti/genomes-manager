package org.genomesmanager.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * The primary key class for the repeats_classification database table.
 * 
 */
@Embeddable
public class RepeatsClassificationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String repClass;
	private String subclass;
	private String order;
	private String superfamily;
	private String family;

    public RepeatsClassificationPK() {
    }

	@Column(name="rclass")
	public String getRepClass() {
		return this.repClass;
	}
	public void setRepClass(String repClass) {
		this.repClass = repClass;
	}

	public String getSubclass() {
		return this.subclass;
	}
	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	@Column(name="rorder")
	public String getOrder() {
		return this.order;
	}
	public void setOrder(String order) {
		this.order = order;
	}

	public String getSuperfamily() {
		return this.superfamily;
	}
	public void setSuperfamily(String superfamily) {
		this.superfamily = superfamily;
	}

	public String getFamily() {
		return this.family;
	}
	public void setFamily(String family) {
		this.family = family;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RepeatsClassificationPK)) {
			return false;
		}
		RepeatsClassificationPK castOther = (RepeatsClassificationPK)other;
		return 
			this.repClass.equals(castOther.repClass)
			&& this.subclass.equals(castOther.subclass)
			&& this.order.equals(castOther.order)
			&& this.superfamily.equals(castOther.superfamily)
			&& this.family.equals(castOther.family);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.repClass.hashCode();
		hash = hash * prime + this.subclass.hashCode();
		hash = hash * prime + this.order.hashCode();
		hash = hash * prime + this.superfamily.hashCode();
		hash = hash * prime + this.family.hashCode();
		
		return hash;
    }
	
	@Transient
	public String toString() {
		return repClass + " " + subclass + " " + 
		order + " " + superfamily + " " + family;
	}
	
}