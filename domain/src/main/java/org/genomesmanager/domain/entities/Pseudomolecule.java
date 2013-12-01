package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
public class Pseudomolecule extends Sequence implements Serializable {
	private static final long serialVersionUID = 1L;
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

	@Transient
	public String getType() {
		return "Pseudomolecule";
	}

	
	@Transient
	@Override
	public String getFastaHeader() {
		return super.getFastaHeader() + "|Pseudomolecule|" + getName() + "|version " + getVersion() ;
	}
	

}