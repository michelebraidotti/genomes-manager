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
 * The persistent class for the helitron_repeats database table.
 * 
 */
@Entity
@Table(name="helitron_repeats", schema="annotation")
@DiscriminatorValue(value="Helitron")
@PrimaryKeyJoinColumn(name = "repeat_id")
@NamedQueries({
    @NamedQuery(name = "HelitronRepeat.findAllBySequence", query = "SELECT h FROM HelitronRepeat h " +
    		"WHERE h.sequence.id = :seqId ORDER BY h.x"),
	@NamedQuery(name = "HelitronRepeat.findAllByChromosome", 
    		query = "SELECT r FROM HelitronRepeat r JOIN r.sequence s JOIN s.chromosome c " +
    				"WHERE c.id = :chrId"),
	@NamedQuery(name = "HelitronRepeat.count", 
			query = "SELECT r.end3, r.end5, count(r.id) FROM HelitronRepeat r " +
					"GROUP BY  r.end3, r.end5"),
	@NamedQuery(name = "HelitronRepeat.countNulc", 
			query = "SELECT r.end3, r.end5, sum(r.y-r.x+1) FROM HelitronRepeat r " +
					"GROUP BY  r.end3, r.end5"),
	@NamedQuery(name = "HelitronRepeat.countPotAuton", 
			query = "SELECT count(r.id) FROM HelitronRepeat r " +
					"WHERE r.isAutonomus = true"),
	@NamedQuery(name = "HelitronRepeat.countPotCdsCount", 
			query = "SELECT count(r.id) FROM HelitronRepeat r " +
					"WHERE r.potentialCdsCount > 0 AND r.potentialCdsCount IS NOT NULL"),
	@NamedQuery(name = "HelitronRepeat.countOrfCount", 
			query = "SELECT count(r.id) FROM HelitronRepeat r " +
					"WHERE r.orfGreaterThan50aa > 0")
})
public class HelitronRepeat extends Repeat implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean isAutonomus;
	private int potentialCdsCount;
	private int orfGreaterThan50aa;
	private String end3;
	private String end5;
	private Boolean isPresentInSativa = false;

    public HelitronRepeat() {
    }


	@Column(name="is_potentially_autonomus")
	public Boolean getIsAutonomus() {
		return this.isAutonomus;
	}

	public void setIsAutonomus(Boolean isAutonomus) {
		this.isAutonomus = isAutonomus;
	}


	@Column(name="potential_cds_count")
	public int getPotentialCdsCount() {
		return this.potentialCdsCount;
	}

	public void setPotentialCdsCount(int potentialCdsCount) {
		this.potentialCdsCount = potentialCdsCount;
	}
	
	@Column(name="orf_greater_than_50aa")
	public int getOrfGreaterThan50aa() {
		return this.orfGreaterThan50aa;
	}

	public void setOrfGreaterThan50aa(int orfGreaterThan50aa) {
		this.orfGreaterThan50aa = orfGreaterThan50aa;
	}
	
	@Column(name="end_3")
	public String getEnd3() {
		return end3;
	}

	public void setEnd3(String end3) {
		this.end3 = end3;
	}

	@Column(name="end_5")
	public String getEnd5() {
		return end5;
	}
	
	public void setEnd5(String end5) {
		this.end5 = end5;
	}	
	
	@Column(name="is_present_in_sativa")
	public Boolean getIsPresentInSativa() {
		return isPresentInSativa;
	}

	public void setIsPresentInSativa(Boolean isPresentInSativa) {
		this.isPresentInSativa = isPresentInSativa;
	}


	@Transient
	@Override
	public String getType() {
		return RepeatsOrder.HEL.getLabel();
	}
	
	@Transient
	@Override
	public String getGff3Type()  {
		return RepeatsGff3Order.HEL.getLabel();
	}
	
	@Transient
	@Override
	public String getStructuralDesc() {
		if (( end5 != null && ! end5.equals("") ) && 
				( end3 != null && ! end3.equals("") )){
			return "Complete";
		}
		else if (end5 != null && ! end5.equals("")) {
			return "Hel5"; 
		}
		else if (end3 != null && ! end3.equals("")) {
			return "Hel3"; 
		}
		else {
			return "";
		}
	}
	
	
	@Transient
	@Override
	public void setAttributes(Properties attributes) throws RepeatException {
		super.setAttributes(attributes);
		if ( attributes.getProperty("potential_cds_count") != null ) {
			potentialCdsCount = Integer.parseInt(attributes.getProperty("potential_cds_count"));
		}
		if ( attributes.getProperty("orf_greater_than_50aa") != null ) {
			orfGreaterThan50aa = Integer.parseInt(attributes.getProperty("orf_greater_than_50aa"));
		}
		if ( attributes.getProperty("is_potentially_autonomus") != null ) {
			if ( attributes.getProperty("is_potentially_autonomus").toLowerCase().equals("true")) {
				isAutonomus = true;
			}
		}
		if ( attributes.getProperty("end_3") != null ) {
			end3 = attributes.getProperty("end_3");
		}
		if ( attributes.getProperty("end_5") != null ) {
			end5 = attributes.getProperty("end_5");
		}
	}
	
	@Transient
	@Override
	public String extraAnnot() {
		String annot = super.extraAnnot();
		if ( potentialCdsCount != 0) {
			annot += ";potential_cds_count=" + potentialCdsCount;
		}
		if ( orfGreaterThan50aa != 0) {
			annot += ";orf_greater_than_50aa=" + orfGreaterThan50aa;
		}
		if ( isAutonomus != null && isAutonomus ) {
			annot += ";is_potentially_autonomus=true";
		}
		if  (end3 != null &&  ! end3.equals("") ) {
			annot += ";end_3=" + end3;
		}
		if  (end5 != null &&  ! end5.equals("") ) {
			annot += ";end_5=" + end5;
		}
		return annot;
	}
	
	@Transient
	@Override
	public void validate() throws OutOfBoundsException, IntervalFeatureException, RepeatException {
		super.validate();
		Boolean completeAttrs = false;
		if ( potentialCdsCount != 0 || orfGreaterThan50aa != 0  || 
				(isAutonomus != null && isAutonomus) ) {
			completeAttrs = true;
		}
		if ( completeAttrs && (end3 == null || end3.equals("")) && (end5 != null &&  ! end5.equals("")) ) {
			throw new RepeatException(" Incomplete helitron cannot be autonomous or " +
					"bearing CDS or ORF. Only End3 field was specified.");
		}
		if ( completeAttrs && (end3 != null && !end3.equals("")) && (end5 == null || end5.equals("")) ) {
			throw new RepeatException(" Incomplete helitron cannot be autonomous or " +
					"bearing CDS or ORF. Only End5 field was specified.");
		}
	}
}