package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the pseudomolecules database table.
 * 
 */
@Entity
@Table(name="pseudomolecules", schema="sequence")
@DiscriminatorValue(value="PSEUDO")
@PrimaryKeyJoinColumn(name = "sequence_id")
@NamedQueries({
	@NamedQuery(name = "Pseudomolecule.findByNameOrdByDate", query = "SELECT p FROM Pseudomolecule p " +
	"WHERE name = :name ORDER BY p.dateCreated DESC"),
	@NamedQuery(name = "Pseudomolecule.findByNameVersion", query = "SELECT p FROM Pseudomolecule p " +
	"WHERE name = :name AND version = :version")
})
public class Pseudomolecule extends Sequence implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String version;
	private Boolean isScaffoldDerived = false;
	private Boolean isUnplaced = false;
	private List<Scaffold> scaffolds;
	public static final int SCAFFOLDS_SPACER_SIZE = 100;

	@Transient
	public static String SCAFFOLDS_SPACER() {
		StringBuilder hundredEns = new  StringBuilder();
		for ( int i = 0 ; i < SCAFFOLDS_SPACER_SIZE; i++ ) {
			hundredEns.append("N");
		}
		return hundredEns.toString();
	}
	
    public Pseudomolecule() {
    }

    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
	@Column(name="is_scaffold_derived")
	public Boolean getIsScaffoldDerived() {
		return isScaffoldDerived;
	}

	public void setIsScaffoldDerived(Boolean isScaffoldDerived) {
		this.isScaffoldDerived = isScaffoldDerived;
	}

	
	@Column(name="is_unplaced")
	public Boolean getIsUnplaced() {
		return isUnplaced;
	}

	public void setIsUnplaced(Boolean isUnplaced) {
		this.isUnplaced = isUnplaced;
	}

	
	@OneToMany(mappedBy="pseudomolecule")
	public List<Scaffold> getScaffolds() {
		return scaffolds;
	}

	public void setScaffolds(List<Scaffold> scaffolds) {
		this.scaffolds = scaffolds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		Pseudomolecule other = (Pseudomolecule) obj;
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

	@Transient
	public String getType() {
		return "Pseudomolecule";
	}

	@Transient
	public String descr() {
		return name + Sequence.NAME_SEPARATOR + version;
	}
	
	@Transient
	@Override
	public String humanName() {
		return name;
	}
	
	@Transient
	@Override
	public String getFastaHeader() {
		return super.getFastaHeader() + "|Pseudomolecule|" + name + "|version " + version ;
	}
	

}