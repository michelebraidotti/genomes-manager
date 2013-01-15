package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
 * The persistent class for the ltr_repeats database table.
 * 
 */
@Entity
@Table(name="ltr_repeats", schema="annotation")
@DiscriminatorValue(value="LTR")
@PrimaryKeyJoinColumn(name = "repeat_id")
@NamedQueries({
    @NamedQuery(name = "LtrRepeat.findAllBySequence", query = "SELECT ltr FROM LtrRepeat ltr " +
    		"WHERE ltr.sequence.id = :seqId ORDER BY ltr.x"),
    @NamedQuery(name = "LtrRepeat.findInRange", 
    		query = "SELECT ltr FROM LtrRepeat ltr " +
    				"WHERE ltr.sequence.id = :seqId AND ltr.x >= :start AND ltr.y <= :end " +
    				"ORDER BY ltr.x"),
	@NamedQuery(name = "LtrRepeat.findAllByChromosome", 
    		query = "SELECT r FROM LtrRepeat r JOIN r.sequence s JOIN s.chromosome c " +
    				"WHERE c.id = :chrId"),
	@NamedQuery(name = "LtrRepeat.count", 
    		query = "SELECT count(r.id) FROM LtrRepeat r"),
	@NamedQuery(name = "LtrRepeat.countNucl", 
    		query = "SELECT sum(r.y-r.x+1) FROM LtrRepeat r"),
	@NamedQuery(name = "LtrRepeat.countComplete", 
    		query = "SELECT count(r.id) FROM LtrRepeat r WHERE r.isComplete = true"),
	@NamedQuery(name = "LtrRepeat.countSolo", 
			query = "SELECT count(r.id) FROM LtrRepeat r WHERE r.isSolo = true"),
	@NamedQuery(name = "LtrRepeat.countTruncated", 
			query = "SELECT count(r.id) FROM LtrRepeat r WHERE " +
					"(r.isSolo = false OR r.isSolo IS NULL) AND " + 
					"(r.isComplete = false OR r.isComplete IS NULL)"),
	@NamedQuery(name = "LtrRepeat.presenceInSativaIsComplete", 
			query = "SELECT r.presenceInSativa, count(r.id) FROM LtrRepeat r " +
					"GROUP BY r.presenceInSativa, r.isComplete " +
					"HAVING r.isComplete = true "), 
	@NamedQuery(name = "LtrRepeat.presenceInSativaAndIsSolo", 
			query = "SELECT r.presenceInSativa, count(r.id) FROM LtrRepeat r " +
					"GROUP BY r.presenceInSativa, r.isSolo " +
					"HAVING r.isSolo = true ")
})
public class LtrRepeat extends Repeat implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigDecimal gcContent3;
	private BigDecimal gcContent5;
	private Boolean intPresence;
	private String intSequence;
	private int intStopCodonsCount;
	private int ltr3Length;
	private int ltr5Length;
	private Boolean isComplete;
	private Boolean isSolo;
	private BigDecimal ltrComparisonInsertionTime;
	private int ltrComparisonMutationAt;
	private int ltrComparisonMutationCa;
	private int ltrComparisonMutationCg;
	private int ltrComparisonMutationCt;
	private BigDecimal ltrComparisonNucDistance;
	private BigDecimal ltrComparisonSimilarity;
	private int pbsX;
	private int pbsY;
	private int pptX;
	private int pptY;
	private Boolean rtPresence;
	private String rtSequence;
	private int rtStopCodonsCount;
	private String tsdSequence;
	private String presenceInSativa;
	
    public LtrRepeat() {
    }

	@Column(name="gc_content_3")
	public BigDecimal getGcContent3() {
		return this.gcContent3;
	}

	public void setGcContent3(BigDecimal gcContent3) {
		this.gcContent3 = gcContent3;
	}


	@Column(name="gc_content_5")
	public BigDecimal getGcContent5() {
		return this.gcContent5;
	}

	public void setGcContent5(BigDecimal gcContent5) {
		this.gcContent5 = gcContent5;
	}


	@Column(name="int_presence")
	public Boolean getIntPresence() {
		return this.intPresence;
	}

	public void setIntPresence(Boolean intPresence) {
		this.intPresence = intPresence;
	}


	@Column(name="int_sequence")
	public String getIntSequence() {
		return this.intSequence;
	}

	public void setIntSequence(String intSequence) {
		this.intSequence = intSequence;
	}


	@Column(name="int_stop_codons_count")
	public int getIntStopCodonsCount() {
		return this.intStopCodonsCount;
	}

	public void setIntStopCodonsCount(int intStopCodonsCount) {
		this.intStopCodonsCount = intStopCodonsCount;
	}


	@Column(name="ltr_3_length")
	public int getLtr3Length() {
		return this.ltr3Length;
	}

	public void setLtr3Length(int ltr3Length) {
		this.ltr3Length = ltr3Length;
	}


	@Column(name="ltr_5_length")
	public int getLtr5Length()  {
		return this.ltr5Length;
	}

	public void setLtr5Length(int ltr5Length) {
		this.ltr5Length = ltr5Length;
	}
	
	@Column(name="is_solo")
	public Boolean getIsSolo() {
		return this.isSolo;
	}

	public void setIsSolo(Boolean isSolo) {
		this.isSolo = isSolo;
	}
	
	
	@Column(name="is_complete")
	public Boolean getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}

	
	@Column(name="ltr_comparison_insertion_time")
	public BigDecimal getLtrComparisonInsertionTime() {
		return this.ltrComparisonInsertionTime;
	}

	public void setLtrComparisonInsertionTime(BigDecimal ltrComparisonInsertionTime) {
		this.ltrComparisonInsertionTime = ltrComparisonInsertionTime;
	}


	@Column(name="ltr_comparison_mutation_at")
	public int getLtrComparisonMutationAt() {
		return this.ltrComparisonMutationAt;
	}

	public void setLtrComparisonMutationAt(int ltrComparisonMutationAt) {
		this.ltrComparisonMutationAt = ltrComparisonMutationAt;
	}


	@Column(name="ltr_comparison_mutation_ca")
	public int getLtrComparisonMutationCa() {
		return this.ltrComparisonMutationCa;
	}

	public void setLtrComparisonMutationCa(int ltrComparisonMutationCa) {
		this.ltrComparisonMutationCa = ltrComparisonMutationCa;
	}


	@Column(name="ltr_comparison_mutation_cg")
	public int getLtrComparisonMutationCg() {
		return this.ltrComparisonMutationCg;
	}

	public void setLtrComparisonMutationCg(int ltrComparisonMutationCg) {
		this.ltrComparisonMutationCg = ltrComparisonMutationCg;
	}


	@Column(name="ltr_comparison_mutation_ct")
	public int getLtrComparisonMutationCt() {
		return this.ltrComparisonMutationCt;
	}

	public void setLtrComparisonMutationCt(int ltrComparisonMutationCt) {
		this.ltrComparisonMutationCt = ltrComparisonMutationCt;
	}


	@Column(name="ltr_comparison_nuc_distance")
	public BigDecimal getLtrComparisonNucDistance() {
		return this.ltrComparisonNucDistance;
	}

	public void setLtrComparisonNucDistance(BigDecimal ltrComparisonNucDistance) {
		this.ltrComparisonNucDistance = ltrComparisonNucDistance;
	}


	@Column(name="ltr_comparison_similarity")
	public BigDecimal getLtrComparisonSimilarity() {
		return this.ltrComparisonSimilarity;
	}

	public void setLtrComparisonSimilarity(BigDecimal ltrComparisonSimilarity) {
		this.ltrComparisonSimilarity = ltrComparisonSimilarity;
	}

	@Column(name="pbs_x")
	public int getPbsX() {
		return this.pbsX;
	}

	public void setPbsX(int pbsX) {
		this.pbsX = pbsX;
	}


	@Column(name="pbs_y")
	public int getPbsY() {
		return this.pbsY;
	}

	public void setPbsY(int pbsY) {
		this.pbsY = pbsY;
	}


	@Column(name="ppt_x")
	public int getPptX() {
		return this.pptX;
	}

	public void setPptX(int pptX) {
		this.pptX = pptX;
	}


	@Column(name="ppt_y")
	public int getPptY() {
		return this.pptY;
	}

	public void setPptY(int pptY) {
		this.pptY = pptY;
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


	@Column(name="rt_stop_codons_count")
	public int getRtStopCodonsCount() {
		return this.rtStopCodonsCount;
	}

	public void setRtStopCodonsCount(int rtStopCodonsCount) {
		this.rtStopCodonsCount = rtStopCodonsCount;
	}


	@Column(name="tsd_sequence")
	public String getTsdSequence() {
		return this.tsdSequence;
	}

	public void setTsdSequence(String tsdSequence) {
		this.tsdSequence = tsdSequence;
	}
	
	@Column(name="presence_in_sativa")
	public String getPresenceInSativa() {
		return presenceInSativa;
	}

	public void setPresenceInSativa(String presenceInSativa) {
		this.presenceInSativa = presenceInSativa;
	}

	@Transient
	@Override
	public String getType() {
		return RepeatsOrder.LTR.getLabel();
	}
	
	@Transient
	@Override
	public String getGff3Type()  {
		if ( isSolo != null && isSolo ) {
			return RepeatsGff3Order.SOLO_LTR.getLabel();
		}
		return RepeatsGff3Order.LTR.getLabel();
	}
	
	@Transient
	@Override
	public String getStructuralDesc() {
		if ( isSolo != null && isSolo ) {
			return "Solo";
		}
		else if ( isComplete != null && isComplete ) {
			return "Complete";
		}
		else {
			return "Truncated";
		}
	}
	
	@Transient
	@Override
	public void validate() throws OutOfBoundsException, IntervalFeatureException, RepeatException {
		super.validate();
		int ltr3end = this.getY() - this.ltr3Length;
		if ( ltr3Length != 0 && !isInternal(ltr3end) ) {
			throw new OutOfBoundsException("ltr 3 start: " + ltr3end);
		}
		int ltr5end = this.getX() + this.ltr5Length;
		if ( ltr5Length != 0 &&  !isInternal(ltr5end) ) {
			throw new OutOfBoundsException("ltr 5 end: " + ltr5end);
		}
		if ( pbsX != 0 && ! isInternal(pbsX) ) {
			throw new OutOfBoundsException("Pbs start: " + pbsX );
		}
		if ( pbsY != 0 && ! isInternal(pbsY) ) {
			throw new OutOfBoundsException("Pbs end: " + pbsY );
		}
		if ( pptX != 0 &&  ! isInternal(pptX) ) {
			throw new OutOfBoundsException("Ppt start: " + pptX );
		}
		if ( pptY != 0 && ! isInternal(pptY) ) {
			throw new OutOfBoundsException("Ppt end: " + pptY );
		}

	}
	
	@Transient
	@Override
	public void setAttributes(Properties attributes) throws RepeatException {
		super.setAttributes(attributes);
		if ( attributes.getProperty("is_solo") != null ) {
			if ( attributes.getProperty("is_solo").toLowerCase().equals("true")) {
				isSolo = true;
			}
		}
		if ( attributes.getProperty("is_complete") != null ) {
			if ( attributes.getProperty("is_complete").toLowerCase().equals("true")) {
				setIsComplete(true);
			}
		}
		if ( attributes.getProperty("ltr_5_length") != null ) {
			this.setLtr5Length(Integer.parseInt(attributes.getProperty("ltr_5_length")));
		}
		if ( attributes.getProperty("ltr_3_length") != null ) {
			this.setLtr3Length(Integer.parseInt(attributes.getProperty("ltr_3_length")));
		}
		if ( attributes.getProperty("pbs_x") != null ) {
			this.setPbsX(Integer.parseInt(attributes.getProperty("pbs_x")));
		}
		if ( attributes.getProperty("pbs_y") != null ) {
			this.setPbsY(Integer.parseInt(attributes.getProperty("pbs_y")));
		}
		if ( attributes.getProperty("ppt_x") != null ) {
			this.setPptX(Integer.parseInt(attributes.getProperty("ppt_x")));
		}
		if ( attributes.getProperty("ppt_y") != null ) {
			this.setPptY(Integer.parseInt(attributes.getProperty("ppt_y")));
		}
		if ( attributes.getProperty("rt_presence") != null ) {
			if ( attributes.getProperty("rt_presence").toLowerCase().equals("true")) {
				rtPresence = true;
			}
		}
		if ( attributes.getProperty("rt_sequence") != null ) {
			rtSequence = attributes.getProperty("rt_sequence");
		}
		if ( attributes.getProperty("int_presence") != null ) {
			if ( attributes.getProperty("int_presence").toLowerCase().equals("true")) {
				intPresence = true;
			}
		}
		if ( attributes.getProperty("int_sequence") != null ) {
			intSequence = attributes.getProperty("int_sequence");
		}
		if ( attributes.getProperty("ltr_comparison_similarity") != null ) {
			ltrComparisonSimilarity = 
				new BigDecimal(attributes.getProperty("ltr_comparison_similarity"));
		}
		if ( attributes.getProperty("ltr_comparison_nuc_distance") != null ) {
			ltrComparisonNucDistance = 
				new BigDecimal(attributes.getProperty("ltr_comparison_nuc_distance"));
		}
		if ( attributes.getProperty("ltr_comparison_mutation_ca") != null ) {
			ltrComparisonMutationCa = 
				Integer.parseInt(attributes.getProperty("ltr_comparison_mutation_ca"));
		}
		if ( attributes.getProperty("ltr_comparison_mutation_ct") != null ) {
			ltrComparisonMutationCt = 
				Integer.parseInt(attributes.getProperty("ltr_comparison_mutation_ct"));
		}
		if ( attributes.getProperty("ltr_comparison_mutation_at") != null ) {
			ltrComparisonMutationAt = 
				Integer.parseInt(attributes.getProperty("ltr_comparison_mutation_at"));
		}
		if ( attributes.getProperty("ltr_comparison_mutation_cg") != null ) {
			ltrComparisonMutationCg = 
				Integer.parseInt(attributes.getProperty("ltr_comparison_mutation_cg"));
		}
		if ( attributes.getProperty("ltr_comparison_insertion_time") != null ) {
			ltrComparisonInsertionTime = 
				new BigDecimal(attributes.getProperty("ltr_comparison_insertion_time"));
		}
		if ( attributes.getProperty("tsd_sequence") != null ) {
			tsdSequence = attributes.getProperty("tsd_sequence");
		}
		if ( attributes.getProperty("gc_content_5") != null ) {
			gcContent5 = new BigDecimal(attributes.getProperty("gc_content_5"));
		}
		if ( attributes.getProperty("gc_content_3") != null ) {
			gcContent3 = new BigDecimal(attributes.getProperty("gc_content_3"));
		}
		if ( attributes.getProperty("rt_stop_codons_count") != null ) {
			rtStopCodonsCount = 
				Integer.parseInt(attributes.getProperty("rt_stop_codons_count"));
		}
		if ( attributes.getProperty("int_stop_codons_count") != null ) {
			intStopCodonsCount = 
				Integer.parseInt(attributes.getProperty("int_stop_codons_count"));
		}
		if ( isSolo != null && isSolo == true && isComplete != null && isComplete == true ) {
			throw new RepeatException("LTR repeat cannot be solo AND complete");
		}
	}
	
	@Transient
	@Override
	public String extraAnnot() {
		String annot = super.extraAnnot();
		DecimalFormat percent = new DecimalFormat("###.##");
		DecimalFormat fourPointTwo = new DecimalFormat("####.##");
		if ( isSolo != null && isSolo) {
			annot += ";is_solo=true";
		}
		if ( isComplete != null && isComplete ) {
			annot += ";is_complete=true";
		}
		if ( ltr5Length != 0) {
			annot += ";ltr_5_length=" + ltr5Length;
		}
		if ( ltr3Length != 0) {
			annot += ";ltr_3_length=" + ltr3Length;
		}
		if ( pbsX != 0) {
			annot += ";pbs_x=" + pbsX;
		}
		if ( pbsY != 0) {
			annot += ";pbs_y=" + pbsY;
		}
		if ( pptX != 0) {
			annot += ";ppt_x=" + pptX;
		}
		if ( pptY != 0) {
			annot += ";ppt_y=" + pptY;
		}
		if ( rtPresence != null && rtPresence ) {
			annot += ";rt_presence=true";
		}
		if  (rtSequence != null &&  ! rtSequence.equals("") ) {
			annot += ";rt_sequence=" + rtSequence;
		}
		if ( intPresence != null && intPresence ) {
			annot += ";int_presence=true";
		}
		if  (intSequence != null &&  ! intSequence.equals("") ) {
			annot += ";int_sequence=" + intSequence;
		}
		if ( ltrComparisonSimilarity != null ) {
			annot += ";ltr_comparison_similarity=" + percent.format(ltrComparisonSimilarity);
		}
		if ( ltrComparisonNucDistance != null ) {
			annot += ";ltr_comparison_nuc_distance=" + fourPointTwo.format(ltrComparisonNucDistance);
		}
		if ( ltrComparisonMutationCa != 0) {
			annot += ";ltr_comparison_mutation_ca=" + ltrComparisonMutationCa;
		}
		if ( ltrComparisonMutationCt != 0) {
			annot += ";ltr_comparison_mutation_ct=" + ltrComparisonMutationCt;
		}
		if ( ltrComparisonMutationAt != 0) {
			annot += ";ltr_comparison_mutation_at=" + ltrComparisonMutationAt;
		}
		if ( ltrComparisonMutationCg != 0) {
			annot += ";ltr_comparison_mutation_cg=" + ltrComparisonMutationCg;
		}
		if  (ltrComparisonInsertionTime != null ) {
			annot += ";ltr_comparison_insertion_time=" + fourPointTwo.format(ltrComparisonInsertionTime);
		}
		if  (tsdSequence != null &&  ! tsdSequence.equals("") ) {
			annot += ";tsd_sequence=" + tsdSequence;
		}
		if ( gcContent5 != null ) {
			annot += ";gc_content_5=" + percent.format(gcContent5);
		}
		if ( gcContent3 != null ) {
			annot += ";gc_content_3=" + percent.format(gcContent3);
		}
		if ( rtStopCodonsCount != 0) {
			annot += ";rt_stop_codons_count=" + rtStopCodonsCount;
		}
		if ( intStopCodonsCount != 0) {
			annot += ";int_stop_codons_count=" + intStopCodonsCount;
		}
		if ( presenceInSativa != null && ! presenceInSativa.isEmpty()) {
			annot += ";presence_in_sativa=" + presenceInSativa;
		}
		return annot;
	}
	
	@Transient
	public void checkIsSolo() {
		if ( getIsSolo() != null && getIsSolo() ) {
			setIsComplete(false);
			setLtr3Length(0);		
			setLtr5Length(0);
			setPbsX(0);
			setPbsY(0);
			setPptX(0);
			setPptY(0);
			setRtPresence(false);
			setRtSequence(null);
			setIntPresence(false);
			setIntSequence(null);
			setLtrComparisonSimilarity(null);
			setLtrComparisonInsertionTime(null);
			setLtrComparisonNucDistance(null);
			setLtrComparisonMutationCa(0);
			setLtrComparisonMutationCt(0);
			setLtrComparisonMutationAt(0);
			setLtrComparisonMutationCg(0);
			setGcContent3(null);
			setGcContent5(null);
			setRtStopCodonsCount(0);
			setIntStopCodonsCount(0);
		}
	}
	
}