package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.genomesmanager.domain.dtos.RepeatsGff3Order;


/**
 * The persistent class for the sine_repeats database table.
 * 
 */
@Entity
@Table(name="sine_repeats", schema="annotation")
@DiscriminatorValue(value="SINE")
@PrimaryKeyJoinColumn(name = "repeat_id")
@NamedQueries({
    @NamedQuery(name = "SineRepeat.findAllBySequence", query = "SELECT s FROM SineRepeat s " +
    		"WHERE s.sequence.id = :seqId ORDER BY s.x"),
	@NamedQuery(name = "SineRepeat.findAllByChromosome", 
    		query = "SELECT r FROM SineRepeat r JOIN r.sequence s JOIN s.chromosome c " +
    				"WHERE c.id = :chrId"),
	@NamedQuery(name = "SineRepeat.count", 
    		query = "SELECT count(r.id) FROM SineRepeat r"),
	@NamedQuery(name = "SineRepeat.countNucl", 
    		query = "SELECT sum(r.y-r.x+1) FROM SineRepeat r")
})
public class SineRepeat extends Repeat implements Serializable {
	private static final long serialVersionUID = 1L;
	private String overallStructureDesc;

    public SineRepeat() {
    }


	@Column(name="overall_structure_desc")
	public String getOverallStructureDesc() {
		return this.overallStructureDesc;
	}

	public void setOverallStructureDesc(String overallStructureDesc) {
		this.overallStructureDesc = overallStructureDesc;
	}
	
	@Transient
	@Override
	public String getStructuralDesc() {
		return overallStructureDesc;
	}
	
	@Transient
	@Override
	public String getType() {
		return RepeatsOrder.SINE.getLabel();
	}
	
	@Transient
	@Override
	public String getGff3Type()  {
		return RepeatsGff3Order.SINE.getLabel();
	}
	
	@Transient
	@Override
	public void setAttributes(Properties attributes) throws RepeatException {
		super.setAttributes(attributes);
		if ( attributes.getProperty("overall_structure_desc") != null ) {
			overallStructureDesc = attributes.getProperty("overall_structure_desc");
		}
	}
	
	@Transient
	@Override
	public String extraAnnot() {
		String annot = super.extraAnnot();
		if  (overallStructureDesc != null &&  ! overallStructureDesc.equals("") ) {
			annot += ";overall_structure_desc=" + overallStructureDesc;
		}
		return annot;
	}
	
}