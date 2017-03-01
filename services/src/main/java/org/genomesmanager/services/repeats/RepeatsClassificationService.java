package org.genomesmanager.services.repeats;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsClassificationException;
import org.genomesmanager.repositories.repeats.RepeatsClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RepeatsClassificationService")
public class RepeatsClassificationService {
	@Autowired
	private RepeatsClassificationRepository repeatsClassificationRepository;
	
	public List<RepeatsClassification> getAll() {
		return repeatsClassificationRepository.getAll();
	}

	public List<String> getAllClassSubClassOrder() {
    	return repeatsClassificationRepository.getAllClassSubClassOrder();
    }
    
    public List<String> getAllSuperfamilies(String classifDefinition) throws RepeatsClassificationException {
    	return repeatsClassificationRepository.getAllSuperfamilies(classifDefinition);
    }
    
    public List<String> getAllSuperfamilies(String repClass, String subclass, String order) {
    	return repeatsClassificationRepository.getAllSuperfamilies(repClass, subclass, order);
    }
    
    public List<String> getAllFamilies(String classifDefinition) throws RepeatsClassificationException {
    	return repeatsClassificationRepository.getAllFamilies(classifDefinition);
    }
    
    public List<String> getAllFamilies(String repClass, String subclass, String order, String superfamily) {
    	return repeatsClassificationRepository.getAllFamilies(repClass, subclass, order, superfamily);
    }

	public List<String> getAllClasses() {
		return repeatsClassificationRepository.getAllClasses();
	}

	public List<String> getAllSubClasses(String repClass) {
		return repeatsClassificationRepository.getAllSubClasses(repClass);
	}

	public List<String> getAllOrders(String repClass, String subclass) {
		return repeatsClassificationRepository.getAllOrders(repClass, subclass);
	}

	public List<String> getAllOrders() {
		return repeatsClassificationRepository.getAllOrders();
	}
    
}
