package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.genomesmanager.common.formats.Gff3Line;
import org.hibernate.annotations.Type;

/*
 * 
 * The persistent class for the repeats database table.
 * 
 */
@Entity
@Table(name = "repeats", schema = "annotation")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "repeats_order", discriminatorType = DiscriminatorType.STRING, length = 10)
@NamedQueries({
		@NamedQuery(name = "Repeat.findAll", query = "SELECT r FROM Repeat r"),
		@NamedQuery(name = "Repeat.findAllWithParents", query = "SELECT  r.id, COUNT(s.id) "
				+ "FROM Repeat r, Repeat s "
				+ "WHERE r.sequence.id = s.sequence.id AND r.id != s.id "
				+ "AND r.x >= s.x AND r.y <= s.y "
				+ "GROUP BY r.id HAVING COUNT(s.id) > 0"),
		@NamedQuery(name = "Repeat.findAllBySequence", query = "SELECT r FROM Repeat r "
				+ "WHERE r.sequence.id = :seqId ORDER BY r.x"),
		@NamedQuery(name = "Repeat.findInRange", query = "SELECT r FROM Repeat r "
				+ "WHERE r.sequence.id = :seqId AND r.x >= :start AND r.y <= :end "
				+ "ORDER BY r.x"),
		@NamedQuery(name = "Repeat.findByClassAndSequence", query = "SELECT r FROM Repeat r JOIN r.repeatsClassification rc "
				+ "WHERE r.sequence.id = :seqId AND rc.id = :repClassId "
				+ "ORDER BY r.x"),
		@NamedQuery(name = "Repeat.findAllBySpecies", query = "SELECT r FROM Repeat r JOIN r.sequence s JOIN s.chromosome c "
				+ "JOIN c.species sp " + "WHERE sp.id = :speciesId"),
		@NamedQuery(name = "Repeat.findAllByChromosome", query = "SELECT r FROM Repeat r JOIN r.sequence s JOIN s.chromosome c "
				+ "WHERE c.id = :chrId"),
		@NamedQuery(name = "Repeat.findByClassAndChromosome", query = "SELECT r FROM Repeat r JOIN r.sequence s JOIN s.chromosome c "
				+ "JOIN r.repeatsClassification rc "
				+ "WHERE c.id = :chrId AND rc.id = :repClassId"
				+ "ORDER BY r.x"),
		@NamedQuery(name = "Repeat.findByCandidateKey", query = "SELECT r FROM Repeat r "
				+ "WHERE r.sequence.id = :seqId AND r.x = :start AND r.y = :end"),
		@NamedQuery(name = "Repeat.count", query = "SELECT count(r.id) FROM Repeat r"),
		@NamedQuery(name = "Repeat.findPotentiallyNested", query = "SELECT r FROM Repeat r, Repeat s "
				+ "WHERE r.id != s.id AND r.sequence.id = s.sequence.id "
				+ "AND s.x <= r.x AND r.y <= s.y"),
		@NamedQuery(name = "Repeat.findParentSlow", query = "SELECT r FROM Repeat r "
				+ "WHERE r.id != :id AND r.sequence.id = :seqId AND r.x <= :x AND r.y >= :y "
				+ "ORDER BY r.y - r.x + 1"),
		@NamedQuery(name = "Repeat.findParent", query = "SELECT s FROM Repeat r, Repeat s "
				+ "WHERE r.sequence.id = s.sequence.id AND r.id != s.id "
				+ "AND r.id = :id AND r.x >= s.x AND r.y <= s.y "
				+ "ORDER BY s.y - s.x + 1"),
		@NamedQuery(name = "Repeat.countChildren", query = "SELECT s.sequence.id, s.id, COUNT(r.id) "
				+ "FROM Repeat r, Repeat s WHERE r.sequence.id = s.sequence.id "
				+ "AND r.id != s.id AND r.x >= s.x AND r.y <= s.y "
				+ "AND s.id = :id GROUP BY s.sequence.id, s.id") })
@SqlResultSetMapping(name = "CountByClassif", columns = {
		@ColumnResult(name = "rclass"), @ColumnResult(name = "subclass"),
		@ColumnResult(name = "rorder"), @ColumnResult(name = "superfamily"),
		@ColumnResult(name = "count_repeats"), @ColumnResult(name = "sum_nucl") })
@NamedNativeQueries({
		@NamedNativeQuery(name = "Repeat.countRepeatsAndBasesByChromosome", query = "select rclass, subclass, rorder, superfamily, count(repeats.id) as count_repeats, sum(y-x+1) as sum_nucl "
				+ "from (repeats_classification "
				+ "left join repeats on (rclass = repeats_class and subclass = repeats_subclass and rorder = repeats_order and superfamily = repeats_superfamily and family = repeats_family )) "
				+ "join sequences on (repeats.sequence_id = sequences.id and repeats.parent_repeat_id is null) "
				+ "left join chromosomes on (sequences.chromosome_id = chromosomes.id) "
				+ "group by chromosomes.id, rclass, subclass, rorder, superfamily "
				+ "having chromosomes.id = ?", resultSetMapping = "CountByClassif"),
		@NamedNativeQuery(name = "Repeat.countRepeatsAndBases", query = "select rclass, subclass, rorder, superfamily, count(repeats.id) as count_repeats, sum(y-x+1) as sum_nucl "
				+ "from repeats_classification left join repeats on (rclass = repeats_class and subclass = repeats_subclass and rorder = repeats_order and superfamily = repeats_superfamily and family = repeats_family and repeats.parent_repeat_id is null) "
				+ "group by rclass, subclass, rorder, superfamily", resultSetMapping = "CountByClassif") })
public class Repeat extends IntervalFeature implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int containedElementsCount;
	private Calendar dateCreated;
	private Calendar dateModified;
	private BigDecimal gcContent;
	private String strandness;
	private int x;
	private int y;
	private Sequence sequence;
	private String repeatText;
	private RepeatsClassification repeatsClassification;
	private Repeat parent;
	private List<Repeat> children;
	private Repeat similar;
	private List<Repeat> similars;
	private String notes;

	public Repeat() {
	}

	@Id
	@SequenceGenerator(name = "REPEATS_ID_GENERATOR", sequenceName = "annotation.repeats_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPEATS_ID_GENERATOR")
	@Column(insertable = false, updatable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "contained_elements_count")
	public int getContainedElementsCount() {
		return this.containedElementsCount;
	}

	public void setContainedElementsCount(int containedElementsCount) {
		this.containedElementsCount = containedElementsCount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	public Calendar getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified")
	public Calendar getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Calendar dateModified) {
		this.dateModified = dateModified;
	}

	@Column(name = "gc_content")
	public BigDecimal getGcContent() {
		return this.gcContent;
	}

	public void setGcContent(BigDecimal gcContent) {
		this.gcContent = gcContent;
	}

	@Column(name = "strandness", columnDefinition = "bpchar(1)")
	public String getStrandness() {
		return this.strandness;
	}

	public void setStrandness(String strandness)
			throws IntervalFeatureException {
		super.validateStrandness(strandness);
		this.strandness = strandness;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) throws IntervalFeatureException {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) throws IntervalFeatureException {
		this.y = y;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "repeat_text")
	@Type(type = "org.hibernate.type.TextType")
	public String getRepeatText() {
		return repeatText;
	}

	public void setRepeatText(String repeatText) {
		this.repeatText = repeatText;
	}

	// bi-directional many-to-one association to Chromosome
	@ManyToOne
	public Sequence getSequence() {
		return this.sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	// bi-directional many-to-one association to RepeatsClassification
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "repeats_class", referencedColumnName = "rclass"),
			@JoinColumn(name = "repeats_subclass", referencedColumnName = "subclass"),
			@JoinColumn(name = "repeats_order", referencedColumnName = "rorder"),
			@JoinColumn(name = "repeats_superfamily", referencedColumnName = "superfamily"),
			@JoinColumn(name = "repeats_family", referencedColumnName = "family"), })
	public RepeatsClassification getRepeatsClassification() {
		return this.repeatsClassification;
	}

	public void setRepeatsClassification(
			RepeatsClassification repeatsClassification) {
		this.repeatsClassification = repeatsClassification;
	}

	// bi-directional many-to-one association to Repeat
	@ManyToOne
	@JoinColumn(name = "parent_repeat_id")
	public Repeat getParent() {
		return this.parent;
	}

	public void setParent(Repeat parent) {
		this.parent = parent;
	}

	// bi-directional many-to-one association to Repeat
	@OneToMany(mappedBy = "parent")
	public List<Repeat> getChildren() {
		return this.children;
	}

	public void setChildren(List<Repeat> children) {
		this.children = children;
	}

	// bi-directional many-to-one association to Repeat
	@ManyToOne
	@JoinColumn(name = "similar_repeat_id")
	public Repeat getSimilar() {
		return this.similar;
	}

	public void setSimilar(Repeat similar) {
		this.similar = similar;
	}

	// bi-directional many-to-one association to Repeat
	@OneToMany(mappedBy = "similar")
	public List<Repeat> getSimilars() {
		return this.similars;
	}

	public void setSimilars(List<Repeat> similars) {
		this.similars = similars;
	}

	@Transient
	public String getType() {
		return null;
	}

	@Transient
	public String getStructuralDesc() {
		return "";
	}

	@Transient
	public void validate() throws OutOfBoundsException,
			IntervalFeatureException, RepeatException {
		if (sequence == null) {
			throw new RepeatException("Sequence must be specified");
		}
		int seqLen = sequence.getLength();
		if (x <= 0 || x > seqLen) {
			throw new OutOfBoundsException("Repeat start " + x
					+ " out of sequence boundaries: 1, " + seqLen);
		}
		if (y <= 0 || y > seqLen) {
			throw new OutOfBoundsException("Repeat end " + y
					+ " out of sequence boundaries: 1, " + seqLen);
		}
		if (x >= y) {
			throw new RepeatException("Repeat start " + x
					+ " greater or equal than repeat end " + y);
		}
	}

	@Transient
	public Boolean isOutdated() {
		if (sequence != null && sequence.getSupersededBy() != null) {
			return true;
		}
		return false;
	}

	@Transient
	public void setAttributes(Properties attributes) throws RepeatException {
		if (attributes.getProperty("notes") != null) {
			this.notes = attributes.getProperty("notes");
		}
	}

	@Transient
	public String getGff3Type() {
		return "";
	}

	@Transient
	public String toGff3Line() {
		return this.buildGff3Annotation().toString();
	}

	@Transient
	public String toGff3WithPseudomolCoordinatesLine(String chrName, Long offset) {
		Gff3Line gff3 = buildGff3Annotation();
		gff3.toPseudomolCoords(chrName, Integer.parseInt(offset + ""));
		return gff3.toString();
	}

	@Transient
	public String extraAnnot() {
		String annot = "";
		annot += ";rclass=" + repeatsClassification.getRepClass()
				+ ";subclass=" + repeatsClassification.getSubclass()
				+ ";rorder=" + repeatsClassification.getOrder()
				+ ";superfamily=" + repeatsClassification.getSuperfamily()
				+ ";family=" + repeatsClassification.getFamily();
		if (notes != null && !notes.isEmpty()) {
			annot += ";notes=" + notes;
		}
		return annot;
	}

	private Gff3Line buildGff3Annotation() {
		Gff3Line gff3 = new Gff3Line();
		gff3.setSeqId(sequence.humanName());
		gff3.setSource("agi_genomes_db");
		gff3.setType(getGff3Type());
		gff3.setStart(x);
		gff3.setEnd(y);
		gff3.setScore(".");
		gff3.setStrand(strandness);
		gff3.setPhase(".");
		gff3.setAttribId(id + "");
		return gff3;
	}

	@Transient
	public String getFastaId() {
		String line = ">" + id + "|" + getType() + "|" + sequence.toString()
				+ "|" + x + "|" + y + "|" + strandness;
		return line;
	}

	@Transient
	public String getSequenceText() throws SequenceSliceException {
		if (strandness.equals("+")) {
			return this.getSequence().getSlice(x, y);
		} else {
			return this.getSequence().getReverseComplementSlice(x, y);
		}
	}

	@PrePersist
	public void setCreateDefaults() {
		if (this.dateCreated == null) {
			this.dateCreated = Calendar.getInstance();
			this.dateModified = Calendar.getInstance();
		}
	}

	@PreUpdate
	public void setUpdateDefaults() {
		this.dateModified = Calendar.getInstance();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Repeat == false) {
			return false;
		}

		if (this == obj) {
			return true;
		}
		Repeat other = (Repeat) obj;
		return new EqualsBuilder()
				.append(this.getX(), other.getX())
				.append(this.getY(), other.getY())
				.append(this.getSequence(), other.getSequence())
				.append(this.getRepeatsClassification(),
						other.getRepeatsClassification()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getX()).append(this.getY())
				.append(this.getSequence())
				.append(this.getRepeatsClassification()).hashCode();
	}

}