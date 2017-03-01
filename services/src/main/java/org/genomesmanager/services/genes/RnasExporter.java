package org.genomesmanager.services.genes;

import org.genomesmanager.bioprograms.formats.AgiExportType;
import org.genomesmanager.domain.dtos.CannotParseSpeciesDefinitionException;
import org.genomesmanager.domain.entities.Chromosome;
import org.genomesmanager.domain.entities.Rna;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Species;
import org.genomesmanager.repositories.genes.RnaRepository;
import org.genomesmanager.repositories.sequences.ScaffoldRepository;
import org.genomesmanager.repositories.sequences.SequenceRepository;
import org.genomesmanager.services.species.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("RnasExporter")
@Scope("prototype")
public class RnasExporter {
    private List<Rna> rnas;
    private List<String> fileContent = null;
    @Autowired
    private RnaRepository rnaRepository;
    @Autowired
	private SpeciesService speciesService;
    @Autowired
	private SequenceRepository sequenceRepository;
	@Autowired
	private ScaffoldRepository scaffoldRepository;
    
    public RnasExporter() {
    }
    
	public List<String> getFileContent() {
		return fileContent;
	}
	
	public void setRnasList(Chromosome chr) {
		rnas = rnaRepository.findBySequenceChromosome(chr);
	}

	public void setRnasList(Species sp) {
		rnas = rnaRepository.findBySequenceChromosomeSpecies(sp);
		
	}

	public void setRnasList(String speciesDefinition) throws CannotParseSpeciesDefinitionException {
		Species sp = speciesService.get(speciesDefinition);
		rnas = rnaRepository.findBySequenceChromosomeSpecies(sp);
	}
	
	public void setFileContent(AgiExportType expType,
			Boolean usingPseudomolCoordinates) throws RnasExporterException {
		if ( expType == null ) {
    		throw new RnasExporterException("No export type defined");
    	}
		if ( rnas == null || rnas.size() == 0 ) { 
			throw new RnasExporterException("No genes found");
		}
		
    	if (expType.equals(AgiExportType.FASTA)) {
    		throw new RnasExporterException("Not yet implemented");
    	}
    	else if (expType.equals(AgiExportType.GFF3)) {
    		exportGff3(rnas, false, usingPseudomolCoordinates);
    	}
    	else if (expType.equals(AgiExportType.GFF3PLUS)) {
    		exportGff3(rnas, true, usingPseudomolCoordinates);
    	}
    	else {
    		throw new RnasExporterException("Export type " + expType.getLabel() + 
    				" not found");
    	}
	}

	private void exportGff3(List<Rna> rnas, boolean extraInfo,
			Boolean usingPseudomolCoordinates) {
		fileContent = new ArrayList<String>();
    	fileContent.add("##gff-version 3\n");
    	for (Rna rna:rnas) {
    		if ( extraInfo && usingPseudomolCoordinates ) {
				Scaffold s = scaffoldRepository.findOne(rna.getSequence().getId());
				fileContent.add(
					rna.toGff3WithPseudomolCoordinatesLine(s.getPseudomolecule().getName(), s.getPseudomolOffset()) +
					rna.extraAnnot() + "\n");

    		}
    		else if ( ! extraInfo && usingPseudomolCoordinates ) {
				Scaffold s = scaffoldRepository.findOne(rna.getSequence().getId());
				fileContent.add(
					rna.toGff3WithPseudomolCoordinatesLine(s.getPseudomolecule().getName(), s.getPseudomolOffset()) + "\n");
    		}
    		else if ( extraInfo && ! usingPseudomolCoordinates ) {
    			fileContent.add(rna.toGff3Line() + rna.extraAnnot() + "\n");
    		}
    		else if ( ! extraInfo && ! usingPseudomolCoordinates ) {
    			fileContent.add(rna.toGff3Line() + "\n");
    		}
    		
    	}
	}
	
}
