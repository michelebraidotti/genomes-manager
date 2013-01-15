package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the repeats_classification database table.
 * 
 */
@Entity
@Table(name="repeats_classification", schema="annotation")
@NamedQueries({
    @NamedQuery(name = "RepeatsClassification.findAll", query = "SELECT rc FROM RepeatsClassification rc " +
    		"ORDER BY rc.id.repClass, rc.id.subclass, rc.id.order, rc.id.superfamily, rc.id.family"),
    @NamedQuery(name = "RepeatsClassification.allClassSubClassOrder", 
    		query = "SELECT DISTINCT rc.id.repClass, rc.id.subclass, rc.id.order " +
    		"FROM RepeatsClassification rc "),
	@NamedQuery(name = "RepeatsClassification.allClasses", 
    		query = "SELECT DISTINCT rc.id.repClass " +
    		"FROM RepeatsClassification rc"),
    @NamedQuery(name = "RepeatsClassification.allSubclasses", 
    		query = "SELECT DISTINCT rc.id.subclass " +
    		"FROM RepeatsClassification rc WHERE rc.id.repClass = :repClass"),
	@NamedQuery(name = "RepeatsClassification.allOrdersByClassAndSubClass", 
    		query = "SELECT DISTINCT rc.id.order " +
    		"FROM RepeatsClassification rc WHERE rc.id.repClass = :repClass AND " +
    		"rc.id.subclass = :subclass"),
    @NamedQuery(name = "RepeatsClassification.allOrders", 
    		query = "SELECT DISTINCT rc.id.order " +
    	    "FROM RepeatsClassification rc"),
    @NamedQuery(name = "RepeatsClassification.allSuperfamilies", 
    		query = "SELECT DISTINCT rc.id.superfamily " +
    		"FROM RepeatsClassification rc WHERE rc.id.repClass = :repClass AND " +
    		"rc.id.subclass = :subclass AND " +
    		"rc.id.order = :order"),
    @NamedQuery(name = "RepeatsClassification.allFamilies", 
    		query = "SELECT DISTINCT rc.id.family " +
    		"FROM RepeatsClassification rc WHERE rc.id.repClass = :repClass AND " +
    		"rc.id.subclass = :subclass AND " +
    		"rc.id.order = :order AND " +
    		"rc.id.superfamily = :superfamily"),
})
public class RepeatsClassification implements Serializable {
	private static final long serialVersionUID = 1L;
	private RepeatsClassificationPK id;
	private List<Repeat> repeats;
	public static String SEPARATOR = ", ";

    public RepeatsClassification() {
    }


	@EmbeddedId
	public RepeatsClassificationPK getId() {
		return this.id;
	}

	public void setId(RepeatsClassificationPK id) {
		this.id = id;
	}
	

	//bi-directional many-to-one association to Repeat
	@OneToMany(mappedBy="repeatsClassification")
	public List<Repeat> getRepeats() {
		return this.repeats;
	}

	public void setRepeats(List<Repeat> repeats) {
		this.repeats = repeats;
	}
	
	@Transient
	public String toDescString() {
		return id.getRepClass() + SEPARATOR + id.getSubclass() + SEPARATOR + 
		id.getOrder() + SEPARATOR + id.getSuperfamily() + SEPARATOR + id.getFamily();
	}
	
	@Override
	public String toString() {
		return toDescString();
	}
	
}