package org.genomesmanager.services.impl.repeats;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.services.repeats.RepeatsClassificationExplorer;
import org.genomesmanager.services.repeats.RepeatsClassificationExplorerException;
import org.springframework.stereotype.Service;

@Service("RepeatsClassificationExplorer")
public class RepeatsClassificationExplorerImpl implements RepeatsClassificationExplorer {
	@PersistenceContext
	private EntityManager em;
	private Query q;
	
    public RepeatsClassificationExplorerImpl() { }

	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<RepeatsClassification> getAll() {
		q = em.createNamedQuery("RepeatsClassification.findAll");
		List<RepeatsClassification> res = q.getResultList();
		return res;
	}
	
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllClassSubClassOrder()
	 */
    @Override
	@SuppressWarnings("unchecked")
	public List<String> getAllClassSubClassOrder() {
    	q = em.createNamedQuery("RepeatsClassification.allClassSubClassOrder");
    	List<Object[]> res = q.getResultList();
    	List<String> names = new ArrayList<String>();
    	for(Object [] line:res) {
			String repClass = (String)line[0];
			String subclass = (String)line[1];
			String order = (String)line[2];
			names.add(repClass + RepeatsClassification.SEPARATOR + subclass + RepeatsClassification.SEPARATOR + order);
    	}
    	return names;
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllSuperfamilies(java.lang.String)
	 */
    @Override
	public List<String> getAllSuperfamilies(String classifDefinition) throws RepeatsClassificationExplorerException {
    	String[] definition = classifDefinition.split(RepeatsClassification.SEPARATOR);
    	if ( definition == null || definition.length < 3 ) { 
    		throw new RepeatsClassificationExplorerException("Cannot parse classification definition " + classifDefinition);
    	}
    	return getAllSuperfamilies(definition[0], definition[1], definition[2]);
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllSuperfamilies(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	@SuppressWarnings("unchecked")
	public List<String> getAllSuperfamilies(String repClass, String subclass, String order) {
    	q = em.createNamedQuery("RepeatsClassification.allSuperfamilies");
    	q.setParameter("repClass", repClass);
    	q.setParameter("subclass", subclass);
    	q.setParameter("order", order);
    	return q.getResultList();
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllFamilies(java.lang.String)
	 */
    @Override
	public List<String> getAllFamilies(String classifDefinition) throws RepeatsClassificationExplorerException {
    	String[] definition = classifDefinition.split(RepeatsClassification.SEPARATOR);
    	if ( definition == null || definition.length < 4 ) { 
    		throw new RepeatsClassificationExplorerException("Cannot parse classification definition " + classifDefinition);
    	}
    	return getAllFamilies(definition[0], definition[1], definition[2], definition[3]);
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllFamilies(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	@SuppressWarnings("unchecked")
	public List<String> getAllFamilies(String repClass, String subclass, String order, String superfamily) {
    	q = em.createNamedQuery("RepeatsClassification.allFamilies");
       	q.setParameter("repClass", repClass);
    	q.setParameter("subclass", subclass);
    	q.setParameter("order", order);
    	q.setParameter("superfamily", superfamily);
    	return q.getResultList();
    }


	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllClasses()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAllClasses() {
		q = em.createNamedQuery("RepeatsClassification.allClasses");
		return q.getResultList();
	}


	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllSubClasses(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAllSubClasses(String repClass) {
		q = em.createNamedQuery("RepeatsClassification.allSubclasses");
       	q.setParameter("repClass", repClass);
		return q.getResultList();
	}


	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllOrders(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAllOrders(String repClass, String subclass) {
		q = em.createNamedQuery("RepeatsClassification.allOrdersByClassAndSubClass");
       	q.setParameter("repClass", repClass);
    	q.setParameter("subclass", subclass);
		return q.getResultList();
	}
	
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllOrders()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAllOrders() {
		q = em.createNamedQuery("RepeatsClassification.allOrders");
		return q.getResultList();
	}
    
}
