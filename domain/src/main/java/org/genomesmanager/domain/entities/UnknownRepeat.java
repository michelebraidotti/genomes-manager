package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.Properties;

import javax.persistence.*;

import org.genomesmanager.domain.dtos.RepeatsGff3Order;


/**
 * The persistent class for the sine_repeats database table.
 * 
 */
@Entity
@Table(name="unknown_repeats", schema="annotation")
@DiscriminatorValue(value="UNKNOWN")
@PrimaryKeyJoinColumn(name = "repeat_id")
@NamedQueries({
    @NamedQuery(name = "UnknownRepeat.findAllBySequence", query = "SELECT u FROM UnknownRepeat u " +
    		"WHERE u.sequence.id = :seqId ORDER BY u.x"),
	@NamedQuery(name = "UnknownRepeat.findAllByChromosome", 
    		query = "SELECT r FROM UnknownRepeat r JOIN r.sequence s JOIN s.chromosome c " +
    				"WHERE c.id = :chrId"),
	@NamedQuery(name = "UnknownRepeat.count", 
    		query = "SELECT count(r.id) FROM UnknownRepeat r"),
})
public class UnknownRepeat extends Repeat implements Serializable {
	private static final long serialVersionUID = 1L;
	private String description;

    public UnknownRepeat() {
    }


	@Column(name="description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Transient
	@Override
	public String getType() {
		return RepeatsOrder.UNKN.getLabel();
	}
	
	@Transient
	@Override
	public String getGff3Type() {
		return RepeatsGff3Order.UNKN.getLabel();
	}
	
	@Transient
	@Override
	public void setAttributes(Properties attributes) throws RepeatException {
		super.setAttributes(attributes);
		if ( attributes.getProperty("description") != null ) {
			description = attributes.getProperty("description");
		}
	}
	
	@Transient
	@Override
	public String extraAnnot() {
		String annot = super.extraAnnot();
		if  (description != null &&  ! description.equals("") ) {
			annot += ";description=" + description;
		}
		return annot;
	}
	
}