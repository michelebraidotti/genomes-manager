package org.genomesmanager.services.repeats;

import org.genomesmanager.bioprograms.formats.AgiExportType;
import org.genomesmanager.domain.dtos.CannotParseSpeciesDefinitionException;
import org.genomesmanager.domain.entities.*;
import org.genomesmanager.repositories.repeats.RepeatRepository;
import org.genomesmanager.repositories.sequences.ScaffoldRepository;
import org.genomesmanager.services.species.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Session Bean implementation class RepeatsExporter
 */
@Service("RepeatsExporter")
@Scope("prototype")
public class RepeatsExporter {
	private List<String> fileContent = null;
	private List<Repeat> repeats = null;
	@Autowired
	private RepeatsService repeatsService;
	@Autowired
	private RepeatRepository repeatRepository;
	@Autowired
	private SpeciesService speciesService;
	@Autowired
	private ScaffoldRepository scaffoldRepository;
	
    public RepeatsExporter() {
    }

	public List<String> getFileContent() {
		return fileContent;
	}
	
	public void loadRepeatsListBySpecies(String speciesDefinition) throws CannotParseSpeciesDefinitionException {
		Species sp = speciesService.get(speciesDefinition);
		repeats = repeatsService.getAllBySpecies(sp.getId());
	}
	
	public List<Repeat> getRepeats() {
		return this.repeats;
	}
	
	public void loadRepeatsList(Chromosome chr) {
		repeats = repeatsService.getAllByChromosome(chr.getId());
	}
	
	public void loadRepeatsList(Chromosome chr, RepeatsOrder repOrd) {
		repeats = repeatsService.getAllByChromosome(chr.getId(), repOrd);
	}
	
	public void loadRepeatsList(Chromosome chr, RepeatsClassification repClass) {
		repeats = repeatsService.getAllByChromosome(chr.getId(), repClass);
	}

	public void loadRepeatsList(Chromosome chr, RepeatsOrder repOrd, String superFamily) {
		repeats = repeatsService.getAllByChromosome(chr.getId(), repOrd, superFamily);
	}

	public void loadRepeatsList(Sequence seq) {
		repeats = repeatsService.getAllBySequence(seq.getId());
	}
	
	public void loadRepeatsList(List<Integer> repIds) {
		repeats = new ArrayList<Repeat>();
		for (Integer repId:repIds) {
			repeats.add(repeatRepository.findOne(repId));
		}
	}

	public void loadRepeatsList(Sequence seq, RepeatsClassification repClass) {
		repeats = repeatsService.getAllBySequence(seq.getId(), repClass);
	}
	
	public void loadRepeatsList(Sequence seq, RepeatsOrder repOrd, String superFamily) {
		repeats = repeatsService.getAllBySequence(seq.getId(), repOrd, superFamily);
	}
	
	public void loadRepeatsList(Sequence seq, RepeatsOrder repOrd) {
		repeats = repeatsService.getAllBySequence(seq.getId(), repOrd);
	}
	
	public int getNOfRepeats() {
		return repeats.size();
	}

	public void setFileContent(AgiExportType expType) throws RepeatsExporterException {
    	if ( expType == null ) {
    		throw new RepeatsExporterException("No export type defined");
    	}
		if ( repeats == null || repeats.size() == 0 ) { 
			throw new RepeatsExporterException("No repeats found");
		}
    	if (expType.equals(AgiExportType.FASTA)) {
    		try {
				exportFasta(repeats);
			} 
    		catch (SequenceSliceException e) {
				throw new RepeatsExporterException("SequenceSlicerException" + 
						e.getMessage());
			}
    	}
    	else if (expType.equals(AgiExportType.GFF3)) {
    		exportGff3(repeats, false);
    	}
    	else if (expType.equals(AgiExportType.GFF3PLUS)) {
    		exportGff3(repeats, true);
    	}
    	else {
    		throw new RepeatsExporterException("Export type " + expType.getLabel() + 
    				" not found");
    	}
	}
	
	public void setFileContent(AgiExportType expType,
			Boolean usingPseudomolCoordinates) throws RepeatsExporterException {
		if ( usingPseudomolCoordinates ) {
			if (expType.equals(AgiExportType.FASTA)) {
				throw new RepeatsExporterException("Exporting fasta using pseudomolecule coords not allowed");
			}
			else if (expType.equals(AgiExportType.GFF3)) {
				exportGff3WithPseudomolCoordinates(repeats, false);
	    	}
	    	else if (expType.equals(AgiExportType.GFF3PLUS)) {
	    		exportGff3WithPseudomolCoordinates(repeats, true);
	    	}
	    	else {
	    		throw new RepeatsExporterException("Export type " + expType.getLabel() + 
	    				" not found");
	    	}
		}
		else {
			setFileContent(expType);
		}
	}
	
    
    private void exportGff3(List<Repeat> repeats, Boolean extraInfo) {
    	fileContent = new ArrayList<String>();
    	fileContent.add("##gff-version 3\n");
		for (Repeat repeat:repeats) {
			if ( extraInfo) {
				fileContent.add(repeat.toGff3Line() + repeat.extraAnnot() + "\n");
			}
			else {
				fileContent.add(repeat.toGff3Line() + "\n");
			}
		}	
	}
    
    private void exportGff3WithPseudomolCoordinates(List<Repeat> repeats, 
    		Boolean extraInfo) {
    	fileContent = new ArrayList<String>();
    	fileContent.add("##gff-version 3\n");
		for (Repeat repeat:repeats) {
			Scaffold s = scaffoldRepository.findOne(repeat.getSequence().getId());
			if ( extraInfo ) {
				fileContent.add(
						repeat.toGff3WithPseudomolCoordinatesLine(s.getPseudomolecule().getName(), s.getPseudomolOffset()) + 
						repeat.extraAnnot() + "\n");
			}
			else {
				fileContent.add(
						repeat.toGff3WithPseudomolCoordinatesLine(s.getPseudomolecule().getName(), s.getPseudomolOffset()) + "\n");
			}
		}	
	}
    
    public void exportFlankingRegions(int flankingLength) {
    	fileContent = new ArrayList<String>();
    	List<String> end5Lines = new ArrayList<String>();
    	List<String> end3Lines = new ArrayList<String>();
    	for (Repeat repeat:repeats) {
    		String line = repeat.getFastaId() + "\n";
    		String line5 = line;
    		String line3 = line;
    		try {
    			int start = repeat.getX() - flankingLength;
    			int end = repeat.getY() + flankingLength;
    			if ( start <= 1 ) { 
    				start = 1;
    			}
    			if ( end >= repeat.getSequence().getLength() ) { 
    				end = repeat.getSequence().getLength(); 
    			}
				line += repeat.getSequence().getSlice(start, end);
				if ( start > 1 ) {
					line5 += repeat.getSequence().getSlice(start, repeat.getX());
					line5 += "\n";
					end5Lines.add(line5);
				}
				if ( end < repeat.getSequence().getLength()) {
					line3 += repeat.getSequence().getSlice(repeat.getY(), end);
					line3 += "\n";
					end3Lines.add(line3);
				}
			} 
    		catch (SequenceSliceException e) {
    			System.out.println(e.getMessage());
			}
	    	line += "\n";
	    	fileContent.add(line);
	    }
    	fileContent.add("#end5\n");
    	for (String entry5:end5Lines) {
    		fileContent.add(entry5);
    	}
    	fileContent.add("#end3\n");
    	for (String entry3:end3Lines) {
    		fileContent.add(entry3);
    	}
    }

	private void exportFasta(List<Repeat> repeats) throws SequenceSliceException {
		fileContent = new ArrayList<String>();
		//Sequence currentSequence = null;
		for (Repeat repeat:repeats) {
			String line = repeat.getFastaId() + "\n";
			if ( repeat.getStrandness().equals("+")) {
				line += repeat.getSequence().getSlice(repeat.getX(), repeat.getY());
			}
			else {
				line += repeat.getSequence().getReverseComplementSlice(repeat.getX(), repeat.getY());
			}
			line += "\n";
			fileContent.add(line);
		}
	}
	
}
