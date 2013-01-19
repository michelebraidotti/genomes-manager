package org.genomesmanager.repositories.jpa.repeats;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsClassificationPK;
import org.genomesmanager.domain.entities.RepeatsOrder;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepo;
import org.genomesmanager.repositories.repeats.RepeatsClassificationException;
import org.springframework.stereotype.Repository;

/**
 * Session Bean implementation class RepeatsClassificationEA
 */
@Repository("RepeatsClassificationRepo")
public class RepeatsClassificationRepoJpa implements RepeatsClassificationRepo {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public RepeatsClassificationRepoJpa() { 
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsClassificationRepo#generate(java.lang.String, java.lang.String)
	 */
    @Override
	public RepeatsClassification generate(String name, 
    		String agiDesc) throws RepeatsClassificationException {
		RepeatsClassificationPK rcPK = new RepeatsClassificationPK();
		String repClass = "";
		String subclass= "";
		String order = "";
		String superFamlily = "";
		String family = "";
		if ( name.equals("HELITRON") || name.equals("HELITRONS") ) {
			repClass = "II";
			subclass= "II";
			order = RepeatsOrder.HEL.getLabel();
			superFamlily = "Helitron";
			family = "NA";
		}
		else if ( name.startsWith("SOLO") ) {
			repClass = "I";
			subclass= "I";
			order = RepeatsOrder.LTR.getLabel();
			superFamlily = "";
			family = "";
		}
		else if ( name.contains("LTR") ) {
			repClass = "I";
			subclass= "I";
			order = RepeatsOrder.LTR.getLabel();
			superFamlily = "";
			family = "";
		}
		else if ( name.contains("LINE") ) {
			repClass = "I";
			subclass= "I";
			order = RepeatsOrder.LINE.getLabel();
			superFamlily = "NA";
			family = "NA";
		}
		else if ( name.contains("DNATE") || name.contains("DNA_TE") 
					|| name.contains("terminal_inverted_repeat_element") ) {
			repClass = "II";
			subclass= "I";
			order = RepeatsOrder.DNATE.getLabel();
			superFamlily = "";
			family = "";
		}
		else if ( name.contains("SINE") ) {
			repClass = "I";
			subclass= "I";
			order = RepeatsOrder.SINE.getLabel();
			superFamlily = "NA";
			family = "NA";
		}
		else if ( name.contains("MITE") ) {
			repClass = "II";
			subclass= "III";
			order = RepeatsOrder.MITE.getLabel();
			superFamlily = "NA";
			family = "NA";
		}
		else if ( name.startsWith("U") || name.equals("unclassified") 
				|| name.equals("unknown") || name.equals("dispersed_repeat")) {
			repClass = "UNKNOWN";
			subclass= "UNKNOWN";
			order = RepeatsOrder.UNKN.getLabel();
			superFamlily = "UNKNOWN";
			family = "UNKNOWN";
		}
		else {
			throw new RepeatsClassificationException("Error classifing " + name + 
					", no suitable repeat class found");
		}
		if ( family.equals("") || family.equals("NA")) {
			String [] splits = agiDesc.split(";");
			for ( String s:splits) {
				if (s.startsWith("family=")) {
					family = s.replace("family=", "");
					family = family.toUpperCase();
				}
				if (s.startsWith("superfamily=")) {
					superFamlily = s.replace("superfamily=", "");
				}
			}
			if ( family.equals("")) {
				throw new RepeatsClassificationException("Error parsing superfamily name " +
						"from definition" + agiDesc);
			}
			if ( superFamlily.equals("")) {
				throw new RepeatsClassificationException("Error parsing family name " +
						"from definition" + agiDesc);
			}
		}
		rcPK.setRepClass(repClass);
		rcPK.setSubclass(subclass);
		rcPK.setOrder(order);
		rcPK.setSuperfamily(superFamlily);
		rcPK.setFamily(family);
		RepeatsClassification repeatClass = new RepeatsClassification();
		repeatClass.setId(rcPK);
		return repeatClass;
	}
    
// No longer used but retained to document family/superfamily nomenclature
//    public String familyToSuperfamily(String family) {
//    	if ( family.toLowerCase().contains("Copia".toLowerCase()) ) {
//    		return "Copia";
//    	}
//    	else if ( family.toLowerCase().contains("Gypsy".toLowerCase()) ) {
//    		return "Gypsy";
//    	}
//    	else if ( family.toLowerCase().contains("ATLANTYS".toLowerCase()) ) {
//    		return "Gypsy";
//    	}
//    	else if ( family.toLowerCase().contains("LTR/unclassified".toLowerCase()) ) {
//    		return "Unclassified";
//    	}
//    	else if ( family.toLowerCase().contains("ENSPM".toLowerCase()) ) {
//    		return "CACTA";
//    	}
//    	else if ( family.toLowerCase().contains("CACTA".toLowerCase()) ) {
//    		return "CACTA";
//    	}
//    	else if ( family.toLowerCase().contains("OSMU".toLowerCase()) ) {
//    		return "Mutator";
//    	}
//    	else if ( family.toLowerCase().contains("MDR".toLowerCase()) ) {
//    		return "Mutator";
//    	}
//    	else if ( family.toLowerCase().contains("MuDR".toLowerCase()) ) {
//    		return "Mutator";
//    	}
//    	else if ( family.toLowerCase().contains("HARB".toLowerCase()) ) {
//    		return "PIF";
//    	}
//    	else if ( family.toLowerCase().contains("DNA/unclassified".toLowerCase()) ) {
//    		return "Unclassified";
//    	}
//    	else if ( family.toLowerCase().contains("Harbinger".toLowerCase()) ) {
//    		return "PIF";
//    	}
//    	else if ( family.toLowerCase().contains("hAT".toLowerCase()) ) {
//    		return "hAT";
//    	}
//    	else if ( family.toLowerCase().contains("Mariner".toLowerCase()) ) {
//    		return "Mariner";
//    	}
//    	else {
//	    	return null;
//    	}
//    }

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsClassificationRepo#delete(org.genomesmanager.domain.entities.RepeatsClassification)
	 */
	@Override
	public void delete(RepeatsClassification repeatsClass) {
		em.remove(repeatsClass);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsClassificationRepo#get(java.lang.String)
	 */
	@Override
	public RepeatsClassification get(String repClassPKDefinition) throws RepeatsClassificationException {
		String[] definition = repClassPKDefinition.split(RepeatsClassification.SEPARATOR);
		if ( definition == null || definition.length < 5 ) { 
			throw new RepeatsClassificationException("Cannot parse classification definition " + repClassPKDefinition);
		}
		RepeatsClassificationPK repClassPK = new RepeatsClassificationPK();
		repClassPK.setRepClass(definition[0]);
		repClassPK.setSubclass(definition[1]);
		repClassPK.setOrder(definition[2]);
		repClassPK.setSuperfamily(definition[3]);
		repClassPK.setFamily(definition[4]);
		return em.find(RepeatsClassification.class, repClassPK);
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsClassificationRepo#get(org.genomesmanager.domain.entities.RepeatsClassificationPK)
	 */
	@Override
	public RepeatsClassification get(RepeatsClassificationPK repClassPK) {
		return em.find(RepeatsClassification.class, repClassPK);
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsClassificationRepo#insert(org.genomesmanager.domain.entities.RepeatsClassification)
	 */
	@Override
	public void insert(RepeatsClassification repeatsClass) throws RepeatsClassificationException {
		validateKey(repeatsClass);
		em.persist(repeatsClass);
	}
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsClassificationRepo#insertIfNotExists(org.genomesmanager.domain.entities.RepeatsClassification)
	 */
	@Override
	public void insertIfNotExists(RepeatsClassification repeatsClass) throws RepeatsClassificationException {
		RepeatsClassification testRep = 
			em.find( RepeatsClassification.class, repeatsClass.getId() );
		if ( testRep == null ) {
			validateKey(repeatsClass);
			em.persist(repeatsClass);
			em.flush();
		}
	}
	
	private void validateKey(RepeatsClassification repClass) throws RepeatsClassificationException {
		RepeatsClassificationPK  repClassPK = repClass.getId();
		if ( repClassPK.getOrder().equals(RepeatsOrder.LINE.getLabel() ) ) {
			if ( ! repClassPK.getRepClass().equals("I") || ! repClassPK.getSubclass().equals("I") ) {
				throw new RepeatsClassificationException("LINE repeat must be Class I and Subclass I " +
						"was '" + repClassPK.getRepClass() + ", " + repClassPK.getSubclass() + "'");
			}
		}
		else if ( repClassPK.getOrder().equals(RepeatsOrder.LTR.getLabel() ) ) {
			if ( ! repClassPK.getRepClass().equals("I") || ! repClassPK.getSubclass().equals("I") ) {
				throw new RepeatsClassificationException("LTR repeat must be Class I and Subclass I " +
						"was '" + repClassPK.getRepClass() + ", " + repClassPK.getSubclass() + "'");
			}
		} 
		else if ( repClassPK.getOrder().equals(RepeatsOrder.SINE.getLabel() ) ) {
			if ( ! repClassPK.getRepClass().equals("I") || ! repClassPK.getSubclass().equals("I") ) {
				throw new RepeatsClassificationException("SINE repeat must be Class I and Subclass I " +
						"was '" + repClassPK.getRepClass() + ", " + repClassPK.getSubclass() + "'");
			}
		}
		else if ( repClassPK.getOrder().equals(RepeatsOrder.HEL.getLabel() ) ) {
			if ( ! repClassPK.getRepClass().equals("II") || ! repClassPK.getSubclass().equals("II") ) {
				throw new RepeatsClassificationException("Helitron repeat must be Class II and Subclass II " +
						"was '" + repClassPK.getRepClass() + ", " + repClassPK.getSubclass() + "'");
			}
		}
		else if ( repClassPK.getOrder().equals(RepeatsOrder.MITE.getLabel() ) ) {
			if ( ! repClassPK.getRepClass().equals("II") || ! repClassPK.getSubclass().equals("III") ) {
				throw new RepeatsClassificationException("MITE repeat must be Class II and Subclass III " +
						"was '" + repClassPK.getRepClass() + ", " + repClassPK.getSubclass() + "'");
			}
		}
		else if ( repClassPK.getOrder().equals(RepeatsOrder.DNATE.getLabel() ) ) {
			if ( ! repClassPK.getRepClass().equals("II") || ! repClassPK.getSubclass().equals("I") ) {
				throw new RepeatsClassificationException("DNATE repeat must be Class II and Subclass I " +
						"was '" + repClassPK.getRepClass() + ", " + repClassPK.getSubclass() + "'");
			}
		}
		else if ( repClassPK.getOrder().equals(RepeatsOrder.UNKN.getLabel() ) ) {
			if ( ! repClassPK.getRepClass().equals("UNKNOWN") || ! repClassPK.getSubclass().equals("UNKNOWN") ) {
				throw new RepeatsClassificationException("UNKNOWN repeat must be Class UNKNOWN and Subclass UNKNOWN " +
						"was '" + repClassPK.getRepClass() + ", " + repClassPK.getSubclass() + "'");
			}
		}
		else {
			if ( repClassPK.getOrder() == null || repClassPK.getOrder().equals("") ) {
				throw new RepeatsClassificationException("Repeat order cannot be blank");
			}
			else {
				throw new RepeatsClassificationException("Repeat order " + repClassPK.getOrder() + " not available, " + 
						" may be " + repClassPK.getOrder() + "s?" );
			}
		}
		
		if ( repClassPK.getSuperfamily() == null || repClassPK.getSuperfamily().equals("") ) {
			throw new RepeatsClassificationException("Repeat superfamily cannot be blank");
		}
		
		if ( repClassPK.getFamily() == null || repClassPK.getFamily().equals("") ) {
			throw new RepeatsClassificationException("Repeat family cannot be blank");
		}
		
	}

	/* (non-Javadoc)
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatsClassificationRepo#getRepeatOrder(java.lang.String)
	 */
	@Override
	public RepeatsOrder getRepeatOrder(String classifDefinition)
			throws RepeatsClassificationException {
		String[] definition = classifDefinition.split(RepeatsClassification.SEPARATOR);
		/*
		 * definition[0] = class
		 * definition[1] = subclass
		 * definition[2] = order
		 */
    	if ( definition == null || definition.length < 3 ) { 
    		throw new RepeatsClassificationException("Cannot parse classification definition " + classifDefinition);
    	}
    	for (RepeatsOrder ro:RepeatsOrder.values() ) {
    		if ( ro.getLabel().equals(definition[2])) {
    			return ro;
    		}
    	}
    	return null;
	}

}
