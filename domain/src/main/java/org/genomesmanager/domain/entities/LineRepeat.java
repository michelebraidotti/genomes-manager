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
 * The persistent class for the line_repeats database table.
 * 
 */
@Entity
@Table(name="line_repeats", schema="annotation")
@DiscriminatorValue(value="LINE")
@PrimaryKeyJoinColumn(name = "repeat_id")
@NamedQueries({
    @NamedQuery(name = "LineRepeat.findAllBySequence", query = "SELECT l FROM LineRepeat l " +
    		"WHERE l.sequence.id = :seqId ORDER BY l.x"),
	@NamedQuery(name = "LineRepeat.findAllByChromosome", 
    		query = "SELECT r FROM LineRepeat r JOIN r.sequence s JOIN s.chromosome c " +
    				"WHERE c.id = :chrId"),
	@NamedQuery(name = "LineRepeat.count", 
    		query = "SELECT count(r.id) FROM LineRepeat r"),
	@NamedQuery(name = "LineRepeat.countNucl", 
    		query = "SELECT sum(r.y-r.x+1) FROM LineRepeat r")
})
public class LineRepeat extends Repeat implements Serializable {
	private static final long serialVersionUID = 1L;
	private String overallStructureDesc;
	private Boolean polyA;
	private Boolean rtPresence;
	private String rtSequence;

    public LineRepeat() {
    }


	@Column(name="overall_structure_desc")
	public String getOverallStructureDesc() {
		return this.overallStructureDesc;
	}

	public void setOverallStructureDesc(String overallStructureDesc) {
		this.overallStructureDesc = overallStructureDesc;
	}


	@Column(name="poly_a")
	public Boolean getPolyA() {
		return this.polyA;
	}

	public void setPolyA(Boolean polyA) {
		this.polyA = polyA;
	}


	@Column(name="rt_presence")
	public Boolean getRtPresence() {
		return this.rtPresence;
	}

	public void setRtPresence(Boolean rtPresence) {
		this.rtPresence = rtPresence;
	}


	@Column(name="rt_sequence")
	public String getRtSequence() {
		return this.rtSequence;
	}

	public void setRtSequence(String rtSequence) {
		this.rtSequence = rtSequence;
	}
	
	@Transient
	@Override
	public String getType() {
		return RepeatsOrder.LINE.getLabel();
	}
	
	@Transient
	@Override
	public String getGff3Type()  {
		return RepeatsGff3Order.LINE.getLabel();
	}
	
	@Transient
	@Override
	public String getStructuralDesc() {
		return overallStructureDesc;
	}
	
	@Transient
	@Override
	public void setAttributes(Properties attributes) throws RepeatException {
		super.setAttributes(attributes);
		if ( attributes.getProperty("poly_a") != null ) {
			if ( attributes.getProperty("poly_a").toLowerCase().equals("true")) {
				polyA = true;
			}
		}
		if ( attributes.getProperty("rt_presence") != null ) {
			if ( attributes.getProperty("rt_presence").toLowerCase().equals("true")) {
				rtPresence = true;
			}
		}
		if ( attributes.getProperty("rt_sequence") != null ) {
			rtSequence = attributes.getProperty("rt_sequence");
		}
		if ( attributes.getProperty("overall_structure_desc") != null ) {
			overallStructureDesc = attributes.getProperty("overall_structure_desc");
		}
	}
	
	@Transient
	@Override
	public String extraAnnot() {
		String annot = super.extraAnnot();
		if ( polyA != null && polyA) {
			annot += ";poly_a=true";
		}
		if ( rtPresence != null && rtPresence) {
			annot += ";rt_presence=true";
		}
		if  (rtSequence != null &&  ! rtSequence.equals("") ) {
			annot += ";rt_sequence=" + rtSequence;
		}
		if  (overallStructureDesc != null &&  ! overallStructureDesc.equals("") ) {
			annot += ";overall_structure_desc=" + overallStructureDesc;
		}
		return annot;
	}
	
}