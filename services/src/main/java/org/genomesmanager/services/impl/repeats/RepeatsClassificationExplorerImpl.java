package org.genomesmanager.services.impl.repeats;

import java.util.List;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.repositories.repeats.RepeatsClassificationException;
import org.genomesmanager.repositories.repeats.RepeatsClassificationsList;
import org.genomesmanager.services.repeats.RepeatsClassificationExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("RepeatsClassificationExplorer")
public class RepeatsClassificationExplorerImpl implements RepeatsClassificationExplorer {
	@Autowired
	private RepeatsClassificationsList repeatsList;
	
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAll()
	 */
	@Override
	public List<RepeatsClassification> getAll() {
		return repeatsList.getAll();
	}
	
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllClassSubClassOrder()
	 */
    @Override
	public List<String> getAllClassSubClassOrder() {
    	return repeatsList.getAllClassSubClassOrder();
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllSuperfamilies(java.lang.String)
	 */
    @Override
	public List<String> getAllSuperfamilies(String classifDefinition) throws RepeatsClassificationException {
    	return repeatsList.getAllSuperfamilies(classifDefinition);
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllSuperfamilies(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public List<String> getAllSuperfamilies(String repClass, String subclass, String order) {
    	return repeatsList.getAllSuperfamilies(repClass, subclass, order);
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllFamilies(java.lang.String)
	 */
    @Override
	public List<String> getAllFamilies(String classifDefinition) throws RepeatsClassificationException {
    	return repeatsList.getAllFamilies(classifDefinition);
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllFamilies(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public List<String> getAllFamilies(String repClass, String subclass, String order, String superfamily) {
    	return repeatsList.getAllFamilies(repClass, subclass, order, superfamily);
    }


	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllClasses()
	 */
	@Override
	public List<String> getAllClasses() {
		return repeatsList.getAllClasses();
	}


	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllSubClasses(java.lang.String)
	 */
	@Override
	public List<String> getAllSubClasses(String repClass) {
		return repeatsList.getAllSubClasses(repClass);
	}


	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllOrders(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getAllOrders(String repClass, String subclass) {
		return repeatsList.getAllOrders(repClass, subclass);
	}
	
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationExplorer#getAllOrders()
	 */
	@Override
	public List<String> getAllOrders() {
		return repeatsList.getAllOrders();
	}
    
}
