package org.genomesmanager.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the repeats_classification database table.
 * 
 */
@Entity
@Table(name = "repeats_classification", schema = "annotation")
@NamedQueries({
		@NamedQuery(name = "RepeatsClassification.findAll", query = "SELECT rc FROM RepeatsClassification rc "
				+ "ORDER BY rc.id.repClass, rc.id.subclass, rc.id.order, rc.id.superfamily, rc.id.family"),
		@NamedQuery(name = "RepeatsClassification.allClassSubClassOrder", query = "SELECT DISTINCT rc.id.repClass, rc.id.subclass, rc.id.order "
				+ "FROM RepeatsClassification rc "),
		@NamedQuery(name = "RepeatsClassification.allClasses", query = "SELECT DISTINCT rc.id.repClass "
				+ "FROM RepeatsClassification rc"),
		@NamedQuery(name = "RepeatsClassification.allSubclasses", query = "SELECT DISTINCT rc.id.subclass "
				+ "FROM RepeatsClassification rc WHERE rc.id.repClass = :repClass"),
		@NamedQuery(name = "RepeatsClassification.allOrdersByClassAndSubClass", query = "SELECT DISTINCT rc.id.order "
				+ "FROM RepeatsClassification rc WHERE rc.id.repClass = :repClass AND "
				+ "rc.id.subclass = :subclass"),
		@NamedQuery(name = "RepeatsClassification.allOrders", query = "SELECT DISTINCT rc.id.order "
				+ "FROM RepeatsClassification rc"),
		@NamedQuery(name = "RepeatsClassification.allSuperfamilies", query = "SELECT DISTINCT rc.id.superfamily "
				+ "FROM RepeatsClassification rc WHERE rc.id.repClass = :repClass AND "
				+ "rc.id.subclass = :subclass AND " + "rc.id.order = :order"),
		@NamedQuery(name = "RepeatsClassification.allFamilies", query = "SELECT DISTINCT rc.id.family "
				+ "FROM RepeatsClassification rc WHERE rc.id.repClass = :repClass AND "
				+ "rc.id.subclass = :subclass AND "
				+ "rc.id.order = :order AND "
				+ "rc.id.superfamily = :superfamily"), })
public class RepeatsClassification implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String repClass;
	private String subclass;
	private String order;
	private String superfamily;
	private String family;
	private List<Repeat> repeats;
	public static String SEPARATOR = ", ";

	public RepeatsClassification() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// bi-directional many-to-one association to Repeat
	@OneToMany(mappedBy = "repeatsClassification")
	public List<Repeat> getRepeats() {
		return this.repeats;
	}

	public void setRepeats(List<Repeat> repeats) {
		this.repeats = repeats;
	}

	@Column(name = "rclass")
	public String getRepClass() {
		return this.repClass;
	}

	public void setRepClass(String repClass) {
		this.repClass = repClass;
	}

	public String getSubclass() {
		return this.subclass;
	}

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	@Column(name = "rorder")
	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSuperfamily() {
		return this.superfamily;
	}

	public void setSuperfamily(String superfamily) {
		this.superfamily = superfamily;
	}

	public String getFamily() {
		return this.family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RepeatsClassification)) {
			return false;
		}
		RepeatsClassification castOther = (RepeatsClassification) other;
		return this.repClass.equals(castOther.getRepClass())
				&& this.subclass.equals(castOther.getSubclass())
				&& this.order.equals(castOther.getOrder())
				&& this.superfamily.equals(castOther.getSuperfamily())
				&& this.family.equals(castOther.getFamily());

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.repClass.hashCode();
		hash = hash * prime + this.subclass.hashCode();
		hash = hash * prime + this.order.hashCode();
		hash = hash * prime + this.superfamily.hashCode();
		hash = hash * prime + this.family.hashCode();

		return hash;
	}

	@Transient
	public String toDescString() {
		return getRepClass() + SEPARATOR + getSubclass() + SEPARATOR
				+ getOrder() + SEPARATOR + getSuperfamily() + SEPARATOR
				+ getFamily();
	}

	@Override
	public String toString() {
		return toDescString();
	}

	public static RepeatsOrder GET_REPEAT_ORDER(String classifDefinition)
			throws RepeatsClassificationException {
		String[] definition = classifDefinition
				.split(RepeatsClassification.SEPARATOR);
		/*
		 * definition[0] = class definition[1] = subclass definition[2] = order
		 */
		if (definition == null || definition.length < 3) {
			throw new RepeatsClassificationException(
					"Cannot parse classification definition "
							+ classifDefinition);
		}
		for (RepeatsOrder ro : RepeatsOrder.values()) {
			if (ro.getLabel().equals(definition[2])) {
				return ro;
			}
		}
		return null;
	}

	public static RepeatsClassification GENERATE(String name, String familyDetails)
			throws RepeatsClassificationException {
		RepeatsClassification rc = new RepeatsClassification();
		String repClass = "";
		String subclass = "";
		String order = "";
		String superFamlily = "";
		String family = "";
		if (name.equals("HELITRON") || name.equals("HELITRONS")) {
			repClass = "II";
			subclass = "II";
			order = RepeatsOrder.HEL.getLabel();
			superFamlily = "Helitron";
			family = "NA";
		} else if (name.startsWith("SOLO")) {
			repClass = "I";
			subclass = "I";
			order = RepeatsOrder.LTR.getLabel();
			superFamlily = "";
			family = "";
		} else if (name.contains("LTR")) {
			repClass = "I";
			subclass = "I";
			order = RepeatsOrder.LTR.getLabel();
			superFamlily = "";
			family = "";
		} else if (name.contains("LINE")) {
			repClass = "I";
			subclass = "I";
			order = RepeatsOrder.LINE.getLabel();
			superFamlily = "NA";
			family = "NA";
		} else if (name.contains("DNATE") || name.contains("DNA_TE")
				|| name.contains("terminal_inverted_repeat_element")) {
			repClass = "II";
			subclass = "I";
			order = RepeatsOrder.DNATE.getLabel();
			superFamlily = "";
			family = "";
		} else if (name.contains("SINE")) {
			repClass = "I";
			subclass = "I";
			order = RepeatsOrder.SINE.getLabel();
			superFamlily = "NA";
			family = "NA";
		} else if (name.contains("MITE")) {
			repClass = "II";
			subclass = "III";
			order = RepeatsOrder.MITE.getLabel();
			superFamlily = "NA";
			family = "NA";
		} else if (name.startsWith("U") || name.equals("unclassified")
				|| name.equals("unknown") || name.equals("dispersed_repeat")) {
			repClass = "UNKNOWN";
			subclass = "UNKNOWN";
			order = RepeatsOrder.UNKN.getLabel();
			superFamlily = "UNKNOWN";
			family = "UNKNOWN";
		} else {
			throw new RepeatsClassificationException("Error classifing " + name
					+ ", no suitable repeat class found");
		}
		if (family.equals("") || family.equals("NA")) {
			String[] splits = familyDetails.split(";");
			for (String s : splits) {
				if (s.startsWith("family=")) {
					family = s.replace("family=", "");
					family = family.toUpperCase();
				}
				if (s.startsWith("superfamily=")) {
					superFamlily = s.replace("superfamily=", "");
				}
			}
			if (family.equals("")) {
				throw new RepeatsClassificationException(
						"Error parsing superfamily name " + "from definition"
								+ familyDetails);
			}
			if (superFamlily.equals("")) {
				throw new RepeatsClassificationException(
						"Error parsing family name " + "from definition"
								+ familyDetails);
			}
		}
		rc.setRepClass(repClass);
		rc.setSubclass(subclass);
		rc.setOrder(order);
		rc.setSuperfamily(superFamlily);
		rc.setFamily(family);
		return rc;
	}

	// No longer used but retained to document family/superfamily nomenclature
	// public String familyToSuperfamily(String family) {
	// if ( family.toLowerCase().contains("Copia".toLowerCase()) ) {
	// return "Copia";
	// }
	// else if ( family.toLowerCase().contains("Gypsy".toLowerCase()) ) {
	// return "Gypsy";
	// }
	// else if ( family.toLowerCase().contains("ATLANTYS".toLowerCase()) ) {
	// return "Gypsy";
	// }
	// else if ( family.toLowerCase().contains("LTR/unclassified".toLowerCase())
	// ) {
	// return "Unclassified";
	// }
	// else if ( family.toLowerCase().contains("ENSPM".toLowerCase()) ) {
	// return "CACTA";
	// }
	// else if ( family.toLowerCase().contains("CACTA".toLowerCase()) ) {
	// return "CACTA";
	// }
	// else if ( family.toLowerCase().contains("OSMU".toLowerCase()) ) {
	// return "Mutator";
	// }
	// else if ( family.toLowerCase().contains("MDR".toLowerCase()) ) {
	// return "Mutator";
	// }
	// else if ( family.toLowerCase().contains("MuDR".toLowerCase()) ) {
	// return "Mutator";
	// }
	// else if ( family.toLowerCase().contains("HARB".toLowerCase()) ) {
	// return "PIF";
	// }
	// else if ( family.toLowerCase().contains("DNA/unclassified".toLowerCase())
	// ) {
	// return "Unclassified";
	// }
	// else if ( family.toLowerCase().contains("Harbinger".toLowerCase()) ) {
	// return "PIF";
	// }
	// else if ( family.toLowerCase().contains("hAT".toLowerCase()) ) {
	// return "hAT";
	// }
	// else if ( family.toLowerCase().contains("Mariner".toLowerCase()) ) {
	// return "Mariner";
	// }
	// else {
	// return null;
	// }

	public void validate() throws RepeatsClassificationException {
		if (getOrder().equals(RepeatsOrder.LINE.getLabel())) {
			if (!getRepClass().equals("I") || !getSubclass().equals("I")) {
				throw new RepeatsClassificationException(
						"LINE repeat must be Class I and Subclass I " + "was '"
								+ getRepClass() + ", " + getSubclass() + "'");
			}
		} else if (getOrder().equals(RepeatsOrder.LTR.getLabel())) {
			if (!getRepClass().equals("I") || !getSubclass().equals("I")) {
				throw new RepeatsClassificationException(
						"LTR repeat must be Class I and Subclass I " + "was '"
								+ getRepClass() + ", " + getSubclass() + "'");
			}
		} else if (getOrder().equals(RepeatsOrder.SINE.getLabel())) {
			if (!getRepClass().equals("I") || !getSubclass().equals("I")) {
				throw new RepeatsClassificationException(
						"SINE repeat must be Class I and Subclass I " + "was '"
								+ getRepClass() + ", " + getSubclass() + "'");
			}
		} else if (getOrder().equals(RepeatsOrder.HEL.getLabel())) {
			if (!getRepClass().equals("II") || !getSubclass().equals("II")) {
				throw new RepeatsClassificationException(
						"Helitron repeat must be Class II and Subclass II "
								+ "was '" + getRepClass() + ", "
								+ getSubclass() + "'");
			}
		} else if (getOrder().equals(RepeatsOrder.MITE.getLabel())) {
			if (!getRepClass().equals("II") || !getSubclass().equals("III")) {
				throw new RepeatsClassificationException(
						"MITE repeat must be Class II and Subclass III "
								+ "was '" + getRepClass() + ", "
								+ getSubclass() + "'");
			}
		} else if (getOrder().equals(RepeatsOrder.DNATE.getLabel())) {
			if (!getRepClass().equals("II") || !getSubclass().equals("I")) {
				throw new RepeatsClassificationException(
						"DNATE repeat must be Class II and Subclass I "
								+ "was '" + getRepClass() + ", "
								+ getSubclass() + "'");
			}
		} else if (getOrder().equals(RepeatsOrder.UNKN.getLabel())) {
			if (!getRepClass().equals("UNKNOWN")
					|| !getSubclass().equals("UNKNOWN")) {
				throw new RepeatsClassificationException(
						"UNKNOWN repeat must be Class UNKNOWN and Subclass UNKNOWN "
								+ "was '" + getRepClass() + ", "
								+ getSubclass() + "'");
			}
		} else {
			if (getOrder() == null || getOrder().equals("")) {
				throw new RepeatsClassificationException(
						"Repeat order cannot be blank");
			} else {
				throw new RepeatsClassificationException("Repeat order "
						+ getOrder() + " not available, " + " may be "
						+ getOrder() + "s?");
			}
		}

		if (getSuperfamily() == null || getSuperfamily().equals("")) {
			throw new RepeatsClassificationException(
					"Repeat superfamily cannot be blank");
		}

		if (getFamily() == null || getFamily().equals("")) {
			throw new RepeatsClassificationException(
					"Repeat family cannot be blank");
		}

	}
}