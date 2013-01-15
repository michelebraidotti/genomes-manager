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
 * The persistent class for the mite_repeats database table.
 * 
 */
@Entity
@Table(name="mite_repeats", schema="annotation")
@DiscriminatorValue(value="MITE")
@PrimaryKeyJoinColumn(name = "repeat_id")
@NamedQueries({
    @NamedQuery(name = "MiteRepeat.findAllBySequence", query = "SELECT mite FROM MiteRepeat mite " +
    		"WHERE mite.sequence.id = :seqId ORDER BY mite.x"),
	@NamedQuery(name = "MiteRepeat.findAllByChromosome", 
    		query = "SELECT r FROM MiteRepeat r JOIN r.sequence s JOIN s.chromosome c " +
    				"WHERE c.id = :chrId"),
	@NamedQuery(name = "MiteRepeat.count", 
    		query = "SELECT count(r.id) FROM MiteRepeat r"),
	@NamedQuery(name = "MiteRepeat.countNucl", 
    		query = "SELECT sum(r.y-r.x+1) FROM MiteRepeat r")
})
public class MiteRepeat extends Repeat implements Serializable {
	private static final long serialVersionUID = 1L;
	private int tirX;
	private int tirY;
	private String tsdSeq;

    public MiteRepeat() {
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


	@Column(name="tsd_seq")
	public String getTsdSeq() {
		return this.tsdSeq;
	}

	public void setTsdSeq(String tsdSeq) {
		this.tsdSeq = tsdSeq;
	}
	
	@Transient
	@Override
	public String getType() {
		return RepeatsOrder.MITE.getLabel();
	}
	
	@Transient
	@Override
	public String getGff3Type()  {
		return RepeatsGff3Order.MITE.getLabel();
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
		if ( attributes.getProperty("tsd_seq") != null ) {
			tsdSeq = attributes.getProperty("tsd_seq");
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
		if  (tsdSeq != null &&  ! tsdSeq.equals("") ) {
			annot += ";tsd_seq=" + tsdSeq;
		}
		return annot;
	}
	
}