package org.genomesmanager.presentation.repeats;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.repositories.repeats.RepeatsClassificationException;
import org.genomesmanager.repositories.repeats.RepeatsClassificationsList;
import org.springframework.beans.factory.annotation.Autowired;

public class BrowseRepeatsByClass {
	private static String NO_SELECTION = "--";
	@Autowired
	private RepeatsClassificationsList repeatsClassificationsList;
	private List<String> classifDefinitions = new ArrayList<String>();
	private String classifSelected = "";
	private List<String> superFamilies = new ArrayList<String>();
	private String selectedSuperFamily = "";
	private List<String> families = new ArrayList<String>();
	private String selectedFamily = "";
	
	public List<String> getClassifDefinitions() {
		if ( classifDefinitions.size() == 0 ) {
			classifDefinitions.add(NO_SELECTION);
			classifDefinitions.addAll(repeatsClassificationsList.getAllClassSubClassOrder());
		}
		return classifDefinitions;
	}

	public List<String> getSuperFamilies() {
		return superFamilies;
	}
	
	public List<String> getFamilies() {
		return families;
	}

	public String getSelectedSuperFamily() {
		return selectedSuperFamily;
	}

	public void setSelectedSuperFamily(String selectedSuperFamily) {
		this.selectedSuperFamily = selectedSuperFamily;
	}

	public String getSelectedFamily() {
		return selectedFamily;
	}

	public void setSelectedFamily(String selectedFamily) {
		this.selectedFamily = selectedFamily;
	}
	
	public void classifSelectedChanged(ValueChangeEvent e) throws RepeatsClassificationException {
		if (classifSelected.equals(NO_SELECTION)) {
			superFamilies = new ArrayList<String>();
			families = new ArrayList<String>();
		}
		else {
			superFamilies.add(NO_SELECTION);
			superFamilies.addAll(repeatsClassificationsList.getAllSuperfamilies(classifSelected));
			families = new ArrayList<String>();
		}
	}
	
	public void superfamilyChanged(ValueChangeEvent e) throws RepeatsClassificationException {
		if ( selectedSuperFamily.equals(NO_SELECTION) ) {
				families = new ArrayList<String>();
		}
		else {
			String definition = classifSelected + RepeatsClassification.SEPARATOR + selectedSuperFamily;
			families.add(NO_SELECTION);
			families.addAll(repeatsClassificationsList.getAllFamilies(definition));
		}
	}
	
	
}
