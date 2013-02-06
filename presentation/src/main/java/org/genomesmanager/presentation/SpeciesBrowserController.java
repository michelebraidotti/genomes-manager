package org.genomesmanager.presentation;

import java.util.List;

import javax.faces.model.DataModel;

import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.services.species.SpeciesBrowser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class SpeciesBrowserController {

		@Autowired
		private SpeciesBrowser speciesBrowser;
		private DataModel<Species> speciesDataModel; 
		
		public SpeciesBrowserController() {
		}

		public List<Species> getAll() {
			List<Species> species = speciesBrowser.getAll();
			speciesDataModel.setWrappedData(species);  
			return species;
		} 
		
		public String getFirst() {
			return speciesBrowser.getAll().get(0).getCommonName();
		}
		
		
}
