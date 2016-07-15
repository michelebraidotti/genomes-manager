package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.biojava3.core.sequence.DNASequence;
import org.hibernate.annotations.Type;


/**
 * The persistent class for the sequences database table.
 * 
 */
@Entity
@Table(name="sequences", schema="sequence")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING,length=15)
@NamedQueries({
	@NamedQuery(name = "Sequence.findAll", query = "SELECT s FROM Sequence s"),
	@NamedQuery(name = "Sequence.findAllByChr", 
			query = "SELECT s FROM Sequence s " +
					"WHERE chromosome.id = :chrId"),
	@NamedQuery(name = "Sequence.findAllBySpecies", 
			query = "SELECT s FROM Sequence s JOIN s.chromosome c JOIN c.species sp " +
					"WHERE sp.id = :speciesId"),
	@NamedQuery(name = "Sequence.findByNameOrdByDate", 
			query = "SELECT s FROM Sequence s " +
					"WHERE name = :name ORDER BY s.dateCreated DESC"),
	@NamedQuery(name = "Sequence.findByNameVersion", 
			query = "SELECT s FROM Sequence s " +
					"WHERE name = :name AND version = :version")
})
public class Sequence implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Calendar dateCreated;
	private Calendar dateModified;
	private String name = "";
	private String version = "";
	private String sequenceText;
	private int length;
	private Chromosome chromosome;
	private Sequence supersededBy;
	private List<Repeat> repeats = new ArrayList<Repeat>();
	private List<Gene> genes = new ArrayList<Gene>();
	private List<Rna> rnas = new ArrayList<Rna>();
	private List<Snp> snps = new ArrayList<Snp>();
	public static String NAME_SEPARATOR = ", ";

    public Sequence() {
    }


    @Id
	@SequenceGenerator(name="SEQUENCES_ID_GENERATOR", sequenceName="sequence.sequences_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCES_ID_GENERATOR")
	@Column(insertable=false, updatable=false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="name")
    public String getName() {
		return name;
	}

    public void setName(String name) {
		this.name = name;
	}


    @Column(name="version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="date_created")
	public Calendar getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="date_modified")
	public Calendar getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Calendar dateModified) {
		this.dateModified = dateModified;
	}


    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name="sequence_text")
    @Type(type = "org.hibernate.type.TextType")
	public String getSequenceText() {
		return this.sequenceText;
	}

	public void setSequenceText(String sequenceText) {
		this.sequenceText = sequenceText;
	}


	@Column(name="length")
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	
	//bi-directional many-to-one association to Individual
    @ManyToOne
	public Chromosome getChromosome() {
		return this.chromosome;
	}

	public void setChromosome(Chromosome chromosome) {
		this.chromosome = chromosome;
	}
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "superseded_by")
	public Sequence getSupersededBy() {
		return supersededBy;
	}

	public void setSupersededBy(Sequence supersededBy) {
		this.supersededBy = supersededBy;
	}
	
	//bi-directional many-to-one association to Repeat
	@OneToMany(mappedBy="sequence")
	public List<Repeat> getRepeats() {
		return this.repeats;
	}

	public void setRepeats(List<Repeat> repeats) {
		this.repeats = repeats;
	}
	
	
	//bi-directional many-to-one association to Repeat
	@OneToMany(mappedBy="sequence")
	public List<Gene> getGenes() {
		return this.genes;
	}

	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}
	
	@OneToMany(mappedBy="sequence")
	public List<Rna> getRnas() {
		return rnas;
	}

	public void setRnas(List<Rna> rnas) {
		this.rnas = rnas;
	}
	
	@OneToMany(mappedBy="sequence")
	public List<Snp> getSnps() {
		return snps;
	}

	public void setSnps(List<Snp> snps) {
		this.snps = snps;
	}
	
	@Transient
	public String getType() {
		return "";
	}
	
	@Transient
	public String getFastaHeader() {
		return ">" + id;
	}
	
	@Override
	public String toString() {
		return descr();
	}
	
	@PrePersist
	public void setCreateDefaults() {
		if ( this.dateCreated == null ) {
			this.dateCreated = Calendar.getInstance();
			this.dateModified = Calendar.getInstance();
		}
	}
	
	@PreUpdate
	public void setUpdateDefaults() {
		this.dateModified = Calendar.getInstance();
	}
	
	@Transient
	public String getMaskedSequence() throws SequenceSliceException {
		StringBuffer maskedSeq = new StringBuffer(sequenceText);
		for (Repeat r:this.getRepeats()) {
			if ( r.getY() <= 0 || r.getY() > sequenceText.length() ) {
				throw new SequenceSliceException("Repeat end (" + r.getY() + ") out of sequence bounds");
			}
			if ( r.getX() <= 0 || r.getX() > sequenceText.length() ) {
				throw new SequenceSliceException("Repeat start (" + r.getX() + ") out of sequence bounds");
			}
			StringBuffer ens = new StringBuffer();
			for ( int i = 0; i<= r.getY() - r.getX(); i++ ) {
				ens.append("N");
			}
			try {
				maskedSeq.replace(r.getX() - 1, r.getY(), ens.toString());
			}
			catch (StringIndexOutOfBoundsException ex) {
				throw new SequenceSliceException("Wrapped StringIndexOutOfBoundsException: " + ex.getMessage());
			}
		}
		return maskedSeq.toString();
    }


	@Transient
	public String getSlice(int start, int end) throws SequenceSliceException {
		int sliceLength = end - start;
		if ( sliceLength <= 0 ) {
			throw new SequenceSliceException(" Start " + start + " >  than end " + end );
		}
		if ( start <= 0 ) {
			throw new SequenceSliceException(" Start " + start + " <= 0");
		}
		if ( end > this.length ) {
			throw new SequenceSliceException(" End " + end + " > " + this.length);
		}
    	return sequenceText.substring(start -1 , end);
    }
	
	@Transient
	public String getReverseComplementSlice(int start, int end) throws SequenceSliceException {
		DNASequence sequence = new DNASequence(getSlice(start, end));
		return sequence.getReverseComplement().getViewedSequence().getSequenceAsString();
    }
	
	@Transient
	public String descr() {
		return name + Sequence.NAME_SEPARATOR + version;
	}
	
	@Transient
	public String humanName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();		
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sequence other = (Sequence) obj;
		if (id != 0 ) {
			if ( other.id == id )
				return true;
			else 
				return false;
		}
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	
}