package org.genomesmanager.services.impl.repeats;

import java.util.List;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsClassificationException;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepository;
import org.genomesmanager.services.repeats.RepeatsClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("RepeatsClassificationService")
public class RepeatsClassificationServiceImpl implements RepeatsClassificationService {
	@Autowired
	private RepeatsClassificationRepository repeatsClassificationRepository;
	
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationService#getAll()
	 */
	@Override
	public List<RepeatsClassification> getAll() {
		return repeatsClassificationRepository.getAll();
	}
	
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationService#getAllClassSubClassOrder()
	 */
    @Override
	public List<String> getAllClassSubClassOrder() {
    	return repeatsClassificationRepository.getAllClassSubClassOrder();
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationService#getAllSuperfamilies(java.lang.String)
	 */
    @Override
	public List<String> getAllSuperfamilies(String classifDefinition) throws RepeatsClassificationException {
    	return repeatsClassificationRepository.getAllSuperfamilies(classifDefinition);
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationService#getAllSuperfamilies(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public List<String> getAllSuperfamilies(String repClass, String subclass, String order) {
    	return repeatsClassificationRepository.getAllSuperfamilies(repClass, subclass, order);
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationService#getAllFamilies(java.lang.String)
	 */
    @Override
	public List<String> getAllFamilies(String classifDefinition) throws RepeatsClassificationException {
    	return repeatsClassificationRepository.getAllFamilies(classifDefinition);
    }
    
    /* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationService#getAllFamilies(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public List<String> getAllFamilies(String repClass, String subclass, String order, String superfamily) {
    	return repeatsClassificationRepository.getAllFamilies(repClass, subclass, order, superfamily);
    }


	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationService#getAllClasses()
	 */
	@Override
	public List<String> getAllClasses() {
		return repeatsClassificationRepository.getAllClasses();
	}


	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationService#getAllSubClasses(java.lang.String)
	 */
	@Override
	public List<String> getAllSubClasses(String repClass) {
		return repeatsClassificationRepository.getAllSubClasses(repClass);
	}


	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationService#getAllOrders(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getAllOrders(String repClass, String subclass) {
		return repeatsClassificationRepository.getAllOrders(repClass, subclass);
	}
	
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsClassificationService#getAllOrders()
	 */
	@Override
	public List<String> getAllOrders() {
		return repeatsClassificationRepository.getAllOrders();
	}
    
}
