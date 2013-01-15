package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.genomesmanager.domain.dtos.RepeatsGff3Order;


/**
 * The persistent class for the dna_te_repeats database table.
 * 
 */
@Entity
@Table(name="dna_te_repeats", schema="annotation")
@DiscriminatorValue(value="DNA_TE")
@PrimaryKeyJoinColumn(name = "repeat_id")
@NamedQueries({
    @NamedQuery(name = "DnaTeRepeat.findAllBySequence", 
    		query = "SELECT d FROM DnaTeRepeat d " +
    			"WHERE d.sequence.id = :seqId ORDER BY d.x"),
    @NamedQuery(name = "DnaTeRepeat.findAllByChromosome", 
    		query = "SELECT r FROM DnaTeRepeat r JOIN r.sequence s JOIN s.chromosome c " +
    				"WHERE c.id = :chrId"),
	@NamedQuery(name = "DnaTeRepeat.count", 
    		query = "SELECT count(r.id) FROM DnaTeRepeat r"),
	@NamedQuery(name = "DnaTeRepeat.countNucl", 
    		query = "SELECT sum(r.y-r.x+1) FROM DnaTeRepeat r")
})
public class DnaTeRepeat extends Repeat implements Serializable {
	private static final long serialVersionUID = 1L;
	private int tirX;
	private int tirY;
	private Boolean transPresence;
	private String transSequence;
	private String tsdSequence;

    public DnaTeRepeat() {
    }


	@Column(name="tir_x")
	public int getTirX() {
		return this.tirX;
	}

	public void setTirX(int tirX) {
		this.tirX = tirX;
	}


	@Column(name="tir_y")
	public int getTirY() {
		return this.tirY;
	}

	public void setTirY(int tirY) {
		this.tirY = tirY;
	}


	@Column(name="trans_presence")
	public Boolean getTransPresence() {
		return this.transPresence;
	}

	public void setTransPresence(Boolean transPresence) {
		this.transPresence = transPresence;
	}


    @Lob()
	@Column(name="trans_sequence")
	public String getTransSequence() {
		return this.transSequence;
	}

	public void setTransSequence(String transSequence) {
		this.transSequence = transSequence;
	}
	
	
	@Column(name="tsd_sequence")
	public String getTsdSequence() {
		return this.tsdSequence;
	}

	public void setTsdSequence(String tsdSequence) {
		this.tsdSequence = tsdSequence;
	}
	
	
	@Transient
	@Override
	public String getType() {
		return RepeatsOrder.DNATE.getLabel();
	}
	
	
	@Transient
	@Override
	public String getGff3Type()  {
		return RepeatsGff3Order.DNATE.getLabel();
	}
	
	
	@Transient
	public Boolean isComplete() {
		if ( this.tirX != 0 &&  this.tirY != 0 ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transient
	@Override
	public void validate() throws OutOfBoundsException, IntervalFeatureException, RepeatException {
		super.validate();
		if ( tirX != 0 && ! isInternal(tirX) ) {
			throw new OutOfBoundsException("Tir start: " + tirX );
		}
		if ( tirY != 0 && ! isInternal(tirY) ) {
			throw new OutOfBoundsException("Tir end: " + tirY );
		}
	}
	
	@Transient
	@Override
	public void setAttributes(Properties attributes) throws RepeatException {
		super.setAttributes(attributes);
		if ( attributes.getProperty("tir_x") != null ) {
			this.setTirX(Integer.parseInt(attributes.getProperty("tir_x")));
		}
		if ( attributes.getProperty("tir_y") != null ) {
			this.setTirY(Integer.parseInt(attributes.getProperty("tir_y")));
		}
		if ( attributes.getProperty("trans_presence") != null ) {
			if ( attributes.getProperty("trans_presence").toLowerCase().equals("true")) {
				transPresence = true;
			}
		}
		if ( attributes.getProperty("trans_sequence") != null ) {
			transSequence = attributes.getProperty("trans_sequence");
		}
		if ( attributes.getProperty("tsd_sequence") != null ) {
			tsdSequence = attributes.getProperty("tsd_sequence");
		}
	}
	
	@Transient
	@Override
	public String extraAnnot() {
		String annot = super.extraAnnot();
		if ( tirX != 0) {
			annot += ";tir_x=" + tirX;
		}
		if ( tirY != 0) {
			annot += ";tir_y=" + tirY;
		}
		if ( transPresence != null && transPresence) {
			annot += ";trans_presence=true";
		}
		if  (transSequence != null &&  ! transSequence.equals("") ) {
			annot += ";trans_sequence=" + transSequence;
		}
		if  (tsdSequence != null &&  ! tsdSequence.equals("") ) {
			annot += ";tsd_sequence=" + tsdSequence;
		}
		return annot;
	}
	
}