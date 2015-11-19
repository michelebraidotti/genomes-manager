package org.genomesmanager.domain.dtos;


import org.genomesmanager.domain.dtos.CannotParseSpeciesDefinitionException;

public class SpeciesDefinition {
	public String genus = "";
	public String species = "";
	public String subspecies = "";
	
	public SpeciesDefinition(String speciesDefinition) throws CannotParseSpeciesDefinitionException {
		if ( speciesDefinition != null ) {
			String [] spDef = speciesDefinition.split("\\s+");
			if ( spDef.length < 2 ) {
				throw new CannotParseSpeciesDefinitionException("Cannot guess genus, species, subspecies " +
						"from species definition '" + speciesDefinition + "'");
			}
			if ( spDef.length == 2 ) {
				genus = spDef[0];
				species = spDef[1];
			}
			else if ( spDef.length == 3 ) {
				genus = spDef[0];
				species = spDef[1];
				subspecies = spDef[2];
			}
			else {
				throw new CannotParseSpeciesDefinitionException("Cannot guess genus, species, subspecies " +
						"from species definition " + speciesDefinition);
			}
		}
	}
}
